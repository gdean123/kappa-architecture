package com.kappa.consumer.support

import org.apache.kafka.streams.kstream.KTable
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.kafka.config.StreamsBuilderFactoryBean
import org.springframework.test.context.junit4.SpringRunner
import stream_processor.WordCountKey
import stream_processor.WordCountValue

@RunWith(SpringRunner::class)
@SpringBootTest
abstract class SpringTestBase {
    @MockBean private val streamsBuilderFactoryBean: StreamsBuilderFactoryBean? = null
    @MockBean private val wordCountsStore: (() -> ReadOnlyKeyValueStore<WordCountKey, WordCountValue>)? = null
    @MockBean private val kTable: KTable<WordCountKey, WordCountValue>? = null
}