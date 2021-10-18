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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import it.cnr.igsg.linkoln.entity.Reference;

public final class LinkolnReference {

	/*
	 * 
	 * Presentation of the extracted references.
	 * 
	 * - getCitation()
	 * - getLinkolnIdentifiers()
	 * 
	 */
	
	
	/*
	 *
	 * TODO
	 * 
	 * Dovrebbe essere compatibile con una sorta di interfaccia universale dei riferimenti
	 * 
	 * 
	 * 
	 */
	
	
	
	private String citation = null;

	private String type = null; //LegislationReference, CaseLawReference, etc.
	private String authority = null;
	private String authoritySection = null;
	private String detachedAuthority = null;
	private String docType = null;
	private String number = null;
	private String year = null; //può essere l'anno specificato insieme al numero oppure l'anno specificato nella data
	private String yearNumber = null; //l'anno specificato insieme al numero
	private String date = null;
	private String caseNumber = null;
	private String city = null;
	private String authCity = null;
	private String detCity = null;
	private String region = null;
	private String country = null;
	private String applicant = null;
	private String defendant = null;
	private String subject = null;
	private String alias = null;
	private String article = null;
	private String comma = null;
	private String paragraph = null;
	private String letter = null;
	private String item = null;
	
	private Collection<LinkolnIdentifier> linkolnIdentifiers = new ArrayList<LinkolnIdentifier>();

	
	LinkolnReference() {
		
		//not to be used
	}

	LinkolnReference(Reference entity) {
		
		this.citation = entity.getText();

		this.alias = entity.getAlias()!=null?entity.getAlias().getValue():null;
		
		this.article = entity.getPartition("article");
		this.comma = entity.getPartition("comma");
		this.paragraph = entity.getPartition("paragraph");
		this.letter = entity.getPartition("letter");
		this.item = entity.getPartition("item");
		
		this.type = entity.getClass().getSimpleName();
		
		this.authority = entity.getAuthorityValue();
		this.authoritySection = entity.getRelatedValue("CL_AUTH_SECTION");
		this.detachedAuthority = entity.getDetAuth()!=null?entity.getDetAuth().getValue():null; //entity.getRelatedValue("CL_DETACHED_SECTION");
		
		this.city = entity.getCity()!=null?entity.getCity().getValue():null;
		this.authCity = entity.getAuthCity()!=null?entity.getAuthCity().getValue():null;
		this.detCity = entity.getDetAuthCity()!=null?entity.getDetAuthCity().getValue():null;
		this.region = entity.getRegion()!=null?entity.getRegion().getValue():null;
		this.country = entity.getCountry()!=null?entity.getCountry().getValue():null;

		this.docType = entity.getDocumentType()!=null?entity.getDocumentType().getValue():null;
		this.number = entity.getNumberValue();
		this.year = entity.getYear()!=null?entity.getYear().getValue():null;
		this.yearNumber = entity.getYearFromNumber()!=null?entity.getYearFromNumber().getValue():null;
		//this.date = entity.getRelatedValue("DOC_DATE");
		//this.caseNumber = entity.getRelatedValue("CASENUMBER");
		this.date = entity.getDate()!=null?entity.getDate().getValue():null;
		this.caseNumber = entity.getCaseNumber()!=null?entity.getCaseNumber().getValue():null;
		this.applicant = entity.getApplicantValue();
		this.defendant = entity.getDefendantValue();
		this.subject = entity.getSubject()!=null?entity.getSubject().getValue():null;
	}

	void addLinkolnIdentifier(LinkolnIdentifier linkolnIdentifier) {
		
		if(linkolnIdentifier != null) linkolnIdentifiers.add(linkolnIdentifier);
	}
	
	public String getCitation() {
		
		return this.citation;
	}
	
	public Collection<LinkolnIdentifier> getLinkolnIdentifiers() {
		
		return Collections.unmodifiableCollection(linkolnIdentifiers);
	}

	public LinkolnIdentifier getLinkolnIdentifier(Identifiers identifier) {
		
		for(LinkolnIdentifier linkolnIdentifier : linkolnIdentifiers) {
			
			if(linkolnIdentifier.getType().equals(identifier)) return linkolnIdentifier;
		}
		
		return null;
	}

	public String getType() {
		
		if(type == null) return null;
		if(type.trim().length() < 1) return null;
		return type.trim();
	}

	public String getAuthority() {
		if(authority == null) return null;
		if(authority.trim().length() < 1) return null;
		return authority.trim();
	}

	public String getAuthoritySection() {
		if(authoritySection == null) return null;
		if(authoritySection.trim().length() < 1) return null;
		return authoritySection.trim();
	}

	public String getDetachedAuthority() {
		if(detachedAuthority == null) return null;
		if(detachedAuthority.trim().length() < 1) return null;
		return detachedAuthority.trim();
	}

	public String getDocType() {
		if(docType == null) return null;
		if(docType.trim().length() < 1) return null;
		return docType.trim();
	}

	public String getNumber() {
		if(number == null) return null;
		if(number.trim().length() < 1) return null;
		return number.trim();
	}

	public String getYear() {
		if(year == null) return null;
		if(year.trim().length() < 1) return null;
		return year.trim();
	}

	public String getYearNumber() {
		if(yearNumber == null) return null;
		if(yearNumber.trim().length() < 1) return null;
		return yearNumber.trim();
	}

	public String getDate() {
		if(date == null) return null;
		if(date.trim().length() < 1) return null;
		return date.trim();
	}

	public String getCaseNumber() {
		if(caseNumber == null) return null;
		if(caseNumber.trim().length() < 1) return null;
		return caseNumber.trim();
	}

	public String getCity() {
		if(city == null) return null;
		if(city.trim().length() < 1) return null;
		return city.trim();
	}

	public String getAuthCity() {
		if(authCity == null) return null;
		if(authCity.trim().length() < 1) return null;
		return authCity.trim();
	}

	public String getDetAuthCity() {
		if(detCity == null) return null;
		if(detCity.trim().length() < 1) return null;
		return detCity.trim();
	}

	public String getRegion() {
		if(region == null) return null;
		if(region.trim().length() < 1) return null;
		return region.trim();
	}

	public String getCountry() {
		if(country == null) return null;
		if(country.trim().length() < 1) return null;
		return country.trim();
	}

	public String getApplicant() {
		if(applicant == null) return null;
		if(applicant.trim().length() < 1) return null;
		return applicant.trim();
	}

	public String getDefendant() {
		if(defendant == null) return null;
		if(defendant.trim().length() < 1) return null;
		return defendant.trim();
	}
	
	public String getSubject() {
		if(subject == null) return null;
		if(subject.trim().length() < 1) return null;
		return subject.trim();
	}
	
	public String getAlias() {
		if(alias == null) return null;
		if(alias.trim().length() < 1) return null;
		return alias.trim();
	}
	
	public String getArticle() {
		if(article == null) return null;
		if(article.trim().length() < 1) return null;
		return article.trim();
	}
	
	public String getComma() {
		if(comma == null) return null;
		if(comma.trim().length() < 1) return null;
		return comma.trim();
	}
	
	public String getParagraph() {
		if(paragraph == null) return null;
		if(paragraph.trim().length() < 1) return null;
		return paragraph.trim();
	}
	
	public String getLetter() {
		if(letter == null) return null;
		if(letter.trim().length() < 1) return null;
		return letter.trim();
	}
	
	public String getItem() {
		if(item == null) return null;
		if(item.trim().length() < 1) return null;
		return item.trim();
	}
	
	public String getExtendedAuthorityName() {
		
		//Compone authority e geos
		String lookupAuth = lookupAuthority();
		if(lookupAuth == null) return null;

		String lookupRegion = lookupRegion();
		String lookupCity = lookupCity(authCity);
		String lookupDetachedCity = lookupCity(detachedAuthority);
		
		if(lookupRegion != null) lookupAuth += " - " + lookupRegion;
		if(lookupCity != null) lookupAuth += " - " + lookupCity;
		if(lookupDetachedCity != null) lookupAuth += " - " + lookupDetachedCity;
		
		return lookupAuth;
	}
	
	private String lookupAuthority() {
		
		if(authority == null) return null;
		if(authority.trim().length() < 1) return null;
		
		if(authority.equals("CE_ECHR")) return "Corte europea dei Diritti dell'Uomo";
		if(authority.equals("EU_CJEU")) return "Corte di Giustizia dell'Unione Europea";
		if(authority.equals("EU_CJEC")) return "Corte di Giustizia della Comunità Europea";
		if(authority.equals("IT_COST")) return "Corte Costituzionale";
		if(authority.equals("IT_CASS")) return "Corte Suprema di Cassazione";
		if(authority.equals("IT_CDS")) return "Consiglio di Stato";
		if(authority.startsWith("IT_CONT")) return "Corte dei Conti";
		if(authority.equals("IT_CSM")) return "Consiglio Superiore della Magistratura";
		if(authority.startsWith("IT_TAR")) return "Tribunale Amministrativo Regionale";
		if(authority.startsWith("IT_CPP")) return "Corte d'Appello";
		if(authority.startsWith("IT_CSS")) return "Corte d'Assise";
		if(authority.startsWith("IT_CSP")) return "Corte d'Assise d'Appello";
		if(authority.startsWith("IT_TRB")) return "Tribunale";
		if(authority.startsWith("IT_TMN")) return "Tribunale dei Minori";
		if(authority.startsWith("IT_TML")) return "Tribunale Militare";
		if(authority.startsWith("IT_USV")) return "Ufficio di Sorveglianza";
		if(authority.startsWith("IT_TSV")) return "Tribunale di Sorveglianza";
		if(authority.startsWith("IT_GPC")) return "Giudice di Pace";
		if(authority.startsWith("IT_PCR")) return "Procura della Repubblica";
		if(authority.startsWith("IT_PTR")) return "Pretura";
		if(authority.startsWith("IT_CGARS")) return "Consiglio di giustizia amministrativa per la regione siciliana";
		
		if(authority.startsWith("IT_CTC")) return "Commissione Tributaria Centrale";
		if(authority.startsWith("IT_CTR")) return "Commissione Tributaria Regionale";
		if(authority.startsWith("IT_CTP")) return "Commissione Tributaria Provinciale";
		
		if(authority.startsWith("EU_COUNCIL")) return "Consiglio dell’Unione Europea";
		if(authority.startsWith("EU_PARLIAMENT_COUNCIL")) return "Parlamento e Consiglio dell’Unione europea";
		if(authority.startsWith("EU_COMMISSION")) return "Commissione europea";
		if(authority.startsWith("EU_PARLIAMENT")) return "Parlamento europeo";
		
		return null;
	}
	
	private String lookupRegion() {
		
		if(region == null) return null;
		if(region.trim().length() < 1) return null;
		
		if(region.equals("IT_ABR")) return "Abruzzo";
		if(region.equals("IT_BAS")) return "Basilicata";
		if(region.equals("IT_CAL")) return "Calabria";
		if(region.equals("IT_CAM")) return "Campania";
		if(region.equals("IT_EMR")) return "Emilia Romagna";
		if(region.equals("IT_FVG")) return "Friuli Venezia Giulia";
		if(region.equals("IT_LAZ")) return "Lazio";
		if(region.equals("IT_LIG")) return "Liguria";
		if(region.equals("IT_LOM")) return "Lombardia";
		if(region.equals("IT_MAR")) return "Marche";
		if(region.equals("IT_MOL")) return "Molise";
		if(region.equals("IT_PIE")) return "Piemonte";
		if(region.equals("IT_PUG")) return "Puglia";
		if(region.equals("IT_SAR")) return "Sardegna";
		if(region.equals("IT_SIC")) return "Sicilia";
		if(region.equals("IT_TOS")) return "Toscana";
		if(region.equals("IT_TAA")) return "Trentino Alto Adige";
		if(region.equals("IT_UMB")) return "Umbria";
		if(region.equals("IT_VDA")) return "Valle d'Aosta";
		if(region.equals("IT_VEN")) return "Veneto";
		
		return null;
	}
	
	private String lookupCity(String city) {
		
		if(city == null) return null;
		if(city.trim().length() < 1) return null;

		if(city.equals("IT_AQ")) return "L'Aquila";
		if(city.equals("IT_PZ")) return "Potenza";
		if(city.equals("IT_CZ")) return "Catanzaro";
		if(city.equals("IT_NA")) return "Napoli";
		if(city.equals("IT_BO")) return "Bologna";
		if(city.equals("IT_TS")) return "Trieste";
		if(city.equals("IT_RM")) return "Roma";
		if(city.equals("IT_GE")) return "Genova";
		if(city.equals("IT_MI")) return "Milano";
		if(city.equals("IT_AN")) return "Ancona";
		if(city.equals("IT_CB")) return "Campobasso";
		if(city.equals("IT_TO")) return "Torino";
		if(city.equals("IT_BA")) return "Bari";
		if(city.equals("IT_CA")) return "Cagliari";
		if(city.equals("IT_PA")) return "Palermo";
		if(city.equals("IT_FI")) return "Firenze";
		if(city.equals("IT_TN")) return "Trento";
		if(city.equals("IT_PG")) return "Perugia";
		if(city.equals("IT_AO")) return "Aosta";
		if(city.equals("IT_VE")) return "Venezia";

		if(city.equals("IT_PE")) return "Pescara";
		if(city.equals("IT_RC")) return "Reggio Calabria";
		if(city.equals("IT_SA")) return "Salerno";
		if(city.equals("IT_PR")) return "Parma";
		if(city.equals("IT_LT")) return "Latina";
		if(city.equals("IT_BS")) return "Brescia";
		if(city.equals("IT_LE")) return "Lecce";
		if(city.equals("IT_CT")) return "Catania";
		if(city.equals("IT_BZ")) return "Bolzano";
		
		if(city.equals("IT_AG")) return "Agrigento";
		if(city.equals("IT_AL")) return "Alessandria";
		if(city.equals("IT_AR")) return "Arezzo";
		if(city.equals("IT_AP")) return "Ascoli Piceno";
		if(city.equals("IT_AT")) return "Asti";
		if(city.equals("IT_AV")) return "Avellino";
		if(city.equals("IT_BL")) return "Belluno";
		if(city.equals("IT_BN")) return "Benevento";
		if(city.equals("IT_BG")) return "Bergamo";
		if(city.equals("IT_BI")) return "Biella";
		if(city.equals("IT_BR")) return "Brindisi";
		if(city.equals("IT_CL")) return "Caltanissetta";
		if(city.equals("IT_CE")) return "Caserta";
		if(city.equals("IT_CH")) return "Chieti";
		if(city.equals("IT_CO")) return "Como";
		if(city.equals("IT_CS")) return "Cosenza";
		if(city.equals("IT_CR")) return "Cremona";
		if(city.equals("IT_KR")) return "Crotone";
		if(city.equals("IT_CN")) return "Cuneo";
		if(city.equals("IT_EN")) return "Enna";
		if(city.equals("IT_FM")) return "Fermo";
		if(city.equals("IT_FE")) return "Ferrara";
		if(city.equals("IT_FG")) return "Foggia";
		if(city.equals("IT_FR")) return "Frosinone";
		if(city.equals("IT_GO")) return "Gorizia";
		if(city.equals("IT_GR")) return "Grosseto";
		if(city.equals("IT_IM")) return "Imperia";
		if(city.equals("IT_IS")) return "Isernia";
		if(city.equals("IT_SP")) return "La Spezia";
		if(city.equals("IT_LC")) return "Lecco";
		if(city.equals("IT_LI")) return "Livorno";
		if(city.equals("IT_LO")) return "Lodi";
		if(city.equals("IT_LU")) return "Lucca";
		if(city.equals("IT_MC")) return "Macerata";
		if(city.equals("IT_MN")) return "Mantova";
		if(city.equals("IT_MT")) return "Matera";
		if(city.equals("IT_ME")) return "Messina";
		if(city.equals("IT_MO")) return "Modena";
		if(city.equals("IT_NO")) return "Novara";
		if(city.equals("IT_NU")) return "Nuoro";
		if(city.equals("IT_OR")) return "Oristano";
		if(city.equals("IT_PD")) return "Padova";
		if(city.equals("IT_PV")) return "Pavia";
		if(city.equals("IT_PC")) return "Piacenza";
		if(city.equals("IT_PI")) return "Pisa";
		if(city.equals("IT_PT")) return "Pistoia";
		if(city.equals("IT_PN")) return "Pordenone";
		if(city.equals("IT_PO")) return "Prato";
		if(city.equals("IT_RG")) return "Ragusa";
		if(city.equals("IT_RA")) return "Ravenna";
		if(city.equals("IT_RE")) return "Reggio Emilia";
		if(city.equals("IT_RI")) return "Rieti";
		if(city.equals("IT_RN")) return "Rimini";
		if(city.equals("IT_RO")) return "Rovigno";
		if(city.equals("IT_SS")) return "Sassari";
		if(city.equals("IT_SV")) return "Savona";
		if(city.equals("IT_SI")) return "Siena";
		if(city.equals("IT_SR")) return "Siracusa";
		if(city.equals("IT_SO")) return "Sondrio";
		if(city.equals("IT_TA")) return "Taranto";
		if(city.equals("IT_TE")) return "Teramo";
		if(city.equals("IT_TR")) return "Terni";
		if(city.equals("IT_OG")) return "Ogliastra";
		if(city.equals("IT_TP")) return "Trapani";
		if(city.equals("IT_TV")) return "Treviso";
		if(city.equals("IT_UD")) return "Udine";
		if(city.equals("IT_VA")) return "Varese";
		if(city.equals("IT_VC")) return "Vercelli";
		if(city.equals("IT_VR")) return "Verona";
		if(city.equals("IT_VV")) return "Vibo Valentia";
		if(city.equals("IT_VI")) return "Vicenza";
		if(city.equals("IT_VT")) return "Viterbo";
		
		if(city.equals("IT_BT")) return "Barletta";
		if(city.equals("IT_MB")) return "Monza";
		
		if(city.equals("IT_NN")) return "Napoli Nord";
		
		if(city.equals("IT_B745")) return "Carbonia";
		if(city.equals("IT_E281")) return "Iglesias";
		if(city.equals("IT_D704")) return "Forlì";
		if(city.equals("IT_C573")) return "Cesena";
		if(city.equals("IT_F023")) return "Massa";
		if(city.equals("IT_B832")) return "Carrara";
		if(city.equals("IT_G015")) return "Olbia";
		if(city.equals("IT_L093")) return "Tempio Pausania";
		if(city.equals("IT_G479")) return "Pesaro";
		if(city.equals("IT_L500")) return "Urbino";
		if(city.equals("IT_L746")) return "Verbania";
		
		if(city.equals("IT_L328")) return "Trani";
		if(city.equals("IT_F924")) return "Nola";
		if(city.equals("IT_E372")) return "Vasto";
		if(city.equals("IT_E379")) return "Ivrea";
		if(city.equals("IT_L245")) return "Torre Annunziata";
		if(city.equals("IT_I921")) return "Spoleto";
		if(city.equals("IT_B300")) return "Busto Arsizio";
		if(city.equals("IT_E435")) return "Lanciano";
		if(city.equals("IT_G288")) return "Palmi";
		if(city.equals("IT_D976")) return "Locri";
		if(city.equals("IT_E974")) return "Marsala";
		if(city.equals("IT_I804")) return "Sulmona";
		if(city.equals("IT_C349")) return "Castrovillari";
		if(city.equals("IT_F104")) return "Melfi";
		if(city.equals("IT_A515")) return "Avezzano";
		if(city.equals("IT_C034")) return "Cassino";
		if(city.equals("IT_M208")) return "Lamezia Terme";
		if(city.equals("IT_H612")) return "Rovereto";
		if(city.equals("IT_I608")) return "Senigallia";
		if(city.equals("IT_F284")) return "Molfetta";
		if(city.equals("IT_E409")) return "Lagonegro";
		if(city.equals("IT_L112")) return "Termini Imerese";
		if(city.equals("IT_A124")) return "Alba";
		if(city.equals("IT_A399")) return "Ariano Irpino";
		if(city.equals("IT_G317")) return "Paola";
		if(city.equals("IT_L628")) return "Vallo della Lucania";
		if(city.equals("IT_E456")) return "Larino";
		if(city.equals("IT_F912")) return "Nocera Inferiore";
		if(city.equals("IT_G148")) return "Orvieto";
		if(city.equals("IT_L182")) return "Tivoli";
		
		
		return null;
	}

}
