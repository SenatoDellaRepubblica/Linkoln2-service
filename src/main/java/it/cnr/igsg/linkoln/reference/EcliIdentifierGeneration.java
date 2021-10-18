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
package it.cnr.igsg.linkoln.reference;

import it.cnr.igsg.linkoln.LinkolnDocument;
import it.cnr.igsg.linkoln.entity.Reference;
import it.cnr.igsg.linkoln.service.impl.Util;

public class EcliIdentifierGeneration implements IdentifierGeneration {

	@Override
	public LinkolnIdentifier getLinkolnIdentifier(LinkolnDocument linkolnDocument, Reference entity) {

		/*
		 * TODO
		 * 
		 * Completare (aumentare) le tipologie di documento di TAR/CDS
		 * 
		 * text = "TAR Piemonte ord. 13 gennaio 1981, n. 17";
		 *  ==> https://e-justice.europa.eu/ecli/it/ECLI:IT:TAR_PIE:1981:17SENT.html
		 * 
		 */
		
		
		//A cosa serve filtrare solo le CaseLawRef? Non ci sono ambiguità sull'autorità emanante. Inoltre ci sono i casi Reference + CL_AUTH (tramite clustering o far-auth)
		//if( !(annotationEntity instanceof CaseLawReference)) return null;
		//CaseLawReference entity = (CaseLawReference) annotationEntity; 
		
		LinkolnIdentifier linkolnIdentifier = new LinkolnIdentifier();
		linkolnIdentifier.setType(Identifiers.ECLI);
		
		//String urlPrefix = "https://e-justice.europa.eu/ecli/";  //Funziona meglio così: https://e-justice.europa.eu/ecli/it/ECLI:CODE.html 
		String urlPrefix = "https://e-justice.europa.eu/ecli/it/";
		
		String auth = entity.getAuthorityValue();
		String year = entity.getYear()!=null?entity.getYear().getValue():null;
		String number = entity.getNumberValue();
		String subject = entity.getSubject()!=null?entity.getSubject().getValue():null;
		String docType = entity.getDocumentType()!=null?entity.getDocumentType().getValue():null;
		
//		if(entity.getText().indexOf("sentenza dell'11") > -1)
//			System.out.println("UNZ");
		
		if(auth == null) {
			
			//TODO make assumptions based on the metadata of the input document?
			if(linkolnDocument.getAuthority().toUpperCase().indexOf("CASS") > -1) auth = "IT_CASS";
			if(linkolnDocument.getAuthority().toUpperCase().indexOf("COST") > -1) auth = "IT_COST";
			
			//TODO allargare alle autorità emananti di merito e appello?? -> ECLI CODES...
			
			if(auth == null) return null;
		}
		
		if(auth.equals("IT_COST") && year != null && number != null) {

			linkolnIdentifier.setCode("ECLI:IT:COST:" + year + ":" + number);
		}

		if(auth.equals("IT_CASS") && year != null && number != null) {
			
			if(subject != null) {

				if(subject.equals("CIVIL")) subject = "CIV";
				if(subject.equals("CRIMINAL")) subject = "PEN";
			} 
			
			if(subject == null && entity.getRelatedEntity("CL_AUTH_SECTION") != null) {
				
				String section = entity.getRelatedEntity("CL_AUTH_SECTION").getValue().toUpperCase();
				
				if(section.equals("L")) subject = "CIV";
				if(section.equals("T")) subject = "CIV";
				if(section.equals("F")) subject = "PEN";
				if(section.equals("7")) subject = "PEN";
				if(section.equals("6-1")) subject = "CIV";
				if(section.equals("6-2")) subject = "CIV";
				if(section.equals("6-3")) subject = "CIV";
				if(section.equals("6-4")) subject = "CIV";
				
			}
			
			if(subject == null && !linkolnDocument.getSector().equals("")) {
				
				//Controlla i metadati del documento di input
				if(linkolnDocument.getSector().toUpperCase().startsWith("C")) subject = "CIV";
				if(linkolnDocument.getSector().toUpperCase().startsWith("P")) subject = "PEN";
				if(linkolnDocument.getSector().toUpperCase().startsWith("L")) subject = "CIV";
			
			} 
			
			if(subject == null && entity.getApplicant() != null || entity.getDefendant() != null) {
				
				//Solitamente se sono specificate le parti siamo nel penale
				subject = "PEN";
				
			} 

			if(subject == null) { 
				
				//TODO guess sector based on other references (eg.: many references to the civil or penal code)
				
				return null;
			}
			
			if(subject == null || subject.equals("")) return null;
			
			linkolnIdentifier.setCode("ECLI:IT:CASS:" + year + ":" + number + subject);
		}
		

		//Corte dei Conti
		if(auth.startsWith("IT_CONT") && year != null && number != null) {
			
			//TODO Manca la sezione della Corte dei Conti
			String codiceSezione = ""; //SSRR, SGPIE, APP2, etc.
			
			if(auth.length() > 9) {
				//Es.: "IT_CONT_FVG"
				
				codiceSezione = "SRC" + auth.substring(8);
			}
			
			
			if( !codiceSezione.equals("")) {
				
				linkolnIdentifier.setCode("ECLI:IT:CONT:" + year + ":" + number + codiceSezione);

				//TODO tipo documento
				String codiceTipoDoc = "";
				
				//Controlla prima se il codiceTipoDoc è nel number stesso
				String fullNumber = "";
				if(entity.getRelatedEntity("NUMBER") != null) {
					
					fullNumber = entity.getRelatedEntity("NUMBER").getValue();
				}
				if(fullNumber.indexOf("-") > -1) codiceTipoDoc = fullNumber.substring(fullNumber.indexOf("-")+1);
				if(codiceTipoDoc.indexOf("-") > -1) {
					
					codiceTipoDoc = codiceTipoDoc.substring(codiceTipoDoc.indexOf("-")+1);
					
				} else {
				
					if(docType != null) {
						
						if(docType.equals("JUDGMENT")) codiceTipoDoc = "SENT";
						if(docType.equals("PARERE")) codiceTipoDoc = "PAR";
						//if(docType.equals("DELIBERA")) codiceTipoDoc = "DEL"; //???
						//TODO
					}
				}
				
				//Altro sistema di identificazione e URL: 
				//https://banchedati.corteconti.it/documentDetail/SRCLAZ/151/2013/PAR
				//https://banchedati.corteconti.it/documentDetail/SRCTOS/301/2009/REG
				//Altro stile:
				//https://banchedati.corteconti.it/documentDetail/MARCHE/SENTENZA/92/2017

				linkolnIdentifier.setUrl("https://banchedati.corteconti.it/documentDetail/" + codiceSezione + "/" + number + "/" + year + "/" + codiceTipoDoc);
				return linkolnIdentifier;
			}
		}
		
		//Consiglio di Stato
		if(auth.equals("IT_CDS") && year != null && number != null) {
			
			//TODO tipo documento
			
			linkolnIdentifier.setCode("ECLI:IT:CDS:" + year + ":" + number + "SENT");
		}
		
		//Consiglio di Giustizia Amministrativa per la Regione Sicilia
		if(auth.equals("IT_CGARS") && year != null && number != null) {
			
			//TODO tipo documento
			
			linkolnIdentifier.setCode("ECLI:IT:CGARS:" + year + ":" + number + "SENT");
		}
		
		//Tribunali Amministrativi Regionali
		if(auth.startsWith("IT_TAR") && year != null && number != null) {

			//TODO tipo documento
			
			linkolnIdentifier.setCode("ECLI:IT:" + auth.substring(3).replaceAll("_", "") + ":" + year + ":" + number + "SENT");
		}
		
		
		//TODO merito???
		
		
		
		if(auth.equals("CE_ECHR")) {
			
			String caseNumber = entity.getCaseNumber()==null?null:entity.getCaseNumber().getValue();
			String applicant = entity.getApplicantValue()==null?null:Util.stripAccents(entity.getApplicantValue().toLowerCase());
			String date = entity.getDate()==null?null:entity.getDate().getValue();
			//String country = entity.getDefendantValue()==null?null:entity.getDefendantValue();
			String country = entity.getCountry()==null?null:entity.getCountry().getValue();
			
			String hudocEcli = HudocRepo.lookup(caseNumber, applicant, date, country);
			
			if( !hudocEcli.equals("")) {
			
				linkolnIdentifier.setCode(hudocEcli);
				linkolnIdentifier.setUrl("http://hudoc.echr.coe.int/eng#{%22ecli%22:[%22" + hudocEcli + "%22]}");
				
				//TODO per le url si potrebbe usare direttamente l'item-id: https://hudoc.echr.coe.int/eng#{"itemid":["001-99612"]}
				
				return linkolnIdentifier;
			}
		}
		
		
		if( !linkolnIdentifier.getCode().equals("")) {
			
			linkolnIdentifier.setUrl(urlPrefix + linkolnIdentifier.getCode() + ".html");
			
			return linkolnIdentifier;
		}

		return null;
	}
	
}
