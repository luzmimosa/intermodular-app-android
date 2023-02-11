package com.example.intermodular.ui.feature.login.data.network

import com.example.intermodular.R

enum class LoginResult(val messageResourceID: Int) {

    SUCCESS(R.string.network_loginresult_success),
    WRONG_CREDENTIALS(R.string.network_loginresult_wrong_credentials),
    NETWORK_ERROR(R.string.network_error_message),
    UNKNOWN_ERROR(R.string.unknown_error_message),

}