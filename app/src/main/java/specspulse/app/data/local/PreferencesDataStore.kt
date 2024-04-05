package specspulse.app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class PreferencesDataStore(context: Context) : LocalDataSource {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

    private val dataStore = context.dataStore

    override val language
        get() = dataStore.data.map {
            it[LANGUAGE]
        }

    override val nightMode
        get() = dataStore.data.map {
            it[NIGHT_MODE]
        }

    override suspend fun updateTheme(nightMode: Int) {
        dataStore.edit {
            it[NIGHT_MODE] = nightMode
        }
    }

    companion object {
        private const val LANGUAGE_KEY = "language"
        private val LANGUAGE = stringPreferencesKey(LANGUAGE_KEY)

        private const val NIGHT_MODE_KEY = "theme"
        private val NIGHT_MODE = intPreferencesKey(NIGHT_MODE_KEY)
    }
}