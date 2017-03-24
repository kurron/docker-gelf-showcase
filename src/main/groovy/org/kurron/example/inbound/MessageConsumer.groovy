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
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.Headers

/**
 * An example of how Spring Cloud Stream consumes messages.
 **/
@Slf4j
@EnableBinding( Sink )
@SuppressWarnings( 'DuplicateStringLiteral' )
class MessageConsumer {

    // a complete hack to signal to the test that all messages have been processed
    @Autowired
    CountDownLatch latch

    // Using StreamListener tells Spring to do automatic payload transformation.
    // You cannot get to the raw Message
    @SuppressWarnings( ['GrMethodMayBeStatic', 'GroovyUnusedDeclaration'] )
    @StreamListener( Sink.INPUT )
    void streamListener( String payload, @Headers Map<String,Object> headers  ) {
        headers.each {
            log.debug( '{} = {}', it.key, it.value )
        }
        log.info( 'Processing via StreamListener {}', payload )
        latch.countDown()
    }

    // Using ServiceActivator gives you raw access at the cost of automatic transformation
    @SuppressWarnings( ['GrMethodMayBeStatic', 'GroovyUnusedDeclaration'] )
    @ServiceActivator( inputChannel=Sink.INPUT )
    void serviceActivator( Message<byte[]> payload, @Headers Map<String,Object> headers  ) {
        headers.each {
            log.debug( '{} = {}', it.key, it.value )
        }
        log.info( 'Processing via ServiceActivator {}', payload )
        latch.countDown()
    }
}
