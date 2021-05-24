package com.daffa.moviecatalogue.core.data

import com.daffa.moviecatalogue.core.data.source.Resource
import com.daffa.moviecatalogue.core.data.source.remote.network.ApiResponse
import com.daffa.moviecatalogue.utils.AppExecutors
import kotlinx.coroutines.flow.*
import java.util.concurrent.Executors

abstract class NetworkBoundResource<ResultType, RequestType>(private val executors: AppExecutors) {

    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        val dbSource = loadFromDb().first()
        if (shouldFetch(dbSource)) {
            emit(Resource.Loading())
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDb().map { Resource.Success(it) })
                }
                is ApiResponse.Empty -> {
                    emitAll(loadFromDb().map { Resource.Success(it) })
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(Resource.Error<ResultType>(apiResponse.errorMessage))
                }
            }
        } else {
            emitAll(loadFromDb().map { Resource.Success(it) })
        }

    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDb(): Flow<ResultType>
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>
    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Resource<ResultType>> = result
}