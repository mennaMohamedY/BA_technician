package com.example.baapplication.addtask

import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.baapplication.R
import com.example.baapplication.adapter.TechniciansAdapter
import com.example.baapplication.databinding.ActivityAddActivityBinding
import java.text.DateFormat
import java.text.SimpleDateFormat

class AddTaskActivity : AppCompatActivity(),TaskNavigator {

    companion object{
        var TechnID:Int?=null
        fun getInstance(TechPosition:Int):AddTaskActivity{
            TechnID=TechPosition
            return AddTaskActivity()
        }
    }

    lateinit var dataBindingTaskDetails:ActivityAddActivityBinding
    lateinit var EngsNames_Adpter: ArrayAdapter<String>
    lateinit var addTaskVM:AddTaskViewModel
    var calendar:Calendar?=null
    var simpleFormat:SimpleDateFormat?=null
    var Date:String?=null
    var simpleDateFormat2:SimpleDateFormat?=null
    var Date2:String?=null

    var selEng:String?=null
    var EngPosition:Int?=-1



    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_activity)
        dataBindingTaskDetails=ActivityAddActivityBinding.inflate(layoutInflater)
        setContentView(dataBindingTaskDetails.root)

        addTaskVM=ViewModelProvider(this).get(AddTaskViewModel::class.java)
        dataBindingTaskDetails.vm=addTaskVM
        addTaskVM.navigator=this
        val intent=Intent()
        EngPosition=intent.getIntExtra("techAdapterPosition",-1)

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

        dataBindingTaskDetails.EngsSpinner.onItemSelectedListener=object :OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selEng=dataBindingTaskDetails.EngsSpinner.selectedItem.toString()

                //add technician id
                //mlhash 3laqa any ahoutaha hena bs 2olt ahotha hena y3ne blmara
                getTechIDFromPosition(TechnID!!)

                if(position == 0){
                    addTaskVM.nothingSelected.set(true)
                }else{
                    addTaskVM.nothingSelected.set(false)
                    addTaskVM.GoingWithEng.set(selEng)

                }
                Toast.makeText(this@AddTaskActivity,"Selected Eng is :${selEng} position: ${position}",Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@AddTaskActivity,"Nothing is selected",Toast.LENGTH_LONG).show()
            }
        }



    }
    fun getSelectedItem():String{
        val chosenEng=dataBindingTaskDetails.EngsSpinner.selectedItem.toString()
        return chosenEng
    }
    fun getSelectedEng():String{
        dataBindingTaskDetails.EngsSpinner.onItemSelectedListener=object :OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {


                selEng=dataBindingTaskDetails.EngsSpinner.selectedItem.toString()
                Toast.makeText(this@AddTaskActivity,"Selected Eng is :${selEng}",Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                addTaskVM.nothingSelected.set(true)
                Toast.makeText(this@AddTaskActivity,"Nothing is selected",Toast.LENGTH_LONG).show()
            }
        }
        return selEng!!
    }

    override fun onSubmitClick() {
        getSelectedEng()
        finish()
    }

    fun getTechIDFromPosition(position:Int){
        if(position==-1){
            Toast.makeText(this@AddTaskActivity,"Problem occured couldn't get the tech id",Toast.LENGTH_LONG).show()
        }else if (position==0){
            addTaskVM.techID.set("Mahmoud01")
        }else if (position==1){
            addTaskVM.techID.set("Reda02")
        }else if (position==2){
            addTaskVM.techID.set("Bahrawy03")
        }else if (position==3){
            addTaskVM.techID.set("Hany04")
        }
    }

}