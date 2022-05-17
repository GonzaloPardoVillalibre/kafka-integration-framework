package gpardo.online.application.kafka.consumers.logicA

import gpardo.online.application.kafka.facades.EntitiesTopicFacade
import gpardo.online.application.kafka.notifications.EntitiesNotification.EntitiesNotificationKey.DEALERSHIP
import gpardo.online.queue.KafkaFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Component

@Component
@DependsOn("CDMEntitiesConsumer")
class DealerConsumer(@Autowired final val entitiesTopicFacade: EntitiesTopicFacade) : KafkaFacade() {
    init {
        onDeleted(DEALERSHIP) { deletedDealer(it) }
        onInserted(DEALERSHIP) { insertedDealer(it) }
        onUpdated(DEALERSHIP) { updatedDealer(it) }
        entitiesTopicFacade.subscribe(this)
    }

    fun deletedDealer(msg: String): Unit {
        println("[DEALERCONSUMER] Dealer deleted: $msg")
    }

    fun insertedDealer(msg: String): Unit {
        println("[DEALERCONSUMER] Dealer inserted: $msg")
    }

    fun updatedDealer(msg: String): Unit {
        println("[DEALERCONSUMER] Dealer updated: $msg")
    }
}