package com.example.baapplication.login

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.baapplication.models.FireStoreUtiles
import com.example.baapplication.models.User
import com.example.baapplication.models.UserProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class LoginViewModel:ViewModel() {
    var UserName=ObservableField<String?>()
    var UserNameError=ObservableField<String?>()

    val phoneNumber=ObservableField<String?>()
    val phoneNumberError=ObservableField<String?>()


    val password=ObservableField<String?>()
    val passwordError=ObservableField<String?>()

    val Repassword=ObservableField<String>()
    val RepasswordError=ObservableField<String>()
    var navigator:NavigatorLogin?=null
    val db = Firebase.firestore

    val auth= FirebaseAuth.getInstance()
    val options = PhoneAuthOptions.newBuilder(auth)

    fun signIn(){
        if(validation()) {
            //navigator?.goToHomeActivity()
            //navigator?.signInWithPhoneNumber()
            auth.createUserWithEmailAndPassword(UserName.get().toString(),password.get().toString())
                .addOnCompleteListener(object :OnCompleteListener<AuthResult>{
                    override fun onComplete(p0: Task<AuthResult>) {
                        if(p0.isSuccessful){
                            navigator?.showMsg()
                            insertUserToDataBase(p0.result.user!!.uid)
                            //dataInFireStor()
                        }else{
                            navigator?.showError(p0.exception?.localizedMessage.toString()+"error on incomplete")
                    }
                }
                })
        }}

    private fun insertUserToDataBase(userId:String) {
        val user=User(
            UserName.get(),
            phoneNumber.get(),
            password.get()!!,
            id = userId
        )
        FireStoreUtiles().insertUserToDataBase(user).addOnCompleteListener({
            if(it.isSuccessful){
                navigator?.goToHomeActivity()
            }else{
                navigator?.showError(it.exception?.localizedMessage.toString()+"Error inFireStoreUtiles")
                //navigator?.goToLoginActivity()

            }
        })
    }
    fun getUserFromDataBase(uid:String){
        FireStoreUtiles().getUserFromDataBase(uid).addOnCompleteListener({
            if(it.isSuccessful){
                val user=it.result.toObject(User::class.java)
                UserProvider.user=user
            }else{
                navigator?.showError(it.exception?.localizedMessage.toString())
            }
        })
    }

    private fun dataInFireStor() {
        // Add a new document with a generated ID
        auth.currentUser?.let {
            db.collection("Technicians")
                .add(it)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }

    }


    fun validation():Boolean{
    var valid=true
    var valid1=true
    var valid2=true
    var valid3=true
    var valid4=true

    if(UserName.get().isNullOrEmpty()){
        valid1=false
        UserNameError.set("Please Enter UserName")
        //show Error msg
    }else if(UserName!=null){
        if(checkEmailAddress(UserName.get()!!)){
            valid1= true
            UserNameError.set(null)
        }else{
            valid1=false
            UserNameError.set("Enter a valid Email ex menna.yousef@bibalex.org")
        }
    }

    if(phoneNumber.get().isNullOrEmpty()){
        valid2=false
        phoneNumberError.set("Please Enter Your PhoneNumber")
    }else if(phoneNumber!=null){
        if(CheckPhoneNum(phoneNumber.get()!!)){
            valid2= true
            phoneNumberError.set(null)
        }else{
            valid2=false
            phoneNumberError.set("Enter a valid phoneNum!")
        }
    }

    if(password.get().isNullOrEmpty()){
            valid3=false
            passwordError.set("Please Enter Your PhoneNumber")
        }else if(password!=null){
            if(password.get()!!.length<6){
                valid3=false
                passwordError.set("Password must be more than 6 characters")
            }else{
                passwordError.set(null)
            }
        }
        if (Repassword.get().isNullOrEmpty() && password.get().isNullOrEmpty()){
            valid4=false
        }else if(Repassword.get().isNullOrEmpty() && !(password.get().isNullOrEmpty())){
            valid4=false
            RepasswordError.set("Please Re-Enter password")
        }else{
            if(Repassword.get().equals(password.get())){
                valid4=true
                RepasswordError.set(null)
            }else{
                valid4=false
                RepasswordError.set("Password mismatch!")
            }
        }
        if (valid1 && valid2 && valid3 && valid4){
            valid=true
        }else {valid=false}


    return valid

}




}