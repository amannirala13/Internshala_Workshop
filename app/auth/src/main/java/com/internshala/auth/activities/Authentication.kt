package com.internshala.auth.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.internshala.auth.R
import com.internshala.auth.fragments.RegisterFrag
import com.internshala.auth.fragments.SignInFrag
import com.internshala.utils.isValidUser

class Authentication : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var closeBtn: ImageButton

    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        
        closeBtn = findViewById(R.id.auth_close_btn)


        closeBtn.setOnClickListener{
            finish()
        }
        
        val signInFragment = SignInFrag()
        val registerFragment = RegisterFrag()

        changeActiveFragment(signInFragment)

        bottomNav = findViewById(R.id.bottom_nav)
        bottomNav.setOnNavigationItemSelectedListener{item->
            when(item.itemId){
                R.id.bottom_nav_sign_in ->{
                   changeActiveFragment(signInFragment) }
                R.id.bottom_nav_register ->{
                    changeActiveFragment(registerFragment) }
            }
            true
        }
    }

    private fun changeActiveFragment(fragment: Fragment){
        if(activeFragment != fragment){
            if(activeFragment != null)
                supportFragmentManager.beginTransaction().hide(activeFragment!!).commit()
            activeFragment = fragment
            supportFragmentManager.beginTransaction().replace(R.id.auth_fragment_holder, activeFragment!!).commit()
        }
    }
}