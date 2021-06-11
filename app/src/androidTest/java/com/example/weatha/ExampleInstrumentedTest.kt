package com.example.weatha

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.weatha.util.Utils
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private val utils = Utils

    @Test
    fun testGetWindSpeedByKPHr() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("12.5 km/h", utils.getWindSpeedByKPHr(appContext, 12.5))
    }
}