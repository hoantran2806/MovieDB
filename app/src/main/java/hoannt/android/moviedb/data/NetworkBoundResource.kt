package hoannt.android.moviedb.data

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class NetworkBoundResource<ResultType, RequestType> @MainThread
protected constructor() {
    private val TAG = "NetworkBound_TranHoan"
    private val asObservable: Observable<Resource<ResultType>>

    init {
        val source: Observable<Resource<ResultType>>
        if (shouldFetch()) {
            Log.i(TAG, "1 - : shouldFetch() = true")
            source = createCall().subscribeOn(Schedulers.io())
                .doOnNext {
                    Log.i(TAG, "3 - : doOnNext")
                    saveCallResult(processResponse(it)!!)
                }
                .flatMap {
                    loadFromDb().toObservable()
                        .map { Resource.success(it) }
                }
                .doOnError {
                    onFetchFail()
                }
                .onErrorResumeNext { t: Throwable ->
                    loadFromDb().toObservable().map {
                        Resource.error(t.message!!, it)
                    }
                }.observeOn(AndroidSchedulers.mainThread())
        } else {
            Log.i(TAG, ": shouldFetch() = false")
            source = loadFromDb().toObservable().map {
                Resource.success(it)
            }
        }

        asObservable = Observable.concat(
            loadFromDb().toObservable()
                .map {
                    Log.i(TAG, ": asObservable")
                    Resource.loading(it)
                }
                .take(1), source
        )
    }

    @WorkerThread
    protected fun processResponse(response: Resource<RequestType>): RequestType? {
        return response.data
    }

    fun getAsObservable(): Observable<Resource<ResultType>> {
        Log.i(TAG, "getAsObservable: call method getAsObservable")
        return asObservable
    }

    private fun onFetchFail() {}

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(): Boolean

    @MainThread
    protected abstract fun loadFromDb(): Flowable<ResultType>

    @MainThread
    protected abstract fun createCall(): Observable<Resource<RequestType>>
}