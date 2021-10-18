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

import it.cnr.igsg.linkoln.Linkoln;
import it.cnr.igsg.linkoln.LinkolnDocument;
import it.cnr.igsg.linkoln.entity.AnnotationEntity;
import it.cnr.igsg.linkoln.entity.EuropeanLegislationReference;
import it.cnr.igsg.linkoln.entity.LegislationReference;
import it.cnr.igsg.linkoln.entity.Reference;
import it.cnr.igsg.linkoln.service.impl.Util;

public class UrnIdentifierGeneration implements IdentifierGeneration {

	@Override
	public LinkolnIdentifier getLinkolnIdentifier(LinkolnDocument linkolnDocument, Reference annotationEntity) {

		if( !(annotationEntity instanceof LegislationReference)) return null;
		if( annotationEntity instanceof EuropeanLegislationReference) return null;
		
		//if( !linkolnDocument.getLanguage().equals("it")) return null; //TODO multi lang and jurisdiction support

		return process((LegislationReference) annotationEntity);
	}

	private LinkolnIdentifier process(LegislationReference entity) {
		
		LinkolnIdentifier linkolnIdentifier = new LinkolnIdentifier();
		linkolnIdentifier.setType(Identifiers.URN);
		
		String urnNir = "urn:nir:";
		String urlPrefix = "http://www.normattiva.it/uri-res/N2Ls?";
		
		String urnAuth = "";
		String urnType = "";
		String urnDate = "";
		String urnNumber = "";
		//String urnVersion = "";
		
		
		
		//EVENTUALE PARTIZIONE
		AnnotationEntity partition = entity.getRelatedEntity("LEG_PARTITION");
		String urnPartition = "";
		if(partition != null) {
			
			urnPartition = partition.getValue().toLowerCase();
			
			urnPartition = urnPartition.replaceAll("-", "");
			urnPartition = urnPartition.replaceAll("_", "-");
			urnPartition = urnPartition.replaceAll("item", "num");
			
			if( !urnPartition.equals("")) {
				
				urnPartition = "~" + urnPartition;
			}
		}
		

		//ALIAS LEGISLATIVI
		AnnotationEntity alias = entity.getRelatedEntity("LEG_ALIAS");
		
		if(alias != null) {
			
			String docUrn = getAliasUrn(alias);
			
			if(docUrn.equals("")) {
				
				//Alias legislativo non presente su normattiva
				if(alias.getValue().equals("CONV_EU_DIR_UOMO")) {
					
					linkolnIdentifier.setCode("");
					linkolnIdentifier.setUrl("https://www.echr.coe.int/Documents/Convention_ITA.pdf");
					
					return linkolnIdentifier;
				}
			}
			
			urnNir += docUrn + urnPartition;
			
			linkolnIdentifier.setCode(urnNir);
			linkolnIdentifier.setUrl(urlPrefix + urnNir);
			
			return linkolnIdentifier;
		}
		
		
		//RIFERIMENTI LEGISLATIVI STANDARD
		/*
		AnnotationEntity docType = entity.getRelatedEntity("LEG_DOCTYPE");
		
		if(docType == null) {
			
			docType = entity.getRelatedEntity("DOCTYPE");
		}
		*/
		AnnotationEntity docType = entity.getDocumentType();
		
		//AnnotationEntity legAuth = entity.getRelatedEntity("LEG_AUTH");
		AnnotationEntity legAuth = entity.getAuthority();
		
		//AnnotationEntity number = entity.getRelatedEntity("NUMBER");
		AnnotationEntity number = entity.getNumber();
		
		//AnnotationEntity year = null;
		AnnotationEntity year = entity.getYear();
		
		//AnnotationEntity date = entity.getRelatedEntity("DOC_DATE");
		AnnotationEntity date = entity.getDate();
		
		
		if(docType != null) {

			if(docType.getValue().equals("LAW")) { urnType = "legge"; urnAuth = "stato"; }
			if(docType.getValue().equals("DECREE_LAW")) { urnType = "decreto.legge"; urnAuth = "stato"; }
			if(docType.getValue().equals("LGS_DECREE")) { urnType = "decreto.legislativo"; urnAuth = "stato"; }
			if(docType.getValue().equals("CONST_LAW")) { urnType = "legge.costituzionale"; urnAuth = "stato"; }
			if(docType.getValue().equals("ROYAL_DECREE")) { urnType = "regio.decreto"; urnAuth = "stato"; }
			if(docType.getValue().equals("ROYAL_DECREE_LAW")) { urnType = "regio.decreto.legge"; urnAuth = "stato"; }
			if(docType.getValue().equals("ROYAL_LGS_DECREE")) { urnType = "regio.decreto.legislativo"; urnAuth = "stato"; }
			if(docType.getValue().equals("LGS_DECREE_LGT")) { urnType = "decreto.legislativo.luogotenenziale"; urnAuth = "stato"; }
			if(docType.getValue().equals("DECREE_LAW_LGT")) { urnType = "decreto.legge.luogotenenziale"; urnAuth = "stato"; }
			if(docType.getValue().equals("DECREE")) { urnType = "decreto"; }
			if(docType.getValue().equals("MIN_DECREE") || docType.getValue().equals("INTERMIN_DECREE")) { urnType = "decreto"; urnAuth = "ministero."; }

			//OPCM
			if(docType.getValue().equals("ORDER") && legAuth != null && legAuth.getValue().equals("IT_PRESIDENT_COUNCIL")) urnType = "ordinanza";
			
			/*
			if(docType.getValue().equals("DISEGNO_LEGGE")) urnType = "disegno.legge";
			if(docType.getValue().equals("PROGETTO_LEGGE")) urnType = "progetto.legge";
			*/
			
			if(legAuth != null && legAuth.getValue().startsWith("IT_MIN")) {
			
				if(docType.getValue().equals("ORDER")) urnType = "ordinanza";
				if(docType.getValue().equals("CIRC")) urnType = "circolare";
				if(docType.getValue().equals("REGULATION")) urnType = "regolamento";
				if(docType.getValue().equals("OPINION")) urnType = "parere";
				if(docType.getValue().equals("RESOLUTION")) urnType = "risoluzione";
				if(docType.getValue().equals("PROVISION")) urnType = "provvedimento";
			}
						
			if(docType.getValue().equals("REGIONAL_LAW")) { urnType = "legge"; urnAuth = "regione."; }
			if(docType.getValue().equals("PROVINCIAL_LAW")) { urnType = "legge"; urnAuth = "provincia."; }
			if(docType.getValue().equals("REGIONAL_REGULATION")) { urnType = "regolamento"; urnAuth = "regione."; }
			if(docType.getValue().equals("PROV_REGULATION")) { urnType = "regolamento"; urnAuth = "provincia."; }
			if(docType.getValue().equals("MUNICIP_REGULATION")) { urnType = "regolamento"; urnAuth = "comune."; }
			
			if(docType.getValue().equals("MIN_ORDER") || docType.getValue().equals("INTERMIN_ORDER")) { urnType = "ordinanza"; urnAuth = "ministero."; }
			if(docType.getValue().equals("MIN_REGULATION") || docType.getValue().equals("INTERMIN_REGULATION")) { urnType = "regolamento"; urnAuth = "ministero."; }
			if(docType.getValue().equals("MIN_CIRCULAR") || docType.getValue().equals("INTERMIN_CIRCULAR")) { urnType = "circolare"; urnAuth = "ministero."; }
			
			/*
			if(docType.getValue().equals("DETERMINA")) urnType = "determina";
			if(docType.getValue().equals("DELIBERA")) urnType = "delibera";
			if(docType.getValue().equals("DETERMINA_INTERCOM")) { urnType = "determina"; urnAuth = "commissione."; }
			if(docType.getValue().equals("DELIBERA_INTERCOM")) { urnType = "delibera"; urnAuth = "commissione."; }
			
			if(docType.getValue().equals("DECREE_DIRIGENZIALE")) urnType = "decreto";
			*/
		}
		
		if(Linkoln.STRICT && urnType.equals("")) {
			
			return null;
		}
		
		if(legAuth != null) { // && urnAuth.equals("")) { <-- "decreto" può avere una specifica authority 
			
			if(legAuth.getValue().equals("IT_PRESIDENT_COUNCIL")) urnAuth = "presidente.consiglio";
			if(legAuth.getValue().equals("IT_PRESIDENT_REPUBLIC")) urnAuth = "presidente.repubblica";
			if(legAuth.getValue().equals("IT_CAPO_GOV")) urnAuth = "capo.governo";
			if(legAuth.getValue().equals("IT_CAPO_PROVV_STATO")) urnAuth = "capo.provvisorio.stato";
			
			if(legAuth.getValue().equals("IT_REGIONAL_COUNCIL")) urnAuth = "consiglio.regione.";
			if(legAuth.getValue().equals("IT_PROVINCIAL_COUNCIL")) urnAuth = "consiglio.provincia.";
			if(legAuth.getValue().equals("IT_MUNICIPAL_COUNCIL")) urnAuth = "consiglio.comune.";
			
			if(legAuth.getValue().equals("IT_REGIONAL_GIUNTA")) urnAuth = "giunta.regione.";
			if(legAuth.getValue().equals("IT_PROVINCIAL_GIUNTA")) urnAuth = "giunta.provincia.";
			if(legAuth.getValue().equals("IT_MUNICIPAL_GIUNTA")) urnAuth = "giunta.comune.";
			
			if(urnAuth.equals("") || urnAuth.endsWith("ministero.")) {
				
				urnAuth = legAuth.getValue().toLowerCase().replaceAll("_", ".");
			}
		}
		
		if(number != null) {
			
			urnNumber = Util.readFirstNumber(number.getValue());
			
			//year = number.getRelatedEntity("YEAR");
		}
		
		if(date != null) {
			
			urnDate = date.getValue();
		
		}
		
		if(date == null && year != null) {
			
			urnDate = year.getValue();
		}
		
		//Non produrre un identificatore Urn Nir se manca l'anno o il numero
		if(urnDate.equals("") || urnNumber.equals("")) {
			
			return null;
		}
			
		urnNir += urnAuth + ":" + urnType + ":" + urnDate + ";" + urnNumber + urnPartition; 
		
		linkolnIdentifier.setCode(urnNir);
		linkolnIdentifier.setUrl(urlPrefix + urnNir);
		
		return linkolnIdentifier;
	}
	
	private String getAliasUrn(AnnotationEntity alias) {
		
		if(alias.getValue().equals("IT_COST")) return "stato:costituzione:1947-12-27";
		
		if(alias.getValue().equals("IT_COD_CIV")) return "stato:regio.decreto:1942-03-16;262:2";
		if(alias.getValue().equals("IT_COD_PEN")) return "stato:regio.decreto:1930-10-19;1398:1";
		if(alias.getValue().equals("IT_COD_PROC_CIV")) return "stato:regio.decreto:1940-10-28;1443:1";
		if(alias.getValue().equals("IT_COD_PROC_PEN")) return "stato:decreto.del.presidente.della.repubblica:1988-09-22;447";
	
		/*
		http://www.normattiva.it/uri-res/N2Ls?urn:nir:stato:regio.decreto:1941-08-25;1368
		REGIO DECRETO 18 dicembre 1941, n. 1368
		Disposizioni per l'attuazione del Codice di procedura civile e disposizioni transitorie. (041U1368) (GU n.302 del 24-12-1941 - Suppl. Ordinario n. 302 )
		note:
		Entrata in vigore del provvedimento: 08/01/1942
		
		http://www.normattiva.it/uri-res/N2Ls?urn:nir:stato:regio.decreto:1942-03-30;318
		REGIO DECRETO 30 marzo 1942, n. 318
		Disposizioni per l'attuazione del Codice civile e disposizioni transitorie. (042U0318) (GU n.91 del 17-4-1942 - Suppl. Ordinario )
		note:
		Entrata in vigore del provvedimento: 19/4/1942.
		
		http://www.normattiva.it/uri-res/N2Ls?urn:nir:ministero.grazia.e.giustizia:decreto:1989-09-30;334
		MINISTERO DI GRAZIA E GIUSTIZIA
		DECRETO 30 settembre 1989, n. 334
		Regolamento per l'esecuzione del codice di procedura penale. (GU n.233 del 5-10-1989 )
		note:
		Entrata in vigore del decreto: 24/10/1989
		 */
		
		
		
		//TODO disp.att. CODICE CIVILE - a quale deve puntare? ce ne sono due: R.D. 30 marzo 1942, n.318 oppure R.D. 18 dicembre 1941 n. 1368 ???
		if(alias.getValue().equals("IT_DISP_ATT_COD_CIV")) return "stato:regio.decreto:1942-03-30;318";
		//if(alias.getValue().equals("IT_DISP_ATT_COD_CIV")) return "stato:regio.decreto:1941-12-18;1368:1";
		
		if(alias.getValue().equals("IT_DISP_ATT_COD_PROC_CIV")) return "stato:regio.decreto:1941-08-25;1368:1"; //stato:regio.decreto:1941-08-25;1368
		if(alias.getValue().equals("IT_DISP_ATT_COD_PROC_PEN")) return "stato:decreto.legislativo:1989-07-28;271"; //D.lgs. 28 luglio 1989 n.271 //stato:decreto.legislativo:1989-07-28;271
		
		/*
		http://www.normattiva.it/uri-res/N2Ls?urn:nir:stato:relazione.e.regio.decreto:1941-02-20;303
		REGIO DECRETO 20 febbraio 1941, n. 303
		Codici penali militari di pace e di guerra (041U0303) (GU n.107 del 6-5-1941 - Suppl. Ordinario )
		note:
		Entrata in vigore del provvedimento: 21/05/1941
		
		http://www.normattiva.it/uri-res/N2Ls?urn:nir:stato:regio.decreto:1941-09-09;1023
		REGIO DECRETO 9 settembre 1941, n. 1023
		Disposizioni di coordinamento, transitorie e di attuazione dei Codici penali militari di pace e di guerra. (041U1023) (GU n.229 del 27-9-1941 - Suppl. Ordinario n. 229 )
		note:
		Entrata in vigore del provvedimento: 01/10/1941
		 */
		if(alias.getValue().equals("IT_COD_MIL_GUERRA")) return "stato:relazione.e.regio.decreto:1941-02-20;303";
		if(alias.getValue().equals("IT_COD_MIL_PACE")) return "stato:relazione.e.regio.decreto:1941-02-20;303";
		
		/*
		http://www.normattiva.it/uri-res/N2Ls?urn:nir:stato:decreto.del.presidente.della.repubblica:1952-02-15;328
		DECRETO DEL PRESIDENTE DELLA REPUBBLICA 15 febbraio 1952, n. 328
		Approvazione del Regolamento per l'esecuzione del Codice della navigazione (Navigazione marittima). (GU n.94 del 21-4-1952 - Suppl. Ordinario )
		
		http://www.normattiva.it/uri-res/N2Ls?urn:nir:stato:decreto.del.presidente.della.repubblica:1992-12-16;495
		DECRETO DEL PRESIDENTE DELLA REPUBBLICA 16 dicembre 1992, n. 495
		Regolamento di esecuzione e di attuazione del nuovo codice della strada. (GU n.303 del 28-12-1992 - Suppl. Ordinario n. 134 )
		note:
		Entrata in vigore del decreto: 1-1-1993
		
		http://www.normattiva.it/uri-res/N2Ls?urn:nir:ministero.sviluppo.economico:decreto:2010-01-13;33
		MINISTERO DELLO SVILUPPO ECONOMICO
		DECRETO 13 gennaio 2010, n. 33
		Regolamento di attuazione del Codice della proprieta' industriale, adottato con decreto legislativo 10 febbraio 2005, n. 30. (10G0044) (GU n.56 del 9-3-2010 - Suppl. Ordinario n. 48 )
		note:
		Entrata in vigore del provvedimento: 10/3/2010
		
		http://www.normattiva.it/uri-res/N2Ls?urn:nir:stato:decreto.legislativo:2006-04-03;152
		DECRETO LEGISLATIVO 3 aprile 2006, n. 152
		Norme in materia ambientale. (GU n.88 del 14-4-2006 - Suppl. Ordinario n. 96 )
		note:
		Entrata in vigore del provvedimento: 29/4/2006, ad eccezione delle disposizioni della Parte seconda che entrano in vigore il 12/8/2006.
		
		http://www.normattiva.it/uri-res/N2Ls?urn:nir:stato:decreto.legislativo:2016-04-18;50
		DECRETO LEGISLATIVO 18 aprile 2016, n. 50
		((Codice dei contratti pubblici)). (16G00062) (GU n.91 del 19-4-2016 - Suppl. Ordinario n. 10 )
		note:
		Entrata in vigore del provvedimento: 19/04/2016
		
		http://www.normattiva.it/uri-res/N2Ls?urn:nir:stato:decreto.legislativo:2006-04-12;163
		DECRETO LEGISLATIVO 12 aprile 2006, n. 163
		Codice dei contratti pubblici relativi a lavori, servizi e forniture in attuazione delle direttive 2004/17/CE e 2004/18/CE. (GU n.100 del 2-5-2006 - Suppl. Ordinario n. 107 )
		note:
		Entrata in vigore del decreto: 1-7-2006
		
		http://www.normattiva.it/uri-res/N2Ls?urn:nir:stato:decreto.del.presidente.della.repubblica:2010-10-05;207
		DECRETO DEL PRESIDENTE DELLA REPUBBLICA 5 ottobre 2010, n. 207
		Regolamento di esecuzione ed attuazione del decreto legislativo 12 aprile 2006, n. 163, recante «Codice dei contratti pubblici relativi a lavori, servizi e forniture in attuazione delle direttive 2004/17/CE e 2004/18/CE». (10G0226) (GU n.288 del 10-12-2010 - Suppl. Ordinario n. 270 )
		note:
		Entrata in vigore del provvedimento 08/06/2011, ad esclusione degli articoli 73 e 74 che entrano in vigore il 25/12/2010.
		 */
		
		if(alias.getValue().equals("IT_COD_NAV")) return "stato:regio.decreto:1942-03-30;327";
		if(alias.getValue().equals("IT_COD_STRADA")) return "stato:decreto.legislativo:1992-04-30;285";
		if(alias.getValue().equals("IT_COD_PROT_DATI")) return "stato:decreto.legislativo:2003-06-30;196";
		if(alias.getValue().equals("IT_COD_COM_ELETTR")) return "stato:decreto.legislativo:2003-08-01;259";
		if(alias.getValue().equals("IT_COD_BENI_CULT")) return "stato:decreto.legislativo:2004-01-22;42";
		if(alias.getValue().equals("IT_COD_PROP_IND")) return "stato:decreto.legislativo:2005-02-10;30";
		if(alias.getValue().equals("IT_COD_AMM_DIG")) return "stato:decreto.legislativo:2005-03-07;82";
		if(alias.getValue().equals("IT_COD_NAUTICA_DIPORTO")) return "stato:decreto.legislativo:2005-07-18;171";
		if(alias.getValue().equals("IT_COD_CONSUMO")) return "stato:decreto.legislativo:2005-09-06;206";
		if(alias.getValue().equals("IT_COD_ASSIC_PRIV")) return "stato:decreto.legislativo:2005-09-07;209";
		if(alias.getValue().equals("IT_COD_CONTR_PUB")) return "stato:decreto.legislativo:2006-04-12;163"; //oppure "stato:decreto.legislativo:2016-04-18;50"
		if(alias.getValue().equals("IT_COD_PARI_OPP")) return "stato:decreto.legislativo:2006-04-11;198";
		if(alias.getValue().equals("IT_COD_ORD_MIL")) return "stato:decreto.legislativo:2010-03-15;66";
		if(alias.getValue().equals("IT_COD_PROC_AMM")) return "stato:decreto.legislativo:2010-07-02;104";
		if(alias.getValue().equals("IT_COD_DEON_FORENSE")) return "";
		
		/*
		CODICE DEL PROCESSO TRIBUTARIO
		http://www.normattiva.it/uri-res/N2Ls?urn:nir:stato:decreto.legislativo:1992-12-31;546
		DECRETO LEGISLATIVO 31 dicembre 1992, n. 546
		Disposizioni sul processo tributario in attuazione della delega al Governo contenuta nell'art. 30 della legge 30 dicembre 1991, n. 413. (GU n.9 del 13-1-1993 - Suppl. Ordinario n. 8 )
		note:
		Entrata in vigore del decreto: 15-1-1993
		
		CODICE DEL TURISMO
		http://www.normattiva.it/uri-res/N2Ls?urn:nir:stato:decreto.legislativo:2011-05-23;79
		DECRETO LEGISLATIVO 23 maggio 2011, n. 79
		Codice della normativa statale in tema di ordinamento e mercato del turismo, a norma dell'articolo 14 della legge 28 novembre 2005, n. 246, nonche' attuazione della direttiva 2008/122/CE, relativa ai contratti di multiproprieta', contratti relativi ai prodotti per le vacanze di lungo termine, contratti di rivendita e di scambio. (11G0123) (GU n.129 del 6-6-2011 - Suppl. Ordinario n. 139 )
		note:
		Entrata in vigore del provvedimento: 21/06/2011
		
		CODICE ANTIMAFIA
		http://www.normattiva.it/uri-res/N2Ls?urn:nir:stato:decreto.legislativo:2011-09-06;159
		DECRETO LEGISLATIVO 6 settembre 2011, n. 159
		Codice delle leggi antimafia e delle misure di prevenzione, nonche' nuove disposizioni in materia di documentazione antimafia, a norma degli articoli 1 e 2 della legge 13 agosto 2010, n. 136. (11G0201) (GU n.226 del 28-9-2011 - Suppl. Ordinario n. 214 )
		note:
		Entrata in vigore del provvedimento: 13/10/2011.
		Le disposizioni del libro II, capi I, II, III e IV entrano in vigore secondo quanto disposto dall'art. 119.
		
		CODICE DI GIUSTIZIA CONTABILE
		http://www.normattiva.it/uri-res/N2Ls?urn:nir:stato:decreto.legislativo:2016-08-26;174
		DECRETO LEGISLATIVO 26 agosto 2016, n. 174
		Codice di giustizia contabile, adottato ai sensi dell'articolo 20 della legge 7 agosto 2015, n. 124. (16G00187) (GU n.209 del 7-9-2016 - Suppl. Ordinario n. 41 )
		note:
		Entrata in vigore del provvedimento: 07/10/2016
		
		CODICE DEL TERZO SETTORE
		http://www.normattiva.it/uri-res/N2Ls?urn:nir:stato:decreto.legislativo:2017-07-03;117
		DECRETO LEGISLATIVO 3 luglio 2017, n. 117
		Codice del Terzo settore, a norma dell'articolo 1, comma 2, lettera b), della legge 6 giugno 2016, n. 106. (17G00128) (GU n.179 del 2-8-2017 - Suppl. Ordinario n. 43 )
		note:
		Entrata in vigore del provvedimento: 03/08/2017
		 */

		if(alias.getValue().equals("IT_TU_DOC_AMM")) return "presidente.repubblica:decreto:2000-12-28;445";
		if(alias.getValue().equals("IT_TU_ESPR_PU")) return "presidente.repubblica:decreto:2001-06-08;327";
		if(alias.getValue().equals("IT_TU_EDIL")) return "presidente.repubblica:decreto:2001-06-06;380";
		if(alias.getValue().equals("IT_TU_CIRC_EU")) return "presidente.repubblica:decreto:2002-01-18;54";
		if(alias.getValue().equals("IT_TU_SPESE_GIUST")) return "presidente.repubblica:decreto:2002-05-30;115";
		if(alias.getValue().equals("IT_TU_CASELL_GIUDIZ")) return "presidente.repubblica:decreto:2002-11-14;313";
		if(alias.getValue().equals("IT_TU_DEB_PUBBL")) return "presidente.repubblica:decreto:2003-12-30;398";
		if(alias.getValue().equals("IT_TU_ORD_MIL")) return "presidente.repubblica:decreto:2010-03-15;90";
		if(alias.getValue().equals("IT_TU_LEGGI_CDS")) return "stato:regio.decreto:1924-06-26;1054";
		if(alias.getValue().equals("IT_TU_PUBBL_SICUR")) return "stato:regio.decreto:1931-06-18;773";
		if(alias.getValue().equals("IT_TU_PESCA")) return "stato:regio.decreto:1931-10-08;1604";
		if(alias.getValue().equals("IT_TU_AVV_STATO")) return "stato:regio.decreto:1933-10-30;1612";
		if(alias.getValue().equals("IT_TU_ACQUE")) return "stato:regio.decreto:1933-12-11;1775";
		if(alias.getValue().equals("IT_TU_CORTE_CONTI")) return "stato:regio.decreto:1934-07-12;1214";
		if(alias.getValue().equals("IT_TU_SANITARIE")) return "stato:regio.decreto:1934-07-27;1265";
		if(alias.getValue().equals("IT_TU_IMP_REGISTRO")) return "stato:regio.decreto:1986-04-26;131";
		if(alias.getValue().equals("IT_TU_IMP_REDDITO")) return "presidente.repubblica:decreto:1986-12-22;917";
		if(alias.getValue().equals("IT_TU_SEQUESTRO")) return "presidente.repubblica:decreto:1950-01-05;180";
		if(alias.getValue().equals("IT_TU_IMPIEGATI")) return "presidente.repubblica:decreto:1957-03-30;3";
		if(alias.getValue().equals("IT_TU_BANCARIO")) return "stato:decreto.legislativo:1993-09-01;385";
		if(alias.getValue().equals("IT_TU_FINANZE")) return "stato:decreto.legislativo:1998;058";
		if(alias.getValue().equals("IT_TU_STUPEFACENTI")) return "presidente.repubblica:decreto:1990-10-09;309";
		if(alias.getValue().equals("IT_TU_ENTI_LOCALI")) return "stato:decreto.legislativo:2000-08-18;267";
		
		
		
/*

{TuCamDep}(({S}?{Dash}?{S}?{TuCamDep})|({S}?[\(]{S}?{TuCamDep}{S}?[\)]))?
	{ saveAlias(yytext(), "presidente.repubblica", "decreto", "30031957", "361"); }

{TuAssInf}(({S}?{Dash}?{S}?{TuAssInf})|({S}?[\(]{S}?{TuAssInf}{S}?[\)]))?
	{ saveAlias(yytext(), "presidente.repubblica", "decreto", "30061965", "1124"); }

{TuIva}(({S}?{Dash}?{S}?{TuIva})|({S}?[\(]{S}?{TuIva}{S}?[\)]))?
	{ saveAlias(yytext(), "presidente.repubblica", "decreto", "26101972", "633"); }

{TuDoganale}(({S}?{Dash}?{S}?{TuDoganale})|({S}?[\(]{S}?{TuDoganale}{S}?[\)]))?
	{ saveAlias(yytext(), "presidente.repubblica", "decreto", "23011973", "43"); }

{TuPostale}(({S}?{Dash}?{S}?{TuPostale})|({S}?[\(]{S}?{TuPostale}{S}?[\)]))?
	{ saveAlias(yytext(), "presidente.repubblica", "decreto", "29031973", "156"); }

{TuMezzogiorno}(({S}?{Dash}?{S}?{TuMezzogiorno})|({S}?[\(]{S}?{TuMezzogiorno}{S}?[\)]))?
	{ saveAlias(yytext(), "presidente.repubblica", "decreto", "06031978", "218"); }

{TuPensioni}(({S}?{Dash}?{S}?{TuPensioni})|({S}?[\(]{S}?{TuPensioni}{S}?[\)]))?
	{ saveAlias(yytext(), "presidente.repubblica", "decreto", "23121978", "915"); }

{TuPromLeggi}(({S}?{Dash}?{S}?{TuPromLeggi})|({S}?[\(]{S}?{TuPromLeggi}{S}?[\)]))?
	{ saveAlias(yytext(), "presidente.repubblica", "decreto", "28121985", "1092"); }

{TuStupe}(({S}?{Dash}?{S}?{TuStupe})|({S}?[\(]{S}?{TuStupe}{S}?[\)]))?
	{ saveAlias(yytext(), "presidente.repubblica", "decreto", "09101990", "309"); }

{TuSuccDon}(({S}?{Dash}?{S}?{TuSuccDon})|({S}?[\(]{S}?{TuSuccDon}{S}?[\)]))?
	{ saveAlias(yytext(), "stato", "decreto.legislativo", "31101990", "346"); }

{TuImpIpo}(({S}?{Dash}?{S}?{TuImpIpo})|({S}?[\(]{S}?{TuImpIpo}{S}?[\)]))?
	{ saveAlias(yytext(), "stato", "decreto.legislativo", "31101990", "347"); }

{TuSenato}(({S}?{Dash}?{S}?{TuSenato})|({S}?[\(]{S}?{TuSenato}{S}?[\)]))?
	{ saveAlias(yytext(), "stato", "decreto.legislativo", "20121993", "533"); }

{TuIstruzione}(({S}?{Dash}?{S}?{TuIstruzione})|({S}?[\(]{S}?{TuIstruzione}{S}?[\)]))?
	{ saveAlias(yytext(), "stato", "decreto.legislativo", "16041994", "297"); }

{TuProdCons}(({S}?{Dash}?{S}?{TuProdCons})|({S}?[\(]{S}?{TuProdCons}{S}?[\)]))?
	{ saveAlias(yytext(), "stato", "decreto.legislativo", "26101995", "504"); }

{TuFinanz}(({S}?{Dash}?{S}?{TuFinanz})|({S}?[\(]{S}?{TuFinanz}{S}?[\)]))?
	{ saveAlias(yytext(), "stato", "decreto.legislativo", "24021998", "58"); }

{TuDiscImm}(({S}?{Dash}?{S}?{TuDiscImm})|({S}?[\(]{S}?{TuDiscImm}{S}?[\)]))?
	{ saveAlias(yytext(), "stato", "decreto.legislativo", "25071998", "286"); }

{TuEntiLoc}(({S}?{Dash}?{S}?{TuEntiLoc})|({S}?[\(]{S}?{TuEntiLoc}{S}?[\)]))?
	{ saveAlias(yytext(), "stato", "decreto.legislativo", "18082000", "267"); }

{TuMatern}(({S}?{Dash}?{S}?{TuMatern})|({S}?[\(]{S}?{TuMatern}{S}?[\)]))?
	{ saveAlias(yytext(), "stato", "decreto.legislativo", "26032001", "151"); }

{TuLavPA}(({S}?{Dash}?{S}?{TuLavPA})|({S}?[\(]{S}?{TuLavPA}{S}?[\)]))?
	{ saveAlias(yytext(), "stato", "decreto.legislativo", "30032001", "165"); }

{TuRadiotel}(({S}?{Dash}?{S}?{TuRadiotel})|({S}?[\(]{S}?{TuRadiotel}{S}?[\)]))?
	{ saveAlias(yytext(), "stato", "decreto.legislativo", "31072005", "177"); }

{TuAmbiente}(({S}?{Dash}?{S}?{TuAmbiente})|({S}?[\(]{S}?{TuAmbiente}{S}?[\)]))?
	{ saveAlias(yytext(), "stato", "decreto.legislativo", "03042006", "152"); }

{TuSicLav}(({S}?{Dash}?{S}?{TuSicLav})|({S}?[\(]{S}?{TuSicLav}{S}?[\)]))?
	{ saveAlias(yytext(), "stato", "decreto.legislativo", "09042008", "81"); }

{TuCaccia}(({S}?{Dash}?{S}?{TuCaccia})|({S}?[\(]{S}?{TuCaccia}{S}?[\)]))?
	{ saveAlias(yytext(), "stato", "regio.decreto", "05061939", "1016"); } 

*/
		
		
		if(alias.getValue().equals("IT_LEGGE_FALL")) return "stato:regio.decreto:1942-03-16;267:1";
		
		if(alias.getValue().equals("IT_LEGGE_FIN_2002")) return "stato:legge:2001-12-28;448";
		if(alias.getValue().equals("IT_LEGGE_FIN_2003")) return "stato:legge:2002-12-27;289";
		
		
		return "";
	}
	
}
