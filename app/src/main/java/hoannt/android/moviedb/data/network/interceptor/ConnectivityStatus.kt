package hoannt.android.moviedb.data.network.interceptor

import android.content.Context
import android.content.ContextWrapper
import android.net.ConnectivityManager

class ConnectivityStatus(context: Context) : ContextWrapper(context) {
    companion object {
        fun isConnected(context: Context): Boolean {
            val manager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val connection = manager.activeNetworkInfo
            return connection != null && connection.isConnectedOrConnecting
        }
    }
}