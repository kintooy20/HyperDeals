package com.example.kent.hyperdeals

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.kent.hyperdeals.NavigationBar.DashboardActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
companion object{

    var userUIDS=""
}

    private val TAG = "LoginActivity"
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginactivity)



        val login = findViewById<View>(R.id.loginButton)

        login.setOnClickListener{

            val loginEmail = findViewById<View>(R.id.loginEmail) as EditText
            val loginPassword = findViewById<View>(R.id.loginPassword) as EditText
            val loginButton = findViewById<View>(R.id.loginButton) as Button
            val loginForgotPassword = findViewById<View>(R.id.loginForgotPassword) as TextView
            val loginProgressBar = findViewById<View>(R.id.loginProgressBar) as ProgressBar

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
                                getUserUID(LoginEmail)
                                val intent = Intent(this, DashboardActivity::class.java)
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
    private fun getUserUID(email:String){
        var db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var documentReference: DocumentReference = db.collection("EmailUID").document(email)
        documentReference.get().addOnCompleteListener(OnCompleteListener<DocumentSnapshot> { task ->
            if (task.isSuccessful) {

                val document = task.result

                if (document.exists()) {
                    Log.d(TAG, "documenet exist"+document.getString("userUID"))
                    userUIDS=document.getString("userUID")!!
                } else {
                    Log.d(TAG, "No such document")
                }
            } else {
                Log.d(TAG, "get failed with ", task.exception)
            }
        })
    }

}


