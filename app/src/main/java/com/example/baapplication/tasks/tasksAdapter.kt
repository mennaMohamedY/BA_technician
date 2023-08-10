package com.example.baapplication.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.baapplication.R
import com.example.baapplication.databinding.SingleTaskDesignBinding
import com.example.baapplication.models.TaskDetails
import com.google.android.gms.tasks.Task

class tasksAdapter(var Tasks:List<TaskDetails?>?):Adapter<tasksAdapter.TasksHolder>() {
    val onTaskDetailsClickListener:OnTaskDetailsClickListener?=null

    fun UpdateData(task:List<TaskDetails?>?){
        Tasks=task
        notifyDataSetChanged()
    }

   // fun addTask(task:TaskDetails){
        //Tasks?.add(task)
      //  notifyItemInserted(Tasks!!.size)
   // }


    class TasksHolder(val singlTaskDetails:SingleTaskDesignBinding):ViewHolder(singlTaskDetails.root){
        fun bind(taskDetails: TaskDetails?) {
            singlTaskDetails.task=taskDetails
            singlTaskDetails.invalidateAll()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksHolder {
        val singleTaskDesign=SingleTaskDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        //val singleTaskDesign:SingleTaskDesignBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.context),
        //    R.layout.single_task_design,parent,false)
        return TasksHolder(singleTaskDesign)
    }

    override fun onBindViewHolder(holder: TasksHolder, position: Int) {
        val currentTask=Tasks?.get(position)
        holder.bind(currentTask)


        /*
        with(holder){
            with(singlTaskDetails){
                taskDescription.text="currentTask?.TaskDescription"
                timee.text=currentTask?.StartTime
                taskDetailsBtn.setOnClickListener({
                    onTaskDetailsClickListener?.OnTaskDetailsClick(position,currentTask!!)
                })
            }
        }
        holder.singlTaskDetails.taskDescription.setText(currentTask?.TaskDescription)


         */

    }

    interface OnTaskDetailsClickListener{
        fun OnTaskDetailsClick(position: Int,task:TaskDetails){}
    }

    override fun getItemCount(): Int {
       return Tasks?.size?:0
    }
}