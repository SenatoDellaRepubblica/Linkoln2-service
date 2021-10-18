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

public final class LinkolnIdentifier {

	/*
	 * 
	 * Urls and identifiers associated with linkoln references.
	 * 
	 * - type of identifier (ECLI,ELI,CELEX,etc.)
	 * - identifier code string
	 * - generated URL for accessing the document on the web
	 * 
	 */
	
	private Identifiers type = null;
	
	private String code = "";
	
	private String url = "";
	
	LinkolnIdentifier() {
		
	}
	
	public Identifiers getType() {
		return type;
	}

	void setType(Identifiers type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	void setUrl(String url) {
		this.url = url;
	}

}
