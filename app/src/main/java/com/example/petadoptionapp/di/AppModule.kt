package com.example.petadoptionapp.di

import com.example.petadoptionapp.data.repoImp.AuthRepoImp
import com.example.petadoptionapp.data.repoImp.PostRepoImp
import com.example.petadoptionapp.data.repoImp.ProfileRepoImp
import com.example.petadoptionapp.domain.repo.AuthRepo
import com.example.petadoptionapp.domain.repo.PostRepo
import com.example.petadoptionapp.domain.repo.ProfileRepo
import com.example.petadoptionapp.domain.usecases.auth.ReloadUserUseCases
import com.example.petadoptionapp.domain.usecases.auth.ResetPasswordUseCase
import com.example.petadoptionapp.domain.usecases.auth.SignInUseCase
import com.example.petadoptionapp.domain.usecases.auth.SignOutUseCase
import com.example.petadoptionapp.domain.usecases.auth.SignUpUseCase
import com.example.petadoptionapp.domain.usecases.post.GetPostUseCase
import com.example.petadoptionapp.domain.usecases.post.PostCreationUseCase
import com.example.petadoptionapp.domain.usecases.profile.SaveProfileUseCase
import com.example.petadoptionapp.domain.usecases.savedPost.GetSavedPostUseCase
import com.example.petadoptionapp.domain.usecases.savedPost.RemoveSavedPostUseCase
import com.example.petadoptionapp.domain.usecases.savedPost.SavePostUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
   fun providePostRepo():PostRepo{
       return PostRepoImp()
   }
    @Provides
    @Singleton
   fun providePostCreationUseCase(postRepo: PostRepo):PostCreationUseCase{
       return PostCreationUseCase(postRepo)
   }
    @Provides
    @Singleton
    fun provideAuthRepo(): AuthRepo {
        return AuthRepoImp()
    }
    @Provides
    @Singleton
    fun provideProfileRepo(): ProfileRepo {
        return ProfileRepoImp()
    }
    @Provides
    @Singleton
    fun provideSignUpUseCase(authRepo: AuthRepo): SignUpUseCase {
        return SignUpUseCase(authRepo)
    }
    @Provides
    @Singleton
    fun provideLoadUserUseCase(authRepo: AuthRepo): ReloadUserUseCases {
        return ReloadUserUseCases(authRepo)
    }
    @Provides
    @Singleton
    fun provideSignInUseCase(authRepo: AuthRepo): SignInUseCase {
        return SignInUseCase(authRepo)
    }
    @Provides
    @Singleton
    fun provideSignOutUseCase(authRepo: AuthRepo): SignOutUseCase {
        return SignOutUseCase(authRepo)
    }
    @Provides
    @Singleton
    fun provideResetPasswordUseCase(authRepo: AuthRepo): ResetPasswordUseCase {
        return  ResetPasswordUseCase(authRepo)
    }
    @Provides
    @Singleton
    fun provideSaveProfileUseCase(profileRepo: ProfileRepo): SaveProfileUseCase {
        return  SaveProfileUseCase(profileRepo)
    }

    @Provides
    @Singleton
    fun provideGetPostUseCase(postRepo: PostRepo): GetPostUseCase {
        return  GetPostUseCase(postRepo)
    }
    @Provides
    @Singleton
    fun provideSavePostUseCase(postRepo: PostRepo): SavePostUseCase {
        return  SavePostUseCase(postRepo)
    }
    @Provides
    @Singleton
    fun provideGetSavedPostUseCase(postRepo: PostRepo): GetSavedPostUseCase {
        return  GetSavedPostUseCase(postRepo)
    }
    @Provides
    @Singleton
    fun provideRemoveSavedPostUseCase(postRepo: PostRepo): RemoveSavedPostUseCase {
        return  RemoveSavedPostUseCase(postRepo)
    }
}