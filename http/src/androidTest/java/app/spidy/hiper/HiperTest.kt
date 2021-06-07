package app.spidy.hiper

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.spidy.hiper.data.HiperResponse
import app.spidy.hiper.utils.mix
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HiperTest: TestCase() {
    private lateinit var hiper: Hiper.Asynchronous

    @Before
    public override fun setUp() {
//        val context = ApplicationProvider.getApplicationContext<Context>()
        hiper = Hiper.getInstance().async()
    }

    @After
    fun cleanUp() {

    }

    @Test
    fun getRequest() = runBlocking {
        val tag = "GET Request"
        var status = 0
        var response: HiperResponse? = null
        val statusCodes = listOf(200,404)
        hiper.get("https://httpbin.org/get").resolve {
            response = it
            status = 1
        }.reject {
            response = it
            status = 1
        }.catch {
            status = 1
        }
        while (status==0) delay(100)
        Log.d(tag, response.toString())

        assertThat(response).isNotNull()
        assertThat(response!!.statusCode).isIn(statusCodes)
    }


    @Test
    fun postRequest() = runBlocking {
        val tag = "POST Request"
        var status = 0
        var response: HiperResponse? = null
        val statusCodes = listOf(200,404)
        hiper.post("https://httpbin.org/post").resolve {
            response = it
            status = 1
        }.reject {
            response = it
            status = 1
        }.catch {
            status = 1
        }
        while (status==0) delay(100)
        Log.d(tag, response.toString())

        assertThat(response).isNotNull()
        assertThat(response!!.statusCode).isIn(statusCodes)
    }


    @Test
    fun headRequest() = runBlocking {
        val tag = "HEAD Request"
        var status = 0
        var response: HiperResponse? = null
        val statusCodes = listOf(200,404)
        hiper.head("https://httpbin.org/image").resolve {
            response = it
            status = 1
        }.reject {
            response = it
            status = 1
        }.catch {
            status = 1
        }
        while (status==0) delay(100)
        Log.d(tag, response.toString())

        assertThat(response).isNotNull()
        assertThat(response!!.statusCode).isIn(statusCodes)
    }



    @Test
    fun getRangeHeaderRequest() = runBlocking {
        val tag = "HEAD Request"
        var status = 0
        var response: HiperResponse? = null
        val statusCodes = listOf(206,404,416) // Partial Content, Not Found, Range Not Satisfiable
        hiper.get("https://i.ibb.co/zsgxRY6/pexels-christina-morillo-1181359.jpg", headers = mix("Range" to "bytes=0-200")).resolve {
            response = it
            status = 1
        }.reject {
            response = it
            status = 1
        }.catch {
            status = 1
        }
        while (status==0) delay(100)
        Log.d(tag, response.toString())

        assertThat(response).isNotNull()
        assertThat(response!!.statusCode).isIn(statusCodes)
    }

}