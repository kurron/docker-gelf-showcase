package org.kurron.example.cdc

/**
 * Fictional service interface to help showcase Wire Mock stubbing.
 */
interface OutboundService {

    /**
     * Connect to a remote server over HTTP.
     * @return results from the call.
     */
    String go()
}
