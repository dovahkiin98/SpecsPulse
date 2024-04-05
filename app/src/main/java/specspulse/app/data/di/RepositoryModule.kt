package specspulse.app.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import specspulse.app.data.local.LocalDataSource
import specspulse.app.data.local.PreferencesDataStore
import specspulse.app.data.remote.JsoupDataSource
import specspulse.app.data.remote.RemoteDataSource
import specspulse.app.data.repo.Repository
import specspulse.app.data.repo.RepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providesLocalDataSource(
        @ApplicationContext context: Context,
    ): LocalDataSource = PreferencesDataStore(context)

    @Provides
    fun providesRemoteDataSource(
    ): RemoteDataSource = JsoupDataSource()

    @Provides
    fun providesRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        @DefaultDispatcher dispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
    ): Repository = RepositoryImpl(
        localDataSource,
        remoteDataSource,
        dispatcher,
        scope,
    )
}