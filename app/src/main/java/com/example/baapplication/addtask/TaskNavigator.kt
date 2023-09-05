package com.example.baapplication.addtask

interface TaskNavigator{
    fun onSubmitClick()
    fun showError(errormsg:String)
    fun taskId(tskID:String)
}