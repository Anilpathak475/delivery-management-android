package co.parsl.android.boilerplate.ui.test.util

import co.parsl.android.boilerplate.data.browse.Bufferoo

/**
 * Factory class for Bufferoo related instances
 */
object BufferooFactory {

    fun makeBufferooList(count: Int): List<Bufferoo> {
        val bufferoos = mutableListOf<Bufferoo>()
        repeat(count) {
            bufferoos.add(BufferooFactory.makeBufferooModel())
        }
        return bufferoos
    }

    fun makeBufferooModel(): Bufferoo {
        return Bufferoo(DataFactory.randomLong(), DataFactory.randomUuid(),
                DataFactory.randomUuid(), DataFactory.randomUuid())
    }

}