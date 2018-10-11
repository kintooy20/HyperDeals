package com.example.kent.hyperdeals

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnregister.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

       homelogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }

        btnregisterbusinessman.setOnClickListener{
            val intent = Intent (this, RegistrationActivityBusinessMan::class.java)
            startActivity(intent)

        }

        btnloginasbusinessman.setOnClickListener{
            val intent = Intent (this,LoginActivityBusinessman::class.java)
            startActivity(intent)
        }
    }
}
