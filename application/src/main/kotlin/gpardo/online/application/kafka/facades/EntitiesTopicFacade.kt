package gpardo.online.application.kafka.facades
import gpardo.online.queue.KafkaFacade
import gpardo.online.application.kafka.notifications.EntitiesNotification
import gpardo.online.queue.ConsumerFacade
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component("CDMEntitiesConsumer")
class EntitiesTopicFacade(): ConsumerFacade {
    override val consumers:MutableList<KafkaFacade> = mutableListOf<KafkaFacade>()
    @KafkaListener(
        topics = ["entities"],
        groupId = "gpardo.online"
    )
    override fun listen(consumerRecord: ConsumerRecord<String, String>) {
        val notification = EntitiesNotification(consumerRecord)
        consumers.forEach{it.handleNotification(notification)}
    }
    override fun subscribe(consumer: KafkaFacade){
        consumers.add(consumer)
    }

}