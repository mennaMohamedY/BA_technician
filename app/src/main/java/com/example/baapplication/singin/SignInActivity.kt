package com.example.baapplication.singin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.baapplication.MainActivity
import com.example.baapplication.R
import com.example.baapplication.databinding.ActivitySignInBinding
import com.example.baapplication.login.LoginActivity

class SignInActivity : AppCompatActivity(),SignInNavigator {

    lateinit var signInDataBinding:ActivitySignInBinding
    lateinit var SignIn_ViewModel:SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        signInDataBinding=ActivitySignInBinding.inflate(layoutInflater)
        setContentView(signInDataBinding.root)

        SignIn_ViewModel=ViewModelProvider(this).get(SignInViewModel::class.java)
        signInDataBinding.vm=SignIn_ViewModel
        SignIn_ViewModel.navigator=this


    }

    override fun showError_userNotexist() {
        Toast.makeText(this,"You Are not Registered Please Create account First!!",Toast.LENGTH_LONG).show()
    }

    override fun navigateToHomeActivity() {
        val intent=Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToRegisterActivity() {
        val intent=Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }

    override fun showError(errorMsg: String) {
        Toast.makeText(this," ${errorMsg}",Toast.LENGTH_LONG).show()
    }

    fun checkIfCurrentUserIsAuthenticatedUSer(){

    }
}