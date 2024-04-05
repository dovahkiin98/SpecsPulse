package specspulse.app.data.local

import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    val language: Flow<String?>

    val nightMode: Flow<Int?>

    suspend fun updateTheme(nightMode: Int)
}