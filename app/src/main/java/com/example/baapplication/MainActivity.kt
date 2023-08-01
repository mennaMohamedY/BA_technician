package com.example.baapplication

import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.baapplication.models.TechDataClass
import com.example.baapplication.adapter.TechniciansAdapter
import com.example.baapplication.addtask.AddTaskActivity
import com.example.baapplication.addtask.AddTaskViewModel
import com.example.baapplication.databinding.ActivityMainBinding
import com.example.baapplication.models.FireStoreUtiles
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    lateinit var dataBindingMain:ActivityMainBinding
    lateinit var techAdapter:TechniciansAdapter
    lateinit var techData:ArrayList<TechDataClass?>
    var vmprovider:AddTaskViewModel?=null

    lateinit var addTaskVM:AddTaskViewModel
    var calendar: Calendar?=null
    var simpleFormat: SimpleDateFormat?=null
    var Date:String?=null
    var simpleDateClockFormat: SimpleDateFormat?=null
    var DateClock:String?=null
    /*


     */

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        dataBindingMain=ActivityMainBinding.inflate(layoutInflater)
        setContentView(dataBindingMain.root)
        vmprovider=ViewModelProvider(this).get(AddTaskViewModel::class.java)
        dataBindingMain.vmm=vmprovider

        calendar=Calendar.getInstance()
        simpleFormat= SimpleDateFormat("dd-MM-yyyy")
        Date=simpleFormat!!.format(calendar!!.time)
        simpleDateClockFormat= SimpleDateFormat("HH:mm:SS")
        DateClock=simpleDateClockFormat!!.format(calendar!!.time)

        techAdapter= TechniciansAdapter(null,vmprovider!!.validTask())
        initData()

        for(i in 0..techData.size-1){
            insertTechToDataBase(techData[i]!!)
        }
        techAdapter.upDateDAta(techData)
        dataBindingMain.recyclerView.adapter=techAdapter
        techAdapter.onAddTaskClickListener=object :TechniciansAdapter.OnAddTaskClickListener{
            override fun OnAddTaskClick(position: Int) {
                val intent=Intent(this@MainActivity,AddTaskActivity::class.java)
                startActivity(intent)
            }
        }


        techAdapter.onAddTaskClickListen=object :TechniciansAdapter.OnAddTaskClickListen{
            override fun OnAddTask(TaskData: TechDataClass, position: Int) {
                 vmprovider!!.addedTask= TaskData
            }
        }
        techAdapter.onEndTaskClickListener=object :TechniciansAdapter.OnEndTaskClickListener{
            override fun OnEndTask(TaskData: TechDataClass, position: Int) {
                Toast.makeText(this@MainActivity,"!!!!!!!",Toast.LENGTH_LONG).show()
                vmprovider!!.EndTimeDate=Date
                vmprovider!!.EndTimeClock=DateClock
                Toast.makeText(this@MainActivity,"Task Ended at ${Date} at time ${DateClock}",Toast.LENGTH_LONG).show()
                Log.e("endTask","task is ended")
            }
        }
    }


    fun insertTechToDataBase(techs:TechDataClass){
        val Technician=TechDataClass(TechID = techs.TechID, techNo_ofTasks = "No Of Tasks")
        FireStoreUtiles().InsertTechnicianToDataBase(Technician).addOnCompleteListener({
            if(it.isSuccessful){
                Toast.makeText(this@MainActivity,"Succeed To Add AllTechnician todatabase",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this@MainActivity,"problem occured ${it.exception.toString()}",Toast.LENGTH_LONG).show()
            }
        })

    }

    fun initData(){
        techData= ArrayList()
        techData.add(TechDataClass(R.drawable.james,"No of Tasks: 3","Mahmoud01"))
        techData.add(TechDataClass(R.drawable.james2,"No of Tasks: 4","Reda02"))
        techData.add(TechDataClass(R.drawable.james3,"No of Tasks: 2","Bahrawy03"))
        techData.add(TechDataClass(R.drawable.james4,"No of Tasks: 1","Hany04"))
    }
}