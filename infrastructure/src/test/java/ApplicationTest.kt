package org.agh.eaiib

import io.ktor.server.testing.withTestApplication
import kotlin.test.Test

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ kodeinApp(true) }) {
        }
    }
}
