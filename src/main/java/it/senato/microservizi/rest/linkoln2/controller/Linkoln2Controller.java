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

package it.senato.microservizi.rest.linkoln2.controller;

import io.swagger.annotations.ApiOperation;
import it.senato.microservizi.rest.linkoln2.config.Linkoln2Configuration;
import it.senato.microservizi.rest.linkoln2.model.ContentBeanDTO;
import it.senato.microservizi.rest.linkoln2.service.Linkoln2Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v2/linkoln/parse")
public class Linkoln2Controller {

    private final Linkoln2Service linkoln2Service;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final Linkoln2Configuration linkoln2Configuration;

    @Autowired
    public Linkoln2Controller(Linkoln2Service linkoln2Service, Linkoln2Configuration linkoln2Configuration) {
        this.linkoln2Service = linkoln2Service;
        this.linkoln2Configuration = linkoln2Configuration;
    }

    @ApiOperation(value="Ritorna il documento parsato")
    @RequestMapping(value = "/{interno}/file", method = RequestMethod.POST, consumes = MediaType.TEXT_HTML_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String parseDocument(@PathVariable("interno") boolean interno, @RequestBody String textToParser)
    {
        return linkoln2Service.parseDocument(textToParser, interno);

    }

    @ApiOperation(value="Ritorna la lista degli elementi parsati")
    @RequestMapping(value = "/{interno}/list", method = RequestMethod.POST, consumes = MediaType.TEXT_HTML_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<ContentBeanDTO> parseDocumentWithList(@PathVariable("interno") boolean interno, @RequestBody String textToParse)
    {
        return linkoln2Service.parseDocumentWithList(textToParse, interno);
    }

}
