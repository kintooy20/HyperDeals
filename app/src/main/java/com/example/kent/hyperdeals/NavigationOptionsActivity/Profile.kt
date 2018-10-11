package com.example.kent.hyperdeals.NavigationOptionsActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.example.kent.hyperdeals.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

import kotlinx.android.synthetic.main.navigationprofile.*

class Profile : AppCompatActivity() {

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigationprofile)


        toolbar.title = getString(R.string.hyper_profile)

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)

        toolbar.setNavigationOnClickListener({
            toolbar.setOnClickListener{
                finish()
            }

        })
    }


    override fun onStart() {
        super.onStart()

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()

        val name = findViewById<TextView>(R.id.tvNavName)
        val email = findViewById<TextView>(R.id.tvNavEmail)

        val mUser = mAuth!!.currentUser
        val mUserData = mDatabaseReference!!.child(mUser!!.uid)

        email!!.text = mUser.email

        mUserData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot?) {
                name!!.text = p0?.child("firstName")?.value as String

            }

            override fun onCancelled(p0: DatabaseError?) {

            }


        })


    }

}
