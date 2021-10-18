package it.cnr.igsg.linkoln.reference;

import it.cnr.igsg.linkoln.LinkolnDocument;
import it.cnr.igsg.linkoln.entity.Reference;

interface IdentifierGeneration {

	LinkolnIdentifier getLinkolnIdentifier(LinkolnDocument linkolnDocument, Reference annotationEntity);
	
}
