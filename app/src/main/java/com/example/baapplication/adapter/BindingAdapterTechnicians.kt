package com.example.baapplication.adapter

import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import com.example.baapplication.R

@BindingAdapter("AvailabilityTxt")
fun showAvaiability(txtview:TextView,avaiablemsg:String?){
    txtview.setText(avaiablemsg?:"Avaiable")
}

@BindingAdapter("ChangeBackgroundColor")
fun Changbgcolor(txtview: TextView,backgroundResource:Int?){
    txtview.setBackgroundResource(backgroundResource?: R.drawable.side_nav_bar)
}

@BindingAdapter("AddOrEndTaskBtn")
fun showAddorEndTask(btn:AppCompatButton,startEndmsg:String?){
    btn.setText(startEndmsg?:"Add Task")
}

@BindingAdapter("ChangeBackgroundBTNColor")
fun Changebgbtncolor(btn:AppCompatButton,backgroundResource:Int?){
    btn.setBackgroundResource(backgroundResource?:R.drawable.rounded_borders_addtask)
}


