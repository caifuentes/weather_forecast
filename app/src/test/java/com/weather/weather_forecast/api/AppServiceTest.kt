package com.weather.weather_forecast.api

import com.weather.weather_forecast.data.api.AppService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppServiceTest {
    private lateinit var service: AppService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(AppService.BASE_URL))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AppService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun requestGetListSuccessful() {
        runBlocking {
            val resultResponse = service
                .getCityList("1701668,3067696,1835848", AppService.DEFAULT_UNIT, AppService.API_KEY)
            Assert.assertNotNull(resultResponse.code())
            Assert.assertNotNull(resultResponse.body())
        }
    }

    @Test
    fun requestGetListFailed() {
        runBlocking {
            val resultResponse = service
                .getCityList("", AppService.DEFAULT_UNIT, AppService.API_KEY)
            Assert.assertNotNull(resultResponse.code())
            Assert.assertNotNull(resultResponse.body())
        }
    }

    @Test
    fun requestGetDetailsSuccessful() {
        runBlocking {
            val resultResponse = service
                .getDetails(1701668, AppService.DEFAULT_UNIT, AppService.API_KEY).body()

            Assert.assertThat(resultResponse?.id, CoreMatchers.`is`(1701668))
            Assert.assertThat(resultResponse?.name, CoreMatchers.`is`("Manila"))
        }
    }

    @Test
    fun requestGetDetailsFailed() {
        runBlocking {
            val resultResponse = service
                .getDetails(1701668, AppService.DEFAULT_UNIT, "").body()

            Assert.assertThat(resultResponse?.id, CoreMatchers.`is`(1701668))
            Assert.assertThat(resultResponse?.name, CoreMatchers.`is`("Manila"))
        }
    }
}