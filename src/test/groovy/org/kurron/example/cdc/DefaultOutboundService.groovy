package org.kurron.example.cdc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestOperations

/**
 * Silly implementation of the service to see if Wire Mock does its job..
 */
@Component
class DefaultOutboundService implements OutboundService {

    @Autowired
    RestOperations template

    @Override
    String go() {
        template.getForObject( "http://example.org/some-resource", String )
    }
}
