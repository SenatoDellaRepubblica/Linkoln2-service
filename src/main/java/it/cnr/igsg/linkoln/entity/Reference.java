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

import java.util.HashMap;
import java.util.Map;

import it.cnr.igsg.linkoln.Linkoln;
import it.cnr.igsg.linkoln.reference.Cluster;
import it.cnr.igsg.linkoln.reference.LinkolnReference;
import it.cnr.igsg.linkoln.service.impl.Util;

public class Reference extends AnnotationEntity {

	private LinkolnReference linkolnReference = null;
	
	private Cluster cluster = null;
	
	public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}
	
	public LinkolnReference getLinkolnReference() {
		
		return linkolnReference;
	}

	public void setLinkolnReference(LinkolnReference linkolnReference) {
		this.linkolnReference = linkolnReference;
	}

	@Override
	public String getEntityName() {

		return "REF";
	}
	
	private String context = "";
	
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	
	public Map<String,AnnotationEntity> name2sharedEntity = new HashMap<String,AnnotationEntity>();
	
	
	public boolean isReference() {
		
		boolean hasAuthority = false;
		boolean hasDocType = false;
		boolean hasNumber = false;
		boolean hasDate = false;
		
		if(getRelatedEntity("ALIAS") != null || getRelatedEntity("LEG_ALIAS") != null || getRelatedEntity("CL_ALIAS") != null) {
			
			return true;
		}
		
		if(getRelatedEntity("CL_AUTH") != null || getRelatedEntity("LEG_AUTH") != null || getRelatedEntity("EU_CL_AUTH") != null || getRelatedEntity("EU_LEG_AUTH") != null) {
			
			hasAuthority = true;
		}
		
		if(name2sharedEntity.get("CL_AUTH") != null || name2sharedEntity.get("LEG_AUTH") != null || name2sharedEntity.get("EU_CL_AUTH") != null || name2sharedEntity.get("EU_LEG_AUTH") != null) {
			
			hasAuthority = true;
		}
		
		if( !hasAuthority) {
			
			//Le sezioni della Cassazione sono da considerarsi come authority?  <-- In alternativa occorre un servizio con pattern specifici per la Cassazione
			if(getRelatedEntity("CL_AUTH_SECTION") != null) {
				
				String value = getRelatedEntity("CL_AUTH_SECTION").getValue().toUpperCase();
				
				if(value.equals("1")||value.equals("2")||value.equals("3")||value.equals("4")||value.equals("5")||value.equals("6")||value.equals("7")||
						value.equals("6-1")||value.equals("6-2")||value.equals("6-3")||value.equals("6-4")||
						value.equals("U")||value.equals("L")||value.equals("T")||value.equals("F")) {
					
					hasAuthority = true;
				}
			} else if(name2sharedEntity.get("DOCTYPE") != null) {
				
				String value = name2sharedEntity.get("DOCTYPE").getValue().toUpperCase();
				
				if(value.equals("1")||value.equals("2")||value.equals("3")||value.equals("4")||value.equals("5")||value.equals("6")||value.equals("7")||
						value.equals("6-1")||value.equals("6-2")||value.equals("6-3")||value.equals("6-4")||
						value.equals("U")||value.equals("L")||value.equals("T")||value.equals("F")) {
					
					hasAuthority = true;
				}
			}
		}
		
		if(getRelatedEntity("DOCTYPE") != null || getRelatedEntity("CL_DOCTYPE") != null || getRelatedEntity("LEG_DOCTYPE") != null || getRelatedEntity("EU_LEG_DOCTYPE") != null) {
			
			hasDocType = true;
		}
		
		if(name2sharedEntity.get("DOCTYPE") != null || name2sharedEntity.get("CL_DOCTYPE") != null || name2sharedEntity.get("LEG_DOCTYPE") != null || name2sharedEntity.get("EU_LEG_DOCTYPE") != null) {
			
			hasDocType = true;
		}
		
		if(getRelatedEntity("NUMBER") != null || getRelatedEntity("CASENUMBER") != null || getRelatedEntity("ALTNUMBER") != null) {
			
			hasNumber = true;
		}
		
		if(getRelatedEntity("DOC_DATE") != null) {
			
			hasDate = true;
		}
		
		if( (hasAuthority || hasDocType) && (hasNumber || hasDate) ) {
			
			return true;
		}
		
		return false;
	}
	
	public Authority getAuthority() {
		
		return getAuthority(false);
	}
	
	public Authority getAuthority(boolean strict) {
		
		if( !strict && Linkoln.CLUSTERING && getCluster() != null) {
			
			return (Authority) getCluster().getAuth();
		}

		AnnotationEntity entity = this.getRelatedEntity("CL_AUTH");
		if(entity != null) return (CaseLawAuthority) entity;
		
		entity = this.getRelatedEntity("LEG_AUTH");
		if(entity != null) return (LegislationAuthority) entity;
		
		entity = this.getRelatedEntity("EU_CL_AUTH");
		if(entity != null) return (EuropeanCaseLawAuthority) entity;
		
		entity = this.getRelatedEntity("EU_LEG_AUTH");
		if(entity != null) return (EuropeanLegislationAuthority) entity;
		
		return null;
	}
	
	public String getAuthorityValue() {
		
		return getAuthorityValue(false);
	}
	
	public String getAuthorityValue(boolean strict) {
		
		Authority entity = getAuthority(strict);
		
		if(entity == null) {
			
			//Se nella citazione è specificata soltanto una delle sezioni di cassazione, guess Cassazione as authority VALUE
			if(Linkoln.EXTENDED_PATTERNS && getRelatedEntity("CL_AUTH_SECTION") != null) {
				
				String value = getRelatedEntity("CL_AUTH_SECTION").getValue().toUpperCase();
				
				if(value.equals("1")||value.equals("2")||value.equals("3")||value.equals("4")||value.equals("5")||value.equals("6")||value.equals("7")||
						value.equals("6-1")||value.equals("6-2")||value.equals("6-3")||value.equals("6-4")||
						value.equals("U")||value.equals("L")||value.equals("T")||value.equals("F")) {
					
					return "IT_CASS";
				}
			}
			
			return null;
		}
		
		return entity.getValue();
	}
	
	public DocumentType getDocumentType() {
		
		return getDocumentType(false);
	}
	
	public DocumentType getDocumentType(boolean strict) {
		
		if( !strict && Linkoln.CLUSTERING && getCluster() != null) {
			
			return (DocumentType) getCluster().getDocType();
		}

		//Il document type può essere attaccato all'alias o direttamente alla reference
		AnnotationEntity parent = getAlias();
		
		if(parent == null) parent = this;
		
		AnnotationEntity entity = parent.getRelatedEntity("CL_DOCTYPE");
		if(entity != null) return (CaseLawDocumentType) entity;

		entity = parent.getRelatedEntity("LEG_DOCTYPE");
		if(entity != null) return (LegislationDocumentType) entity;
		
		entity = parent.getRelatedEntity("EU_LEG_DOCTYPE");
		if(entity != null) return (EuropeanLegislationDocumentType) entity;
		
		entity = parent.getRelatedEntity("DOCTYPE");
		if(entity != null) return (DocumentType) entity;
		
		return null;
	}

	public AnnotationEntity getCity() {
	
		return getCity(false);
	}
	
	public AnnotationEntity getCity(boolean strict) {
		
		if( !strict && Linkoln.CLUSTERING && getCluster() != null) {
			
			return getCluster().getCity();
		}

		AnnotationEntity entity = this.getRelatedEntity("CITY");
		
		if(entity != null) {

			return entity;
		}
		
		//Look first in detached section then in authority
		
		AnnotationEntity auth = this.getRelatedEntity("CL_DETACHED_SECTION");
		
		if(auth == null) {
			
			//get the main authority
			auth = getAuthority();
			
			if(auth == null) return null;
			
			//Look for a detatched auth within the main auth
			auth = auth.getRelatedEntity("CL_DETACHED_SECTION");
			
			if(auth == null) {
				
				//get back to the main auth
				auth = getAuthority();
			}			
		}
		
		if(auth != null) {
			
			entity = auth.getRelatedEntity("CITY");
			
			if(entity != null) {

				return entity;
			}
			
			entity = auth.getRelatedEntity("MUNICIPALITY");
			
			if(entity != null) {

				return entity;
			}
		}
		
		return null;
	}

	public AnnotationEntity getAuthCity() {
		
		//Look only in authority
		
		AnnotationEntity auth = getAuthority();
		
		if(auth != null) {
			
			AnnotationEntity entity = auth.getRelatedEntity("CITY");
			
			if(entity != null) {

				return entity;
			}
			
			entity = auth.getRelatedEntity("MUNICIPALITY");
			
			if(entity != null) {

				return entity;
			}
		}
		
		return null;
	}
	
	public AnnotationEntity getDetAuth() {
		
		//Look only in detatched authority
		AnnotationEntity auth = this.getRelatedEntity("CL_DETACHED_SECTION");
		
		if(auth == null) {
			
			//look for a det auth within the main auth
			auth = getAuthority();
			if(auth != null) {
				
				auth = auth.getRelatedEntity("CL_DETACHED_SECTION");
				
			} else {
				return null;
			}
		}

		return auth;
	}
	
	public AnnotationEntity getDetAuthCity() {
		
		AnnotationEntity auth = getDetAuth();
		
		if(auth != null) {
			
			AnnotationEntity entity = auth.getRelatedEntity("CITY");
			
			if(entity != null) {

				return entity;
			}
			
			entity = auth.getRelatedEntity("MUNICIPALITY");
			
			if(entity != null) {

				return entity;
			}
		}
		
		return null;
	}
	
	public AnnotationEntity getRegion() {
		
		return getRegion(false);
	}
	
	public AnnotationEntity getRegion(boolean strict) {
		
		if( !strict && Linkoln.CLUSTERING && getCluster() != null) {
			
			return getCluster().getRegion();
		}

		AnnotationEntity entity = this.getRelatedEntity("REGION");
		
		if(entity != null) {

			return entity;
		}
		
		Authority auth = getAuthority();
		
		if(auth != null) {
			
			entity = auth.getRelatedEntity("REGION");
			
			if(entity != null) {

				return entity;
			}
			
			AnnotationEntity authDist = auth.getRelatedEntity("CL_DETACHED_SECTION");
			
			if(authDist != null) {
				
				entity = authDist.getRelatedEntity("REGION");
				
				if(entity != null) {

					return entity;
				}
			}
		}
		
		return null;
	}
	
	public AnnotationEntity getCountry() {
		
		AnnotationEntity entity = this.getRelatedEntity("COUNTRY");
		
		if(entity != null) {

			return entity;
		}
		
		Defendant def = getDefendant(true);
		
		if(def != null) {
			
			entity = def.getRelatedEntity("COUNTRY");
			
			if(entity != null) {

				return entity;
			}
		}
		
		return null;
	}
	
	public String getNumberValue() {
		
		return getNumberValue(false);
	}
	
	public String getNumberValue(boolean strict) {
		
		AnnotationEntity entity = getNumber(strict);
		
		if(entity != null) {
			
			return Util.readFirstNumber(entity.getValue()); //TODO non è detto sia sempre il primo numero (es.: 2019/123)
		}

		return null;
	}
	
	public AnnotationEntity getNumber() {
		
		return getNumber(false);
	}
	
	public AnnotationEntity getNumber(boolean strict) {
		
		if( !strict && Linkoln.CLUSTERING && getCluster() != null) {
			
			return getCluster().getNumber();
		}

		AnnotationEntity entity = this.getRelatedEntity("NUMBER");
		if(entity != null) {
			
			return entity;
		}

		return null;
	}
	
	public Year getYear() {
		
		return getYear(false);
	}
	
	public Year getYear(boolean strict) {
		
		if( !strict && Linkoln.CLUSTERING && getCluster() != null) {
			
			return (Year) getCluster().getYear();
		}

		//Look into NUMBER first, then DATE
		
		AnnotationEntity entity = this.getRelatedEntity("NUMBER");
		if(entity != null) {
			
			AnnotationEntity year = entity.getRelatedEntity("YEAR");
			if(year != null) return (Year) year;
		}
		
		entity = this.getRelatedEntity("DOC_DATE");
		if(entity != null) {
			
			AnnotationEntity year = entity.getRelatedEntity("YEAR");
			if(year != null) return (Year) year;
		}

		return null;
	}
	
	public Year getYearFromNumber() {
		
		AnnotationEntity entity = this.getRelatedEntity("NUMBER");
		if(entity != null) {
			
			AnnotationEntity year = entity.getRelatedEntity("YEAR");
			if(year != null) return (Year) year;
		}
		
		return null;
	}

	public Date getDate() {
		
		return getDate(false);
	}
	
	public Date getDate(boolean strict) {
		
		if( !strict && Linkoln.CLUSTERING && getCluster() != null) {
			
			return (Date) getCluster().getDate();
		}

		AnnotationEntity entity = this.getRelatedEntity("DOC_DATE");
		
		if(entity != null) {
			
			return (Date) entity;
		}

		return null;
	}
	
	public AnnotationEntity getCaseNumber() {
		
		return getCaseNumber(false);
	}
	
	public AnnotationEntity getCaseNumber(boolean strict) {
		
		if( !strict && Linkoln.CLUSTERING && getCluster() != null) {
			
			return getCluster().getCaseNumber();
		}

		AnnotationEntity entity = this.getRelatedEntity("CASENUMBER");
		
		if(entity != null) {
			
			return entity;
		}

		return null;
	}
	
	public String getApplicantValue() {
		
		return getApplicantValue(false);
	}
	
	public String getApplicantValue(boolean strict) {

		Applicant applicant = getApplicant(strict);
		
		if(applicant != null) {
			
			if(applicant.getValue() != null && applicant.getValue().length() > 0) return applicant.getValue();
			
			return applicant.getText();
		}
		
		return null;
	}
	
	public Applicant getApplicant() {
		
		return getApplicant(false);
	}
	
	public Applicant getApplicant(boolean strict) {
		
		if( !strict && Linkoln.CLUSTERING && getCluster() != null) {
			
			return (Applicant) getCluster().getApplicant();
		}

		AnnotationEntity party = this.getRelatedEntity("PARTY");
		
		if(party != null) {
			
			AnnotationEntity applicant = party.getRelatedEntity("APPLICANT");
			
			if(applicant != null) {
				
				return (Applicant) applicant;
			}
		}
		
		return null;
	}
	
	public String getDefendantValue() {
		
		return getDefendantValue(false);
	}
	
	public String getDefendantValue(boolean strict) {

		Defendant defendant = getDefendant(strict);
		
		if(defendant != null) {
			
			if(defendant.getValue() != null && defendant.getValue().length() > 0) return defendant.getValue();
			
			return defendant.getText();
		}
		
		return null;
	}
	
	public Defendant getDefendant() {
		
		return getDefendant(false);
	}
	
	public Defendant getDefendant(boolean strict) {

		if( !strict && Linkoln.CLUSTERING && getCluster() != null) {
			
			return (Defendant) getCluster().getDefendant();
		}

		AnnotationEntity party = this.getRelatedEntity("PARTY");
		
		if(party != null) {
			
			AnnotationEntity defendant = party.getRelatedEntity("DEFENDANT");
			
			if(defendant != null) {
				
				return (Defendant) defendant;
			}
		}
		
		return null;
	}

	public Subject getSubject() {
	
		return getSubject(false);
	}
	
	public Subject getSubject(boolean strict) {
		
		if( !strict && Linkoln.CLUSTERING && getCluster() != null) {
			
			return (Subject) getCluster().getSubject();
		}

		AnnotationEntity entity = this.getRelatedEntity("SUBJECT");
		
		if(entity != null) {
			
			return (Subject) entity;
		}
		
		AnnotationEntity caseNum = this.getRelatedEntity("CASENUMBER");
		
		if(caseNum != null) {
			
			entity = caseNum.getRelatedEntity("SUBJECT");
			
			if(entity != null) {
				
				return (Subject) entity;
			}
		}
		
		return null;
	}
	
	public Alias getAlias() {
		
		AnnotationEntity entity = this.getRelatedEntity("LEG_ALIAS");
		
		if(entity != null) {
			
			return (LegislationAlias) entity;
		}
		
		entity = this.getRelatedEntity("EU_LEG_ALIAS");
		
		if(entity != null) {
			
			return (EuropeanLegislationAlias) entity;
		}
		
		return null;
	}
	
	public String getPartition(String partitionType) {
	
		if(partitionType == null || partitionType.trim().length() < 1) return null;
		
		AnnotationEntity partition = this.getRelatedEntity("LEG_PARTITION");
		
		if(partition != null) {
			
			AnnotationEntity part = partition.getRelatedEntity(partitionType.trim().toUpperCase());
			
			if(part != null) {
				
				return part.getValue();
			}
		}
		
		return null;
	}
	
}
