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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import it.cnr.igsg.linkoln.Linkoln;
import it.cnr.igsg.linkoln.service.impl.ClusterService;
import it.cnr.igsg.linkoln.service.impl.FarAuthorityService;
import it.cnr.igsg.linkoln.service.impl.FinalizeAnnotations;
import it.cnr.igsg.linkoln.service.impl.HtmlBoldRenderer;
import it.cnr.igsg.linkoln.service.impl.HtmlRenderer;
import it.cnr.igsg.linkoln.service.impl.Util;
import it.cnr.igsg.linkoln.service.impl.it.Abbreviations;
import it.cnr.igsg.linkoln.service.impl.it.AddPartitionsToReferences;
import it.cnr.igsg.linkoln.service.impl.it.AliasPartitions;
import it.cnr.igsg.linkoln.service.impl.it.AliasReferences;
import it.cnr.igsg.linkoln.service.impl.it.Aliases;
import it.cnr.igsg.linkoln.service.impl.it.AliasesExtended;
import it.cnr.igsg.linkoln.service.impl.it.ArticleNumbers;
import it.cnr.igsg.linkoln.service.impl.it.Articles;
import it.cnr.igsg.linkoln.service.impl.it.CaseLawAuthorities;
import it.cnr.igsg.linkoln.service.impl.it.CaseNumbers;
import it.cnr.igsg.linkoln.service.impl.it.Commas;
import it.cnr.igsg.linkoln.service.impl.it.Dates;
import it.cnr.igsg.linkoln.service.impl.it.DetachedSections;
import it.cnr.igsg.linkoln.service.impl.it.DocTypes;
import it.cnr.igsg.linkoln.service.impl.it.ExtendAuthorities;
import it.cnr.igsg.linkoln.service.impl.it.FullStops;
import it.cnr.igsg.linkoln.service.impl.it.Geos;
import it.cnr.igsg.linkoln.service.impl.it.Items;
import it.cnr.igsg.linkoln.service.impl.it.JointCaseNumbers;
import it.cnr.igsg.linkoln.service.impl.it.Journals;
import it.cnr.igsg.linkoln.service.impl.it.LegislationAuthorities;
import it.cnr.igsg.linkoln.service.impl.it.Letters;
import it.cnr.igsg.linkoln.service.impl.it.Ministries;
import it.cnr.igsg.linkoln.service.impl.it.Municipalities;
import it.cnr.igsg.linkoln.service.impl.it.NamedEntities;
import it.cnr.igsg.linkoln.service.impl.it.NationalAuthorities;
import it.cnr.igsg.linkoln.service.impl.it.Numbers;
import it.cnr.igsg.linkoln.service.impl.it.PartialReferences;
import it.cnr.igsg.linkoln.service.impl.it.Parties;
import it.cnr.igsg.linkoln.service.impl.it.Partitions;
import it.cnr.igsg.linkoln.service.impl.it.References;
import it.cnr.igsg.linkoln.service.impl.it.ReferencesLaw;
import it.cnr.igsg.linkoln.service.impl.it.RegionalCaseLawAuthorities;
import it.cnr.igsg.linkoln.service.impl.it.RegionalLegislationAuthorities;
import it.cnr.igsg.linkoln.service.impl.it.RvNumbers;
import it.cnr.igsg.linkoln.service.impl.it.SectionSubjects;
import it.cnr.igsg.linkoln.service.impl.it.Sections;
import it.cnr.igsg.linkoln.service.impl.it.Stopwords;
import it.cnr.igsg.linkoln.service.impl.it.Subjects;
import it.cnr.igsg.linkoln.service.impl.it.Vs;


public class ServiceManager {

	private Collection<LinkolnService> services = null;
	
	
	public ServiceManager() {
		
		services = new ArrayList<LinkolnService>();
		
		initServices();
	}
	
	private void initServices() {

		//Manually add services -- Do not use SPI for the moment
		
		services.add(new Articles());
		services.add(new Commas());
		services.add(new Letters()); //Must run before stopwords (lettera a comma 1)
		
		services.add(new Stopwords());
		
		services.add(new Geos());
		services.add(new DetachedSections());
		services.add(new CaseLawAuthorities());
		services.add(new Sections());
		services.add(new SectionSubjects());
		services.add(new LegislationAuthorities());
		services.add(new RegionalCaseLawAuthorities());
		services.add(new RegionalLegislationAuthorities());
		
		services.add(new Dates());
		services.add(new JointCaseNumbers());
		services.add(new CaseNumbers());
		services.add(new RvNumbers());
		services.add(new Numbers());
		//services.add(new Items());  //Spostato dopo Aliases per nuova regola basata su LKN_ALIAS
		
		services.add(new Journals());
		
		services.add(new Aliases());
		services.add(new AliasesExtended());
		services.add(new Items());
		services.add(new Aliases()); //Run it again (TODO: only in case AliasExtended has produced some changes)
		services.add(new Ministries());
		services.add(new NationalAuthorities());
		
		services.add(new DocTypes());
		services.add(new Subjects());
		
		services.add(new Abbreviations());
		
		if(Util.token2code != null)	services.add(new Municipalities());
		if(Util.token2code != null)	services.add(new ExtendAuthorities());
		
		services.add(new Vs());
		services.add(new NamedEntities());
		services.add(new Parties());
		
		services.add(new ReferencesLaw());
		services.add(new References());
		if(Linkoln.CLUSTERING || Linkoln.FAR_AUTH) services.add(new PartialReferences());
		services.add(new AliasPartitions());
		services.add(new AliasReferences());
		services.add(new ArticleNumbers());
		services.add(new Partitions());
		services.add(new AddPartitionsToReferences());
		if(Linkoln.FAR_AUTH) services.add(new FullStops());
		if(Linkoln.FAR_AUTH) services.add(new FarAuthorityService());
		if(Linkoln.CLUSTERING) services.add(new ClusterService());

		services.add(new FinalizeAnnotations());
		
		services.add(new HtmlRenderer());

		// TODO: modifica Senato
		// services.add(new HtmlBoldRenderer());

	}
	
	/*
	 * Returns a fixed-order collection of the available services in a given language 
	 * and jurisdiction (if specified), along with the available default services.
	 */
	public Collection<LinkolnService> getServices(String lang) {
		
		Collection<LinkolnService> selectedServices = new ArrayList<LinkolnService>();

		for(LinkolnService service : services) {
			
			selectedServices.add(service);
		}
		
		return Collections.unmodifiableCollection(selectedServices);
	}
}
