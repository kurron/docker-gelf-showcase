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
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import org.junit.experimental.categories.Category
import org.kurron.categories.InboundIntegrationTest
import org.kurron.example.shared.ApplicationProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.context.annotation.Bean
import spock.lang.Specification

/**
 * Integration test showing the messages are arriving at the consumer.
 **/
@Category( InboundIntegrationTest )
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.NONE )
@Slf4j
class MessageConsumerIntegrationTest extends Specification {

    @Autowired
    MessageProducer producer

    @Autowired
    Sink sink

    @Autowired
    ApplicationProperties configuration

    @Autowired
    CountDownLatch latch

    void 'verify application startup'() {

        expect: 'make sure the interface is backed by an implementation'
        sink.input()
    }

    void 'exercise event processing'() {

        expect: 'all sent events get processed'
        configuration.outstandingMessages.times {
            producer.generateMessagePayload()
        }
        latch.await( 10, TimeUnit.SECONDS )
    }

    // keep the configuration right next to the tests
    @TestConfiguration
    static class Configuration {

        @Bean
        MessageProducer messageProducer() {
            new MessageProducer()
        }
    }
}
