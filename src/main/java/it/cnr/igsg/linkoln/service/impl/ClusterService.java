package it.cnr.igsg.linkoln.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import it.cnr.igsg.linkoln.entity.AnnotationEntity;
import it.cnr.igsg.linkoln.entity.Reference;
import it.cnr.igsg.linkoln.reference.Cluster;
import it.cnr.igsg.linkoln.service.LinkolnService;

public class ClusterService extends LinkolnService {

	@Override
	public String language() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String version() {
		
		return "0.1";
	}

	@Override
	protected boolean run() {

		ArrayList<Reference> references = new ArrayList<Reference>();
		Set<Reference> skipList = new HashSet<Reference>();
		Set<Cluster> clusters = new HashSet<Cluster>();
		
		for(AnnotationEntity ae : getLinkolnDocument().getAnnotationEntities()) {
			
			if(ae instanceof Reference && ((Reference) ae).getAlias() == null) {
				
				references.add((Reference) ae);
			}
		}

		for(Reference ref : references) {
			
			if(skipList.contains(ref)) continue;
			
			skipList.add(ref);
			
			Cluster cluster = ref.getCluster(); 
					
			if(cluster == null) {
				
				cluster = new Cluster();
				clusters.add(cluster);
				cluster.add(ref);
				ref.setCluster(cluster);
			}
			
			for(Reference obj : references) {
				
				if(skipList.contains(obj)) continue;
				
				if(areCompatibles(ref, obj)) {
					
					//System.out.println("COMPATIBLE\n" + ref.toString() + "\nWITH\n" + obj.toString());
					
					cluster.add(obj);
					obj.setCluster(cluster);
					
					skipList.add(obj);
				}
			}
		}
		
		return false;
	}

	private boolean areCompatibles(Reference ref1, Reference ref2) {

		/*
		 * 
		 * TODO
		 * 
		 * Questa roba testa solo la condizione necessaria, non quella sufficiente
		 * Non solo non ci devono essere features incompatibili, 
		 * ci devono anche essere delle feature in comune uguali per essere compabili.
		 * 
		 * Numero/Anno
		 * Auth/Numero
		 * Auth/Data
		 * Tipo/Numero
		 * Tipo/Data
		 * 
		 * 
		 * Per ogni feature, vedere se è incompabile e se è uguale
		 * 
		 */
		
		//CONDIZIONI NECESSARIE
		
		//boolean sameNumberYear = false;
		boolean sameNumber = false;
		boolean sameYear = false;
		boolean sameDate = false;
		boolean sameAuth = false;
		boolean sameDocType = false;
		boolean sameCaseNumber = false;
		
		//if(ref1.getNumber(true) != null && ref2.getNumber(true) != null && !ref1.getNumberValue(true).equals(ref2.getNumberValue(true))) return false;
		if(ref1.getNumber(true) != null && ref2.getNumber(true) != null) {
			
			if(ref1.getNumberValue(true).equals(ref2.getNumberValue(true))) {
				
				sameNumber = true;
				
			} else {
				
				return false;
			}
		}
		
		//if(ref1.getYear(true) != null && ref2.getYear(true) != null && !ref1.getYear(true).getValue().equals(ref2.getYear(true).getValue())) return false;
		if(ref1.getYear(true) != null && ref2.getYear(true) != null) {
			
			if(ref1.getYear(true).getValue().equals(ref2.getYear(true).getValue())) {
				
				sameYear = true;
				
			} else {
				
				return false;
			}
		}
		
		
		//DECREE - MIN_DECREE - DECREE_LAW
		/*
		if(ref1.getDocumentType(true) != null && ref2.getDocumentType(true) != null &&
			ref1.getDocumentType(true).getValue().indexOf(ref2.getDocumentType(true).getValue()) == -1 && 
					ref2.getDocumentType(true).getValue().indexOf(ref1.getDocumentType(true).getValue()) == -1) {
			
			return false;
		}
		*/
		if(ref1.getDocumentType(true) != null && ref2.getDocumentType(true) != null) {
			
			if(ref1.getDocumentType(true).getValue().indexOf(ref2.getDocumentType(true).getValue()) > -1 || 
					ref2.getDocumentType(true).getValue().indexOf(ref1.getDocumentType(true).getValue()) > -1) {
				
				sameDocType = true;
				
			} else {
				
				return false;				
			}
		}

		//IT_TAR - IT_TAR_FI, IT_CONT - IT_CONT_LAZ - IT_TRB IT_TRB_BO IT_TRB_BO_L347 
		/*
		if(ref1.getAuthorityValue(true) != null && ref2.getAuthorityValue(true) != null &&
				ref1.getAuthorityValue(true).indexOf(ref2.getAuthorityValue(true)) == -1 && 
						ref2.getAuthorityValue(true).indexOf(ref1.getAuthorityValue(true)) == -1) {
			
			return false;
		}
		*/
		if(ref1.getAuthorityValue(true) != null && ref2.getAuthorityValue(true) != null) {
			
			if(ref1.getAuthorityValue(true).indexOf(ref2.getAuthorityValue(true)) > -1 || 
					ref2.getAuthorityValue(true).indexOf(ref1.getAuthorityValue(true)) > -1) {
		
				sameAuth = true;
				
			} else {
				
				return false;
			}
		}

			
		//if(ref1.getRelatedValue("DOC_DATE") != null && ref2.getRelatedValue("DOC_DATE") != null && !ref1.getRelatedValue("DOC_DATE").equals(ref2.getRelatedValue("DOC_DATE"))) return false;
		if(ref1.getRelatedValue("DOC_DATE") != null && ref2.getRelatedValue("DOC_DATE") != null) {
			
			if(ref1.getRelatedValue("DOC_DATE").equals(ref2.getRelatedValue("DOC_DATE"))) {
				
				sameDate = true;
				
			} else {
				
				return false;
			}
		}
		
		
		//if(ref1.getRelatedValue("CASENUMBER") != null && ref2.getRelatedValue("CASENUMBER") != null && !ref1.getRelatedValue("CASENUMBER").equals(ref2.getRelatedValue("CASENUMBER"))) return false;
		if(ref1.getRelatedValue("CASENUMBER") != null && ref2.getRelatedValue("CASENUMBER") != null) {
			
			if(ref1.getRelatedValue("CASENUMBER").equals(ref2.getRelatedValue("CASENUMBER"))) {
				
				sameCaseNumber = true;
				
			} else {
				
				return false;
			}
		}
		
		
		//CONDIZIONI SUFFICIENTI
		
		if(sameYear && sameNumber) return true;
		
		if(sameAuth && sameNumber) return true;
		
		if(sameDocType && sameNumber) return true;
		
		if(sameAuth && sameDate) return true;
		
		if(sameDocType && sameDate) return true;
		
		if(sameCaseNumber) return true;
		
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
}
