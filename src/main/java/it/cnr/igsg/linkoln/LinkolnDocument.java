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

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import it.cnr.igsg.linkoln.entity.AnnotationEntity;
import it.cnr.igsg.linkoln.reference.LinkolnReference;
import it.cnr.igsg.linkoln.service.LinkolnAnnotationService;
import it.cnr.igsg.linkoln.service.LinkolnRenderingService;
import it.cnr.igsg.linkoln.service.impl.HtmlCleanCommentsPreProcessing;
import it.cnr.igsg.linkoln.service.impl.HtmlCleanPreProcessing;
import it.cnr.igsg.linkoln.service.impl.HtmlPreProcessing;

public class LinkolnDocument {


	/* TODO Ogni text deve avere associata una lingua. Si possono avere più text in lingue diverse nello stesso linkolnDocument */
	
	
	public Set<String> auths = new HashSet<String>();
	
	
	/*
	 * Input text and language handling.
	 */
	
	private String language = "";
	
	private boolean failed = false;
	
	private String identifier = "";

	private String plainText = "";
	
	private String originalText = "";
	
	private String finalAnnotationsText = ""; //text after Finalize linkoln annotations service
	
	private String testoNonAnnotato = ""; //testo non incluso nei tag [LKN]

	public void setFinalAnnotationsText(String finalAnnotationsText) {
		
		this.finalAnnotationsText = finalAnnotationsText;
	}
	
	public String getFinalAnnotationsText() {
		
		return finalAnnotationsText;
	}
	
	public void setTestoNonAnnotato(String testoNonAnnotato) {
		
		this.testoNonAnnotato = testoNonAnnotato;
	}
	
	public String getTestoNonAnnotato() {
		
		return testoNonAnnotato;
	}
	
	public ArrayList<Object> originalContents = new ArrayList<Object>(); //TODO use ServiceBus / LinkolnPipeline
	
	public Set<Integer> htmlCuts = new HashSet<Integer>();
	
	public boolean isPlainText = true;
	
	private Collection<LinkolnReference> linkolnReferences = new ArrayList<LinkolnReference>();
	
	private ArrayList<Integer> fullStops = new ArrayList<Integer>(); //Si assume che sono aggiunti in ordine di occorrenza nel testo dal servizio apposito
	
	public void addFullStop(Integer fs) {
		
		if(fs != null)
			this.fullStops.add(fs);
	}
	
	public int getPreviousFullStop(int position) {
		
		int fullStop = -1;
		
		for(Integer fs : fullStops) {
			
			if(fs < position) 
				fullStop = fs;
			else
				break;
		}
		
		
		return fullStop;
	}
	
	/*
	 * Metadati dell'atto
	 */
	private String sector = "";
	private String authority = "";

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		
		if(sector != null) {
			this.sector = sector.trim();
		}
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		
		if(authority != null) {
			this.authority = authority.trim();
		}
	}	
	
	
	/*
	 * Altro:
	 */
	private long executionTime = 0;
	
	public long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	public boolean hasFailed() {
		return failed;
	}

	public void setFailed(boolean failedExecution) {
		this.failed = failedExecution;
	}

	public String getIdentifier() {
		return identifier;
	}

	void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setText(String text) {
		
		this.originalText = text;
		
		htmlPreProcessing();
	}
	
	public String getOriginalText() {
		
		return this.originalText;
	}

	//Initialize "originalContents", "cuts" and set plainText
	private void htmlPreProcessing() {
		
		if(originalText.trim().startsWith("<")) { //TODO improve check for html/xml inputs..
			
			isPlainText = false;
		}
		
		HtmlCleanCommentsPreProcessing hccpp = new HtmlCleanCommentsPreProcessing();
		
		try {
			
			hccpp.yyreset(new StringReader(originalText));
			
			hccpp.yylex();
			
		} catch (IOException e) {

			e.printStackTrace();
			
			return;
		}
		
		String processedOriginalText = hccpp.getOutput();
		
		//System.out.println("CLEAN COMMENTS:\n" + processedOriginalText);
		
		HtmlCleanPreProcessing hcpp = new HtmlCleanPreProcessing();
		
		try {
			
			hcpp.yyreset(new StringReader(processedOriginalText));
			
			hcpp.yylex();
			
		} catch (IOException e) {

			e.printStackTrace();
			
			return;
		}
		
		processedOriginalText = hcpp.getOutput();
		
		//System.out.println("CLEAN HEAD:\n" + processedOriginalText);
		
		HtmlPreProcessing hpp = new HtmlPreProcessing();
		
		try {
			
			hpp.yyreset(new StringReader(processedOriginalText));
			
			hpp.yylex();
			
		} catch (IOException e) {

			e.printStackTrace();
			
			return;
		}
		
		StringBuilder plainContents = new StringBuilder();
		
		for(Object content : hpp.getContents()) {
			
			if(content instanceof StringBuilder) {
				
				plainContents.append(content.toString());
			}
		}
		
		plainText = plainContents.toString();
			
		htmlCuts = hpp.getCuts();
		
		originalContents = hpp.getContents();
		
		//System.out.println("PLAIN TEXT:\n" + plainText);
	}	

	/*
	 * Text Annotations
	 */
	
	private Collection<LinkolnAnnotationService> annotationServices = null;
	
	private Collection<LinkolnRenderingService> renderingServices = null;

	protected Collection<AnnotationEntity> entities = new ArrayList<AnnotationEntity>();
	
	protected Map<String,AnnotationEntity> id2entity = new HashMap<String,AnnotationEntity>();
	
	protected String getNextId() {
		
		return String.valueOf(entities.size() + 1);
	}
	
	public void addAnnotationEntity(AnnotationEntity entity) {
		
		if( !entity.getId().equals("") && id2entity.get(entity.getId()) != null) {
			
			//AnnotationEntity già presente nel linkoln document, non fare altro
			return;
		}

		//Nuova Annotation Entity
		
		String id = getNextId();
		
		entity.setId(id);
		entities.add(entity);
		id2entity.put(entity.getId(), entity);
	}

	public void replaceAnnotationEntity(AnnotationEntity oldEntity, AnnotationEntity newEntity) {
		
		entities.remove(oldEntity);
		entities.add(newEntity);
		id2entity.put(newEntity.getId(), newEntity);
	}

	public AnnotationEntity getAnnotationEntity(String id) {
		
		return id2entity.get(id);
	}
	
	/*
	 * TODO Spostare tutta la parte delle annotazioni e degli oggetti in memoria corrispondenti nel Service Bus
	 */ 
	public Collection<AnnotationEntity> getAnnotationEntities() {
		
		return Collections.unmodifiableCollection(entities);
	}
	
	public void resetEntities() {
		
		entities = new ArrayList<AnnotationEntity>();
	}
	
	LinkolnDocument() {
		
		init();
	}
	
	private void init() {
		
		//init or reset the legal references collection

		annotationServices = new ArrayList<LinkolnAnnotationService>();
		
		renderingServices = new ArrayList<LinkolnRenderingService>();
		
		errors = new ArrayList<String>();
		
		messages = new ArrayList<String>();
	}
	
	public Collection<LinkolnAnnotationService> getAnnotationServices() {
		
		return Collections.unmodifiableCollection(this.annotationServices);
	}
	
	public boolean addAnnotationService(LinkolnAnnotationService service) {
		
		return this.annotationServices.add(service);
	}

	public boolean addRenderingService(LinkolnRenderingService service) {
		
		return this.renderingServices.add(service);
	}

	public final String getAnnotatedText() {
		
		//The original text annotated in a specific format.
		
		if(annotationServices.size() == 0) {
			
			return this.plainText;
		}
		
		return ((ArrayList<LinkolnAnnotationService>) annotationServices).get(annotationServices.size() - 1).getOutput();
	}
	
	public final String getRendering(String name) {
		
		if(name == null || name.trim().length() < 1) return null;
		
		for(LinkolnRenderingService service : renderingServices) {
			
			if(service.name().equalsIgnoreCase(name)) return service.getOutput();
		}
		
		return null;
	}
	
	public LinkolnAnnotationService getAnnotationService(String serviceClassName) {
		
		for(LinkolnAnnotationService service : this.getAnnotationServices() ) {
			
			if(service.getClass().getSimpleName().equalsIgnoreCase(serviceClassName)) {
				
				return service;
			}
		}
		
		return null;
	}
	
	public boolean addLinkolnReference(LinkolnReference linkolnReference) {
		
		return linkolnReferences.add(linkolnReference);
	}
	
	public Collection<LinkolnReference> getReferences() {
		
		return Collections.unmodifiableCollection(linkolnReferences);
	}
	

	
	/*
	 * Debug, messages and errors.
	 */
	
	//Error and warning captured during the execution of the Engine
	private Collection<String> errors = null;
	private Collection<String> messages = null;

	public final Collection<String> getErrors() {

		return Collections.unmodifiableCollection(errors);
	}

	public final Collection<String> getMessages() {

		return Collections.unmodifiableCollection(messages);
	}

}
