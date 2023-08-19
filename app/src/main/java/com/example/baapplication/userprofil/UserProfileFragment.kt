package com.example.baapplication.userprofil

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.baapplication.R
import com.example.baapplication.databinding.FragmentUserProfileBinding
import com.example.baapplication.models.UserProvider

class UserProfileFragment : Fragment(),UserProfileNavigator {
    lateinit var binding:FragmentUserProfileBinding
    lateinit var UserViewMode:UserProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserViewMode=ViewModelProvider(this).get(UserProfileViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //binding=FragmentUserProfileBinding.inflate(inflater,container,false)
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_user_profile,container,false)
        return binding.root
       // return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.vm=UserViewMode
        UserViewMode.navigator=this

        UserViewMode.emaill.set(UserProvider.user?.email)
        UserViewMode.passwordd.set(UserProvider.user?.password)
        UserViewMode.phoneNumberr.set(UserProvider.user?.phoneNumber)
        var fname=UserViewMode.emaill.get()?.split(".")
        var secname=fname?.get(1)?.split("@")
        UserViewMode.usernamee.set("Welcome, ${fname?.get(0)} ${secname?.get(0)}")
    }

    override fun showPassword() {
        binding?.passTxtview?.transformationMethod=HideReturnsTransformationMethod.getInstance()
    }

    override fun hidePassword() {
        binding?.passTxtview?.transformationMethod=PasswordTransformationMethod.getInstance()
    }

}