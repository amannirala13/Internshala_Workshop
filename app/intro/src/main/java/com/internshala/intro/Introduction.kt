package com.internshala.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class Introduction : AppCompatActivity() {

    private lateinit var closeBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)

        closeBtn = findViewById(R.id.intro_close_btn)

        closeBtn.setOnClickListener {
            try {
                startActivity(Intent(this, Class.forName("com.internshala.workshop.activities.MainActivity")))
            }catch(e: ClassNotFoundException){
                Toast.makeText(this, "${e.message}", Toast.LENGTH_LONG).show()
            }
        }

    }
}