/*
 * Copyright (c) 2017. Ronald D. Kurr kurr@jvmguy.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kurron.example.inbound

import groovy.util.logging.Slf4j
import org.junit.experimental.categories.Category
import org.kurron.categories.OutboundIntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import spock.lang.Specification

/**
 * Integration test showcasing TestRestTemplate support.
 **/
@Category( OutboundIntegrationTest )
// IMPORTANT: to get a pre-built template, you cannot use SpringBootTest.WebEnvironment.NONE
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@Slf4j
class RestTemplateShowcaseTest extends Specification {

    @Autowired
    TestRestTemplate template

    void 'do a simple GET'() {

        expect: 'make sure the interface is backed by an implementation'
        template
        def headers = template.getForEntity( 'http://www.microsoft.com/' , String ).headers
        headers.location.toString().contains( 'http://www.microsoft.com/en-us/' )
    }

    // You can also influence the template's construction, if needed
    @TestConfiguration
    static class Configuration {

        @Bean
        RestTemplateBuilder restTemplateBuilder() {
            // add customizations if needed
            new RestTemplateBuilder()
        }
    }
}
