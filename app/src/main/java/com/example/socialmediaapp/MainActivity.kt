package com.example.socialmediaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.socialmediaapp.databinding.ActivityMainBinding
import com.example.socialmediaapp.fragments.Home
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //getting profile img from register acctivity
//        val framentManager = supportFragmentManager
//        val fragmentTransaction = framentManager.beginTransaction()
//        val data = intent.getStringExtra("profileImg")
//        val bundle = Bundle()
//        val fragment = Home()
//        bundle.putString("profileImg",data)
//        fragmentTransaction.add(binding.bottomNavigationView.id,fragment).commit()



        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        val navController = navHostFragment!!.findNavController()

        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_menu)
        binding.bottomNavigationView.setupWithNavController(navController)


    }
}