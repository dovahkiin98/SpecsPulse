package specspulse.app

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import specspulse.app.data.local.LocalDataSource
import specspulse.app.data.remote.RemoteDataSource
import specspulse.app.data.repo.RepositoryImpl

class ExampleUnitTest {
    private val remoteDataSource = mockk<RemoteDataSource>()
    private val localDataSource = mockk<LocalDataSource>()

    @OptIn(DelicateCoroutinesApi::class)
    val repository = RepositoryImpl(
        localDataSource = localDataSource,
        remoteDataSource = remoteDataSource,
        dispatcher = Dispatchers.IO,
        scope = GlobalScope,
    )

    @Test
    fun `searchDevices should call remoteDataSource search`() = runTest {
        val searchTerm = "abc"

        repository.searchDevices(searchTerm)

        coVerify { remoteDataSource.search(searchTerm) }
    }
}