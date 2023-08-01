package com.example.baapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.baapplication.R
import com.example.baapplication.databinding.SingleTechnicianDesignBinding
import com.example.baapplication.models.TechDataClass

class TechniciansAdapter(var Data:ArrayList<TechDataClass?>?,var OnSubmitSuccess:Boolean):Adapter<TechniciansAdapter.myHolder>() {
    var onAddTaskClickListener:OnAddTaskClickListener?=null
    var count=1
    var onAddTaskClickListen: OnAddTaskClickListen?=null
    var onEndTaskClickListener: OnEndTaskClickListener?=null


    class myHolder(val singlePersonDataBinding:SingleTechnicianDesignBinding):ViewHolder(singlePersonDataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myHolder {
        val singlePersonDataBinding=SingleTechnicianDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return myHolder(singlePersonDataBinding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: myHolder, position: Int) {
        val currentItem = Data?.get(position)
        holder.singlePersonDataBinding.PersonImg.setImageResource(currentItem!!.techImg!!)
        holder.singlePersonDataBinding.noOfTasks.setText(currentItem?.techNo_ofTasks)
        holder.singlePersonDataBinding.GoToTask.setOnClickListener({
            //holder.singlePersonDataBinding.availableOrBusy.setText("On Task")
            //holder.singlePersonDataBinding.availableOrBusy.setBackgroundColor(R.color.error)
            //holder.singlePersonDataBinding.availableOrBusy.background= R.color.error.toDrawable()
            //holder.singlePersonDataBinding.availableOrBusy.setBackgroundResource(R.color.red)
            if (count % 2 == 0) {
                holder.singlePersonDataBinding.availableOrBusy.setText("Available")
                holder.singlePersonDataBinding.availableOrBusy.setBackgroundResource(R.color.teal)
                //holder.singlePersonDataBinding.GoToTask.setBackgroundColor(R.color.teal)
                holder.singlePersonDataBinding.GoToTask.setBackgroundResource(R.drawable.rounded_borders_addtask)
                holder.singlePersonDataBinding.GoToTask.setText("ADD TASK")
                onEndTaskClickListener?.OnEndTask(currentItem,position)

                count += 1
            }
            else if (count % 2 != 0) {
                onAddTaskClickListener?.OnAddTaskClick(position)
                if(OnSubmitSuccess ==true){
                    holder.singlePersonDataBinding.availableOrBusy.setText("On Task")
                    holder.singlePersonDataBinding.availableOrBusy.setBackgroundResource(R.color.red)

                    holder.singlePersonDataBinding.GoToTask.setText("End Task")
                    //holder.singlePersonDataBinding.GoToTask.setBackgroundColor(R.color.red)
                    holder.singlePersonDataBinding.GoToTask.setBackgroundResource(R.drawable.rounded_borders_endtask)
                }



                count += 1

            }

            /*
            holder.singlePersonDataBinding.availableOrBusy.background.setColorFilter(

                ResourcesCompat.getColor(Resources.getSystem(),R.color.red,null),
                PorterDuff.Mode.SRC_ATOP)
            onAddTaskClickListener?.OnAddTaskClick(position)


        })
            */
        })
    }

    interface OnAddTaskClickListen {
        fun OnAddTask(TaskData: TechDataClass, position: Int)

    }

    override fun getItemCount(): Int {
        return Data?.size?:0
    }
    fun upDateDAta(data:ArrayList<TechDataClass?>?){
        Data=data
        notifyDataSetChanged()
    }
    interface OnAddTaskClickListener{
        fun OnAddTaskClick(position: Int)
    }
    interface OnEndTaskClickListener{
        fun OnEndTask(TaskData: TechDataClass, position: Int)
    }
}