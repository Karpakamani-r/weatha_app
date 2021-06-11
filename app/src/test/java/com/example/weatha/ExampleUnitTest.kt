package com.example.weatha


import com.example.weatha.model.data.Weather
import com.example.weatha.util.Utils
import junit.framework.TestCase.assertEquals
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {

    private val utils = Utils

    @Test
    fun testConvertCelFromKelvin() {
        Assert.assertEquals("86.85°C", utils.getCelsiusFromKelvin(360.0))
    }

    @Test
    fun testGetTimeFromUnixTime() {
        Assert.assertEquals("Jun 10, 2021", utils.getDateFromUnixTime(1623349189))
    }

    @Test
    fun testDataClass() {
        val myViewState = Weather(1, "Chennai", "Very Hot", "ico")
        assertEquals(1, myViewState.id)
        assertEquals("Chennai", myViewState.main)
        assertEquals("Very Hot", myViewState.description)
        assertEquals("ico", myViewState.icon)
    }

}