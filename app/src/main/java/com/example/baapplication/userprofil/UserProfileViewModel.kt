package com.example.baapplication.userprofil

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class UserProfileViewModel:ViewModel() {
    var emaill=ObservableField<String>()
    var passwordd=ObservableField<String>()
    var phoneNumberr=ObservableField<String>()
    var usernamee=ObservableField<String>()

    var toggle=0
    var navigator:UserProfileNavigator?=null

    fun TogglePassword(){
        toggle+=1
        if (toggle %2 !=0){
            navigator?.showPassword()
        }else{
            navigator?.hidePassword()
        }
    }



}