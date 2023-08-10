package com.example.baapplication.tasks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.baapplication.models.FireStoreUtiles
import com.example.baapplication.models.NoOfTasks
import com.example.baapplication.models.TaskDetails
import com.google.android.gms.tasks.OnCompleteListener

class TasksViewModel :ViewModel(){
    //val Task:TaskDetails?=null

    val TasksLiveData=MutableLiveData<List<TaskDetails>>()
    var noOfTask=MutableLiveData<NoOfTasks>()


    /*fun loadTasks(){
        Task?.TaskId?.let {
            FireStoreUtiles().getAllTasks(it).addOnCompleteListener({
                if (it.isSuccessful){
                    val tasks=it.result.toObjects(TaskDetails::class.java)
                    TasksLiveData.value=tasks
                }
            })
        }
    }

     */

}