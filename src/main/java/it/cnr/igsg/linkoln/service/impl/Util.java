/*******************************************************************************
 * Copyright (c) 2016-2021 Institute of Legal Information and Judicial Systems IGSG-CNR (formerly ITTIG-CNR)
 * 
 * This program and the accompanying materials  are made available under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either version 3 of the License, or (at your option)
 * any later version. 
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at: https://www.gnu.org/licenses/gpl-3.0.txt
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is 
 * distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 *  
 * Authors: Lorenzo Bacci (IGSG-CNR)
 ******************************************************************************/
package it.cnr.igsg.linkoln.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	
	private static final Pattern digits = Pattern.compile("\\d+");

	private static final String munFileName = "data/municipalities.txt";
	
	public static Map<String,String> token2code = null;
	
	public static boolean initMunicipalities() throws IOException {
		
		if(Util.token2code != null) {
			
			return false;
		}
		
		File munFile = new File(munFileName);
		
		BufferedReader reader = null;
		InputStream in = null;
		
		if( !munFile.exists()) {
			
			//Look for it as a resource in the jar file
			Util util = new Util();
			in = util.getClass().getResourceAsStream("/" + munFileName);
			
			if(in == null) {
				return false;
			}
			
			reader = new BufferedReader(new InputStreamReader(in));
			
		} else {
			
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(munFile)));
		}
	
		Util.token2code = new HashMap<String,String>();
		
		String l = null;

	    while( ( l = reader.readLine() ) != null ) {
	    	
	    	if(l.startsWith("/*") || l.startsWith("*") || l.startsWith(" *") || l.startsWith("#")) continue;

			int split = l.indexOf(" ");
			String code = l.substring(0, split).trim();
			String namedEntity = l.substring(split).trim().toLowerCase();
		
			Util.token2code.put(namedEntity, code);
	    }
	    
	    reader.close();  
	    if(in != null) in.close();

		return true;
	}
	
	public static final String readFirstNumber(String text) {
	
		if(text == null) return null;
		
		Matcher matcher = digits.matcher(text);
		String number = "";
		
		while(matcher.find()) {
			
			number = text.substring(matcher.start(), matcher.end());			
			break;
		}
		
		return number;
	}

	public static final String readSecondNumber(String text) {
		
		if(text == null) return null;
		
		Matcher matcher = digits.matcher(text);
		String number = "";
		
		boolean first = false;
		while(matcher.find()) {
			
			number = text.substring(matcher.start(), matcher.end());
			if(first) break;
			first = true;
		}
		
		return number;
	}

	public static final String readLastNumber(String text) {
		
		Matcher matcher = digits.matcher(text);
		String number = "";
		
		while( matcher.find() ) {
			number = text.substring(matcher.start(), matcher.end());
		}
		
		return number;
	}
	
	public static final String removeAllAnnotations(String text) {
		
		Cleaner ac = new Cleaner();
		
		try {
			
			ac.yyreset(new StringReader(text));
			
			ac.yylex();
			
		} catch (IOException e) {

			e.printStackTrace();
			
			return "";
		}
		
		return ac.getOutput();
	}
	
	public static final boolean checkValue(String text) {
		
		ValueChecker vc = new ValueChecker();
		
		try {
			
			vc.yyreset(new StringReader(text));
			
			vc.yylex();
			
		} catch (IOException e) {

			e.printStackTrace();
			
			return false;
		}
		
		return vc.isAllowed();
	}
	
	public static final String tokenize(String text) {
		
		Tokenizer t = new Tokenizer();
		
		try {
			
			t.yyreset(new StringReader(text));
			
			t.yylex();
			
		} catch (IOException e) {

			e.printStackTrace();
			
			return "";
		}

		return t.getOutput().toLowerCase().replaceAll("\\s+", " ").trim();
	}
	
	public static final boolean isFutureReference(int year, String metaYear) {
		
		if(metaYear.length() < 1) {
			return false;
		}
		
		int value = 0;
		
		try {
			
			value = Integer.valueOf(metaYear);
			
		} catch (NumberFormatException e) {

			return false;
		}
	
		if(metaYear.length() == 2) {
			
			if(value < 21) {
				
				metaYear = "20" + metaYear;
				
			} else {
				
				metaYear = "19" + metaYear;
			}
		}
		
		value = Integer.valueOf(metaYear);
		
		if(year > value) {
			
			return true;
		}
		
		return false;
	}
	
	public static String getEqualPart(String a, String b) {
		
		for(int i = 0; i < a.length(); i++) {
			
			Character ai = a.charAt(i);
			
			if(i >= b.length()) {
				
				return a.substring(0, i);
			}
			
			Character bi = b.charAt(i);

			if( !ai.equals(bi)) {
				
				return a.substring(0, i);
			}
		}
		
		return a;
	}
	
	public static String normalizeYear(String year) {
		
		if(year.length() == 2) {

			int value = Integer.valueOf(year);

			if(value < 21) {
				
				year = "20" + year;
				
			} else {
				
				year = "19" + year;
			}
		
		}
		
		return year;
	}

	public static String stripAccents(String s) {
		
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		return s;
	}
	
	public static String getTwoLettersCountryCode(String threeLettersCountryCode) {
		
		String country = threeLettersCountryCode;
		
		if(country.equals("AUT")) return "AT";
		if(country.equals("BEL")) return "BE";
		if(country.equals("BGR")) return "BG";
		if(country.equals("HRV")) return "HR";
		if(country.equals("CYP")) return "CY";
		if(country.equals("CZE")) return "CZ";
		if(country.equals("DNK")) return "DK";
		if(country.equals("EST")) return "EE";
		if(country.equals("FIN")) return "FI";
		if(country.equals("FRA")) return "FR";
		if(country.equals("DEU")) return "DE";
		if(country.equals("GRC")) return "GR";
		if(country.equals("ITA")) return "IT";
		if(country.equals("IRL")) return "IE";
		if(country.equals("ITA")) return "IT";
		if(country.equals("LVA")) return "LV";
		if(country.equals("LTU")) return "LT";
		if(country.equals("LUX")) return "LU";
		if(country.equals("MLT")) return "MT";
		if(country.equals("NLD")) return "NL";
		if(country.equals("POL")) return "PL";
		if(country.equals("PRT")) return "PT";
		if(country.equals("ROU")) return "RO";
		if(country.equals("SVK")) return "SK";
		if(country.equals("SVN")) return "SI";
		if(country.equals("ESP")) return "ES";
		if(country.equals("SWE")) return "SE";
		if(country.equals("GBR")) return "GB";
		if(country.equals("ALB")) return "AL";
		if(country.equals("AND")) return "AD";
		if(country.equals("ARM")) return "AM";
		if(country.equals("AZE")) return "AZ";
		if(country.equals("BIH")) return "BA";
		if(country.equals("GEO")) return "GE";
		if(country.equals("ISL")) return "IS";
		if(country.equals("LIE")) return "LI";
		if(country.equals("MKD")) return "MK";
		if(country.equals("MCO")) return "MC";
		if(country.equals("MNE")) return "ME";
		if(country.equals("NOR")) return "NO";
		if(country.equals("MDA")) return "MD";
		if(country.equals("RUS")) return "RU";
		if(country.equals("SMR")) return "SM";
		if(country.equals("SRB")) return "RS";
		if(country.equals("CHE")) return "CH";
		if(country.equals("TUR")) return "TR";
		if(country.equals("UKR")) return "UA";
		if(country.equals("BLR")) return "BY";
		
		return "";
	}
	
}
