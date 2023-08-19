package com.example.baapplication.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.baapplication.databinding.SingleTechnicianDesignBinding
import com.example.baapplication.models.TechDataClass

class TecAdapt(val tecs:List<TechDataClass>):Adapter<TecAdapt.MyTecAdapter>() {
    class MyTecAdapter(val itemBinding:SingleTechnicianDesignBinding):ViewHolder(itemBinding.root){
        fun bind(tech:TechDataClass){
            itemBinding.PersonImg.setImageResource(tech.techImg!!)
            itemBinding.techName.setText(tech.techName)
            //itemBinding.availableOrBusy.setText()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTecAdapter {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyTecAdapter, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}