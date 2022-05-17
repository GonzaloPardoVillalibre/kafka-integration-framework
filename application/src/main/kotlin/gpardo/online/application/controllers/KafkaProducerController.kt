package gpardo.online.application.controllers

import gpardo.online.application.models.Customer
import gpardo.online.application.models.Dealer
import gpardo.online.application.services.KafkaProducerService
import gpardo.online.queue.OppType.INSERT
import gpardo.online.queue.OppType.DELETE
import gpardo.online.queue.OppType.UPDATE
import gpardo.online.application.kafka.notifications.EntitiesNotification.EntitiesNotificationKey.CUSTOMER
import gpardo.online.application.kafka.notifications.EntitiesNotification.EntitiesNotificationKey.DEALERSHIP
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/producer")
class KafkaProducerController {
    @Autowired
    lateinit var kafkaProducerService: KafkaProducerService

    @PostMapping("/insertedCustomer")
    fun insertedCustomer(@Validated @RequestBody customer: Customer): ResponseEntity<Any>{
            println("Publishing customer insertion")
            return kafkaProducerService.publishEntityEvent(Json.encodeToString(customer), INSERT, CUSTOMER)
    }

    @PostMapping("/deletedCustomer")
    fun deletedCustomer(@Validated @RequestBody customer: Customer): ResponseEntity<Any>{
        println("Publishing customer delete")
        return kafkaProducerService.publishEntityEvent(Json.encodeToString(customer), DELETE, CUSTOMER)
    }

    @PostMapping("/updatedCustomer")
    fun updatedCustomer(@Validated @RequestBody customer: Customer): ResponseEntity<Any>{
        println("Publishing customer update")
        return kafkaProducerService.publishEntityEvent(Json.encodeToString(customer), UPDATE, CUSTOMER)
    }

    @PostMapping("/insertedDealer")
    fun insertedDealer(@Validated @RequestBody dealer: Dealer): ResponseEntity<Any>{
        println("Publishing dealer insertion")
        return kafkaProducerService.publishEntityEvent(Json.encodeToString(dealer), INSERT, DEALERSHIP)
    }

    @PostMapping("/deletedDealer")
    fun deletedDealer(@Validated @RequestBody dealer: Dealer): ResponseEntity<Any>{
        println("Publishing dealer delete")
        return kafkaProducerService.publishEntityEvent(Json.encodeToString(dealer), DELETE, DEALERSHIP)
    }

    @PostMapping("/updatedDealer")
    fun updatedDealer(@Validated @RequestBody dealer: Dealer): ResponseEntity<Any>{
        println("Publishing dealer update")
        return kafkaProducerService.publishEntityEvent(Json.encodeToString(dealer), UPDATE, DEALERSHIP)
    }

}