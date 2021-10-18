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
package it.cnr.igsg.linkoln.service;

import it.cnr.igsg.linkoln.Linkoln;
import it.cnr.igsg.linkoln.LinkolnDocument;

public abstract class LinkolnService implements Comparable<LinkolnService>, LinkolnServiceInfo {

	
	private LinkolnDocument linkolnDocument = null;

	public LinkolnDocument getLinkolnDocument() {
		return linkolnDocument;
	}
	
	private int index = -1; //Relative position in a META-INF/services file
	
	public final void setIndex(int index) {
		
		if(this.index == -1) {
		
			this.index = index;
		}
	}
	
	public final int getIndex() {
		
		return this.index;
	}

	public final String getDescription() {
		
		String description = this.getClass().getName() + " (" + version() + ") for language " + language();
		
		return description;
	}

	
	public final boolean initService(LinkolnDocument linkolnDocument) {
		
		this.linkolnDocument = linkolnDocument;
		
		return true;
	}
	
	/*
	 * Execute the service
	 */
	public final boolean runService() {
		
		if(Linkoln.DEBUG) System.out.println("Running " + this.getDescription());
		
		beforeRun();
		
		run();
		
		return afterRun();
	}
	
	/*
	 * Specific implementation of the service.
	 */
	protected abstract boolean run();

	/*
	 * Enable/disable the execution of the service.
	 * A service could be disabled if the linkolnDocument (the input)
	 * doesn't meet specific requirements of the service.
	 * For example, a service for the recognition of patterns of a specific court
	 * should be disabled if the issuing authority of the input is a different court. 
	 */
	public boolean runnable() {
		
		return true;
	}
	              
	protected void beforeRun() {

	}

	protected boolean afterRun() {

		return true;
	}
	
	@Override
	public final int compareTo(LinkolnService o) {


		if(this.getIndex() < o.getIndex()) return -1;
		if(this.getIndex() > o.getIndex()) return 1;
		
		return this.getDescription().compareTo(o.getDescription());
	}
}
