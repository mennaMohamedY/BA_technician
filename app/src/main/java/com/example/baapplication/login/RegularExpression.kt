package com.example.baapplication.login

import java.util.regex.Pattern

val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)

fun checkEmail(email: String): Boolean {
    return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
}
val EMAIL_ADDRESS: Pattern = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "bibalex" +
            "\\." +
            "org"
)

fun checkEmailAddress(email: String): Boolean {
    return EMAIL_ADDRESS.matcher(email).matches()
}

val USERNAME_PATTERN:Pattern=Pattern.compile(
    "[a-zA-Z]{1,15}"+
            "\\." +
            "[a-zA-Z][a-zA-Z]{1,15}"
)
fun CheckUserName(userName:String):Boolean{
    return USERNAME_PATTERN.matcher(userName).matches()
}
val PHONENUMBER_PATTERN:Pattern=Pattern.compile(
    "[0-9]{11}"
)
fun CheckPhoneNum(PhoneNum:String):Boolean{
    return PHONENUMBER_PATTERN.matcher(PhoneNum).matches()
}