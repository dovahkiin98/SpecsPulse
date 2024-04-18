package specspulse.app.data.repo

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import specspulse.app.data.local.LocalDataSource
import specspulse.app.data.remote.RemoteDataSource

class RepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val dispatcher: CoroutineDispatcher,
    private val scope: CoroutineScope,
) : Repository {

    override suspend fun getMostPopular() = withContext(dispatcher) {
        withTimeout(TIMEOUT) {
            remoteDataSource.search("")
        }

    }

    override suspend fun searchDevices(term: String) = makeRequest {
        remoteDataSource.search(term)
    }

    override suspend fun getDeviceDetails(link: String) = makeRequest {
        remoteDataSource.getDeviceDetails(link)
    }

    override suspend fun getDeviceImages(link: String) = makeRequest {
        remoteDataSource.getDeviceImages(link)
    }

    private suspend fun <T> makeRequest(
        block: suspend CoroutineScope.() -> T,
    ) = withContext(dispatcher) {
        withTimeout(TIMEOUT) {
            block()
        }
    }

    companion object {
        const val TIMEOUT = 8_000L
    }
}