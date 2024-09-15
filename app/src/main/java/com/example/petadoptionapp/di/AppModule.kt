package com.example.petadoptionapp.di

import android.content.Context
import androidx.room.Room
import com.example.petadoptionapp.data.local.cachingAppliedApplications.AppliedApplicationsDao
import com.example.petadoptionapp.data.local.cachingAppliedApplications.Database
import com.example.petadoptionapp.data.local.cachingUserProfile.UserDatastore
import com.example.petadoptionapp.data.repoImp.ApplicationRepoImp
import com.example.petadoptionapp.data.repoImp.AuthRepoImp
import com.example.petadoptionapp.data.repoImp.PostRepoImp
import com.example.petadoptionapp.data.repoImp.ProfileRepoImp
import com.example.petadoptionapp.domain.repo.ApplicationRepo
import com.example.petadoptionapp.domain.repo.AuthRepo
import com.example.petadoptionapp.domain.repo.PostRepo
import com.example.petadoptionapp.domain.repo.ProfileRepo
import com.example.petadoptionapp.domain.usecases.application.AddAppliedApplicationLocallyUseCase
import com.example.petadoptionapp.domain.usecases.application.DatabaseIsEmptyUseCase
import com.example.petadoptionapp.domain.usecases.application.EditNotificationUseCase
import com.example.petadoptionapp.domain.usecases.application.GetApplicationPostUseCase
import com.example.petadoptionapp.domain.usecases.application.GetApplicationsUseCase
import com.example.petadoptionapp.domain.usecases.application.GetAppliedApplicationsUseCase
import com.example.petadoptionapp.domain.usecases.application.SendingApplicationUseCase
import com.example.petadoptionapp.domain.usecases.application.SetApplicationStatusUseCase
import com.example.petadoptionapp.domain.usecases.auth.DeleteNotVerifiedUserUseCase
import com.example.petadoptionapp.domain.usecases.auth.DeleteUserAccountUseCase
import com.example.petadoptionapp.domain.usecases.auth.ReloadUserUseCases
import com.example.petadoptionapp.domain.usecases.auth.ResendVerificationEmailUseCase
import com.example.petadoptionapp.domain.usecases.auth.ResetPasswordUseCase
import com.example.petadoptionapp.domain.usecases.auth.SignInUseCase
import com.example.petadoptionapp.domain.usecases.auth.SignOutUseCase
import com.example.petadoptionapp.domain.usecases.auth.SignUpUseCase
import com.example.petadoptionapp.domain.usecases.post.DeletePostUseCase
import com.example.petadoptionapp.domain.usecases.post.EditPostUseCase
import com.example.petadoptionapp.domain.usecases.post.GetFilteredPostsUseCase
import com.example.petadoptionapp.domain.usecases.post.GetMyPostsUseCase
import com.example.petadoptionapp.domain.usecases.post.GetPostUseCase
import com.example.petadoptionapp.domain.usecases.post.GetSelectedCountryPostsUseCase
import com.example.petadoptionapp.domain.usecases.post.PostCreationUseCase
import com.example.petadoptionapp.domain.usecases.profile.ProfileExistsUseCase
import com.example.petadoptionapp.domain.usecases.profile.SaveProfileUseCase
import com.example.petadoptionapp.domain.usecases.savedPost.GetSavedPostUseCase
import com.example.petadoptionapp.domain.usecases.savedPost.RemoveSavedPostUseCase
import com.example.petadoptionapp.domain.usecases.savedPost.SavePostUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context:Context): Context {
        return context
    }
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
   fun provideEditPostUseCase(postRepo: PostRepo): EditPostUseCase {
       return EditPostUseCase(postRepo)
   }
    @Provides
    @Singleton
   fun provideDeletePostUseCase(postRepo: PostRepo): DeletePostUseCase {
       return DeletePostUseCase(postRepo)
   }
    @Provides
    @Singleton
   fun provideGetFilteredPostsUseCase(postRepo: PostRepo): GetFilteredPostsUseCase {
       return GetFilteredPostsUseCase(postRepo)
   }
    @Provides
    @Singleton
   fun provideGetSelectedCountryPostsUseCase(postRepo: PostRepo): GetSelectedCountryPostsUseCase {
       return GetSelectedCountryPostsUseCase(postRepo)
   }
    @Provides
    @Singleton
    fun provideAuthRepo(db:Database,userDatastore: UserDatastore): AuthRepo {
        return AuthRepoImp(db,userDatastore)
    }
    @Provides
    @Singleton
    fun provideProfileRepo(userDatastore:UserDatastore): ProfileRepo {
        return ProfileRepoImp(userDatastore)
    }
    @Provides
    @Singleton
    fun provideApplicationRepo(): ApplicationRepo {
        return ApplicationRepoImp()
    }
    @Provides
    @Singleton
    fun provideSignUpUseCase(authRepo: AuthRepo): SignUpUseCase {
        return SignUpUseCase(authRepo)
    }
    @Provides
    @Singleton
    fun provideResendVerificationEmailUseCase(authRepo: AuthRepo): ResendVerificationEmailUseCase {
        return ResendVerificationEmailUseCase(authRepo)
    }
    @Provides
    @Singleton
    fun provideDeleteNotVerifiedUserUseCase(authRepo: AuthRepo): DeleteNotVerifiedUserUseCase {
        return DeleteNotVerifiedUserUseCase(authRepo)
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
    fun provideDeleteUserAccountUseCase(authRepo: AuthRepo): DeleteUserAccountUseCase {
        return  DeleteUserAccountUseCase(authRepo)
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
    @Provides
    @Singleton
    fun provideGetMyPostsUseCase(postRepo: PostRepo): GetMyPostsUseCase {
        return  GetMyPostsUseCase(postRepo)
    }
    @Provides
    @Singleton
    fun provideSendingApplicationUseCase(applicationRepo: ApplicationRepo): SendingApplicationUseCase{
        return  SendingApplicationUseCase(applicationRepo)
    }
    @Provides
    @Singleton
    fun provideGetApplicationsUseCase(applicationRepo: ApplicationRepo): GetApplicationsUseCase {
        return  GetApplicationsUseCase(applicationRepo)
    }
    @Provides
    @Singleton
    fun provideSetApplicationStatusUseCase(applicationRepo: ApplicationRepo): SetApplicationStatusUseCase {
        return SetApplicationStatusUseCase(applicationRepo)
    }
    @Provides
    @Singleton
    fun provideGetApplicationPostUseCase(applicationRepo: ApplicationRepo): GetApplicationPostUseCase {
        return  GetApplicationPostUseCase(applicationRepo)
    }
    @Provides
    @Singleton
    fun provideEditNotificationUseCase(applicationRepo: ApplicationRepo): EditNotificationUseCase {
        return  EditNotificationUseCase(applicationRepo)
    }
    @Provides
    @Singleton
    fun provideGetAppliedApplicationsUseCase(applicationRepo: ApplicationRepo,dao: AppliedApplicationsDao): GetAppliedApplicationsUseCase {
        return  GetAppliedApplicationsUseCase(applicationRepo,dao)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): Database{
        val db = Room.databaseBuilder(context = context
            ,Database::class.java,
            "appliedApplications.db"
        ).build()
        return db
    }
    @Provides
    @Singleton
    fun provideDao(db:Database): AppliedApplicationsDao {
        return db.appliedApplicationsDao
    }
    @Provides
    @Singleton
    fun provideAddAppliedApplicationUseCase(dao: AppliedApplicationsDao): AddAppliedApplicationLocallyUseCase {
    return AddAppliedApplicationLocallyUseCase(dao)
    }
    @Provides
    @Singleton
    fun provideCheckDatabaseIsEmptyUseCase(dao: AppliedApplicationsDao): DatabaseIsEmptyUseCase {
    return DatabaseIsEmptyUseCase(dao)
    }

    //profile use cases
    @Provides
    @Singleton
    fun provideProfileExistsUseCase(profileRepo: ProfileRepo): ProfileExistsUseCase {
        return ProfileExistsUseCase(profileRepo)
    }

    @Provides
    @Singleton
    fun provideUserDatastore(context: Context):UserDatastore{
        return UserDatastore(context)
    }
}