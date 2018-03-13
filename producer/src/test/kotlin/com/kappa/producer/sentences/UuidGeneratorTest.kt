package com.kappa.producer.sentences

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class UuidGeneratorTest {
    private lateinit var uuidGenerator: UuidGenerator

    @Before
    fun setUp() {
        uuidGenerator = UuidGenerator()
    }

    @Test
    fun `generates a uuid`() {
        assertThat(uuidGenerator.generate()).hasSize(36)
        assertThat(uuidGenerator.generate()).isNotEqualTo(uuidGenerator.generate())
    }
}