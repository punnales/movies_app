package com.punnales.moviesapp.data.local.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.punnales.moviesapp.UserProto
import com.punnales.moviesapp.data.local.datastore.serializer.UserProtoSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    private const val USER_DATASTORE_FILE = "user.pb"

    @Provides
    @Singleton
    fun provideDriverDatastore(@ApplicationContext context: Context): DataStore<UserProto> =
        DataStoreFactory.create(
            UserProtoSerializer,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { context.dataStoreFile(USER_DATASTORE_FILE) },
            corruptionHandler = null
        )
}