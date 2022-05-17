package gpardo.online.application.models

import kotlinx.serialization.Serializable

@Serializable
class Dealer(val uniqueId:String, val location:String, val mail:String) {
}