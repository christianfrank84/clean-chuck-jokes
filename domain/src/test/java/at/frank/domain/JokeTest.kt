package at.frank.domain

import org.junit.Assert.assertNotNull
import org.junit.Test

class JokeTest {
    @Test
    fun `should create Joke`() {
        assertNotNull(Joke())
    }
}