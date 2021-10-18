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

import java.util.ArrayList;
import java.util.Collection;

import it.cnr.igsg.linkoln.Linkoln;
import it.cnr.igsg.linkoln.LinkolnDocument;
import it.cnr.igsg.linkoln.entity.Reference;

public class LinkolnReferenceFactory {

	/*
	 * Init new LinkolnReferences. 
	 */
	
	private static Collection<IdentifierGeneration> generators = null;
	
	private static void initGenerators() {
		
		if(generators == null) {
			
			generators = new ArrayList<IdentifierGeneration>();
			
			generators.add(new UrnIdentifierGeneration());
			generators.add(new GazzettaUfficialeIdentifierGeneration());
			generators.add(new CelexIdentifierGeneration());
			generators.add(new EuropeanEliIdentifierGeneration());
			generators.add(new EcliIdentifierGeneration());
		}
	}
	
	public static LinkolnReference createLinkolnReference(LinkolnDocument linkolnDocument, Reference annotationEntity) {

		if(annotationEntity == null) return null;
		
		LinkolnReference linkolnReference = new LinkolnReference(annotationEntity);

		annotationEntity.setLinkolnReference(linkolnReference);

		//Generation of linkoln identifiers
		initGenerators();
		
		for(IdentifierGeneration generator : generators) {
			
			linkolnReference.addLinkolnIdentifier(generator.getLinkolnIdentifier(linkolnDocument, annotationEntity));
		}

		if(Linkoln.STRICT) {
			
			//In modalità STRICT-CSM, filtra i reference che non hanno nè identificato nè lkn-auth-name
			if(linkolnReference.getLinkolnIdentifiers().size() == 0 && linkolnReference.getExtendedAuthorityName() == null) {
				
				return null;
			}
		}

		linkolnDocument.addLinkolnReference(linkolnReference);

		return linkolnReference;
	}

}
