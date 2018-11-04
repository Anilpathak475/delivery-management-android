package co.parsl.android.boilerplate.remote

import co.parsl.android.boilerplate.remote.model.BufferooModel
import io.reactivex.Flowable
import retrofit2.http.GET

/**
 * Defines the abstract methods used for interacting with the Bufferoo API
 */
interface BufferooService {

    @GET("team.json")
    fun getBufferoos(): Flowable<BufferooResponse>

    class BufferooResponse {
        lateinit var team: List<BufferooModel>
    }

}