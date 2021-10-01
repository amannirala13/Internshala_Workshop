package com.internshala.workshop.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.internshala.auth.activities.Authentication
import com.internshala.intro.Introduction
import com.internshala.workshop.R
import com.internshala.utils.isFirstRun
import com.internshala.utils.isValidUser
import android.view.WindowInsetsController

import android.view.WindowInsets
import android.view.WindowManager

/**
 * Splash screen:
 *
 * The first screen of the activity. Its main purpose is branding and doing some initial
 * task before the application could start its services. However, in this application is just for
 * branding.
 *
 * @constructor
 */
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

    }

    override fun onStart() {
        super.onStart()
        /**
         * Adding a delay of 2.5s for better experience
         */
        Handler(Looper.getMainLooper()).postDelayed({
            endSplashScreen()
        }, 2500) //Change this value to change the splashscreen duration
    }

    /**
     * This function contains the logic that decides which activity the application should navigate
     * to once the splash-screen ends
     *
     * @return [Unit]
     */
    private fun endSplashScreen(){
        if(isFirstRun(this)){
          // Show info activity and other initial one time details e.g. Intro video or page
            startActivity(Intent(this, Introduction::class.java))
            finish()
        }
        else{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}