package com.example.baapplication.todotasks

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.baapplication.R
import com.example.baapplication.bottoshee.AddTodoActivity
import com.example.baapplication.databinding.FragmentToDoBinding

//import com.example.baapplication.databinding.FragmentToDoBinding


class ToDoFragment : Fragment() {
    lateinit var todobinding: FragmentToDoBinding
    lateinit var todoVM:TodoTasksFragViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoVM=ViewModelProvider(this).get(TodoTasksFragViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        todobinding=DataBindingUtil.inflate(inflater,R.layout.fragment_to_do,container,false)
        return todobinding.root
        //return inflater.inflate(R.layout.fragment_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todobinding.vm=todoVM

        todobinding.addTodoBTN.setOnClickListener({
            val intent=Intent(requireContext(),AddTodoActivity::class.java)
            startActivity(intent)
        })
    }


}