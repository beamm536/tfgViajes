package com.appclass.myapplication.data.dataStore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences

val Context.datastore by preferencesDataStore(name = "favorites")