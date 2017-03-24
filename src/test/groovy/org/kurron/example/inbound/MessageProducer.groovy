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
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.integration.annotation.InboundChannelAdapter

/**
 * Produces test messages.
 **/
@Slf4j
@EnableBinding( Source )
class MessageProducer {

    @InboundChannelAdapter( Source.OUTPUT )
    String generateMessagePayload() {
        long epoch = Calendar.instance.timeInMillis / 1000
        def payload = Long.toString( epoch )
        log.debug( 'Sending {}', payload )
        payload
    }
}
