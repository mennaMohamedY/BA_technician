package com.example.baapplication.addtask

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.databinding.ObservableField
import androidx.databinding.adapters.AdapterViewBindingAdapter.OnNothingSelected
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.baapplication.models.*
import com.google.firebase.firestore.FirebaseFirestore

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
    var spinnerError=ObservableField<String>()
    var nothingSelected=ObservableField<Boolean>()
    var GoingWithEng=ObservableField<String>()
    //val auth=FirebaseFirestore.getInstance()
    var techID=ObservableField<String>()

    var noOfTask=MutableLiveData<NoOfTasks>()




    var validate=ObservableField<Boolean>()
    var monthsArray:MutableList<Months>? = null



    @RequiresApi(Build.VERSION_CODES.N)
    fun createTask(){
        if(validTask()){

            validate=validate

            val task=TaskDetails(
                TaskDescription = taskDescription.get().toString(),
                GoingWith = GoingWithEng.get(),
                StartTime = startTimeDate +" "+ startTimeClock + "AM",
                CreatedBy = UserProvider.user?.email,
                TaskNo = 1,
                TechId = techID.get().toString()
            )
            /*
            FireStoreUtiles().addTask(task)
            assignONTask(techID.get().toString())
            navigator?.onSubmitClick()

             */
            var month=startTimeDate?.split("-")
            var currentMonth=month?.get(1)
            //var currentMonth=NoOfTasks(month?.get(1))
            FireStoreUtiles().addTask(task,currentMonth!!)
            assignONTask(techID.get().toString())
            techTaskProvider.taskID=task.TaskId
            techTaskProvider.taskid?.add(task.TaskId!!)
            techTaskProvider.ti?.putIfAbsent("Mahmoud01",techTaskProvider.taskID!!)
            navigator?.onSubmitClick()

            //getAllMonths(currentMonth!!,task)
            //assignONTask(techID.get().toString())
            //navigator?.onSubmitClick()




        }

    }
    /*
    fun getAllMonths(currenrmonth:String,task:TaskDetails){
        var exist=false
        FireStoreUtiles().getAllMonths().addOnCompleteListener {
            if(it.isSuccessful){
                monthsArray=mutableListOf()
                monthsArray=it.result.toObjects(Months::class.java)
                exist=true
            }else{
            }
        }
        if(monthsArray?.size!! >=1){
            for(i in 0..monthsArray?.size!!-1){
                if (currenrmonth==monthsArray!![i].monthName){
                    exist=true
                    FireStoreUtiles().addTasksInMonths(task,currenrmonth)
                }
            }

        }else{
            exist=false
            FireStoreUtiles().addMonth(task,currenrmonth)
            FireStoreUtiles().addTasksInMonths(task,currenrmonth)

        }
    }

     */
    fun assignONTask(techID:String){
        techTaskProvider.techId=techID
        techTaskProvider.techOnTask=true

       /* FireStoreUtiles().getTechnician(techID).addOnCompleteListener {
            if (it.isSuccessful){
                val doc = it.result.toObject(TechDataClass::class.java)
                doc?.onTask=true
            }else{
                navigator?.showError(it.exception!!.localizedMessage)
            }
        }
        */
        FireStoreUtiles().updateTechOnDataBase(techID,true,UserProvider.user?.email!!)

    }

    fun validTask():Boolean{
        var valid=true
        var validtaskdesc=true
        var validtaskdate=true
        var validateSpinner=true


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

        if(nothingSelected.get()== true){
            validateSpinner=false
            spinnerError.set("Please Choose The Engineer")
        }else{
            spinnerError.set(null)
            validateSpinner
        }

        if(validtaskdesc && validtaskdate && validateSpinner){
            valid = true
        }else{
            valid= false
        }
        return valid

    }

    fun OnNothingSelected(){

        spinnerError.set("Please Choose The Engineer")
    }
}