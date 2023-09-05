package com.example.baapplication.tasksinmonths

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.baapplication.databinding.EachMonthTasksBinding
import com.example.baapplication.models.TaskDetails

class TasksInMonthsAdapter(var tasksDetils:MutableList<TaskDetails?>?):Adapter<TasksInMonthsAdapter.MyTasksHolder>(){

    class MyTasksHolder(val tasksviews:EachMonthTasksBinding):ViewHolder(tasksviews.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTasksHolder {
        val tasksviews=EachMonthTasksBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyTasksHolder(tasksviews)
    }

    override fun onBindViewHolder(holder: MyTasksHolder, position: Int) {
        var currentItem=tasksDetils?.get(position)
        holder.tasksviews.taskDescription.text=currentItem?.TaskDescription
        holder.tasksviews.taskCreatedBy.text=currentItem?.CreatedBy
        holder.tasksviews.goinwith.text=currentItem?.GoingWith
        holder.tasksviews.starttimee.text=currentItem?.StartTime
        holder.tasksviews.endtimee.text=currentItem?.EndTask
    }

    override fun getItemCount(): Int {
        return tasksDetils?.size?:0
    }

    fun updateData(tasksdetils:MutableList<TaskDetails?>?){
        tasksDetils=tasksdetils
        notifyDataSetChanged()
    }

}