package com.example.baapplication.sidrawe

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.baapplication.MainActivity
import com.example.baapplication.R
import com.example.baapplication.databinding.ActivitySideDrawerBinding
import com.example.baapplication.hompag.HomePageFragment
import com.example.baapplication.todotasks.ToDoFragment
import com.example.baapplication.userprofil.UserProfileFragment

//import com.example.baapplication.sidrawe.databinding.ActivitySideDrawerBinding

class SideDrawerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySideDrawerBinding
    lateinit var homepageFragment:HomePageFragment
    lateinit var todoFragment:ToDoFragment
    lateinit var userprofilefragment:UserProfileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySideDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarSideDrawer.toolbar)
        binding.appBarSideDrawer.menuGetSidedrawer.setOnClickListener({
            binding.drawerLayout.open()
        })
        homepageFragment=HomePageFragment()
        todoFragment=ToDoFragment()
        userprofilefragment=UserProfileFragment()
        pushFragment(homepageFragment)
        onItemClick()

    }
    fun onItemClick(){
        binding.navView.setNavigationItemSelectedListener {
            if (it.itemId == R.id.nav_home){
                //pushFragment(homepageFragment)
                val intent= Intent(this,MainActivity::class.java)
                startActivity(intent)
            }else if(it.itemId == R.id.nav_profile){
                pushFragment(userprofilefragment)
            }else if(it.itemId == R.id.nav_todo){
                pushFragment(todoFragment)
            }
            return@setNavigationItemSelectedListener true
        }
    }

    fun pushFragment(fragment:Fragment){

        supportFragmentManager.beginTransaction().replace(R.id.settings_and_categories_framLayout,fragment).commit()
        binding.drawerLayout.close()
    }


}