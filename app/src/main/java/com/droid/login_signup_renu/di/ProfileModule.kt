package com.droid.login_signup_renu.di

import com.droid.login_signup_renu.repository.ProfileRepository
import com.droid.login_signup_renu.repository.ProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideProfileRepository(): ProfileRepository {
        return ProfileRepositoryImpl() // Provide the implementation of ProfileRepository
    }
}
