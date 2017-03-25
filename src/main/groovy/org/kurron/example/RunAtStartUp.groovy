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
package org.kurron.example

import groovy.util.logging.Slf4j
import org.slf4j.MDC
import org.slf4j.Marker
import org.slf4j.MarkerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner

/**
 * Initialization logic, like pre-warming a cache, goes here.
 **/
@Slf4j
class RunAtStartUp implements ApplicationRunner {

    private void storeValuesInMdc()
    {
        MDC.put( 'message-code', '45678' )
        MDC.put( 'realm', 'Nashua Testing Lab' )
    }

    @Override
    void run( final ApplicationArguments arguments ) {

        log.debug( 'Command-line arguments are: ', arguments.sourceArgs.join( ',' ) )
        int iterations = arguments.getNonOptionArgs() ? arguments.getNonOptionArgs().first() as int : 10

        storeValuesInMdc()
        final Marker audienceMarker = MarkerFactory.getMarker( 'Operations' )

        iterations.times {
            //def logError = ThreadLocalRandom.current().nextBoolean()
            def logError = false
            logError ? log.error( audienceMarker, 'Forced failure!', new RuntimeException( "Iteration ${it}" ) ) : log.debug( audienceMarker, 'Iteration {}', it )
            Thread.sleep( 1000 )
        }

    }
}
