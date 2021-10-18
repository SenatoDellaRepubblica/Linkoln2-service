package it.cnr.igsg.linkoln.reference;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import it.cnr.igsg.linkoln.entity.AnnotationEntity;
import it.cnr.igsg.linkoln.entity.Reference;

public class Cluster {

	private Set<Reference> references = new HashSet<Reference>();
	
	private String type = null; //LegislationReference, CaseLawReference, EuropeanLegislationReference, etc.
	private AnnotationEntity auth = null;
	private AnnotationEntity section = null;
	private AnnotationEntity detAuth = null;
	private AnnotationEntity docType = null;
	private AnnotationEntity number = null;
	private AnnotationEntity year = null;
	private AnnotationEntity date = null;
	private AnnotationEntity caseNumber = null;
	private AnnotationEntity applicant = null;
	private AnnotationEntity defendant = null;
	private AnnotationEntity region = null;
	private AnnotationEntity city = null;
	private AnnotationEntity subject = null;

	public Collection<Reference> getReferences() {
		
		return references;
	}

	public void add(Reference reference) {
		
		references.add(reference);
		
		if(type == null || reference.getClass().getSimpleName().length() > type.length()) {
			
			this.type = reference.getClass().getSimpleName();
		}
		
		if(reference.getAuthority() != null) {
		
			if(auth == null || reference.getAuthorityValue().length() > auth.getValue().length()) {
				
				auth = reference.getAuthority();
			}
		}
		
		if(reference.getDocumentType() != null) {
		
			if(docType == null || reference.getDocumentType().getValue().length() > docType.getValue().length()) {
				
				docType = reference.getDocumentType();
			}
		}
		
		if(reference.getRelatedEntity("CL_AUTH_SECTION") != null) {
			
			if(section == null || reference.getRelatedValue("CL_AUTH_SECTION").length() > section.getValue().length()) {
				
				section = reference.getRelatedEntity("CL_AUTH_SECTION");
			}
		}
		
		if(reference.getRelatedEntity("CL_DETACHED_SECTION") != null) {
			
			if(detAuth == null || reference.getRelatedValue("CL_DETACHED_SECTION").length() > detAuth.getValue().length()) {
				
				detAuth = reference.getRelatedEntity("CL_DETACHED_SECTION");
			}
		}
		
		if(reference.getNumber() != null) {
			
			if(number == null || reference.getNumber().getValue().length() > number.getValue().length()) {
				
				number = reference.getNumber();
			}
		}
		
		if(reference.getYear() != null) {
			
			if(year == null || reference.getYear().getValue().length() > year.getValue().length()) {
				
				year = reference.getYear();
			}
		}
		
		if(reference.getRelatedEntity("DOC_DATE") != null) {
			
			if(date == null || reference.getRelatedEntity("DOC_DATE").getValue().length() > date.getValue().length()) {
				
				date = reference.getRelatedEntity("DOC_DATE");
			}
		}
		
		if(reference.getRelatedEntity("CASENUMBER") != null) {
			
			if(caseNumber == null || reference.getRelatedEntity("CASENUMBER").getValue().length() > caseNumber.getValue().length()) {
				
				caseNumber = reference.getRelatedEntity("CASENUMBER");
			}
		}
		
		if(reference.getApplicant() != null) {
			
			if(applicant == null || reference.getApplicantValue().length() > applicant.getValue().length()) {
				
				applicant = reference.getApplicant();
			}
		}
		
		if(reference.getDefendant() != null) {
			
			if(defendant == null || reference.getDefendantValue().length() > defendant.getValue().length()) {
				
				defendant = reference.getDefendant();
			}
		}

		if(reference.getRegion() != null) {
			
			if(region == null || reference.getRegion().getValue().length() > region.getValue().length()) {
				
				region = reference.getRegion();
			}
		}
		
		if(reference.getCity() != null) {
			
			if(city == null || reference.getCity().getValue().length() > city.getValue().length()) {
				
				city = reference.getCity();
			}
		}
		
		if(reference.getSubject() != null) {
			
			if(subject == null || reference.getSubject().getValue().length() > subject.getValue().length()) {
				
				subject = reference.getSubject();
			}
		}
	}


	public AnnotationEntity getAuth() {
		return auth;
	}

	public AnnotationEntity getSection() {
		return section;
	}

	public AnnotationEntity getDetAuth() {
		return detAuth;
	}

	public AnnotationEntity getDocType() {
		return docType;
	}

	public AnnotationEntity getNumber() {
		return number;
	}

	public AnnotationEntity getYear() {
		return year;
	}

	public AnnotationEntity getDate() {
		return date;
	}

	public AnnotationEntity getCaseNumber() {
		return caseNumber;
	}

	public AnnotationEntity getApplicant() {
		return applicant;
	}

	public AnnotationEntity getDefendant() {
		return defendant;
	}

	public AnnotationEntity getRegion() {
		return region;
	}

	public AnnotationEntity getCity() {
		return city;
	}

	public AnnotationEntity getSubject() {
		return subject;
	}

	public String getType() {
		return type;
	}
	
	
	
}
