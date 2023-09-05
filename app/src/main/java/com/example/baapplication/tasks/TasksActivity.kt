package com.example.baapplication.tasks

import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.baapplication.databinding.ActivityTasksBinding
import com.example.baapplication.models.FireStoreUtiles
import com.example.baapplication.models.NoOfTasks
import com.example.baapplication.models.TaskDetails
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import java.text.SimpleDateFormat

class TasksActivity : AppCompatActivity() {
    companion object{
        var Tech_ID:String?=null
        fun getInstance(TechID:String):TasksActivity{
            Tech_ID=TechID
            return TasksActivity()
        }
    }

    lateinit var taskBinding:ActivityTasksBinding
    lateinit var taskVm:TasksViewModel
    lateinit var TasksAdapter:tasksAdapter
    var calendar: Calendar?=null
    var simpleFormat: SimpleDateFormat?=null
    var Date:String?=null
    var currentDay:String?=null



    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskBinding=ActivityTasksBinding.inflate(layoutInflater)
        setContentView(taskBinding.root)

        taskVm=ViewModelProvider(this).get(TasksViewModel::class.java)
        taskBinding.vm=taskVm

        //mutablelistof -> creates an empty mutable list
        TasksAdapter=tasksAdapter(mutableListOf())
        taskBinding.tasksRecyclerview.adapter=TasksAdapter
        //FireStoreUtiles().getTechTasks(Tech_ID!!)
        //val TasksDataReference=FireStoreUtiles().getTechTasks(Tech_ID!!)
        //getTasks()
       // getAllTasks()
       // loadTasks()
        calendar=Calendar.getInstance()
        simpleFormat= SimpleDateFormat("dd-MM-yyyy")
        var datsepareted=simpleFormat.toString().split("-")
        Toast.makeText(this,"${simpleFormat}",Toast.LENGTH_LONG).show()
        simpleFormat= SimpleDateFormat("dd-MM-yyyy")
        Date=simpleFormat!!.format(calendar!!.time)
        Log.e("date","${simpleFormat}, ")
        Date=simpleFormat!!.format(calendar!!.time)
        var h=Date.toString().split("-")



        currentDay=h.get(1)


        //taskVm.loadTasks()
        //subdcribeToLiveData()
        subdcribeToLiveData()
        gg()
        //subdcribeToLiveData()

    }
    fun subdcribeToLiveData(){
        taskVm.TasksLiveData.observe(this){
            TasksAdapter.UpdateData(it)
        }
    }
    fun loadTasks(){
        FireStoreUtiles().getAllTasks(Tech_ID!!,"09").addOnCompleteListener({
            if (it.isSuccessful){
                if(it.result.toObjects(TaskDetails::class.java).size == 0){
                    taskVm.TasksLiveData.value=null
                }else{
                    val tasks=it.result.toObjects(TaskDetails::class.java)
                    taskVm.TasksLiveData.value=tasks
                }

               // val No_OfTasks= NoOfTasks("N0 Of Tasks:"+tasks.size)
               // taskVm.noOfTask.value="N0 Of Tasks:"+tasks.size
            }else{
              //  Toast.makeText(this@TasksActivity,"Error occured ${it.exception.toString()}",Toast.LENGTH_LONG).show()
            }
        })
    }
    fun gg(){
        FireStoreUtiles().getAllTasks(Tech_ID!!,currentDay!!).addOnCompleteListener({
            if(it.isSuccessful){
                if(it.result.toObjects(TaskDetails::class.java).size == 0){
                    taskVm.TasksLiveData.value=null
                }

                val tasks = it.result.toObjects(TaskDetails::class.java)
                taskVm.TasksLiveData.value=tasks
            }
        })
    }
    /*

    fun getTasks(){
        FireStoreUtiles().getTechTasks(Tech_ID!!)
            .addSnapshotListener(object :com.google.firebase.firestore.EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if(error!=null){
                        Toast.makeText(this@TasksActivity,"Error occured ${error.localizedMessage}",Toast.LENGTH_LONG).show()
                    }
                    value?.documentChanges?.forEach({
                        val task = it.document.toObject(TaskDetails::class.java)
                        TasksAdapter.addTask(task)
                    })
                }

            })
    }

     */
    fun getAllTasks(){
        FireStoreUtiles().getTechTasks(Tech_ID!!)
            .get().addOnCompleteListener(object :OnCompleteListener<QuerySnapshot>{
                override fun onComplete(p0: Task<QuerySnapshot>) {
                    val tasks=p0.result.toObjects(TaskDetails::class.java)
                    TasksAdapter.UpdateData(tasks)
                }

            })
    }




}