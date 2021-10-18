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
import it.cnr.igsg.linkoln.entity.EuropeanLegislationReference;
import it.cnr.igsg.linkoln.entity.LegislationReference;
import it.cnr.igsg.linkoln.entity.OfficialJournal;
import it.cnr.igsg.linkoln.entity.Reference;

public class GazzettaUfficialeIdentifierGeneration implements IdentifierGeneration {

	@Override
	public LinkolnIdentifier getLinkolnIdentifier(LinkolnDocument linkolnDocument, Reference annotationEntity) {

		if( !(annotationEntity instanceof LegislationReference)) return null;
		if( annotationEntity instanceof EuropeanLegislationReference) return null;
		
		//if( !linkolnDocument.getLanguage().equals("it")) return null; //TODO multi lang and jurisdiction support

		return process((LegislationReference) annotationEntity);
	}

	private LinkolnIdentifier process(LegislationReference entity) {
		
		LinkolnIdentifier linkolnIdentifier = new LinkolnIdentifier();
		linkolnIdentifier.setType(Identifiers.GAZZETTE);
		
		//Controlla se c'è la pubblicazione in Gazzetta Ufficiale
		OfficialJournal gu = (OfficialJournal) entity.getRelatedEntity("OFF_JOURNAL");
		
		if(gu != null) {
			
			String guUrl = getGazzettaUfficialeUrl(entity);
			
			if( !guUrl.equals("")) {
				
				linkolnIdentifier.setUrl(guUrl);
				
				return linkolnIdentifier;
			}
		}
		
		return null;
	}
	
	private String getGazzettaUfficialeUrl(LegislationReference entity) {
		
		
		//http://www.gazzettaufficiale.it/gazzetta/serie_generale/caricaDettaglio?dataPubblicazioneGazzetta=2007-02-26&numeroGazzetta=47
		
		//ESISTE ANCHE QUESTA FORMA:
		//http://www.gazzettaufficiale.it/eli/gu/2018/03/10/58/sg/pdf
		//ovvero
		//http://www.gazzettaufficiale.it/eli/gu/2018/03/10/58/sg
		
		//TODO VEDERE COME GESTIRE I SUPPLEMENTI -altro parametro?
		
		if(entity.getRelatedEntity("NUMBER") == null || entity.getRelatedEntity("DOC_DATE") == null) {
			
			return "";
		}
		
		String guNumber = entity.getRelatedEntity("NUMBER").getValue();
		String guDate = entity.getRelatedEntity("DOC_DATE").getValue();
		
		return "http://www.gazzettaufficiale.it/gazzetta/serie_generale/caricaDettaglio?dataPubblicazioneGazzetta=" + guDate + "&numeroGazzetta=" + guNumber;
	}
}
