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

package it.senato.microservizi.rest.linkoln2.logic;

import it.cnr.igsg.linkoln.Linkoln;
import it.cnr.igsg.linkoln.LinkolnDocument;
import it.senato.microservizi.rest.linkoln2.config.Linkoln2Configuration;
import it.senato.microservizi.rest.linkoln2.model.ContentBeanDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://gitlab.com/IGSG/LINKOLN/linkoln
 */
public class Liknoln2Logic {

    public static final String HTTP_WWW_NORMATTIVA = "http://www.normattiva.it/uri-res/N2Ls?";
    public static final String WWW_SENATO_IT_JAPP_BGT_NIR = "https://www.senato.it/japp/bgt/nir/URN2DEA?urn=";
    private final Linkoln2Configuration bgtConfiguration;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public Liknoln2Logic(Linkoln2Configuration bgtConfiguration) {
        this.bgtConfiguration = bgtConfiguration;
    }

    public List<ContentBeanDTO> parseRifNormWithList(String textToParse, boolean intDomain)
    {
        String parsedHtml = parseRifNormWithChunk(textToParse, intDomain);
        return getParsedLinks(parsedHtml, intDomain);
    }


    /**
     * Esegue il parsing dei riferimenti normativi per Chunk
     * @param textToParser
     * @param intDomain
     * @return
     */
    public String parseRifNormWithChunk(String textToParser, boolean intDomain) {

        StringBuilder sb = new StringBuilder();
        int FRAGMENT_SIZE = 500_000;
        int old_cut_pos =0;

        // Individua i chunk di dimensione FRAGMENT_SIZE e li processa
        for (int i = 0; i < textToParser.length() && textToParser.length()> FRAGMENT_SIZE; i++)
        {
            // processa i frammenti di grandezza FRAGMENT_SIZE
            if (i > 0 && i % FRAGMENT_SIZE == 0)
            {
                while (i < textToParser.length() - 1)
                {
                    // Avanza fino al prossimo angolare
                    if (textToParser.charAt(i) == '<' && i < textToParser.length() - 1 && textToParser.charAt(i + 1) == '/')
                    {
                        parse_chunk(intDomain, sb, textToParser.substring(old_cut_pos, i));
                        old_cut_pos = i;
                        break;
                    }
                    i++;
                }
            }
        }
        // processa la coda o l'intero frammento
        parse_chunk(intDomain, sb, textToParser.substring(old_cut_pos, textToParser.length()));

        return sb.toString();
    }

    /**
     * Esegue il parsing del singolo chunk di testo
     *
     * @param intDomain indica se utilizzare il riferimento interno alla De Agostini o Normattiva
     * @param sb StringBuilder a cui accodare il chunk parsato
     * @param chunk chunk da parsare
     */
    private void parse_chunk(boolean intDomain, StringBuilder sb, String chunk) {
        LinkolnDocument linkolnDocument = Linkoln.run(chunk);
        String prd_chunk = linkolnDocument.getAnnotatedText();
        if (intDomain)
            prd_chunk= prd_chunk.replace(HTTP_WWW_NORMATTIVA, WWW_SENATO_IT_JAPP_BGT_NIR);
        sb.append(prd_chunk);
    }

    /**
     * Ritorna la lista delle URN parsate all'interno del documento
     *
     * @param parsedText
     * @return
     */
    private static List<ContentBeanDTO> getParsedLinks(String parsedText, boolean intDomain)
    {
        String regexString = "<a\\s+href=\"(%s[^\"]*)\"[^>]*>(.*?)</a>";
        Pattern regex = Pattern.compile(String.format(regexString,
                ((intDomain)?Pattern.quote(WWW_SENATO_IT_JAPP_BGT_NIR):Pattern.quote(HTTP_WWW_NORMATTIVA))),
                Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

        Matcher matches = regex.matcher(parsedText);
        ArrayList<ContentBeanDTO> list = new ArrayList<>();
        while (matches.find()) {
            ContentBeanDTO bean = new ContentBeanDTO();
            bean.setMatch(matches.group(0).trim());
            bean.setHref(matches.group(1).trim());
            bean.setContent(matches.group(2).trim());
            list.add(bean);
        }
        return list;
    }

}
