package co.parsl.android.boilerplate.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import co.parsl.android.boilerplate.data.browse.Bufferoo

interface BufferooRepository {

    open fun clearBufferoos(): Completable

    open fun saveBufferoos(bufferoos: List<Bufferoo>): Completable

    open fun getBufferoos(): Flowable<List<Bufferoo>>

}