package it.cnr.igsg.linkoln.reference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import it.cnr.igsg.linkoln.Linkoln;
import it.cnr.igsg.linkoln.service.impl.Util;

public class HudocRepo {

	//ECLI:CE:ECHR:1984:0514REP000811177	8111/77		GBR	HORFIELD v. THE UNITED KINGDOM
	//ECLI:CE:ECHR:1974:0530DEC000507871	5078/71		DEU;ITA	K. v. ITALY AND THE FEDERAL REPUBLIC OF GERMANY
	//ECLI:CE:ECHR:1974:0530DEC000541672	5416/72		AUT	X. v. AUSTRIA
	//ECLI:CE:ECHR:1974:0531DEC000576772	5767/72;5922/72;5929/72;5930/72;5931/72;5953/72;5954/72;5955/72;5956/72;5957/72;5984/73;5985/73;5986/73;5987/73;5988/73;6011/73		AUT	16 AUSTRIAN COMMUNES (1) AND SOME OF THEIR COUNCILLORS v. AUSTRIA
	//ECLI:CE:ECHR:2002:0514JUD002287693	22876/93	2002-05-14	TUR	SEMSI ONEN v. TURKEY
	
	/*
	 * Mappe possibili:
	 * 
	 * - APPLICATION NUMERO-ANNO (su 4 cifre), vanno espanse tutte le cause riunite separate da punto e virgola -> ECLI
	 * 
	 * - APPLICANT (normalizzato, senza accenti etc., esclusi tutti quelli minori di una certa lunghezza, escluso AND OTHERS) -> ECLI  [SEGNALARE I DOPPIONI !!! ]
	 * 
	 * - DATA -> ECLI  [EVENTUALMENTE MESCOLARE DATA con DEFENDANT per disambiguare]
	 * 
	 * Le mappe si possono incrociare (soprattutto le ultime due).
	 * 
	 * 
	 */
	
	private static final String hudocFileName = "data/hudoc.tsv";
	
	private static Map<String,String> caseNumber2ecli = null;
	private static Map<String,String> applicant2ecli = null;
	private static Map<String,String> date2ecli = null;
	
	public static String lookup(String caseNumber, String applicant, String date, String country) {
		
		if(caseNumber != null) {
			
			if(caseNumber2ecli.get(caseNumber) != null) return caseNumber2ecli.get(caseNumber); 
		}

		if(date != null && country != null) {
			
			if(date2ecli.get(date + "_" + country) != null) return date2ecli.get(date + "_" + country);
		}
		
		if(applicant != null) {
			
			if(applicant2ecli.get(applicant) != null) return applicant2ecli.get(applicant);
		}
		
		return "";
	}
	
	public static void init() throws IOException {
		
		if(caseNumber2ecli == null) {
		
			if(Linkoln.DEBUG) System.out.print("Loading hudoc repo...");
			
			caseNumber2ecli = new HashMap<String,String>();
			applicant2ecli = new HashMap<String,String>();
			date2ecli = new HashMap<String,String>();
	
			
			File hudocFile = new File(hudocFileName);
			
			BufferedReader reader = null;
			InputStream in = null;
			
			if( !hudocFile.exists()) {
				
				//Look for it as a resource in the jar file
				Util util = new Util();
				in = util.getClass().getResourceAsStream("/" + hudocFileName);
				
				if(in == null) {
					return;
				}
				
				reader = new BufferedReader(new InputStreamReader(in));
				
			} else {
				
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(hudocFile)));
			}
			
			String l = null;

		    while( ( l = reader.readLine() ) != null ) {

		    	if(l.startsWith("/*") || l.startsWith("*") || l.startsWith(" *") || l.startsWith("#")) continue;
		    	
		    	String[] items = l.split("\t");
		    	
		    	String ecli = items[0];
		    	String caseNumber = items[1];
		    	String date = items[2];
		    	String country = items[3];
		    	String applicant = items[4].trim();
		    	
		    	//Case numbers
		    	if(caseNumber.length() > 4) { 
			    	if(caseNumber.indexOf(";") > -1) {
			    		
			    		String[] caseNumbers = caseNumber.split(";");
			    		
			    		for(int i=0; i<caseNumbers.length; i++) {
			    			
			    			addCaseNumber(caseNumbers[i], ecli);
			    		}
			    		
			    	} else {
			    		
			    		addCaseNumber(caseNumber, ecli);
			    	}
		    	}
		    	//applicants
		    	if(applicant.length() > 2) {
		    		
		    		applicant = Util.stripAccents(applicant.toLowerCase());
		    		applicant2ecli.put(applicant, ecli);
		    	}

		    	//date and country
		    	if(date.length() > 1 && country.length() > 1) {
		    		
		    		if(country.indexOf(";") > -1) country = country.substring(0,country.indexOf(";"));
		    		
		    		date2ecli.put(date + "_" + Util.getTwoLettersCountryCode(country), ecli);
		    	}
		    	
		    }
		    
		    reader.close();  
		    if(in != null) in.close();
			
			if(Linkoln.DEBUG) System.out.println("done.");
		}
	}
	
	private static void addCaseNumber(String caseNumber, String ecli) {
		
		String number = caseNumber.substring(0, caseNumber.indexOf("/"));
		String year = caseNumber.substring(caseNumber.indexOf("/")+1);
		
		if(year.startsWith("0") || year.startsWith("1")) {
			
			year = "20" + year;
			
		} else {
			
			year = "19" + year;
		}
		
		caseNumber2ecli.put(number + "-" + year, ecli);
	}
	
	
}
