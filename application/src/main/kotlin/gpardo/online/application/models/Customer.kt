package gpardo.online.application.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

@Serializable
class Customer(val name:String, val age:Int, val mail:String) {
}