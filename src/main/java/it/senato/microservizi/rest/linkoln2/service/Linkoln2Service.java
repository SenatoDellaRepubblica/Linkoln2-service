/*******************************************************************************
 * Copyright (c) 2020-2021 Senato della Repubblica
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
 ******************************************************************************/

package it.senato.microservizi.rest.linkoln2.service;

import it.senato.microservizi.rest.linkoln2.config.Linkoln2Configuration;
import it.senato.microservizi.rest.linkoln2.logic.Liknoln2Logic;
import it.senato.microservizi.rest.linkoln2.model.ContentBeanDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Linkoln2Service {
    private final Linkoln2Configuration bgtConfiguration;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public Linkoln2Service(Linkoln2Configuration bgtConfiguration) {
        this.bgtConfiguration = bgtConfiguration;
    }

    public String parseDocument(String text, boolean intDomain)
    {
            Liknoln2Logic bl = new Liknoln2Logic(this.bgtConfiguration);
            return bl.parseRifNormWithChunk(text, intDomain);
    }

    public List<ContentBeanDTO> parseDocumentWithList(String text, boolean intDomain)
    {
        Liknoln2Logic bl = new Liknoln2Logic(this.bgtConfiguration);
        return bl.parseRifNormWithList(text, intDomain);
    }

}
