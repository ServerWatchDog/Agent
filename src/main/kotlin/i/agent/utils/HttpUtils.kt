package i.agent.utils

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.Optional
import kotlin.reflect.KClass

class HttpUtils(private val baseUrl: String, private val token: String) {
    private val client = OkHttpClient()
    private val gson = Gson()

    fun <T : Any> get(path: String, type: KClass<T>): Optional<T> {
        return client.newCall(
            Request.Builder()
                .url("$baseUrl$path")
                .addHeader("Authorization", "Bearer $token").build()
        ).execute().body?.let { Optional.of(gson.fromJson(it.string(), type.javaObjectType)) } ?: Optional.empty()
    }

    fun <T : Any> post(path: String, type: KClass<T>, post: Any): Optional<T> {
        return client.newCall(
            Request.Builder()
                .post(
                    gson.toJson(post)
                        .toRequestBody("application/json".toMediaType())
                )
                .url("$baseUrl$path")
                .addHeader("Authorization", "Bearer $token").build()
        ).execute().body?.let { Optional.of(gson.fromJson(it.string(), type.javaObjectType)) } ?: Optional.empty()
    }
}
