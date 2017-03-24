/*
 * Copyright (c) 2016. Ronald D. Kurr kurr@jvmguy.com
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
package org.kurron.example.shared

import org.hibernate.validator.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

import javax.validation.constraints.NotNull

/**
 * Custom configuration properties that are driven by Spring Boot and its application.yml file.*/
@ConfigurationProperties( value = 'example', ignoreUnknownFields = false )
@Validated
class ApplicationProperties {

    /**
     * Contains the logging related properties.*/
    @NotNull
    Logging logging

    /**
     * Contains the dead letter exchange settings.*/
    @NotNull
    DeadLetter deadletter

    /**
     * Contains the inbound AMQP settings.*/
    @NotNull
    Inbound inbound

    /**
     * Contains the outbound AMQP settings.*/
    @NotNull
    Outbound outbound

    static class Logging {
        /**
         * Identifies this type of service. Used in logging.*/
        @NotEmpty
        String serviceCode

        /**
         * Identifies this instance of the service. Used in logging.*/
        @NotEmpty
        String serviceInstance

        /**
         * Logically groups a collection of services. Used in logging.*/
        @NotEmpty
        String realm
    }

    static class DeadLetter {
        /**
         * This property controls the name of the exchange used to publish poison messages to.*/
        @NotEmpty
        String exchangeName

        /**
         * This property controls the queue that is bound to the poison messages exchange.*/
        @NotEmpty
        String queueName

        /**
         * This property controls the routing key that is used when publishing poison messages.*/
        @NotEmpty
        String routingKey

        /**
         * This property controls how many times a message will be processed before being declared a poison message.*/
        @org.hibernate.validator.constraints.Range( min = 1L, max = 5L )
        int messageRetryAttempts
    }

    static class Inbound {
        /**
         * This property controls the name of the exchange that the outbound gateway publishes to.*/
        @NotEmpty
        String exchangeName

        /**
         * This property controls the routing key that the outbound gateway publishes to.*/
        @NotEmpty
        String routingKey

        /**
         * This property controls the queue that is bound to the inbound exchange.*/
        @NotEmpty
        String queueName
    }

    static class Outbound {
        /**
         * This property controls the name of the exchange that the outbound gateway publishes to.*/
        @NotEmpty
        String exchangeName

        /**
         * This property controls the routing key that the outbound gateway publishes to.*/
        @NotEmpty
        String routingKey
    }

    /**
     * This property controls...*/
    String foo

    /**
     * A dumb way to test that all messages got processed.
     */
    @org.hibernate.validator.constraints.Range( min = 1L, max = 10L )
    Integer outstandingMessages

}
