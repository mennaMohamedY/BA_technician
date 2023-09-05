package com.example.baapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.baapplication.R
import com.example.baapplication.databinding.SingleTechnicianDesignBinding
//import com.example.baapplication.databinding.SingleTechnicianDesignBinding
import com.example.baapplication.models.*
import com.google.firebase.firestore.ktx.toObject

class TechniciansAdapter(var Data:List<TechDataClass?>?,var techNoOfTasks:NoOfTasks?):Adapter<TechniciansAdapter.myHolder>() {
    var onAddTaskClickListener:OnAddTaskClickListener?=null
    var count=1
    var onAddTaskClickListen: OnAddTaskClickListen?=null
    var onEndTaskClickListener: OnEndTaskClickListener?=null
    var onViewTasksClickListener:OnViewTasksClickListener?=null
    var taskCreator:String?=null
    var onNumOFTaskClickListener: OnNumOfTasksClickListener?=null

    var showErrorfun:ShowErrorfun?=null

    var onTechClickListener:OnTechClickListener?=null

    fun UpdateOnSubmitChanged(OnsubmitSuccess:Boolean){
       // OnSubmitSuccess=OnsubmitSuccess
        notifyDataSetChanged()
    }
    fun UpdateNoOFTasks(no_ofTASKs:NoOfTasks){
        techNoOfTasks=no_ofTASKs
        notifyDataSetChanged()
    }

    fun UpdateTechsDataClass(techs:List<TechDataClass?>?){
        this.Data=techs
        notifyDataSetChanged()
    }





    class myHolder(val singlePersonDataBinding: SingleTechnicianDesignBinding):ViewHolder(singlePersonDataBinding.root){
        fun bind(techDatta:TechDataClass?) {
            //singlePersonDataBinding.ttn=technoTasks
            //singlePersonDataBinding.techniciann=techDatta
            //singlePersonDataBinding.techName.text=techDatta?.techName

            /*if (techDatta?.onTask==true){
                singlePersonDataBinding.availableOrBusy.text="On Task"
            }else{
                singlePersonDataBinding.availableOrBusy.text="Available"
                singlePersonDataBinding.techName.setBackgroundResource(R.drawable.side_nav_bar)

            }

             */

            singlePersonDataBinding.executePendingBindings()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myHolder {
        val singlePersonDataBinding=SingleTechnicianDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       // val singlePersonDataBinding=SingleTechnicianDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        //val singlePersonDataBinding:SingleTechnicianDesignBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.single_technician_design,parent,false)
        return myHolder(singlePersonDataBinding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: myHolder, position: Int) {
        val currentItem = Data?.get(position)
        //val no_o_t=techNoOfTasks
        holder.bind(currentItem)
        holder.singlePersonDataBinding.viewTasks.setOnClickListener({
            onViewTasksClickListener?.OnViewTaskClick(position,currentItem!!)
        })


        /*


        holder.singlePersonDataBinding.PersonImg.setImageResource(currentItem!!.techImg!!)
        holder.singlePersonDataBinding.techName.setText(currentItem.techName)
        holder.singlePersonDataBinding.techName.setOnClickListener({
            onTechClickListener?.OnTechClick(position,currentItem)
            holder.bind(no_o_t,currentItem)
            //holder.singlePersonDataBinding.noOfTasks.setText(techNoOfTasks?.no_ofTask)
        })
        holder.singlePersonDataBinding.PersonImg.setOnClickListener({
            onTechClickListener?.OnTechClick(position,currentItem)
            holder.bind(no_o_t,currentItem)
        })

         */



        if(currentItem?.onTask==true){
            holder.singlePersonDataBinding.availableOrBusy.setText("On Task")
            holder.singlePersonDataBinding.availableOrBusy.setBackgroundResource(R.color.red)
            holder.singlePersonDataBinding.techName.setBackgroundResource(R.color.red)

            holder.singlePersonDataBinding.GoToTask.setText("End Task")
            //holder.singlePersonDataBinding.GoToTask.setBackgroundColor(R.color.red)
            holder.singlePersonDataBinding.GoToTask.setBackgroundResource(R.drawable.rounded_borders_endtask)
        }else{
            holder.singlePersonDataBinding.availableOrBusy.setText("Available")
            holder.singlePersonDataBinding.techName.setBackgroundResource(R.drawable.side_nav_bar)
            holder.singlePersonDataBinding.availableOrBusy.setBackgroundResource(R.drawable.side_nav_bar)
            holder.singlePersonDataBinding.GoToTask.setText("Add Task")
            holder.singlePersonDataBinding.GoToTask.setBackgroundResource(R.drawable.rounded_borders_addtask)
        }

        holder.singlePersonDataBinding.techName.setText(currentItem?.techName)
        holder.singlePersonDataBinding.PersonImg.setImageResource(currentItem?.techImg!!)
        holder.singlePersonDataBinding.noOfTasks.setText(currentItem.techNo_ofTasks)

        holder.singlePersonDataBinding.noOfTasks.setOnClickListener {
            onNumOFTaskClickListener?.OnNumOfTaskClick(currentItem,position)
        }

        holder.singlePersonDataBinding.GoToTask.setOnClickListener({
            val actualUserUsingApp=UserProvider.user?.email
//            holder.singlePersonDataBinding.availableOrBusy.setText("On Task")
            if(currentItem?.onTask != true){

                holder.singlePersonDataBinding.availableOrBusy.setText("On Task")
                holder.singlePersonDataBinding.availableOrBusy.setBackgroundResource(R.color.red)
                holder.singlePersonDataBinding.techName.setBackgroundResource(R.color.red)
                holder.singlePersonDataBinding.GoToTask.setText("End Task")
                //holder.singlePersonDataBinding.GoToTask.setBackgroundColor(R.color.red)
                holder.singlePersonDataBinding.GoToTask.setBackgroundResource(R.drawable.rounded_borders_endtask)
                onAddTaskClickListener?.OnAddTaskClick(currentItem,position)

            }else if(currentItem.onTask==true && currentItem?.taskCreatedBy==actualUserUsingApp){
                //&& FireStoreUtiles().getTechnician(currentItem?.TechID!!).result.toObject(TechDataClass::class.java)?.onTask==true
                holder.singlePersonDataBinding.availableOrBusy.setText("Available")
                holder.singlePersonDataBinding.techName.setBackgroundResource(R.drawable.side_nav_bar)
                holder.singlePersonDataBinding.availableOrBusy.setBackgroundResource(R.drawable.side_nav_bar)
                FireStoreUtiles().updateTechOnDataBase(currentItem?.TechID!!,false,"null")
                holder.singlePersonDataBinding.GoToTask.setBackgroundResource(R.drawable.rounded_borders_addtask)
                holder.singlePersonDataBinding.GoToTask.setText("ADD TASK")
                onEndTaskClickListener?.OnEndTask(currentItem,position)
                /*
                if(taskcreator.toString() == actualUserUsingApp.toString()){
                    holder.singlePersonDataBinding.availableOrBusy.setText("Available")
                    holder.singlePersonDataBinding.techName.setBackgroundResource(R.drawable.side_nav_bar)
                    holder.singlePersonDataBinding.availableOrBusy.setBackgroundResource(R.drawable.side_nav_bar)
                    FireStoreUtiles().updateTechOnDataBase(currentItem.TechID!!,false,"null")
                    holder.singlePersonDataBinding.GoToTask.setBackgroundResource(R.drawable.rounded_borders_addtask)
                    holder.singlePersonDataBinding.GoToTask.setText("ADD TASK")
                    onEndTaskClickListener?.OnEndTask(currentItem,position)

            }

                 */
            }else if(currentItem.onTask==true && currentItem?.taskCreatedBy!=actualUserUsingApp){
                showErrorfun?.showError()


            }
        }
            // onAddTaskClickListener?.OnAddTaskClick(position)
            //var availabilityTxtView=holder.singlePersonDataBinding.availableOrBusy.text

        // val currenttech= FireStoreUtiles().getTechnician(currentItem?.TechID!!).result.toObject(TechDataClass::class.java)

        /*
        var currenttech=FireStoreUtiles().getTechnician(currentItem?.TechID!!).addOnCompleteListener({
            if (it.isSuccessful){
                //            val taskcreator=currenttech?.taskCreatedBy
                var resu=it.result.toObject(TechDataClass::class.java)
                taskCreator=resu?.taskCreatedBy

            }
        })
        */

        )





        //holder.singlePersonDataBinding.noOfTasks.setText(currentItem?.techNo_ofTasks)
        /*
        holder.singlePersonDataBinding.GoToTask.setOnClickListener({

            //holder.singlePersonDataBinding.availableOrBusy.setText("On Task")
            //holder.singlePersonDataBinding.availableOrBusy.setBackgroundColor(R.color.error)
            //holder.singlePersonDataBinding.availableOrBusy.background= R.color.error.toDrawable()
            //holder.singlePersonDataBinding.availableOrBusy.setBackgroundResource(R.color.red)
            var availabilityTxtView=holder.singlePersonDataBinding.availableOrBusy.text.toString()

           if(availabilityTxtView=="On Task"){
               val currenttech= FireStoreUtiles().getTechnician(currentItem?.TechID!!).result.toObject(TechDataClass::class.java)
                val taskcreator=currenttech?.taskCreatedBy
                val actualUserUsingApp=UserProvider.user?.email
                if(taskcreator.toString() == actualUserUsingApp.toString()){
                    holder.singlePersonDataBinding.availableOrBusy.setText("Available")
                    holder.singlePersonDataBinding.techName.setBackgroundResource(R.drawable.side_nav_bar)
                    holder.singlePersonDataBinding.availableOrBusy.setBackgroundResource(R.drawable.side_nav_bar)
                    FireStoreUtiles().updateTechOnDataBase(currentItem?.TechID!!,false,"null")
                    holder.singlePersonDataBinding.GoToTask.setBackgroundResource(R.drawable.rounded_borders_addtask)
                    holder.singlePersonDataBinding.GoToTask.setText("ADD TASK")
                    onEndTaskClickListener?.OnEndTask(currentItem,position)
                    count += 1
                }
            }

            //count % 2 == 0 &&

            if (availabilityTxtView=="On Task" ) {

                holder.singlePersonDataBinding.availableOrBusy.setText("available")
                //holder.singlePersonDataBinding.availableOrBusy.setBackgroundResource(R.color.teal)
                //holder.singlePersonDataBinding.techName.setBackgroundResource(R.color.teal)
                //holder.singlePersonDataBinding.GoToTask.setBackgroundColor(R.color.teal)
               // android:background="@drawable/side_nav_bar"
                holder.singlePersonDataBinding.techName.setBackgroundResource(R.drawable.side_nav_bar)
                holder.singlePersonDataBinding.availableOrBusy.setBackgroundResource(R.drawable.side_nav_bar)
                FireStoreUtiles().updateTechOnDataBase(currentItem.TechID!!,false,"null")



                holder.singlePersonDataBinding.GoToTask.setBackgroundResource(R.drawable.rounded_borders_addtask)
                holder.singlePersonDataBinding.GoToTask.setText("ADD TASK")
                onEndTaskClickListener?.OnEndTask(currentItem,position)

                count += 1
            }else if (availabilityTxtView=="Available") {

                onAddTaskClickListener?.OnAddTaskClick(position)
                /*
                if(OnSubmitSuccess ==true){
                     holder.singlePersonDataBinding.availableOrBusy.setText("On Task")
                     holder.singlePersonDataBinding.availableOrBusy.setBackgroundResource(R.color.red)

                     holder.singlePersonDataBinding.GoToTask.setText("End Task")
                     //holder.singlePersonDataBinding.GoToTask.setBackgroundColor(R.color.red)
                     holder.singlePersonDataBinding.GoToTask.setBackgroundResource(R.drawable.rounded_borders_endtask)
                 }
                 */

                holder.singlePersonDataBinding.availableOrBusy.setText("on Task")
                holder.singlePersonDataBinding.availableOrBusy.setBackgroundResource(R.color.red)
                holder.singlePersonDataBinding.techName.setBackgroundResource(R.color.red)

                holder.singlePersonDataBinding.GoToTask.setText("End Task")
                //holder.singlePersonDataBinding.GoToTask.setBackgroundColor(R.color.red)
                holder.singlePersonDataBinding.GoToTask.setBackgroundResource(R.drawable.rounded_borders_endtask)
                count += 1

            }


            //count % 2 != 0

            /*holder.singlePersonDataBinding.availableOrBusy.background.setColorFilter(

                ResourcesCompat.getColor(Resources.getSystem(),R.color.red,null),
                PorterDuff.Mode.SRC_ATOP)

             */
            onAddTaskClickListener?.OnAddTaskClick(position)
        })

         */


    }
    interface OnTechClickListener{
        fun OnTechClick(position: Int,TaskData: TechDataClass)
    }
    interface OnViewTasksClickListener{
        fun OnViewTaskClick(position: Int,TaskData: TechDataClass)
    }

    interface OnAddTaskClickListen {
        fun OnAddTask(TaskData: TechDataClass, position: Int)
    }
    interface OnNumOfTasksClickListener{
        fun OnNumOfTaskClick(TaskData: TechDataClass, position: Int)
    }

    override fun getItemCount(): Int {
        return Data?.size?:0
    }
    fun upDateDAta(data: ArrayList<TechDataClass?>){
        Data=data
        notifyDataSetChanged()
    }
    interface OnAddTaskClickListener{
        fun OnAddTaskClick(curntItm: TechDataClass,position: Int)
    }
    interface OnEndTaskClickListener{
        fun OnEndTask(TaskData: TechDataClass, position: Int)
    }
    interface ShowErrorfun{
        fun showError()
    }
}