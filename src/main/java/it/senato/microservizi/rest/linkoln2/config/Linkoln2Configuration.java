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

package it.senato.microservizi.rest.linkoln2.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import static org.apache.commons.lang3.SystemUtils.IS_OS_LINUX;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;

@Configuration
public class Linkoln2Configuration {
    private final Environment env;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public Linkoln2Configuration(Environment env) {
        this.env = env;
    }

    private String getRootBgtPath() {
        String basePath;
        if (IS_OS_WINDOWS) {
            basePath = env.getProperty("win.bgt.path");
        } else if (IS_OS_LINUX) {
            basePath = env.getProperty("nix.bgt.path");
        } else {
            throw new RuntimeException("Sistema operativo non definito");
        }
        return basePath;
    }

}
