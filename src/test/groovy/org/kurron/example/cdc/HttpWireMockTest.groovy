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
package org.kurron.example.cdc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.cloud.contract.wiremock.WireMockRestServiceServer
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

/**
 * An example from the documentation..
 **/
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
class HttpWireMockTest extends Specification {

    @Autowired
    OutboundService sut

    @Autowired
    RestTemplate template

    void 'showcase HTTP mocking'() {

        expect:
        def server = WireMockRestServiceServer.with( template )
                                              .baseUrl( 'http://example.org' )
                                              .stubs( 'classpath:/mappings/' )
                                              .build()
        sut.go() == 'Hello, World!'
        server.verify()
    }

    @TestConfiguration
    static class Configuration {

        // just to test out the wire mock stuff
        @Bean
        RestTemplate restTemplate() {
            new RestTemplate()
        }

        @Bean
        DefaultOutboundService defaultOutboundService() {
            new DefaultOutboundService()
        }
    }

}
