package hoannt.android.moviedb.data.network.interceptor

import hoannt.android.moviedb.AppConstants.Companion.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()
        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("api_key", API_KEY).build()

        val requestBuilder = originalRequest.newBuilder().url(newUrl)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}