package com.example.baapplication.login

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.baapplication.MainActivity
import com.example.baapplication.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.auth.PhoneAuthProvider.verifyPhoneNumber
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity(),NavigatorLogin{
    lateinit var dataBindingLogin:ActivityLoginBinding
    lateinit var viewModelLogin:LoginViewModel
    var verificationID:String?=null
    var auth:FirebaseAuth?=null
    val phoneNumber:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        dataBindingLogin=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(dataBindingLogin.root)

        viewModelLogin=ViewModelProvider(this).get(LoginViewModel::class.java)
                dataBindingLogin.vm=viewModelLogin
        viewModelLogin.navigator=this

    }

    override fun goToHomeActivity() {
        val intent=Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    override fun signInWithPhoneNumber() {
        TODO("Not yet implemented")
    }

    override fun showError(errorMsg: String) {
        Toast.makeText(this,"${errorMsg}",Toast.LENGTH_LONG).show()
    }

    override fun goToLoginActivity() {
        TODO("Not yet implemented")
    }

    override fun showMsg() {
        Toast.makeText(this,"Succeed",Toast.LENGTH_LONG).show()

    }


}