package com.example.baapplication.nooftasks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.baapplication.R
import com.example.baapplication.databinding.ActivityNoOfTasksBinding
import com.example.baapplication.models.FireStoreUtiles
import com.example.baapplication.models.NoOfTasks
import com.example.baapplication.models.TaskDetails
import com.example.baapplication.models.TechDataClass
import com.example.baapplication.tasksinmonths.TasksInMonthsActivity

class NoOfTasksActivity : AppCompatActivity() {
    lateinit var MonthsInInt:ArrayList<String>
    lateinit var MonthsInString:ArrayList<String>
    var tasksDetaills:MutableList<TaskDetails?>?=null

    companion object{
        var CurrentTech:TechDataClass?=null
        fun getInstance(currenttech:TechDataClass):NoOfTasksActivity{
            CurrentTech=currenttech
            return NoOfTasksActivity()
        }
    }
    var Months:MutableList<NumOfTasksDataClass>? = null
    lateinit var noActivityBinding:ActivityNoOfTasksBinding
    lateinit var NumOftasksAdapter:NumTasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_no_of_tasks)
        noActivityBinding= ActivityNoOfTasksBinding.inflate(layoutInflater)
        setContentView(noActivityBinding.root)
        Months=mutableListOf()
        defineMonths()
        NumOftasksAdapter= NumTasksAdapter(Months)
        noActivityBinding.noTasksRecyclerview.adapter=NumOftasksAdapter

        /*
        FireStoreUtiles().getAllMonths(CurrentTech?.TechID!!).addOnCompleteListener {
            if (it.isSuccessful){
                 Toast.makeText(this,"month is ${it.result.toObjects(NoOfTasks::class.java)}", android.widget.Toast.LENGTH_LONG).show()
                //it.result.toObjects(String::class.java).
            }
            else{
                Toast.makeText(this,"something went wrong",Toast.LENGTH_LONG).show()
            }
        }

         */
        initMonthsInInt()
        for(i in 0.. MonthsInInt.size-1){
            fillTask(MonthsInInt[i],i)
        }
        NumOftasksAdapter.onDetailsBtnClickListener=object :NumTasksAdapter.OnDetailsBtnClickListener{
            override fun onDetailsBtnClick(position: Int, dataa: NumOfTasksDataClass) {
               var curntMonts= convertMonth(dataa.Month)
                val aa=FireStoreUtiles().getAllTasksInMonth(CurrentTech?.TechID!!,curntMonts).addOnCompleteListener({
                    if(it.isSuccessful){
                        val aa=it.result.toObjects(TaskDetails::class.java)
                        val intent= Intent(this@NoOfTasksActivity,TasksInMonthsActivity.getInstance(aa)::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@NoOfTasksActivity,"something went wrong",Toast.LENGTH_LONG).show()
                    }
                })

            }
        }





    }
    fun fillTask(Monthh:String,position:Int){
        FireStoreUtiles().getAllTasksInMonth(CurrentTech?.TechID!!,Monthh).addOnCompleteListener {
            if(it.isSuccessful){
                var tasksnumm=it.result.toObjects(TaskDetails::class.java).size
                var tasksMonth=MonthsInString.get(position)
                Months?.add(NumOfTasksDataClass(tasksnumm,tasksMonth))
                NumOftasksAdapter.UpdateData(Months)
                tasksDetaills=it.result.toObjects(TaskDetails::class.java)

            }else{
                Toast.makeText(this,"Error occurent somethingwent worng",Toast.LENGTH_LONG).show()
            }
        }

    }
    fun defineMonths(){
        MonthsInString=ArrayList()
        MonthsInString?.add("August 2023")
        MonthsInString?.add("September 2023")
        MonthsInString?.add("October 2023")
        MonthsInString?.add("November 2023")
        MonthsInString?.add("December 2023")
        MonthsInString?.add("January 2024")

    }
    fun initMonthsInInt(){
        MonthsInInt=ArrayList()
        MonthsInInt.add("08")
        MonthsInInt.add("09")
        MonthsInInt.add("10")
        MonthsInInt.add("11")
        MonthsInInt.add("12")
        MonthsInInt.add("1")
    }
    fun convertMonth(monts:String):String{
        if (monts == "August 2023"){return MonthsInInt[0]}
        else if (monts == "September 2023") {return MonthsInInt[1]}
        else if (monts == "October 2023"){ return MonthsInInt[2]}
        else if (monts == "November 2023") {return MonthsInInt[3]}
        else if (monts == "December 2023"){ return MonthsInInt[4]}
        else if (monts == "January 2023"){ return MonthsInInt[5]}
        else return ""

    }

}