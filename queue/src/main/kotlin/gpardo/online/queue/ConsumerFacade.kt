package gpardo.online.queue

import gpardo.online.queue.KafkaFacade
import org.apache.kafka.clients.consumer.ConsumerRecord

interface ConsumerFacade {
    val consumers:MutableList<KafkaFacade>
    fun listen(consumerRecord: ConsumerRecord<String, String>): Unit
    fun subscribe(consumer: KafkaFacade): Unit
}