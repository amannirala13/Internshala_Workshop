package com.internshala.workshop.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.internshala.workshop.fragments.ProfileFrag
import com.internshala.workshop.fragments.SettingsFrag
import com.internshala.workshop.R
import com.internshala.workshop.fragments.WorkshopFrag

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private var activeFragment: Fragment? = null

    val profileFrag = ProfileFrag()
    val workshopFrag = WorkshopFrag()
    val settingsFrag = SettingsFrag()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.main_bottom_nav)

        changeActiveFragment(profileFrag)

        bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.main_bottom_nav_home -> changeActiveFragment(profileFrag)
                R.id.main_bottom_nav_workshop -> changeActiveFragment(workshopFrag)
                R.id.main_bottom_nav_settings -> changeActiveFragment(settingsFrag)
            }
            true
        }

    }

    private fun changeActiveFragment(fragment: Fragment){
        if(activeFragment != fragment){
            if(activeFragment != null)
                supportFragmentManager.beginTransaction().hide(activeFragment!!).commit()
            activeFragment = fragment
            supportFragmentManager.beginTransaction().replace(R.id.main_fragment_holder, activeFragment!!).commit()
        }
    }
}