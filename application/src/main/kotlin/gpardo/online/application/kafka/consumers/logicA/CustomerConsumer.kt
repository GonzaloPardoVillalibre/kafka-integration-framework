package gpardo.online.application.kafka.consumers.logicA

import gpardo.online.application.kafka.facades.EntitiesTopicFacade
import gpardo.online.application.kafka.notifications.EntitiesNotification.EntitiesNotificationKey.CUSTOMER
import gpardo.online.queue.KafkaFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Component


@Component
@DependsOn("CDMEntitiesConsumer")
class CustomerConsumer(@Autowired final val entitiesTopicFacade: EntitiesTopicFacade) : KafkaFacade() {
    init {
        onDeleted(CUSTOMER) { deletedCustomer(it) }
        onInserted(CUSTOMER) { insertedCustomer(it) }
        onUpdated(CUSTOMER) { updatedCustomer(it) }
        entitiesTopicFacade.subscribe(this)
    }

    fun deletedCustomer(msg: String): Unit {
        println("[CUSTOMERCONSUMER] Customer deleted: $msg")
    }

    fun insertedCustomer(msg: String): Unit {
        println("[CUSTOMERCONSUMER] Customer inserted: $msg")
    }

    fun updatedCustomer(msg: String): Unit {
        println("[CUSTOMERCONSUMER] Customer updated: $msg")
    }
}