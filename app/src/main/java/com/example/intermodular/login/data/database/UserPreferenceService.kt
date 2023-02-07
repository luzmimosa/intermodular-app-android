package com.example.intermodular.login.data.database

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private const val user_preferences= "user_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name= user_preferences)

class UserPreferenceService @Inject constructor(private val context: Context): IUserPreferences{
    override suspend fun addToken(key: String, value: String){
        val preferenceKey= stringPreferencesKey(key)

        context.dataStore.edit{
            it[preferenceKey] = value
        }
    }

    override suspend fun getToken(key: String): String {
        return try{
            val preferencedKey= stringPreferencesKey(key)
            val preferences= context.dataStore.data.first()
            preferences[preferencedKey] ?:""
        } catch(e: Exception){
            Log.e("WikiHonk", "Error: ${e.message}")
            ""
        }
    }
}