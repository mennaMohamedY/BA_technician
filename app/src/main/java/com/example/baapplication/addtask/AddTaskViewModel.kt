package com.example.baapplication.addtask

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.baapplication.models.TechDataClass

class AddTaskViewModel :ViewModel() {
    var addedTask: TechDataClass?=null
    var startTimeDate:String?=null
    var startTimeClock:String?=null
    var EndTimeDate:String?=null
    var EndTimeClock:String?=null

    var taskDescription=ObservableField<String>()
    var taskDecriptionError=ObservableField<String>()

    var Date=ObservableField<String>()
    var DateError=ObservableField<String>()
    var navigator:TaskNavigator?=null


    fun createTask(){
        if(validTask()){
            navigator?.onSubmitClick()
        }

    }
    fun validTask():Boolean{
        var valid=true
        var validtaskdesc=true
        var validtaskdate=true


        if(taskDescription.get().isNullOrEmpty()){
            validtaskdesc=false
            taskDecriptionError.set("Task Description is required!!")
        }else{
            validtaskdesc=true
            taskDecriptionError.set(null)
        }

        if(Date.get().isNullOrEmpty()){
            validtaskdate=false
            DateError.set("Please Press on the Start Task Button")
        }
        else{
            validtaskdate=true
            DateError.set(null)
        }

        if(validtaskdesc && validtaskdate){
            valid = true
        }else{
            valid= false
        }
        return valid

    }
}