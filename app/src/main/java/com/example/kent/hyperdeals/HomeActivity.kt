package com.example.kent.hyperdeals

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TabHost
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.homeactivity.*
import org.w3c.dom.Text
import kotlinx.android.synthetic.main.expandablelistview.*


class HomeActivity : AppCompatActivity() {

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private var tvName: TextView? = null
    private var tvEmail: Text? = null


    private var tabhost:TabHost?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homeactivity)


        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()


        val mTabHost = findViewById<TabHost>(R.id.tabHost)
        mTabHost.setup()
        mTabHost.addTab(mTabHost.newTabSpec("Category").setIndicator("Category", null).setContent(R.id.Category))
        mTabHost.addTab(mTabHost.newTabSpec("Promap").setIndicator("Promap", null).setContent(R.id.Promap))
        mTabHost.addTab(mTabHost.newTabSpec("Settings").setIndicator("History", null).setContent(R.id.History))


        val listHeader = listOf("Jeans" , "Computers")

        val jeansList = listOf("Denim" , "Ripped" , "Formal" , "Slacks")
        val brandList = listOf("Acer" , "Lenovo" , "SONY" , "Dell")

        val listChild = HashMap<String,List<String>>()
        listChild.put(listHeader[0],jeansList)
        listChild.put(listHeader[1],brandList)

/*        val expandableListAdapter = ExpandableListViewActivity(this,listHeader,listChild)*//*

        expandableListView.setAdapter(expandableListAdapter)*/

        Promap.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
        val progressbar = findViewById<View>(R.id.progressBarHome) as ProgressBar


        logout.setOnClickListener{

            mAuth!!.signOut()

            progressbar.visibility = View.VISIBLE

            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)

            progressbar.visibility = View.INVISIBLE
        }
    }




         override fun onStart() {
        super.onStart()

             val Email = findViewById<View>(R.id.displayEmail) as TextView
             val firstname = findViewById<View>(R.id.displayName) as TextView

             val mUser = mAuth!!.currentUser
             val mUserData = mDatabaseReference!!.child(mUser!!.uid)

           Email!!.text = mUser.email

             mUserData.addValueEventListener(object:ValueEventListener{
                 override fun onDataChange(p0: DataSnapshot?) {
                    firstname!!.text = p0?.child("firstName")?.value as String

                 }
                 override fun onCancelled(p0: DatabaseError?) {

                 }


             })




    }



}
