package com.example.kent.hyperdeals.FragmentActivities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.util.SparseBooleanArray
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.example.kent.hyperdeals.Adapters.PromoListAdapter
import com.example.kent.hyperdeals.Adapters.PromoModel
import com.example.kent.hyperdeals.Adapters.PromoModelBusinessman

import com.example.kent.hyperdeals.FragmentActivities.FragmentProMap.TAG
import com.example.kent.hyperdeals.Interface.RecyclerTouchListener
import com.example.kent.hyperdeals.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.*
import com.google.firebase.firestore.Transaction
import com.squareup.picasso.Picasso
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.dialogbox.*
import kotlinx.android.synthetic.main.fragmentpromaplist.*
import kotlinx.android.synthetic.main.notification_layout_row.*
import org.jetbrains.anko.image
import org.jetbrains.anko.imageURI
import org.jetbrains.anko.support.v4.toast
import java.io.File

class FragmentProMapList: Fragment() {



    private var myDialog: Dialog? = null

    private var promolist = ArrayList<PromoModel>()
    private var promolist1= ArrayList<PromoModelBusinessman>()
    private var mAdapter : PromoListAdapter? = null
    private var mSelected: SparseBooleanArray = SparseBooleanArray()
    private var mFirebaseFirestore = FirebaseFirestore.getInstance()


    val KEY_SENT = "sent"
    val KEY_VIEWED= "viewed"
    val KEY_INTERESTED = "interested"

    var TAG = "Hyperdeals"

    companion object {

        const val KEY = "asdad"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragmentpromaplist,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        promolist = ArrayList()

        val database = FirebaseFirestore.getInstance()

        val layoutManager = LinearLayoutManager(context)
        recyclerViewProMapList.layoutManager = layoutManager
        recyclerViewProMapList.itemAnimator = DefaultItemAnimator()


        database.collection("PromoDetails").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (DocumentSnapshot in task.result) {
                    val upload = DocumentSnapshot.toObject(PromoModel::class.java)
                    Log.d(TAG, DocumentSnapshot.getId() + " => " + DocumentSnapshot.getData())
                    promolist.add(upload)
                    toast("success")

                    mAdapter = PromoListAdapter(mSelected, promolist)
                    recyclerViewProMapList.adapter = mAdapter

                }

            } else
                toast("error")
        }

      /*   val email: String
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var documentReference: DocumentReference = db.collection("EmailUID").document(email)
        for ()*/

        recyclerViewProMapList.addOnItemTouchListener(RecyclerTouchListener(this.context!!.applicationContext, object : RecyclerTouchListener.ClickListener {
            override fun onClick(view: View, position: Int) {

                val fragmentContainer = specificPromoContainer
                val bundle = Bundle()
                val promos = promolist[position]

                val transaction : Transaction?=null

                val db = FirebaseFirestore.getInstance()
                val promoDetailsReference = db.collection("PromoDetails").document(promos.promoStore)

                db.runTransaction({
                    val snapshot = it.get(promoDetailsReference)
                    val updatedView = snapshot.getDouble(KEY_SENT) + 1
                    it.update(promoDetailsReference,KEY_SENT,updatedView)

                    //PLEASE CHECK IF NIGANA BA NI - KENT MODIFIED
                 /*   Handler().postDelayed({
                    }, 5000)
*/


                }).addOnSuccessListener {
                    toast("Nice")


                }.addOnFailureListener{
                    toast("Failed")
                }

                db.runTransaction({
                    val snapshott = it.get(promoDetailsReference)
                    val updatedVieww = snapshott.getDouble(KEY_VIEWED) + 1
                    it.update(promoDetailsReference,KEY_VIEWED,updatedVieww)


                }).addOnSuccessListener {
                    toast("Nice")
                }.addOnFailureListener{
                    toast("Failed")
                }





           /*     mFirebaseFirestore.collection("PromoDetails").document(promos.promoStore).update(KEY_VIEWED,promos.viewed+5)
                toast("Nice")*/



                myDialog = Dialog(activity)
                myDialog?.setContentView(R.layout.dialogbox)


                Picasso.get()
                        .load(promos.promoImageLink)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(myDialog!!.promoPicture)


                myDialog!!.promoNAME.text = promolist[position].promoname
                myDialog!!.promoDESCRIPTION.text = promolist[position].promodescription
                myDialog!!.promoLOCATION.text = promolist[position].promoPlace
                myDialog!!.promoNUMBER.text = promolist[position].promoContactNumber
                myDialog!!.promoSTORE.text = promolist[position].promoStore

            //Inflating of images in Dialog builder: Work in Progress Kent

           /*   myDialog!!.promoPicture.set(promolist[position].promoImageLink)*/

              /*  myDialog?.close!!.setOnClickListener {
                    myDialog?.dismiss()

                }
*/

                myDialog?.call!!.setOnClickListener {
                    val callIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + promolist[position].promoContactNumber))
                    startActivity(callIntent)
                }

                    myDialog?.map?.setOnClickListener {
                        val gmmIntentUri = Uri.parse("google.navigation:q="+ promolist[position].promoLatLng)
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.`package` = "com.google.android.apps.maps"
                        startActivity(mapIntent)
                    }

                        myDialog?.interested?.setOnClickListener {
                            Toast.makeText(activity, "Thank you for your feedback.", Toast.LENGTH_SHORT).show()


                            db.runTransaction({
                                val snapshot = it.get(promoDetailsReference)
                                val updatedView = snapshot.getDouble(KEY_INTERESTED) + 5
                                it.update(promoDetailsReference,KEY_INTERESTED,updatedView)


                            }).addOnSuccessListener {
                                toast("Nice")

//  interested!!.visibility = View.GONE


                            }.addOnFailureListener{
                                toast("Failed")
                            }


                        /* mFirebaseFirestore.collection("Reach").document().set(reach.interested + 10) */

                            myDialog?.dismiss()

                        }

                            myDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            myDialog?.window?.setGravity(Gravity.CENTER)
                            myDialog?.show()

                            /*   myDialog?.close!!.setOnClickListener {
                    myDialog?.dismiss()

                }

                myDialog?.call!!.setOnClickListener{
                    val callIntent = Intent (Intent.ACTION_CALL, Uri.parse("tel:" + "09154638674"))
                    startActivity(callIntent)
                }


                myDialog?.map?.setOnClickListener{
                    val gmmIntentUri = Uri.parse("google.navigation:q=10.2804,123.8818")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.`package` = "com.google.android.apps.maps"
                    startActivity(mapIntent)
                }

                myDialog?.interested?.setOnClickListener{
                    Toast.makeText(activity,"Thank you for your feedback.", Toast.LENGTH_SHORT).show()
                    myDialog?.dismiss()

                }

                myDialog?.window?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
                myDialog?.window?.setGravity(Gravity.CENTER)
                myDialog?.show()


            }


        }))*/

                        }


    }))
    }

    }
