package com.internshala.workshop.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.internshala.workshop.fragments.ProfileFrag
import com.internshala.workshop.fragments.SettingsFrag
import com.internshala.workshop.R
import com.internshala.workshop.fragments.WorkshopFrag

/**
 * Application MainActivity. This activity holds the home screen and all other important fragments.
 *
 * @property bottomNav BottomNavigationView
 * @property activeFragment Fragment?
 * @property profileFrag ProfileFrag
 * @property workshopFrag WorkshopFrag
 * @property settingsFrag SettingsFrag
 */
class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private var activeFragment: Fragment? = null

    private val profileFrag = ProfileFrag()
    private val workshopFrag = WorkshopFrag()
    private val settingsFrag = SettingsFrag()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.main_bottom_nav)

        if(savedInstanceState == null)
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

    /**
     * Changes the active fragment
     *
     * @param fragment [Fragment]
     */
    private fun changeActiveFragment(fragment: Fragment){
        if(activeFragment != fragment){
            if(activeFragment != null)
                supportFragmentManager.beginTransaction().hide(activeFragment!!).commit()
            activeFragment = fragment
            supportFragmentManager.beginTransaction().replace(R.id.main_fragment_holder, activeFragment!!).commit()
        }
    }
}