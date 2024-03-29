package co.parsl.android.boilerplate.cache.mapper

import co.parsl.android.boilerplate.cache.model.CachedBufferoo
import co.parsl.android.boilerplate.cache.test.factory.BufferooFactory
import co.parsl.android.boilerplate.data.browse.Bufferoo
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class BufferooMapperTest {

    private val bufferooEntityMapper = BufferooEntityMapper()

    @Test
    fun mapToCachedMapsData() {
        val bufferooEntity = BufferooFactory.makeBufferooEntity()
        val cachedBufferoo = bufferooEntityMapper.mapToCached(bufferooEntity)

        assertBufferooDataEquality(bufferooEntity, cachedBufferoo)
    }

    @Test
    fun mapFromCachedMapsData() {
        val cachedBufferoo = BufferooFactory.makeCachedBufferoo()
        val bufferooEntity = bufferooEntityMapper.mapFromCached(cachedBufferoo)

        assertBufferooDataEquality(bufferooEntity, cachedBufferoo)
    }

    private fun assertBufferooDataEquality(bufferoo: Bufferoo,
                                           cachedBufferoo: CachedBufferoo) {
        assertEquals(bufferoo.name, cachedBufferoo.name)
        assertEquals(bufferoo.title, cachedBufferoo.title)
        assertEquals(bufferoo.avatar, cachedBufferoo.avatar)
    }

}