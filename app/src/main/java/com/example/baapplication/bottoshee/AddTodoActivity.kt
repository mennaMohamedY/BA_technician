package com.example.baapplication.bottoshee

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.baapplication.databinding.ActivityAddTodoBinding
import java.util.*

class AddTodoActivity : AppCompatActivity() {
    lateinit var todoBinding:ActivityAddTodoBinding
    lateinit var todovm:AddViewModel
    lateinit var calendar: Calendar

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoBinding=ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(todoBinding.root)
        calendar=Calendar.getInstance()

        todovm=ViewModelProvider(this).get(AddViewModel::class.java)
        todoBinding.chooseDate.setOnClickListener({
            val datePicker= DatePickerDialog(this)
            datePicker.show()
            datePicker.setOnDateSetListener{ view,year,month,dayOfMonth->
                calendar.set(Calendar.YEAR,year)
                calendar.set(Calendar.MONTH,month)
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                todoBinding.chooseDate.text="${month+1}-$dayOfMonth-$year"

            }
        })

    }
}