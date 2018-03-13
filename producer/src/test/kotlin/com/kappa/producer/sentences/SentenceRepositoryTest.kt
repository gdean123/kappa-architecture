package com.kappa.producer.sentences

import com.kappa.producer.kafka.KafkaTopicWriter
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import producer.SentenceCreatedKey
import producer.SentenceCreatedValue

class SentenceRepositoryTest {
    private lateinit var kafkaTopicWriter: KafkaTopicWriter
    private lateinit var uuidGenerator: UuidGenerator
    private lateinit var sentenceRepository: SentenceRepository

    @Before
    fun setUp() {
        uuidGenerator = mock()
        kafkaTopicWriter = mock()
        sentenceRepository = SentenceRepository(kafkaTopicWriter, uuidGenerator)
    }

    @Test
    fun `#create emits the sentence on the sentence_created topic`() {
        whenever(uuidGenerator.generate()).thenReturn("some-uuid")
        sentenceRepository.create("rainbows and sunshine")

        verify(kafkaTopicWriter).write(
            "sentence_created",
            SentenceCreatedKey("some-uuid"),
            SentenceCreatedValue("rainbows and sunshine")
        )
    }
}