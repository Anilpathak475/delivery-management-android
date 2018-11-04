package co.parsl.android.boilerplate.data.test.factory

import co.parsl.android.boilerplate.data.browse.Bufferoo
import co.parsl.android.boilerplate.data.test.factory.DataFactory.Factory.randomLong
import co.parsl.android.boilerplate.data.test.factory.DataFactory.Factory.randomUuid

/**
 * Factory class for Bufferoo related instances
 */
object BufferooFactory {

    fun makeBufferoo(): Bufferoo {
        return Bufferoo(randomLong(), randomUuid(), randomUuid(), randomUuid())
    }

    fun makeBufferooList(count: Int): List<Bufferoo> {
        val bufferoos = mutableListOf<Bufferoo>()
        repeat(count) {
            bufferoos.add(makeBufferoo())
        }
        return bufferoos
    }
}