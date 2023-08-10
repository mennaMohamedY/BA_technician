package com.example.baapplication.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//@Parcelize
data class TaskDetails(
    var TaskDescription:String?=null,
    var GoingWith:String?=null,
    var StartTime:String?=null,
    var EndTask:String?=null,
    var CreatedBy:String?=null,
    var TaskId:String?=null,
    var TaskNo:Int?=null,
    var TechId:String?=null
)
    //:Parcelable{



