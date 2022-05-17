package gpardo.online.application.kafka.consumers.logicB

import gpardo.online.application.kafka.facades.EntitiesTopicFacade
import gpardo.online.application.kafka.notifications.EntitiesNotification.EntitiesNotificationKey.CUSTOMER
import gpardo.online.application.kafka.notifications.EntitiesNotification.EntitiesNotificationKey.DEALERSHIP
import gpardo.online.queue.KafkaFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn

@org.springframework.stereotype.Component
@DependsOn("CDMEntitiesConsumer")
class EntityDeleterConsumer (@Autowired final val entitiesTopicFacade: EntitiesTopicFacade) : KafkaFacade() {
    init {
        onDeleted(DEALERSHIP) { deletedDealer(it) }
        onDeleted(CUSTOMER) { deltedCustomer(it) }
        entitiesTopicFacade.subscribe(this)
    }

    fun deletedDealer(msg: String): Unit {
        println("[ENTITYDELETERCONSUMER] Dealer deleted: $msg")
    }

    fun deltedCustomer(msg: String): Unit {
        println("[ENTITYDELETERCONSUMER] Customer deleted: $msg")
    }

}