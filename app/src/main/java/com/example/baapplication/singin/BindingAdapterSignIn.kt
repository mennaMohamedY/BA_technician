package com.example.baapplication.singin

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout


@BindingAdapter("Error")
fun showError(textInputlayout: TextInputLayout, errorMsg:String?){
    textInputlayout.error=errorMsg
}