package com.example.kent.hyperdeals

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.kent.hyperdeals.NavigationBar.DashboardActivity
import com.example.kent.hyperdeals.NavigationBar.DrawerActivityBusinessman
import com.google.firebase.auth.FirebaseAuth

class LoginActivityBusinessman : AppCompatActivity() {


    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginactivitybusinessman)



        val login = findViewById<View>(R.id.BusinessmanloginButton)

        login.setOnClickListener{

            val loginEmail = findViewById<View>(R.id.BusinessmanloginEmail) as EditText
            val loginPassword = findViewById<View>(R.id.BusinessmanloginPassword) as EditText
            val loginProgressBar = findViewById<View>(R.id.loginProgressBarBM) as ProgressBar

            var LoginEmail = loginEmail.text.toString()
            var LoginPassword = loginPassword.text.toString()

            mAuth = FirebaseAuth.getInstance()

            if (!LoginEmail.isEmpty() && !LoginPassword.isEmpty()) {
                loginProgressBar.visibility = View.VISIBLE

                mAuth!!.signInWithEmailAndPassword(LoginEmail, LoginPassword)
                        .addOnCompleteListener(this) { task ->
                            loginProgressBar.visibility = View.INVISIBLE

                            if (task.isSuccessful){
                                Toast.makeText(this,"Login Successful!", Toast.LENGTH_SHORT).show()

                                val intent = Intent(this, DrawerActivityBusinessman::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this,"Login Failure", Toast.LENGTH_SHORT).show()

                            }

                        }
            }

            else {
                Toast.makeText(this,"Enter necessary credentials", Toast.LENGTH_SHORT).show()

            }
        }


    }
}
