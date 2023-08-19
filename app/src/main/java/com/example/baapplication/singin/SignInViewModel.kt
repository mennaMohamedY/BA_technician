package com.example.baapplication.singin

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.baapplication.models.FireStoreUtiles
import com.example.baapplication.models.User
import com.example.baapplication.models.UserProvider
import com.google.firebase.auth.FirebaseAuth

class SignInViewModel:ViewModel() {
    var email = ObservableField<String>()
    var emailError = ObservableField<String>()

    var password = ObservableField<String>()
    var passwordError = ObservableField<String>()
    var auth = FirebaseAuth.getInstance()
    var navigator: SignInNavigator? = null

    var currentUserEmail: String? = null
    var currentUserPassword: String? = null
    var currentUserphoneNumber:String?=null
    var currentUserID:String?=null
    var currentUser :User?=null

    var authenticated_user= false

    var toggle=0

    //var allAuthUsers= MutableList<User>()


    fun Sign_IN() {
        if (valid()) {
            if (checkIfCurrentUserIsAuthenticatedUSer(currentUserEmail!!)) {
               // navigator?.showError("currentEmailis${currentUserEmail}")
               // navigator?.navigateToHomeActivity()
            } else {
               // navigator?.showError("Current User isn't Authenticated it doesn't have privilages to wrtie only read")
            }
        } else {
           // navigator?.showError_userNotexist()
        }
    }

    fun goToRegisterActivity() {
        navigator?.navigateToRegisterActivity()
    }

    fun TogglePassword(){
        toggle+=1
        if(toggle %2 != 0){
            navigator?.showPassword()
        }else{
            navigator?.hidePassword()
        }
    }

    fun valid(): Boolean {
        var validemail = false
        if (email.get().isNullOrEmpty()) {
            validemail = false
            emailError.set("email is required")
        } else if (password.get().isNullOrEmpty()) {
            validemail = false
            emailError.set(null)
            passwordError.set("Password is Required!!")
        } else {
            emailError.set(null)
            passwordError.set(null)

            auth.signInWithEmailAndPassword(email.get()!!, password.get()!!).addOnCompleteListener {
                if (it.isSuccessful) {
                    validemail = true
                    //navigator?.navigateToHomeActivity()
                    currentUserEmail = it.result.user?.email
                    //currentUserPassword = password.get()
                    currentUserID = it.result.user?.uid
                    getphoneNumber(currentUserID!!)

                    currentUser= User(currentUserEmail,currentUserphoneNumber,currentUserPassword,currentUserID)
                    UserProvider.user=currentUser

                    navigator?.showError("current Email is ${currentUserEmail}")
                    checkIfCurrentUserIsAuthenticatedUSer(currentUserEmail!!)
                    if (authenticated_user==false){
                        navigator?.showError("you don't have privilage to add tasks you can only view your tasks or add private tasks to be" +
                                " done")
                        navigator?.showError("${currentUser}")
                        navigator?.showError("${currentUser}")

                        Log.e("userProvider","${currentUser}")
                        navigator?.navigateToSideDrawer()

                    }
                 /*   if(checkIfCurrentUserIsAuthenticatedUSer(currentUserEmail!!)) {
                        navigator?.showError("true navigate to home")

                        Log.e("currentEmail", "currentEmailis ${currentUserEmail}")
                       // navigator?.showError("currentEmailis${currentUserEmail}")
                        navigator?.navigateToHomeActivity()
                    } else {
                        //navigator?.showError("currentEmailis ${currentUserEmail}")
                        Log.e("currentEmail", "currentEmailis ${currentUserEmail}")
                       // navigator?.showError("Current User isn't Authenticated it doesn't have privilages to wrtie only read")
                    }
                    //navigator?.showError("currentEmailis ${currentUserEmail}")



                  */
                }
                 else{
                    var passError = "The password is invalid or the user does not have a password."
                    var EmailError = it.exception?.localizedMessage
                    val Error: String? = null
                    //Log.e("passwordErr404",passError!!)
                    if (it.exception?.localizedMessage == passError) {
                        emailError.set(null)
                        passwordError.set("Password is incorrect Please Insert the correct one")
                       // navigator?.showError(it.exception!!.localizedMessage)

                    } else if (it.exception?.localizedMessage == EmailError) {
                        passwordError.set(null)
                        emailError.set("Email is Invalid Either you are not Registered Or it's inCorrect email")
                       // navigator?.showError(it.exception!!.localizedMessage)
                    }


                    //validemail=false
                    //navigator?.showError_userNotexist()
                    //navigator?.showError(it.exception.toString())
                  //  navigator?.showError(it.exception!!.localizedMessage)


                }
            }
        }

        return validemail

    }
    fun getphoneNumber(U_id:String){
        FireStoreUtiles().getUserFromDataBase(U_id).addOnCompleteListener {
            if (it.isSuccessful){
                val doc=it.result.toObject(User::class.java)
                currentUserphoneNumber=doc?.phoneNumber
                currentUserEmail = doc?.email
                currentUserPassword = password.get()

                currentUser= User(currentUserEmail,currentUserphoneNumber,currentUserPassword,currentUserID)
                UserProvider.user=currentUser
                Log.e("phoneNumber","phone ${currentUserphoneNumber}")
            }
        }
    }

    fun checkIfCurrentUserIsAuthenticatedUSer(currentEmail:String):Boolean{
        //val current_user=auth.currentUser
        //allAuthUsers= ArrayList()
        navigator?.showError("checkCurremtUser1")
        FireStoreUtiles().getAllAuthenticatedUsers().addOnCompleteListener {
           var allAuthUsers=it.result.toObjects(User::class.java)
            for (i in 0..allAuthUsers.size-1){
                //navigator?.showError("checkCurremtUser${i} +current check with ${allAuthUsers.get(i).email}")
                if(currentEmail.toString() == allAuthUsers.get(i).email.toString()){
                    authenticated_user=true
                    //navigator?.showError("we have a winnner ${authenticated_user}")
                    navigator?.navigateToHomeActivity()

                    Log.e("looper","Authenticated is now ${authenticated_user}")
                   // navigator?.showError("checkCurremtUser3")
                }
            }
        }
        return authenticated_user

    }
    }