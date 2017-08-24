package com.kappa.web.values

import com.kappa.web.kafka.TopicListener
import org.springframework.kafka.listener.MessageListener
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
open class ValueListener(
    private val topicListener: TopicListener,
    private val valueRepository: ValueRepository
) {
    @PostConstruct
    fun listen() {
        topicListener.listen("values-topic", MessageListener({ message ->
            valueRepository.save(Value(message.value()))
        }))
    }
}