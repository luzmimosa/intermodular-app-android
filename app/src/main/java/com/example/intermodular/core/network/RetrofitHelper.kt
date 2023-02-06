package com.example.intermodular.core.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetAddress
import java.net.Socket
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

object RetrofitHelper {
    fun getRetrofit(): Retrofit{
        val client = OkHttpClient.Builder()
            .sslSocketFactory(
                NoopSSLSocketFactory.INSTANCE,
                NoopTrustManager()
            )
            .hostnameVerifier { _, _ -> true }
            .build()

        return Retrofit
            .Builder()
            .baseUrl("https://intermodular.fadedbytes.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

class NoopTrustManager : X509TrustManager {
    companion object {
        val INSTANCE: NoopTrustManager = NoopTrustManager()
    }

    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()

}

class NoopSSLSocketFactory : SSLSocketFactory() {
    companion object {
        val INSTANCE: NoopSSLSocketFactory = NoopSSLSocketFactory()
    }

    private val sslContext = SSLContext.getInstance("TLS").apply {
        init(null, arrayOf(NoopTrustManager.INSTANCE), SecureRandom())
    }

    override fun getDefaultCipherSuites(): Array<String> = sslContext.socketFactory.defaultCipherSuites

    override fun getSupportedCipherSuites(): Array<String> = sslContext.socketFactory.supportedCipherSuites

    override fun createSocket(socket: Socket?, host: String?, port: Int, autoClose: Boolean): Socket? =
        sslContext.socketFactory.createSocket(socket, host, port, autoClose)

    override fun createSocket(host: String?, port: Int): Socket? =
        sslContext.socketFactory.createSocket(host, port)

    override fun createSocket(host: String?, port: Int, localHost: InetAddress?, localPort: Int): Socket? =
        sslContext.socketFactory.createSocket(host, port, localHost, localPort)

    override fun createSocket(host: InetAddress?, port: Int): Socket? =
        sslContext.socketFactory.createSocket(host, port)

    override fun createSocket(address: InetAddress?, port: Int, localAddress: InetAddress?, localPort: Int): Socket? =
        sslContext.socketFactory.createSocket(address, port, localAddress, localPort)
}