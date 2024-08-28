package com.smsservice.sms.controller

import com.twilio.Twilio
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


// Data class to represent the request body
data class SmsRequest(val to: String, val from: String, val message: String, val geo: String)

// Controller class to handle SMS requests
@RestController
@RequestMapping("/sms")
class SmsController {

    @Value("\${twilio.accountSid}")
    private lateinit var accountSid: String

    @Value("\${twilio.authToken}")
    private lateinit var authToken: String

    @PostConstruct
    fun init() {
        Twilio.init(accountSid, authToken)
    }

    // Endpoint to send SMS
    @PostMapping("/send")
    fun sendSms(@RequestBody request: SmsRequest) {
            Message.creator(
                PhoneNumber(request.to),
                PhoneNumber(request.from),
                request.message
            ).create()
    }

}
