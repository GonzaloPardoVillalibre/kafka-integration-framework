package gpardo.online.queue
import gpardo.online.queue.OppType

interface Notification {
    /**
     * Gets the message from the kafka ConsumerRecord
     */
    fun getMessage():String

    /**
     * Gets the opperation type from the Kafka ConsumerRecord
     */
    fun getOppType():OppType

    /**
     * Builds the key to store callbacks
     */
    fun getKey():Any
}