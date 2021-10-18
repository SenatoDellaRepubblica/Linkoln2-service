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
package it.cnr.igsg.linkoln.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import it.cnr.igsg.linkoln.service.impl.Util;


public abstract class AnnotationEntity {

	protected String id = ""; //annotation numeric identifier
	
	protected int position = -1; //position relative to the plain text
	
	protected String text = ""; //full raw text included in the annotation
	
	private String value = ""; //normalized value
	
	public final String getValue() {
		return value;
	}

	public void setValue(String value) {
		
		//Sono permessi soltanto valori composti dai caratteri: [A-Z] [0-9] "_" "-"
		value = value.toUpperCase().replaceAll("\n", "").replaceAll("\r", "").trim();

		if( !Util.checkValue(value)) {
			
			System.err.println("checkValue() fails! value: " + value + " (" + this.getClass().getName() + ") pos:" + this.getPosition() + " text:" + this.getText());
			
		} else {
		
			this.value = value;
		}
	}

	public final String getRelatedValue(String relatedEntityName) {
		
		AnnotationEntity entity = this.getRelatedEntity(relatedEntityName);
		if(entity != null) return entity.getValue();

		return null;
	}
	
	protected Map<String,AnnotationEntity> name2entity = new HashMap<String,AnnotationEntity>();
	
	public final void addRelatedEntity(AnnotationEntity annotationEntity) {
		
		name2entity.put(annotationEntity.getEntityName(), annotationEntity);
	}
	
	public final AnnotationEntity getRelatedEntity(String entityName) {
		
		return name2entity.get(entityName);
	}
	
	public final Collection<AnnotationEntity> getRelatedEntities() {
		
		return Collections.unmodifiableCollection(name2entity.values());
	}
	
	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public abstract String getEntityName();

	private String getDescription() {
		
		String output = "[" + this.getEntityName() + ":" + this.getValue() + ":" + this.getId() + ":" + this.getPosition() + "] \"" + this.getText() + "\"";

		return output;
	}
	
	public final String toString() {

		String related = "";
		
		for(AnnotationEntity relatedEntity : name2entity.values()) {
			
			related += " { " + relatedEntity.getDescription() + " }";
		}
		
		return getDescription() + related; 
	}
	
	public String getId() {
		return id;
	}

	public final void setId(String id) {
		this.id = id;
	}

	public final int getPosition() {
		return position;
	}

	public final void setPosition(int position) {
		this.position = position;
	}
	
}
