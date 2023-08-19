package com.example.baapplication

import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.baapplication.adapter.AllTechniciansViewModel
import com.example.baapplication.adapter.TechniciansAdapter
import com.example.baapplication.addtask.AddTaskActivity
import com.example.baapplication.addtask.AddTaskViewModel
import com.example.baapplication.databinding.ActivityMainBinding
import com.example.baapplication.models.*
import com.example.baapplication.tasks.TasksActivity
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    lateinit var dataBindingMain:ActivityMainBinding
    lateinit var techAdapter:TechniciansAdapter
    lateinit var techData:ArrayList<TechDataClass?>
    //var vmprovider:AddTaskViewModel?=null
    lateinit var viewmodelTechnicians:AllTechniciansViewModel

    //lateinit var addTaskVM:AddTaskViewModel
    var calendar: Calendar?=null
    var simpleFormat: SimpleDateFormat?=null
    var Date:String?=null
    var simpleDateClockFormat: SimpleDateFormat?=null
    var DateClock:String?=null

    var noOfTask= MutableLiveData<String>()
    var numOfTasks:Int?=0

    var tt:List<TechDataClass>?=null
    var techList:ArrayList<TechDataClass?>?=null
    var i=0
    var techPics:ArrayList<Int>?=null
    var te_chs: MutableList<TechDataClass>? =null
    var finalTechs:MutableList<TechDataClass>?=null


    /*


     */

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        dataBindingMain=ActivityMainBinding.inflate(layoutInflater)
        setContentView(dataBindingMain.root)
        //vmprovider=ViewModelProvider(this).get(AddTaskViewModel::class.java)
       // dataBindingMain.vmm=vmprovider
        viewmodelTechnicians=ViewModelProvider(this).get(AllTechniciansViewModel::class.java)
        dataBindingMain.vmm=viewmodelTechnicians

        calendar=Calendar.getInstance()
        simpleFormat= SimpleDateFormat("dd-MM-yyyy")
        Date=simpleFormat!!.format(calendar!!.time)
        simpleDateClockFormat= SimpleDateFormat("HH:mm:SS")
        DateClock=simpleDateClockFormat!!.format(calendar!!.time)

        techAdapter= TechniciansAdapter(mutableListOf(),null)
        //hn3mlha btre2a tanya kda kda de mkntsh sh3'ala
        //lma ndos 3ala end task ygblna alw2t

        dataBindingMain.recyclerView.adapter=techAdapter
        initTechsImg()
        subdcribeToLiveData()
        //initData()
       //subdcribeToTechDataChange()
        //Log.e("techs","${techList}")
        //nn()
        x()



       // te_chs=tryyy()
        //viewmodelTechnicians.recieveTechDataChanged()
        viewmodelTechnicians.tecchs.observe(this){
            techAdapter.UpdateTechsDataClass(it)
        }
        //-->Log.e("ttnuserProvidertech","${UserProvider.technicianss?.get(0)}")


        /*for(i in 0..techData.size-1){
            insertTechToDataBase(techData[i]!!)
            //Log.e("techsnam","${te_chs!!.size}")
            //Log.e("techsname","${te_chs!![i].techName}")
            // Log.e("techs","${techList?.size}")
           // Log.e("techs","${techList?.get(i)?.techName}")

        }

         */






        //techAdapter.upDateDAta(techData)
        //tryyy()
       // techAdapter.upDateDAta(techList!!)
       // techAdapter.upDateDAta(techList!!)

        //getTechsFromDataBase()
        //techAdapter.upDateDAta(techData)
        //techAdapter.upDateDAta(tt)
        techAdapter.showErrorfun=object :TechniciansAdapter.ShowErrorfun{
            override fun showError() {
                Toast.makeText(this@MainActivity,"You Don't have permission to End the Task,Only the User who Created the task can end it!",Toast.LENGTH_LONG).show()
            }

        }
        techAdapter.onAddTaskClickListener=object :TechniciansAdapter.OnAddTaskClickListener{
            /*
            override fun OnAddTaskClick(position: Int) {
                val intent=Intent(this@MainActivity,AddTaskActivity.getInstance(position)::class.java)
                //intent.putExtra("techAdapterPosition",position)
                startActivity(intent)
            }
            */

            override fun OnAddTaskClick(curntItm: TechDataClass, position: Int) {
                val intent=Intent(this@MainActivity,AddTaskActivity.getInstance(curntItm.TechID!!)::class.java)
                //intent.putExtra("techAdapterPosition",position)
                startActivity(intent)
            }
        }
        insertAuthenticatedUsers()


        techAdapter.onAddTaskClickListen=object :TechniciansAdapter.OnAddTaskClickListen{
            override fun OnAddTask(TaskData: TechDataClass, position: Int) {
                 //vmprovider!!.addedTask= TaskData
                viewmodelTechnicians.addedTask=TaskData
            }
        }
        techAdapter.onEndTaskClickListener=object :TechniciansAdapter.OnEndTaskClickListener{
            override fun OnEndTask(TaskData: TechDataClass, position: Int) {
              //  Toast.makeText(this@MainActivity,"!!!!!!!",Toast.LENGTH_LONG).show()
                //vmprovider!!.EndTimeDate=Date
                //vmprovider!!.EndTimeClock=DateClock
                //viewmodelTechnicians.EndTimeDate=
                Toast.makeText(this@MainActivity,"Task Ended at ${Date} at time ${DateClock}",Toast.LENGTH_LONG).show()
                Log.e("endTask","task is ended")
                val intent=Intent(this@MainActivity,MainActivity::class.java)
                startActivity(intent)
            }
        }
        techAdapter.onViewTasksClickListener=object :TechniciansAdapter.OnViewTasksClickListener{
            override fun OnViewTaskClick(position: Int, TaskData: TechDataClass) {
                val intent=Intent(this@MainActivity,TasksActivity.getInstance(TaskData.TechID!!)::class.java)
                startActivity(intent)
            }

        }


        //techAdapter.UpdateOnSubmitChanged(vmprovider?.validate?.get()!!)
    }
    /*
    *  techAdapter.onTechClickListener=object :TechniciansAdapter.OnTechClickListener{
            override fun OnTechClick(position: Int, TaskData: TechDataClass) {
                val noOFtsk=gg(TaskData.TechID!!)
                val ttn=NoOfTasks("No Of Tasks:" +noOFtsk)
                //vmprovider!!.noOfTask.value=ttn
                viewmodelTechnicians.noOfTask.value=ttn
                subdcribeToLiveData()

            }
        }*/

    fun x(){
        FireStoreUtiles().getTecs().addSnapshotListener { value, error ->
            if (error!=null){
                //   Toast.makeText(this,"error on getting techs ${error.localizedMessage}", Toast.LENGTH_LONG).show()
            }else{
                val technssnapshots=value?.toObjects(TechDataClass::class.java)
                for(doc in value!!.documentChanges){
                    when(doc.type){
                        DocumentChange.Type.MODIFIED -> {
                            finalTechs=mutableListOf()
                            if(doc.document.id=="Mahmoud01"){
                                var docc=doc.document.toObject(TechDataClass::class.java)
                                numOfTasks=gg(docc.TechID!!)
                                val ModifiedTech=TechDataClass(
                                    techImg = techPics!!.get(0),
                                    techName = docc.techName,
                                    techNo_ofTasks = "Num of Tasks: "+ numOfTasks,
                                    TechID = docc.TechID,
                                    onTask = docc.onTask,
                                    taskCreatedBy = docc.taskCreatedBy)
                                FireStoreUtiles().getAllTech().addOnCompleteListener {
                                    if(it.isSuccessful){
                                        val tx=it.result.toObjects(TechDataClass::class.java)
                                        finalTechs!!.add(ModifiedTech)
                                        finalTechs!!.add(tx.get(1))
                                        finalTechs!!.add(tx.get(2))
                                        finalTechs!!.add(tx.get(3))

                                    } }
                                viewmodelTechnicians.tecchs.value=finalTechs

                            }else if(doc.document.id=="Reda02"){
                                var docc=doc.document.toObject(TechDataClass::class.java)
                                numOfTasks=gg(docc.TechID!!)
                                val ModifiedTech=TechDataClass(
                                    techImg = techPics!!.get(1),
                                    techName = docc.techName,
                                    techNo_ofTasks = "Num of Tasks: "+ numOfTasks,
                                    TechID = docc.TechID,
                                    onTask = docc.onTask,
                                    taskCreatedBy = docc.taskCreatedBy)
                                FireStoreUtiles().getAllTech().addOnCompleteListener {
                                    if(it.isSuccessful){
                                        val tx=it.result.toObjects(TechDataClass::class.java)
                                        finalTechs!!.add(tx.get(0))
                                        finalTechs!!.add(ModifiedTech)
                                        finalTechs!!.add(tx.get(2))
                                        finalTechs!!.add(tx.get(3))

                                    } }
                                viewmodelTechnicians.tecchs.value=finalTechs
                            }else if(doc.document.id=="Bahrawy03"){
                                var docc=doc.document.toObject(TechDataClass::class.java)
                                numOfTasks=gg(docc.TechID!!)
                                val ModifiedTech=TechDataClass(
                                    techImg = techPics!!.get(2),
                                    techName = docc.techName,
                                    techNo_ofTasks = "Num of Tasks: "+ numOfTasks,
                                    TechID = docc.TechID,
                                    onTask = docc.onTask,
                                    taskCreatedBy = docc.taskCreatedBy)
                                FireStoreUtiles().getAllTech().addOnCompleteListener {
                                    if(it.isSuccessful){
                                        val tx=it.result.toObjects(TechDataClass::class.java)
                                        finalTechs!!.add(tx.get(0))
                                        finalTechs!!.add(tx.get(1))
                                        finalTechs!!.add(ModifiedTech)
                                        finalTechs!!.add(tx.get(3))
                                    } }
                                viewmodelTechnicians.tecchs.value=finalTechs

                            }else if(doc.document.id=="Hany04"){
                                var docc=doc.document.toObject(TechDataClass::class.java)
                                numOfTasks=gg(docc.TechID!!)
                                val ModifiedTech=TechDataClass(
                                    techImg = techPics!!.get(3),
                                    techName = docc.techName,
                                    techNo_ofTasks = "Num of Tasks: "+ numOfTasks,
                                    TechID = docc.TechID,
                                    onTask = docc.onTask,
                                    taskCreatedBy = docc.taskCreatedBy)
                                FireStoreUtiles().getAllTech().addOnCompleteListener {
                                    if(it.isSuccessful){
                                        val tx=it.result.toObjects(TechDataClass::class.java)
                                        finalTechs!!.add(tx.get(0))
                                        finalTechs!!.add(tx.get(1))
                                        finalTechs!!.add(tx.get(2))
                                        finalTechs!!.add(ModifiedTech)

                                    } }
                                viewmodelTechnicians.tecchs.value=finalTechs

                            }


                            /*
                            val tech_obj=doc.document.toObject(TechDataClass::class.java)
                            doc.newIndex
                            val documentCHangedId=doc.document.id

                            if(doc.document.toObject(TechDataClass::class.java).onTask == true){
                                viewmodelTechnicians.changbg.value=R.color.red
                                viewmodelTechnicians.showAvailability.value="On Task"
                            }else{
                                viewmodelTechnicians.changbg.value=R.drawable.side_nav_bar
                                viewmodelTechnicians.showAvailability.value="Available"
                            }
                             */

                        }
                        else -> {Unit}
                    }
                }
            }

        }}
    fun y(){

    }



/*
    fun subdcribeToTechDataChange(){
        techList=ArrayList()
         FireStoreUtiles().getAllTech().addSnapshotListener(
             object :EventListener<QuerySnapshot>{
                 override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                     if(error != null){
                         Toast.makeText(this@MainActivity,"error ${error}",Toast.LENGTH_LONG).show()
                     }else{
                         value?.documentChanges?.forEach({

                             val tech = it.document.toObject(TechDataClass::class.java)
                             //techList.add(tech)
                             techList!!.add(TechDataClass(
                                 techImg = techData.get(i)?.techImg,
                                 techName = tech.techName,
                                 techNo_ofTasks = tech.techNo_ofTasks,
                                 TechID = tech.TechID,
                                 onTask = tech.onTask,
                                 taskCreatedBy = tech.taskCreatedBy

                             ))
                             val techhh=TechDataClass(
                                 techImg = techData.get(i)?.techImg,
                                 techName = tech.techName,
                                 techNo_ofTasks = tech.techNo_ofTasks,
                                 TechID = tech.TechID,
                                 onTask = tech.onTask,
                                 taskCreatedBy = tech.taskCreatedBy
                             )
                             Toast.makeText(this@MainActivity,"${techhh}",Toast.LENGTH_LONG).show()
                             i+=1
                         })

                         techAdapter.UpdateTechsDataClass(techList)
                     }
                 }
             })
    }


 */
    fun gg(TechID:String):Int{
        FireStoreUtiles().getAllTasks(TechID).addOnCompleteListener({
            if(it.isSuccessful){
                val tasks = it.result.toObjects(TaskDetails::class.java)
                numOfTasks=tasks.size
                //taskVm.TasksLiveData.value=tasks
            }
        })
        return numOfTasks!!
    }
    fun nn(){
        FireStoreUtiles().getAllTech().addOnCompleteListener {
            if(it.isSuccessful){
                val tes=it.result.toObjects(TechDataClass::class.java)
                viewmodelTechnicians.tecchs.value=tes
                for(i in 0..tes.size-1){
                    if (tes[i].onTask==true){
                        viewmodelTechnicians.showAvailability.value="On Task"
                        viewmodelTechnicians.changbg.value=R.color.red
                        viewmodelTechnicians.showStartEndTask.value="End Task"
                        viewmodelTechnicians.changeBTNbg.value=R.drawable.rounded_borders_endtask
                    }else {
                        viewmodelTechnicians.showAvailability.value="Available"
                        viewmodelTechnicians.changbg.value=R.drawable.side_nav_bar
                        viewmodelTechnicians.showStartEndTask.value="Add Task"
                        viewmodelTechnicians.changeBTNbg.value=R.drawable.rounded_borders_addtask
                    }
                }
            }
        }
    }

    fun subdcribeToLiveData(){
        /*
        viewmodelTechnicians.noOfTask.observe(this){
            techAdapter.UpdateNoOFTasks(it)
        }

         */
        viewmodelTechnicians.tecchs.observe(this){
            techAdapter.UpdateTechsDataClass(it)
        }
    }

    override fun onStart() {
        super.onStart()
        subdcribeToLiveData()
        nn()
    }


    fun initTechsImg(){
        techPics= ArrayList()
        techList=ArrayList()
        techPics!!.add(R.drawable.bahrawy)
        techPics!!.add(R.drawable.james4)
        techPics!!.add(R.drawable.mahmoud4)
        techPics!!.add(R.drawable.reda)
    }
    fun tryyy(): MutableList<TechDataClass>? {

        FireStoreUtiles().getAllTech().addOnCompleteListener {
            if(it.isSuccessful){
                te_chs=it.result.toObjects(TechDataClass::class.java)
                Log.e("success","succeed!!!!")
                Log.e("ttt","${te_chs}")
                UserProvider.technicianss=te_chs



                for (i in 0..3){
                    techList!!.add(TechDataClass(
                        techImg = techPics!!.get(i),
                        techName = te_chs!!.get(i).techName,
                        techNo_ofTasks = te_chs!!.get(i).techNo_ofTasks,
                        TechID = te_chs!!.get(i).TechID,
                        onTask = te_chs!!.get(i).onTask,
                        taskCreatedBy = te_chs!!.get(i).taskCreatedBy))
                   // Toast.makeText(this,"${te_chs!!.get(i).TechID}",Toast.LENGTH_LONG).show()
                    Log.e("n","${te_chs!!.get(i).TechID}")
                }
            }else{
                Log.e("failed","${it.exception?.localizedMessage}")
            }
        }


        //--------------------------------------------->
        FireStoreUtiles().getTecs().addSnapshotListener { value, error ->
            if (error!=null){
                Toast.makeText(this,"error on getting techs ${error.localizedMessage}",Toast.LENGTH_LONG).show()
            }else{
                val technssnapshots=value?.toObjects(TechDataClass::class.java)
                for(doc in value!!.documentChanges){
                    when(doc.type){
                        DocumentChange.Type.MODIFIED -> {
                            viewmodelTechnicians.showAvailability.value=doc.document.toObject(TechDataClass::class.java).onTask.toString()
                        }
                        else -> {Unit}
                    }
                }
            }



        }



        return te_chs

    }



    fun insertTechToDataBase(techs:TechDataClass){
        val Technician=TechDataClass(TechID = techs.TechID, techNo_ofTasks = "No Of Tasks", techName = techs.techName, techImg = techs.techImg)
        FireStoreUtiles().InsertTechnicianToDataBase(Technician).addOnCompleteListener({
            if(it.isSuccessful){
                Toast.makeText(this@MainActivity,"Succeed To Add AllTechnician todatabase",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this@MainActivity,"problem occured ${it.exception.toString()}",Toast.LENGTH_LONG).show()
            }
        })

    }
    var taskCreatedBy:ArrayList<String>?=null

    /*fun getTechsFromDataBase(){
        FireStoreUtiles().getAllTech().addOnCompleteListener {
            //initData()
            if(it.isSuccessful){
                 tt= it.result.toObjects(TechDataClass::class.java)
                 //initData()
                tt!!.forEach({
                   techData.add(TechDataClass(taskCreatedBy=it.taskCreatedBy, onTask = it.onTask))
                    Toast.makeText(this,"Error occured ${it.techName} is Busy right now",Toast.LENGTH_LONG).show()

                })
            }else{
                Toast.makeText(this,"Error occured ${it.exception!!.localizedMessage}",Toast.LENGTH_LONG).show()
            }
        }
    }

     */

    fun initData(){
        techData= ArrayList()
        techData.add(TechDataClass(R.drawable.mahmoud4,  techName = "Tech mahmoud","No of Tasks: 3","Mahmoud01"))
        techData.add(TechDataClass(R.drawable.reda,techName ="Tech Reda","No of Tasks: 4","Reda02"))
        techData.add(TechDataClass(R.drawable.bahrawy,techName ="Tech Bahrawy","No of Tasks: 2","Bahrawy03"))
        techData.add(TechDataClass(R.drawable.james4,techName ="Tech Hany","No of Tasks: 1","Hany04"))
    }

    var authen_users_Array:ArrayList<User?>? =null

    fun insertAuthenticatedUsers(){
        AuthenUsersArrayFill()
        for (i in 0..authen_users_Array!!.size-1){
            authen_users_Array!![i]?.let { FireStoreUtiles().InsertAuthenticatedUsers(it).addOnCompleteListener {
                if (it.isSuccessful){
                  //  Toast.makeText(this@MainActivity,"Authenticated Users Collection is created and have 3 authenticated users",
                    //    Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this@MainActivity,
                        "Couldn't create Authenticated users collection ${it.exception?.localizedMessage}",
                        Toast.LENGTH_LONG).show()
                }
            } }
        }

    }
    fun AuthenUsersArrayFill(){
        authen_users_Array= ArrayList()
        authen_users_Array!!.add(User(id = "salah", email = "salah.salah@bibalex.dom", password = "002345"))
        authen_users_Array!!.add(User(id = "shawky", email = "shawky.shawky@bibalex.dom"))
        authen_users_Array!!.add(User(id = "ali", email = "ali.elsheikh@bibalex.dom"))

    }

}