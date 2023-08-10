package com.example.baapplication

import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.baapplication.adapter.TechniciansAdapter
import com.example.baapplication.addtask.AddTaskActivity
import com.example.baapplication.addtask.AddTaskViewModel
import com.example.baapplication.databinding.ActivityMainBinding
import com.example.baapplication.models.*
import com.example.baapplication.tasks.TasksActivity
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    lateinit var dataBindingMain:ActivityMainBinding
    lateinit var techAdapter:TechniciansAdapter
    lateinit var techData:ArrayList<TechDataClass?>
    var vmprovider:AddTaskViewModel?=null

    //lateinit var addTaskVM:AddTaskViewModel
    var calendar: Calendar?=null
    var simpleFormat: SimpleDateFormat?=null
    var Date:String?=null
    var simpleDateClockFormat: SimpleDateFormat?=null
    var DateClock:String?=null

    var noOfTask= MutableLiveData<String>()
    var numOfTasks:Int?=0

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

        techAdapter= TechniciansAdapter(null,vmprovider!!.validTask(),null)
        initData()


        for(i in 0..techData.size-1){
            insertTechToDataBase(techData[i]!!)
        }
        techAdapter.upDateDAta(techData)
        dataBindingMain.recyclerView.adapter=techAdapter
        techAdapter.onAddTaskClickListener=object :TechniciansAdapter.OnAddTaskClickListener{
            override fun OnAddTaskClick(position: Int) {
                val intent=Intent(this@MainActivity,AddTaskActivity.getInstance(position)::class.java)
                //intent.putExtra("techAdapterPosition",position)
                startActivity(intent)
            }
        }
        insertAuthenticatedUsers()


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
        techAdapter.onViewTasksClickListener=object :TechniciansAdapter.OnViewTasksClickListener{
            override fun OnViewTaskClick(position: Int, TaskData: TechDataClass) {
                val intent=Intent(this@MainActivity,TasksActivity.getInstance(TaskData.TechID!!)::class.java)
                startActivity(intent)
            }

        }
        techAdapter.onTechClickListener=object :TechniciansAdapter.OnTechClickListener{
            override fun OnTechClick(position: Int, TaskData: TechDataClass) {
                val noOFtsk=gg(TaskData.TechID!!)
                val ttn=NoOfTasks("No Of Tasks:" +noOFtsk)
                vmprovider!!.noOfTask.value=ttn
                subdcribeToLiveData()

            }

        }

        //techAdapter.UpdateOnSubmitChanged(vmprovider?.validate?.get()!!)
    }
    fun gg(TechID:String):Int{
        FireStoreUtiles().getAllTasks(TechID).addOnCompleteListener({
            if(it.isSuccessful){
                val tasks = it.result.toObjects(TaskDetails::class.java)
                numOfTasks=tasks.size
                //taskVm.TasksLiveData.value=tasks
            }
        })
        return numOfTasks!!
    }

    fun subdcribeToLiveData(){
        vmprovider?.noOfTask?.observe(this){
            techAdapter.UpdateNoOFTasks(it)
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
        techData.add(TechDataClass(R.drawable.mahmoud4,  techName = "Tech mahmoud","No of Tasks: 3","Mahmoud01"))
        techData.add(TechDataClass(R.drawable.reda,techName ="Tech Reda","No of Tasks: 4","Reda02"))
        techData.add(TechDataClass(R.drawable.bahrawy,techName ="Tech Bahrawy","No of Tasks: 2","Bahrawy03"))
        techData.add(TechDataClass(R.drawable.james4,techName ="Tech Hany","No of Tasks: 1","Hany04"))
    }

    var authen_users_Array:ArrayList<User?>? =null

    fun insertAuthenticatedUsers(){
        AuthenUsersArrayFill()
        for (i in 0..authen_users_Array!!.size-1){
            authen_users_Array!![i]?.let { FireStoreUtiles().InsertAuthenticatedUsers(it).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this@MainActivity,"Authenticated Users Collection is created and have 3 authenticated users",
                        Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this@MainActivity,
                        "Couldn't create Authenticated users collection ${it.exception?.localizedMessage}",
                        Toast.LENGTH_LONG).show()
                }
            } }
        }

    }
    fun AuthenUsersArrayFill(){
        authen_users_Array= ArrayList()
        authen_users_Array!!.add(User(id = "salah", email = "salah.salah@bibalex.dom", password = "002345"))
        authen_users_Array!!.add(User(id = "shawky", email = "shawky.shawky@bibalex.dom"))
        authen_users_Array!!.add(User(id = "ali", email = "ali.elsheikh@bibalex.dom"))

    }

}