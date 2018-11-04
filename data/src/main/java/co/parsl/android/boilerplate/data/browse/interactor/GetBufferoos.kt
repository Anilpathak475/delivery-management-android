package co.parsl.android.boilerplate.data.browse.interactor

import io.reactivex.Flowable
import co.parsl.android.boilerplate.data.browse.Bufferoo
import co.parsl.android.boilerplate.data.executor.PostExecutionThread
import co.parsl.android.boilerplate.data.executor.ThreadExecutor
import co.parsl.android.boilerplate.data.interactor.FlowableUseCase
import co.parsl.android.boilerplate.data.repository.BufferooRepository

open class GetBufferoos(val bufferooRepository: BufferooRepository,
                        threadExecutor: ThreadExecutor,
                        postExecutionThread: PostExecutionThread):
        FlowableUseCase<List<Bufferoo>, Void?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Void?): Flowable<List<Bufferoo>> {
        return bufferooRepository.getBufferoos()
    }
}