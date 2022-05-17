package gpardo.online.application.kafka.notifications

import gpardo.online.queue.OppType
import gpardo.online.queue.OppType.INSERT
import gpardo.online.queue.OppType.DELETE
import gpardo.online.queue.OppType.UPDATE
import gpardo.online.queue.OppType.UNKNOWN
import gpardo.online.queue.Notification
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.header.Header
import org.apache.kafka.common.header.Headers

class EntitiesNotification(record: ConsumerRecord<String, String>, oppTypeHeader: String = "eventType", entityHeader: String = "entityType") :
    Notification {
    private var oppType: OppType
    private var key: EntitiesNotificationKey
    private var message:String

    init {
        message = record.value()
        oppType = extractOppType(record.headers(), oppTypeHeader)
        key = buildKey(record.headers(), entityHeader)
    }

    enum class EntitiesNotificationKey {
        CUSTOMER, DEALERSHIP, UNKNOWN
    }

    private fun extractOppType(headers: Headers, eventHeader: String): OppType {
        val itHeader = headers.headers(eventHeader)
        val iterator: Iterator<Header> = itHeader.iterator()
        if (iterator.hasNext()) {
            return when(String(iterator.next().value())){
                INSERT.toString() -> INSERT
                DELETE.toString() -> DELETE
                UPDATE.toString() -> UPDATE
                else -> UNKNOWN
            }
        }
        return UNKNOWN
    }

    private fun buildKey(headers: Headers, entityHeader: String): EntitiesNotificationKey {
        val itHeader = headers.headers(entityHeader)
        val iterator: Iterator<Header> = itHeader.iterator()
        if (iterator.hasNext()) {
            return when(String(iterator.next().value())){
                EntitiesNotificationKey.CUSTOMER.toString() -> EntitiesNotificationKey.CUSTOMER
                EntitiesNotificationKey.DEALERSHIP.toString() -> EntitiesNotificationKey.DEALERSHIP
                else -> EntitiesNotificationKey.UNKNOWN
            }
        }
        return EntitiesNotificationKey.UNKNOWN
    }

    override fun getMessage(): String {
        return message
    }

    override fun getOppType(): OppType {
        return oppType
    }

    override fun getKey(): Any {
        return key
    }
}
