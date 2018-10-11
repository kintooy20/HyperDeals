package com.example.kent.hyperdeals

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivityBusinessMan : AppCompatActivity() {



    var mDatabaseReference: DatabaseReference? = null
    var mAuth: FirebaseAuth? = null
    var mDatabase: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registrationactivitybusinessman)

        val submit = findViewById<View>(R.id.Businessmanbtnsubmit) as Button

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()


        submit.setOnClickListener {


            val tvLastNameBM = findViewById<View>(R.id.Businessmanlastname) as EditText
            val tvFirstNameBM = findViewById<View>(R.id.Businessmanfirstname) as EditText
            val tvEmailBM = findViewById<View>(R.id.Businessmanemail) as EditText
            val tvPasswordBM = findViewById<View>(R.id.Businessmanpassword) as EditText
            val progressbarBM = findViewById<View>(R.id.progressbarBM) as ProgressBar


            var email = tvEmailBM.text.toString()
            var password = tvPasswordBM.text.toString()
            var lastname = tvLastNameBM.text.toString()
            var firstname = tvFirstNameBM.text.toString()


            if (!email.isEmpty() && !password.isEmpty() && !lastname.isEmpty() && !firstname.isEmpty()) {
                progressbarBM.visibility = View.VISIBLE

                mAuth!!.createUserWithEmailAndPassword(email!!, password!!)
                        .addOnCompleteListener(this) {task ->
                            progressbarBM.visibility = View.INVISIBLE

                            if (task.isSuccessful)
                            {
                                val userID = mAuth!!.currentUser!!.uid
                                val currentUserDb = mDatabaseReference!!.child(userID)
                                currentUserDb.child("firstName").setValue(firstname)
                                currentUserDb.child("lastName").setValue(lastname)
                                Toast.makeText(this, "Registered Success!", Toast.LENGTH_SHORT).show()

                                val intent = Intent(this,LoginActivityBusinessman::class.java)
                                startActivity(intent)

                            } else {
                                Toast.makeText(this,"Error!", Toast.LENGTH_SHORT).show()

                            }
                        }
            }
            else {
                Toast.makeText(this, "Enter credentials!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
