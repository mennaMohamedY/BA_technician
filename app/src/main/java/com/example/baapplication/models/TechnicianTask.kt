package com.example.baapplication.models

data class TechnicianTask(
    val taskDescription:String,
    val goingWithEng:String,
    val startTime:String,
    val endTime:String,
    val CreatedBy:String,
    val TechnicianId:String,
    val noOfTasks:Int
)
