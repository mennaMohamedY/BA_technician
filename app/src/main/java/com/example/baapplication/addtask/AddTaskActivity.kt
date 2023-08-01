package com.example.baapplication.addtask

import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.baapplication.R
import com.example.baapplication.adapter.TechniciansAdapter
import com.example.baapplication.databinding.ActivityAddActivityBinding
import java.text.DateFormat
import java.text.SimpleDateFormat

class AddTaskActivity : AppCompatActivity(),TaskNavigator {

    lateinit var dataBindingTaskDetails:ActivityAddActivityBinding
    lateinit var EngsNames_Adpter: ArrayAdapter<String>
    lateinit var addTaskVM:AddTaskViewModel
    var calendar:Calendar?=null
    var simpleFormat:SimpleDateFormat?=null
    var Date:String?=null
    var simpleDateFormat2:SimpleDateFormat?=null
    var Date2:String?=null



    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_activity)
        dataBindingTaskDetails=ActivityAddActivityBinding.inflate(layoutInflater)
        setContentView(dataBindingTaskDetails.root)

        addTaskVM=ViewModelProvider(this).get(AddTaskViewModel::class.java)
        dataBindingTaskDetails.vm=addTaskVM
        addTaskVM.navigator=this

        EngsNames_Adpter=
            ArrayAdapter(this,android.R.layout.simple_list_item_1,resources.getStringArray(R.array.Engineers_names))
        EngsNames_Adpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dataBindingTaskDetails.EngsSpinner.adapter=EngsNames_Adpter
        calendar=Calendar.getInstance()
        simpleFormat= SimpleDateFormat("dd-MM-yyyy")
        Date=simpleFormat!!.format(calendar!!.time)
        simpleDateFormat2= SimpleDateFormat("HH:mm:SS")
        Date2=simpleDateFormat2!!.format(calendar!!.time)


        dataBindingTaskDetails.startTimeBtn.setOnClickListener({
            dataBindingTaskDetails.startTimeText.setText("Date: " + Date + " \nTime: " + Date2)
            addTaskVM.startTimeDate=Date
            addTaskVM.startTimeClock=Date2
        })


    }

    override fun onSubmitClick() {
        finish()
    }
}