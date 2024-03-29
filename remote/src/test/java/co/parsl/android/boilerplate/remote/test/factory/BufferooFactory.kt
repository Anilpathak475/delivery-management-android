package co.parsl.android.boilerplate.remote.test.factory

import co.parsl.android.boilerplate.remote.BufferooService
import co.parsl.android.boilerplate.remote.model.BufferooModel
import co.parsl.android.boilerplate.remote.test.factory.DataFactory.Factory.randomLong
import co.parsl.android.boilerplate.remote.test.factory.DataFactory.Factory.randomUuid

/**
 * Factory class for Bufferoo related instances
 */
class BufferooFactory {

    companion object Factory {

        fun makeBufferooResponse(): BufferooService.BufferooResponse {
            val bufferooResponse = BufferooService.BufferooResponse()
            bufferooResponse.team = makeBufferooModelList(5)
            return bufferooResponse
        }

        fun makeBufferooModelList(count: Int): List<BufferooModel> {
            val bufferooEntities = mutableListOf<BufferooModel>()
            repeat(count) {
                bufferooEntities.add(makeBufferooModel())
            }
            return bufferooEntities
        }

        fun makeBufferooModel(): BufferooModel {
            return BufferooModel(randomLong(), randomUuid(), randomUuid(), randomUuid())
        }

    }

}