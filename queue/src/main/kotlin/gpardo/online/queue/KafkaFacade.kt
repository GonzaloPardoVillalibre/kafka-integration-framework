package gpardo.online.queue

import gpardo.online.queue.OppType.INSERT
import gpardo.online.queue.OppType.DELETE
import gpardo.online.queue.OppType.UPDATE

open class KafkaFacade {
    private var callbacks: HashMap<Any, (String) -> Unit > = HashMap()

    fun onInserted(key:Any="None", callback:(String) -> Unit) {
        callbacks[Pair(key, INSERT)] = callback
    }

    fun onDeleted(key:Any="None", callback:(String) -> Unit) {
        callbacks[Pair(key,DELETE)] = callback
    }

    fun onUpdated(key:Any="None", callback:(String) -> Unit) {
        callbacks[Pair(key,UPDATE)] = callback
    }

    fun handleNotification(notification: Notification){
        val consumerName = this.javaClass.name.split(".").last().uppercase()
        println("[$consumerName] Consumming notification")
        val cb : ((String) -> Unit)? = callbacks[Pair(notification.getKey(), notification.getOppType())]
        cb?.invoke(notification.getMessage()) ?: println("[$consumerName] Nothing to do for all")
    }
}