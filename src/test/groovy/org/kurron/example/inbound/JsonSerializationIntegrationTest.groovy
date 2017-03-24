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

import static org.assertj.core.api.Assertions.assertThat
import groovy.util.logging.Slf4j
import java.text.SimpleDateFormat
import org.junit.Rule
import org.junit.experimental.categories.Category
import org.kurron.categories.InboundIntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.boot.test.rule.OutputCapture
import org.springframework.boot.test.util.EnvironmentTestUtils
import org.springframework.context.ConfigurableApplicationContext
import spock.lang.Specification

/**
 * Integration test showing the messages are arriving at the consumer.
 **/
@Category( InboundIntegrationTest )
@JsonTest
@Slf4j
class JsonSerializationIntegrationTest extends Specification {

    @Autowired
    private JacksonTester<Event> json

    @Autowired
    private ConfigurableApplicationContext environment

    @Rule
    private final OutputCapture capture = new OutputCapture()

    void setup() {
        // an example of how to introduce a property into the environment prior to the tests running
        EnvironmentTestUtils.addEnvironment( environment, 'org=Spring', 'name=Boot' )
    }

    @SuppressWarnings( 'Println' )
    void 'verify we can watch std out'() {

        expect: 'showcase how to watch stdout/stderr'
        println environment.environment.getProperty( 'org' )
        // the stream contains other output so you have to do a contains
        capture.toString().contains( 'Spring' )
    }

    void 'verify serialization'() {

        expect: 'pick out a field from the generated JSON'
        def event = new Event( time: new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm'Z'", Locale.US ).format( Calendar.instance.time ),
                               value: 'bob' )
        assertThat( json.write( event ) ).extractingJsonPathStringValue( '@.random-value' ).isEqualTo( 'bob' )
    }

    void 'verify deserialization'() {

        expect: 'pick out a field from the ingested JSON'
        def content = '{ "iso-time": "not really time", "random-value": "ted" }'
        json.parseObject( content ).value == 'ted'
    }
}
