package com.example.baapplication.login

interface NavigatorLogin {

    fun goToHomeActivity()
    fun signInWithPhoneNumber()
    fun showError(errorMsg:String)
    fun goToLoginActivity()
    fun showMsg()
    fun goToSignInActivity()
}