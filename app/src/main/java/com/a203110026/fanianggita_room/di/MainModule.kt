package com.a203110026.fanianggita_room.di

import android.content.Context
import androidx.room.Room
import com.a203110026.fanianggita_room.data.CatatanDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): CatatanDatabase =
        Room.databaseBuilder(context, CatatanDatabase::class.java, "notes.db").build()

    @Provides
    fun providesNotesDao(dataBase: CatatanDatabase) = dataBase.noteDao()
}