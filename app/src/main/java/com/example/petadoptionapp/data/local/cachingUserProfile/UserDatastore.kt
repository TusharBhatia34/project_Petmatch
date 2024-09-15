package com.example.petadoptionapp.data.local.cachingUserProfile

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.petadoptionapp.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "userProfile")

class UserDatastore @Inject constructor(private val context: Context) {


    // Define keys
    private val PROFILE_PICTURE = stringPreferencesKey("profilePicture")
    private val NAME_KEY = stringPreferencesKey("name")
    private val LOCATION_KEY = stringPreferencesKey("location")
    private val ABOUT_KEY = stringPreferencesKey("about")
    private val PROFILE_EXISTS_KEY = booleanPreferencesKey("profileExists")



    val profileExists: Flow<Boolean?> = context.dataStore.data.map { preferences ->
        preferences[PROFILE_EXISTS_KEY]
    }

     fun getProfileInfo() =  context.dataStore.data.map { preferences ->

             UserProfile(
                 name = preferences[NAME_KEY]?:"Unknown",
                 country = preferences[LOCATION_KEY]?:"Unknown location",
                 about = preferences[ABOUT_KEY]?:"N/A",
                 profilePicture = preferences[PROFILE_PICTURE]?:"",
             )

    }



    // Save values
    suspend fun saveProfileInfo(userProfile: UserProfile) {
        context.dataStore.edit { preferences ->
            preferences[PROFILE_PICTURE] = userProfile.profilePicture
            preferences[NAME_KEY] = userProfile.name
            preferences[LOCATION_KEY] = userProfile.country
            preferences[ABOUT_KEY] = userProfile.about
            preferences[PROFILE_EXISTS_KEY] = true

        }
    }
    suspend fun updateProfileExists() {
        context.dataStore.edit { preferences ->
            preferences[PROFILE_EXISTS_KEY] = false
        }
    }

    // Clear all data
    suspend fun clearDataStore() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    fun getCurrentProfileLocation() =
        context.dataStore.data.map{pref ->
            pref[LOCATION_KEY]
        }
     fun getCurrentProfilePicture() =
        context.dataStore.data.map{pref ->
            pref[PROFILE_PICTURE]
        }
    }

