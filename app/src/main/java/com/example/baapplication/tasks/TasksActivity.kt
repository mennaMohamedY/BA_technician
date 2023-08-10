package com.example.baapplication.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.baapplication.databinding.ActivityTasksBinding
import com.example.baapplication.models.FireStoreUtiles
import com.example.baapplication.models.NoOfTasks
import com.example.baapplication.models.TaskDetails
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

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
        FireStoreUtiles().getAllTasks(Tech_ID!!).addOnCompleteListener({
            if (it.isSuccessful){
                val tasks=it.result.toObjects(TaskDetails::class.java)
                taskVm.TasksLiveData.value=tasks
                val No_OfTasks= NoOfTasks("N0 Of Tasks:"+tasks.size)
               // taskVm.noOfTask.value="N0 Of Tasks:"+tasks.size
            }else{
                Toast.makeText(this@TasksActivity,"Error occured ${it.exception.toString()}",Toast.LENGTH_LONG).show()
            }
        })
    }
    fun gg(){
        FireStoreUtiles().getAllTasks(Tech_ID!!).addOnCompleteListener({
            if(it.isSuccessful){
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