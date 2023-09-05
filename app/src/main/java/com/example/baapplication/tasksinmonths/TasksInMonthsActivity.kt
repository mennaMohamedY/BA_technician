package com.example.baapplication.tasksinmonths

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.baapplication.R
import com.example.baapplication.databinding.ActivityNoOfTasksBinding
import com.example.baapplication.models.TaskDetails
import com.example.baapplication.tasks.TasksActivity

class TasksInMonthsActivity : AppCompatActivity() {
    lateinit var tasksInMonthAdapter:TasksInMonthsAdapter

    companion object{
        var TasksArray:MutableList<TaskDetails?>?=null

        fun getInstance(tasksarray:MutableList<TaskDetails?>?):TasksInMonthsActivity{
            TasksArray=tasksarray
            return TasksInMonthsActivity()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tasksInMonthBinding=ActivityNoOfTasksBinding.inflate(layoutInflater)
        setContentView(tasksInMonthBinding.root)

        tasksInMonthAdapter= TasksInMonthsAdapter(TasksArray)
        tasksInMonthAdapter.updateData(TasksArray)
        tasksInMonthBinding.noTasksRecyclerview.adapter=tasksInMonthAdapter
    }
}