package com.org.capstone.nutrifish.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.org.capstone.nutrifish.data.remote.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>){

    suspend fun login(token: String) {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = true
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = false
            preferences[TOKEN_KEY] = ""
            preferences[NAME_KEY] = ""
            preferences[USERNAME_KEY] = ""
            preferences[EMAIL_KEY] = ""
            preferences[PASSWORD_KEY] = ""
        }
    }

    fun getUser(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[UID_KEY] ?: "",
                preferences[NAME_KEY] ?: "",
                preferences[USERNAME_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[PASSWORD_KEY] ?: "",
                preferences[STATE_KEY] ?: false,
                preferences[TOKEN_KEY] ?: "",
                preferences[GOOGLE] ?: false,
                preferences[PHOTO_KEY] ?: ""
            )
        }
    }

    suspend fun setUser(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[UID_KEY] = user.uid
            preferences[NAME_KEY] = user.name
            preferences[USERNAME_KEY] = user.username ?: ""
            preferences[EMAIL_KEY] = user.email
            preferences[STATE_KEY] = user.isLogin
            preferences[TOKEN_KEY] = user.token
            preferences[GOOGLE] = user.isGoogle
            preferences[PHOTO_KEY] = user.photoUrl ?: ""
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        private val UID_KEY = stringPreferencesKey("uid")
        private val NAME_KEY = stringPreferencesKey("name")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val STATE_KEY = booleanPreferencesKey("state")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val GOOGLE = booleanPreferencesKey("isGoogle")
        private val PHOTO_KEY = stringPreferencesKey("photoUrl")

        fun getInstance(dataStore: DataStore<Preferences>) : SettingPreferences {
            return INSTANCE ?: synchronized(this){
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }

    }
}