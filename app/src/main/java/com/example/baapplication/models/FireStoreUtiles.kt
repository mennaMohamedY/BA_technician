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
import java.security.AllPermission

class FireStoreUtiles {
    //singleton
    val UsersCollectionName="Users"
    val TechniciansCollectionName="Technicians"
    val TasksCollection="Tasks"
    val AuthenticatedUSers="Authenticated_Users"


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

    fun getTechnician(technicianID:String):Task<DocumentSnapshot>{
        val Allcollection=getCollectionRef(TechniciansCollectionName).document(technicianID)
        return Allcollection.get()

        /*
        * it.result.toObjects(TechDataClass)
        * in vm val roomsliveData=MutableLiveData<List<Room>>()
        * */
    }
    fun updateTechOnDataBase(technicianID: String,OnTask:Boolean,CreatedBy:String){
        //val techs=getCollectionRef(TechniciansCollectionName).document(technicianID).update("onTask",OnTask)
        val techs=getCollectionRef(TechniciansCollectionName).document(technicianID).update(mapOf("onTask" to OnTask,"taskCreatedBy" to CreatedBy))

    }
    fun updateTechEndTimeOnDataBase(technicianID: String,endTime:String,TaskID:String,month:String){
        //val techs=getCollectionRef(TechniciansCollectionName).document(technicianID).update("onTask",OnTask)
        val techs=getCollectionRef(TechniciansCollectionName).document(technicianID).collection(month)
            .document(TaskID).update("endTask",endTime)

    }
    fun getMonthCollectionRef(collectionName:String):CollectionReference{
        val database=FirebaseFirestore.getInstance()
        val collectionRef=database.collection(collectionName)
        return collectionRef
    }

    fun addTask(task:TaskDetails,monthNAme:String):Task<Void>{
        var TechRef=getCollectionRef(TechniciansCollectionName).document(task.TechId!!)

        //collection 3lshan yeshel list of documents "list of tasks"
        //it's not a single task for each technician
        //so the next line says make a collection with name tasks
        //var Tasks=TechRef.collection(TasksCollection)
        var Tasks=TechRef.collection(monthNAme!!)



        var taskDoc=Tasks.document()
        task.TaskId = taskDoc.id!!
        techTaskProvider.taskID=taskDoc.id
        techTaskProvider.taskid=ArrayList()
        techTaskProvider.taskid?.add(taskDoc.id)
        techTaskProvider.taskid?.add(task.TechId!!)



        return taskDoc.set(task)
    }


    fun getAllTech():Task<QuerySnapshot>{
        val techs=getCollectionRef(TechniciansCollectionName).get()
        return techs
    }
    fun getTecs():CollectionReference{
        val techs=getCollectionRef(TechniciansCollectionName)
        return techs
    }

    fun getTechTasks(TechId:String):CollectionReference{
        return getCollectionRef(TechniciansCollectionName).document(TechId).collection(TasksCollection)
    }
    fun getAllMonths(TechId: String):Task<QuerySnapshot>{
        val monthsref=getCollectionRef(TechId).get()
        return monthsref
    }
    fun getAllTasksInMonth(TechId: String,Month: String):Task<QuerySnapshot>{
        var tasksRef=getCollectionRef(TechniciansCollectionName).document(TechId).collection(Month)
        return tasksRef.get()


    }
    fun getAllTasks(TechId:String,Month:String):Task<QuerySnapshot>{
        //var TechRef=getCollectionRef(TechniciansCollectionName).document(tech_id)
       // var Tasks=TechRef.collection(TasksCollection)
        //val tasksRef= getCollectionRef(TechniciansCollectionName).document(TechId).collection(TasksCollection)
        val tasksRef= getCollectionRef(TechniciansCollectionName).document(TechId).collection(Month)

        return tasksRef.get()

       // return Tasks.get()
    }

    fun InsertAuthenticatedUsers(user:User):Task<Void>{
        val docRef=getCollectionRef(AuthenticatedUSers).document(user.id!!)
        return docRef.set(user)
    }
    fun getAllAuthenticatedUsers():Task<QuerySnapshot>{
        val collectionref=getCollectionRef(AuthenticatedUSers).get()
        return collectionref
    }




}