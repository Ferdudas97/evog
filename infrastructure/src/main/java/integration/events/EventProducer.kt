package org.agh.eaiib.integration.events

import integration.sendAsJson
import org.apache.kafka.clients.producer.Producer


inline fun Producer<String, String>.send(obj: Any) {
    sendAsJson(topics, obj)
}


val topics = "persons"