package com.example.baapplication.nooftasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.baapplication.databinding.ActivityNoOfTasksBinding
import com.example.baapplication.databinding.SingleTaskDsgnBinding

class NumTasksAdapter(var NumOfData:List<NumOfTasksDataClass?>?):Adapter<NumTasksAdapter.MyNumAdapter>() {
    var onDetailsBtnClickListener:OnDetailsBtnClickListener?=null
    class MyNumAdapter(val numitemview:SingleTaskDsgnBinding):ViewHolder(numitemview.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNumAdapter {
        val numview=SingleTaskDsgnBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyNumAdapter(numview)

    }

    override fun onBindViewHolder(holder: MyNumAdapter, position: Int) {
        var currentItem=NumOfData?.get(position)
        holder.numitemview.monthName.text=currentItem?.Month
        holder.numitemview.ntasks.text= " " + currentItem?.NomOfTasks + "Task "
        holder.numitemview.taskDetailsBtn.setOnClickListener {
            onDetailsBtnClickListener?.onDetailsBtnClick(position,currentItem!!)
        }
    }

    override fun getItemCount(): Int {
        return NumOfData?.size?:0
    }
    fun UpdateData(numofdata:List<NumOfTasksDataClass?>?){
        NumOfData=numofdata
        notifyDataSetChanged()
    }
    interface OnDetailsBtnClickListener{
        fun onDetailsBtnClick(position: Int,dataa:NumOfTasksDataClass)
    }
}