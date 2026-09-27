package com.tawajood.the_community_user.data.app_pref

import com.tawajood.the_community_user.data.data_store.IDataStorePreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(
    dataStore: IDataStorePreferences
) {

    @Volatile
    var language: String = Locale.getDefault().language
        private set

    @Volatile
    var token: String? = null
        private set

    init {
        CoroutineScope(Dispatchers.IO).launch {
            launch {
                dataStore.languageFlow.collect {
                    language = it.code
                }
            }
            launch {
                dataStore.tokenFlow.collect {
                    token = it
                }
            }
        }
    }
}
