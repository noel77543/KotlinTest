package tw.noel811221.sung.kotlintest.utils

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class ApiCallback(private var event: Int) : Callback<ResponseBody> {
    companion object {
        const val EVENT_CAFE = 1
    }


    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        val responseBody = response.body()
        if (responseBody != null) {
            onSuccess(responseBody.string(), event)
        } else {
            val message: String = response.message();
            onFailed(message, event)
        }
    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        onFailed(t.message.toString(), event)

    }


     abstract fun onSuccess(response: String, event: Int)
     abstract fun onFailed(message: String, event: Int)
}