package co.parsl.android.boilerplate.data

import io.reactivex.Completable
import io.reactivex.Flowable
import co.parsl.android.boilerplate.data.browse.Bufferoo
import co.parsl.android.boilerplate.data.repository.BufferooRepository
import co.parsl.android.boilerplate.data.source.BufferooDataStoreFactory

/**
 * Provides an implementation of the [BufferooRepository] interface for communicating to and from
 * data sources
 */
open class BufferooDataRepository(private val factory: BufferooDataStoreFactory) :
        BufferooRepository {

    override fun clearBufferoos(): Completable {
        return factory.retrieveCacheDataStore().clearBufferoos()
    }

    override fun saveBufferoos(bufferoos: List<Bufferoo>): Completable {
        return factory.retrieveCacheDataStore().saveBufferoos(bufferoos)
    }

    override fun getBufferoos(): Flowable<List<Bufferoo>> {
        return factory.retrieveCacheDataStore().isCached()
                .flatMapPublisher {
                    factory.retrieveDataStore(it).getBufferoos()
                }
                .flatMap {
                    saveBufferoos(it).toSingle { it }.toFlowable()
                }
    }

}