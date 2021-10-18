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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@EnableSwagger2
@Configuration
public class SwaggerDocumentationConfig {

    ApiInfo apiInfo() {

        List<VendorExtension> vendorExtensions = new ArrayList<>();
        vendorExtensions.add(new StringVendorExtension("x-area", "Testi"));
        vendorExtensions.add(new StringVendorExtension("x-tipologia", "Servizio"));
        vendorExtensions.add(new StringVendorExtension("x-referente", "Roberto Battistoni"));

        return new ApiInfoBuilder()
                .title("Linkoln2 Service")
                .description("Servizio per il parsing dei riferimenti normativi")
                .license("Senato della Repubblica")
                .version("1.0.0")
                .contact(new Contact("Roberto Battistoni", "", "r.battistoni@senato.it"))
                .extensions(vendorExtensions)
                .build();
    }

    @Bean
    public Docket customImplementation() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("it.senato"))
                .paths(PathSelectors.any())
                .build();
    }
}
