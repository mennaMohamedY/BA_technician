package com.example.baapplication.adapter

import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.baapplication.R
import com.example.baapplication.models.FireStoreUtiles
import com.example.baapplication.models.NoOfTasks
import com.example.baapplication.models.TaskDetails
import com.example.baapplication.models.TechDataClass
import com.google.firebase.firestore.DocumentChange

class AllTechniciansViewModel:ViewModel() {
    var showAvailability=MutableLiveData<String?>()
    var changbg=MutableLiveData<Int?>()
    var showStartEndTask=MutableLiveData<String?>()
    var changeBTNbg=MutableLiveData<Int?>()
    //var availabletxtobserverr=ObservableField<String>()

    var noOfTask= MutableLiveData<NoOfTasks>()
    var EndTimeDate=ObservableField<String?>()
    var EndTimeClock=ObservableField<String?>()
    var addedTask: TechDataClass?=null
    var tecchs=MutableLiveData<List<TechDataClass>>()

    fun recieveTechDataChanged(){
        FireStoreUtiles().getTecs().addSnapshotListener { value, error ->
            if (error!=null){
             //   Toast.makeText(this,"error on getting techs ${error.localizedMessage}", Toast.LENGTH_LONG).show()
            }else{
                val technssnapshots=value?.toObjects(TechDataClass::class.java)
                for(doc in value!!.documentChanges){
                    when(doc.type){
                        DocumentChange.Type.MODIFIED -> {
                           // viewmodelTechnicians.showAvailability.value=doc.document.toObject(TechDataClass::class.java).onTask.toString()
                           //tecchs.value=doc.document.toObject(TechDataClass::class.java)
                        }
                        else -> {Unit}
                    }
                }
            }

        }}

    /*
    fun TasksFun(){
        if (availabletxtobserverr.get()=="Available"){
            availabletxtobserver.set("on Task")
            changbg.value=R.color.red
        }else{
            availabletxtobserver.set("on Task")
            changbg.value=R.color.red
        }

    }

     */









}