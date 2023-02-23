package com.example.intermodular.ui.feature.userinfo.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.intermodular.core.authentication.AuthenticationTokenManager

class UserInfoViewModel(val context: Context): ViewModel() {

    fun logOut() {
        AuthenticationTokenManager.saveToken(context, null)
    }

}