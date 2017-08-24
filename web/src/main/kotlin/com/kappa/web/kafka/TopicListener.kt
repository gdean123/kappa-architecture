package com.kappa.web.kafka

import org.springframework.kafka.listener.MessageListener

interface TopicListener {
    fun listen(topic: String, messageListener: MessageListener<Int, String>)
}