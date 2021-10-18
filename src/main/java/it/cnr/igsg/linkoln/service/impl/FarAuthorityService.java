package it.cnr.igsg.linkoln.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.cnr.igsg.linkoln.Linkoln;
import it.cnr.igsg.linkoln.entity.AnnotationEntity;
import it.cnr.igsg.linkoln.entity.Authority;
import it.cnr.igsg.linkoln.entity.Reference;
import it.cnr.igsg.linkoln.service.LinkolnService;

public class FarAuthorityService extends LinkolnService {

	@Override
	public String language() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String version() {
		
		return "0.1";
	}
	
	private static final int MAX_DIST = 512; //128;

	@Override
	protected boolean run() {

		ArrayList<Reference> references = new ArrayList<Reference>();
		ArrayList<Authority> authorities = new ArrayList<Authority>();
		
		for(AnnotationEntity ae : getLinkolnDocument().getAnnotationEntities()) {
			
			if(ae instanceof Reference) {
				
				Reference ref = (Reference) ae;
				
				if(ref.getAlias() != null) continue;
				
				if(ref.getAuthority(true) != null) continue;
				
				if(ref.getDocumentType(true) != null) {
					if( !ref.getDocumentType(true).getValue().equals("JUDGMENT") && !ref.getDocumentType(true).getValue().equals("ORDER")) {
						
						continue;
					}
				}
				
				//if(ref.getApplicant(true) != null) continue;
				
				if(ref.getNumber(true) != null && ref.getYear(true) != null) {
					
					references.add((Reference) ae);
					continue;
				}
				
				if(ref.getDocumentType(true) != null && ref.getNumber(true) != null) {
					
					references.add((Reference) ae);
					continue;
				}

				if(ref.getDocumentType(true) != null && ref.getDate(true) != null) {
					
					references.add((Reference) ae);
					continue;
				}

				if(ref.getNumber(true) != null && ref.getDate(true) != null) {
					
					references.add((Reference) ae);
					continue;
				}
		
			}
			
			if(ae instanceof Authority && (ae.getValue().equalsIgnoreCase("IT_CASS") || ae.getValue().equalsIgnoreCase("IT_COST")) ) {
				
				authorities.add((Authority) ae);
			}
		}

		for(Reference ref : references) {
			
			Authority authority = getClosestAuthority(authorities, ref);
			
			if(authority != null && 
					(ref.getPosition() - authority.getPosition()) < MAX_DIST && (ref.getPosition() - authority.getPosition()) > -1 &&  
						authority.getPosition() > getLinkolnDocument().getPreviousFullStop(ref.getPosition())) {
				
				ref.addRelatedEntity(authority);
			}
		}
		
		return false;
	}
	
	private Authority getClosestAuthority(ArrayList<Authority> authorities, Reference ref) {
		
		Authority auth = null;
		
		Integer distance = null;
		
		for(Authority a : authorities) {
			
			int currentDistance = ref.getPosition() - a.getPosition();
			
			if(distance == null || (currentDistance > 0 && currentDistance < distance)) {
				
				auth = a;
				distance = currentDistance;
			}
		}
		
		return auth;
	}
	
}
