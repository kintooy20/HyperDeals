package com.example.kent.hyperdeals

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kent.hyperdeals.Adapters.PromoModel
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_promo__detail.*

class  Promo_Detail : AppCompatActivity() {

    val TAG:String="Promyo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promo__detail)

        val sharedPreferences = getSharedPreferences("userInfo", android.content.Context.MODE_PRIVATE)
        val key = sharedPreferences.getString("key","")

        val db = FirebaseFirestore.getInstance()
        db.collection("PromoDetails").document(key)
                .get().addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        var  retrieveDetail= documentSnapshot.toObject(PromoModel::class.java)!!
                        Picasso.get()
                                .load(retrieveDetail.promoImageLink)
                                .placeholder(R.mipmap.ic_launcher)
                                .into(imagePromo)
                        tvPromoter.text=retrieveDetail.promoStore
                        tvPromoDescription.text=retrieveDetail.promodescription
                        tvTitle.text=retrieveDetail.promoname
                        tvPromoLocation.text=retrieveDetail.promoPlace
                    } else {
                        log("Failed to retrieve")
                    }
                }




    }


    fun log(message:String){

        Log.d(TAG,message)

    }
}
