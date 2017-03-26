package org.kurron.example

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * A silly endpoint that we can hit to exercise logging.
 */
@RestController
class EchoGateway {

    @RequestMapping( path  = ['/'], produces = ['text/plain'] )
    String ping() {
        UUID.randomUUID() as String
    }
}
