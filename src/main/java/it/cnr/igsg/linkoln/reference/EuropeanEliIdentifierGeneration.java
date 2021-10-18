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
import it.cnr.igsg.linkoln.entity.EuropeanLegislationReference;
import it.cnr.igsg.linkoln.entity.Reference;

public class EuropeanEliIdentifierGeneration implements IdentifierGeneration {

	@Override
	public LinkolnIdentifier getLinkolnIdentifier(LinkolnDocument linkolnDocument, Reference annotationEntity) {

		if( !(annotationEntity instanceof EuropeanLegislationReference)) return null;
		
		return process((EuropeanLegislationReference) annotationEntity);
	}

	private LinkolnIdentifier process(EuropeanLegislationReference entity) {
		
		LinkolnIdentifier linkolnIdentifier = new LinkolnIdentifier();
		linkolnIdentifier.setType(Identifiers.ELI);
		
		String lang = "ita"; //TODO multi lang and jurisdiction
		
		if(entity.getRelatedEntity("EU_LEG_ALIAS") != null) {
			
			return null;
		}
		
		String prefix = "http://data.europa.eu/eli";

		//[NUMBER:37-2003-EC:43:291] "2003/37/EC" 
		String number = "";
		String year = "";
		String type = "";
		
		/*
		AnnotationEntity docType = entity.getRelatedEntity("EU_LEG_DOCTYPE");
	        
		if(docType == null) docType = entity.getRelatedEntity("LEG_DOCTYPE");
		if(docType == null) docType = entity.getRelatedEntity("DOCTYPE");  
		*/  
		
		AnnotationEntity docType = entity.getDocumentType();
		if(docType == null) return null;

		if(docType.getValue().equals("DIRECTIVE")) type = "dir";
		if(docType.getValue().equals("REGULATION")) type = "reg";
		if(docType.getValue().equals("DECISION")) type = "dec";
		
		if(type.equals("")) return null;
		
		//AnnotationEntity numberEntity = entity.getRelatedEntity("NUMBER");
		AnnotationEntity numberEntity = entity.getNumber();

		if(numberEntity == null) return null;
		
		//Per adesso considera soltanto i reference espressi con anno e numero dentro il tag NUMBER
		
		String[] numberItems = numberEntity.getValue().split("\\-");
		
		if(numberItems.length < 2) return null;
		
		number = numberItems[0];
		year = numberItems[1];
		
		if(number.equals("") || year.equals("")) return null;
		
		String eliPartition="";
		
		AnnotationEntity partitionEntity = entity.getRelatedEntity("LEG_PARTITION");
		
		if(partitionEntity != null) {
			
			if(partitionEntity.getRelatedEntity("ARTICLE") != null) {
				
				eliPartition += "/art_" + partitionEntity.getRelatedEntity("ARTICLE").getValue();

				if(partitionEntity.getRelatedEntity("PARAGRAPH") != null) {
					
					eliPartition += "/par_" + partitionEntity.getRelatedEntity("PARAGRAPH").getValue();
				}
			}
		}
			
		String eli = prefix + "/" + type + "/" + year + "/" + number + eliPartition + "/oj";
		
		String url = eli + "/" + lang;

		linkolnIdentifier.setCode(eli);
		linkolnIdentifier.setUrl(url);
		
		return linkolnIdentifier;

	}
}
