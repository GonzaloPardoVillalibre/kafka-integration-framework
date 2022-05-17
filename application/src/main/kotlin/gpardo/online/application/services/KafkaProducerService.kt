package gpardo.online.application.services

import gpardo.online.queue.OppType
import gpardo.online.application.kafka.notifications.EntitiesNotification.EntitiesNotificationKey
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service

@Service
class KafkaProducerService(private val kafkaTemplate: KafkaTemplate<String, String>) {
    companion object {
        private const val TOPIC = "entities"
        private const val EVENT_TYPE_KEY = "eventType"
        private const val ENTITY_TYPE_KEY = "entityType"
    }

    fun <T>publishEntityEvent(value:T, oppType: OppType, entittiesNotificationKey: EntitiesNotificationKey): ResponseEntity<Any> {
        return try {
            val message: Message<T> = MessageBuilder
                .withPayload(value)
                .setHeader(KafkaHeaders.TOPIC, TOPIC)
                .setHeader(EVENT_TYPE_KEY, oppType.toString())
                .setHeader(ENTITY_TYPE_KEY, entittiesNotificationKey.toString())
                .build()
            kafkaTemplate.send(message)
            println("Message sent with success")
            ResponseEntity.ok().build()
        } catch (e: Exception) {
            println("Exception: {$e}")
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error to send message")
        }
    }
}