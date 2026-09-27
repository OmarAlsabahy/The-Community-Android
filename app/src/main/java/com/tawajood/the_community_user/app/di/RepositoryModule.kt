package com.tawajood.the_community_user.app.di

import com.tawajood.the_community_user.data.repository.auth.AuthRepository
import com.tawajood.the_community_user.data.repository.home.HomeRepository
import com.tawajood.the_community_user.data.repository.language.LanguageRepository
import com.tawajood.the_community_user.data.repository.on_boarding.OnBoardingRepository
import com.tawajood.the_community_user.data.repository.profile.ProfileRepository
import com.tawajood.the_community_user.domain.repository.auth.IAuthRepository
import com.tawajood.the_community_user.domain.repository.home.IHomeRepository
import com.tawajood.the_community_user.domain.repository.language.ILanguageRepository
import com.tawajood.the_community_user.domain.repository.on_boarding.IOnBoardingRepository
import com.tawajood.the_community_user.domain.repository.profile.IProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLanguageRepository(
        languageRepository: LanguageRepository
    ): ILanguageRepository

    @Binds
    @Singleton
    abstract fun bindOnBoardingRepository(
        onBoardingRepository: OnBoardingRepository
    ): IOnBoardingRepository
    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        repository: AuthRepository
    ): IAuthRepository
    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        repository: ProfileRepository
    ): IProfileRepository
    @Binds
    @Singleton
    abstract fun bindHomeRepository(
        repository: HomeRepository
    ): IHomeRepository
}
