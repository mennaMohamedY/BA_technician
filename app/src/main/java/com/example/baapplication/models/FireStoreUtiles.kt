package com.example.baapplication.models

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.auth.User
import com.example.baapplication.models.User
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.dataObjects

class FireStoreUtiles {
    //singleton
    val UsersCollectionName="Users"
    val TechniciansCollectionName="Technicians"

    fun getCollectionRef(collectionName:String):CollectionReference{
        val database=FirebaseFirestore.getInstance()
        val collectionRef=database.collection(collectionName)
        return collectionRef
    }
    fun insertUserToDataBase(user: User): Task<Void> {
        /*
         val doc= database.collection(UsersCollectionName) //create collection if not exist or get it if it's already exists
              .document()
         //doc it creates new doc "new row" with new id
         */
        val docRef=getCollectionRef(UsersCollectionName).document(user.id!!)
        //creates new doc with the same id as the userid
        //id of authentication "user.id" same as firestore doc id
        return docRef.set(user)
    }

    fun getUserFromDataBase(uid:String):Task<DocumentSnapshot>{
       val docRef= getCollectionRef(UsersCollectionName).document(uid)
        return docRef.get()
    }

    fun InsertTechnicianToDataBase(technician:TechDataClass): Task<Void>{
        val docRef=getCollectionRef(TechniciansCollectionName).document(technician.TechID!!)
        return docRef.set(technician)
    }

    fun getAllTechnicians():Task<QuerySnapshot>{
        val Allcollection=getCollectionRef(TechniciansCollectionName).get()
        return Allcollection

        /*
        * it.result.toObjects(TechDataClass)
        * in vm val roomsliveData=MutableLiveData<List<Room>>()
        * */
    }



}