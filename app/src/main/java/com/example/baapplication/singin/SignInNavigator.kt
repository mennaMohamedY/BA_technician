package com.example.baapplication.singin

interface SignInNavigator {
    fun showError_userNotexist()
    fun navigateToHomeActivity()
    fun navigateToRegisterActivity()
    fun showError(errorMsg:String)
    fun navigateToSideDrawer()

    fun showPassword()
    fun hidePassword()


}