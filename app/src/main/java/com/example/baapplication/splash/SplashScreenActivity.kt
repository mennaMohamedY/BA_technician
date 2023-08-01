package com.example.baapplication.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.baapplication.MainActivity
import com.example.baapplication.R
import com.example.baapplication.login.LoginActivity
import com.example.baapplication.models.FireStoreUtiles
import com.example.baapplication.models.User
import com.example.baapplication.models.UserProvider
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed({
            checkUser()
        },2000)


    }

    fun checkUser(){
        val auth=FirebaseAuth.getInstance()
        if(auth.currentUser == null){
            goToLoginActivity()
        }
        FireStoreUtiles().getUserFromDataBase(auth.currentUser!!.uid)
            .addOnCompleteListener({
                if(it.isSuccessful){
                    //lw dkhl roh hat aluser w hoto fe user
                    //w shelo fl userprovider
                   val user=it.result.toObject(User::class.java)
                    UserProvider.user = user
                    goToHomeActivity()
                }else{
                    //if there is no internt for example
                    goToLoginActivity()
                }
            })
    }
    fun goToHomeActivity(){
        val intent=Intent(this@SplashScreenActivity,MainActivity::class.java)
        startActivity(intent)
    }
    fun goToLoginActivity(){
        val intent= Intent(this@SplashScreenActivity,LoginActivity::class.java)
        startActivity(intent)
    }
}