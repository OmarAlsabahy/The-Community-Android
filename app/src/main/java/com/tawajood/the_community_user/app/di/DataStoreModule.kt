package com.tawajood.the_community_user.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.tawajood.the_community_user.data.data_store.DataStorePreferences
import com.tawajood.the_community_user.data.data_store.IDataStorePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private const val DS_NAME = "settings"

    @Provides
    @Singleton
    fun providePreferencesDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(DS_NAME) }
        )
    }

    @Provides
    @Singleton
    fun provideDataStorePreferencesImp(
        pref: DataStore<Preferences>
    ): IDataStorePreferences = DataStorePreferences(pref)
}