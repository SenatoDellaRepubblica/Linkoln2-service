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
import it.cnr.igsg.linkoln.entity.AnnotationEntity;
import it.cnr.igsg.linkoln.entity.EuropeanCaseLawReference;
import it.cnr.igsg.linkoln.entity.EuropeanLegislationReference;
import it.cnr.igsg.linkoln.entity.Reference;
import it.cnr.igsg.linkoln.service.impl.Util;

public class CelexIdentifierGeneration implements IdentifierGeneration {

	@Override
	public LinkolnIdentifier getLinkolnIdentifier(LinkolnDocument linkolnDocument, Reference annotationEntity) {

		if( annotationEntity instanceof EuropeanLegislationReference) return process((EuropeanLegislationReference) annotationEntity); 
		
		if( annotationEntity instanceof EuropeanCaseLawReference) return process((EuropeanCaseLawReference) annotationEntity);
		
		return null;
	}

	private LinkolnIdentifier process(EuropeanLegislationReference entity) {
		
		LinkolnIdentifier linkolnIdentifier = new LinkolnIdentifier();
		linkolnIdentifier.setType(Identifiers.CELEX);
		
		String lang = "it";
		
        String prefix = "http://eur-lex.europa.eu/legal-content/" + lang + "/TXT/?uri=CELEX:";
		
		if(entity.getRelatedEntity("EU_LEG_ALIAS") != null) {
			
			String aliasCelex = getAliasCelex(entity, entity.getRelatedEntity("EU_LEG_ALIAS"), lang);
			
			linkolnIdentifier.setCode(aliasCelex);
			linkolnIdentifier.setUrl(prefix + aliasCelex);
			
			return linkolnIdentifier;
		}

		String celex = "";
		
		String type = "";
		String sector = "";
		String celexNumber = "";
		String celexYear = "";
		
		AnnotationEntity docType = entity.getDocumentType();
     
        if(docType == null) {
        	
        		return null;
        }
        
        if(docType.getValue().equals("DIRECTIVE")) { sector="3"; type = "L"; }
        if(docType.getValue().equals("REGULATION")) { sector="3"; type = "R"; }
        if(docType.getValue().equals("RECOMMENDATION")) { sector="3"; type = "H"; }
        if(docType.getValue().equals("DECISION")) { sector="3"; type = "D"; }
        
        AnnotationEntity number = entity.getNumber();
        
        if(number != null) {
        	
        		celexNumber = Util.readFirstNumber(number.getValue());
        		celexYear = "";
        		
        		if(number.getRelatedEntity("YEAR") != null) {
        		
        			celexYear = Util.readLastNumber(number.getValue()); //temp
        		}
        }
        
        if(celexYear.equals("")) {
        	
        		AnnotationEntity date = entity.getDate();
        		
        		if(date != null && date.getRelatedEntity("YEAR") != null) {
        			
        			celexYear = date.getRelatedEntity("YEAR").getValue();
        		}
        }
        
		if(celexNumber.length() == 1) { celexNumber = "000" + celexNumber; }
		if(celexNumber.length() == 2) { celexNumber = "00" + celexNumber; }
		if(celexNumber.length() == 3) { celexNumber = "0" + celexNumber; }

		//TODO Year deve essere letto in altro modo!

		celex = sector + celexYear + type + celexNumber;
		
		//Non produrre un identificatore Celex se manca l'anno o il numero
		if(celexYear.equals("") || celexNumber.equals("")) {
			
			return null;
		}
		
		linkolnIdentifier.setCode(celex);
		linkolnIdentifier.setUrl(prefix + celex);
		
		return linkolnIdentifier;
	}

	private String getAliasCelex(AnnotationEntity entity, AnnotationEntity alias, String lang) {
		
		String celex = "";
		
        String celexSuffix = "";
    	
        String artValue = "";
        
		AnnotationEntity partitionEntity = entity.getRelatedEntity("LEG_PARTITION");
		
		if(partitionEntity != null) {
			
			if(partitionEntity.getRelatedEntity("ARTICLE") != null) {
				
				artValue += partitionEntity.getRelatedEntity("ARTICLE").getValue();
			}
		}

        if(artValue.length()>0) {
			
			if(artValue.length()==1) artValue = "00" + artValue;
			if(artValue.length()==2) artValue = "0" + artValue;
			celexSuffix = artValue;
			
		} else {
			
			celexSuffix = "/TXT";
		}

        //if(alias.getValue().equals("EU_TUE")) celex = "11992M";  //Questo non funziona TODO
        if(alias.getValue().equals("EU_TUE")) celex = "11992E";
        
        if(alias.getValue().equals("EU_TCEE")) celex = "11957E/TXT";
        
        if(alias.getValue().equals("EU_TFUE")) celex = "12008E";
		
		
		return celex + celexSuffix;
	}
	
	private LinkolnIdentifier process(EuropeanCaseLawReference entity) {
		
		LinkolnIdentifier linkolnIdentifier = new LinkolnIdentifier();
		linkolnIdentifier.setType(Identifiers.CELEX);
		
		AnnotationEntity auth = entity.getAuthority();
		
		if(auth == null || ( !auth.getValue().equals("EU_CJEU") && !auth.getValue().equals("EU_CJEC")) ) return null;
		
		AnnotationEntity caseNumber = entity.getCaseNumber();
		
		if(caseNumber == null || caseNumber.getValue() == null || caseNumber.getValue().length() < 4) return null;
		
		String lang = "it";
		
        String prefix = "http://eur-lex.europa.eu/legal-content/" + lang + "/TXT/?uri=CELEX:";
		
		String celex = "";
		
		String type = "CJ";
		String sector = "6";
		String celexNumber = Util.readFirstNumber(caseNumber.getValue());
		String celexYear = Util.normalizeYear(Util.readSecondNumber(caseNumber.getValue()));
		
		/*
		 * TODO
		 * Nel caso di cause riunite si devono prendere i valori anno e numero più vecchi.
		 */
		
		
		AnnotationEntity docType = entity.getDocumentType();
		
        if(docType != null && docType.getValue().equals("JUDGMENT")) { type = "CJ"; }
        if(docType != null && docType.getValue().equals("ORDER")) { type = "CO"; }
        
		if(celexNumber.length() == 1) { celexNumber = "000" + celexNumber; }
		if(celexNumber.length() == 2) { celexNumber = "00" + celexNumber; }
		if(celexNumber.length() == 3) { celexNumber = "0" + celexNumber; }

		celex = sector + celexYear + type + celexNumber;
		
		//Non produrre un identificatore Celex se manca l'anno o il numero
		if(celexYear.equals("") || celexNumber.equals("")) {
			
			return null;
		}
		
		linkolnIdentifier.setCode(celex);
		linkolnIdentifier.setUrl(prefix + celex);
		
		return linkolnIdentifier;
	}
}
