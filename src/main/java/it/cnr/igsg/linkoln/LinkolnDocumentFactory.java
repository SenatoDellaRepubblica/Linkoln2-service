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
package it.cnr.igsg.linkoln;

public class LinkolnDocumentFactory {

	/*
	 * Init new LinkolnDocuments. 
	 */
	
	public static LinkolnDocument createLinkolnDocument() {
		
		LinkolnDocument linkolnDocument = new LinkolnDocument();
		
		return linkolnDocument;
	}
	
	public static LinkolnDocument createLinkolnDocument(String identifier) { //TODO LinkolnIdentifier

		LinkolnDocument linkolnDocument = new LinkolnDocument();
		
		if(identifier != null && identifier.trim().length() > 0) {
		
			linkolnDocument.setIdentifier(identifier.trim());
		}
		
		return linkolnDocument;
	}

}
