package com.example.kent.hyperdeals.FragmentActivities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kent.hyperdeals.Adapters.PromoListAdapter
import com.example.kent.hyperdeals.Adapters.PromoModel
import com.example.kent.hyperdeals.Adapters.PromoModelBusinessman
import com.example.kent.hyperdeals.Home.HomeAdapter
import com.example.kent.hyperdeals.R
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragmentcategory.*
import kotlinx.android.synthetic.main.fragmentpromaplist.*
import org.jetbrains.anko.support.v4.toast


class FragmentCategory: Fragment() {

    private var promolist1= ArrayList<PromoModelBusinessman>()
    private var mAdapter : HomeAdapter? = null
    private var mSelected: SparseBooleanArray = SparseBooleanArray()

    private var mFirebaseFirestore = FirebaseFirestore.getInstance()
    private var promolist = ArrayList<PromoModel>()
    var TAG = "Hyperdeals"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragmentcategory, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      /*  mFirebaseFirestore.collection("PromoDetails").document(promos.promoStore).update(KEY_VIEWED,promos.viewed+5)*/

      val image =  mFirebaseFirestore.collection("PromoDetails").document("Bench").collection("promoImageLink").get()
        val text = mFirebaseFirestore.collection("PromoDetails").document("Bench").collection("promoname")


        promolist = ArrayList()

        val database = FirebaseFirestore.getInstance()

        val layoutManager = LinearLayoutManager(context)
        my_recycler_view111.layoutManager = layoutManager
        my_recycler_view111.itemAnimator = DefaultItemAnimator()


        database.collection("PromoDetails").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (DocumentSnapshot in task.result) {
                    val upload = DocumentSnapshot.toObject(PromoModel::class.java)
                    Log.d(TAG, DocumentSnapshot.getId() + " => " + DocumentSnapshot.getData())
                    promolist.add(upload)
                    toast("success")

                    mAdapter = HomeAdapter(mSelected, promolist)
                    my_recycler_view111.adapter = mAdapter

                }

            } else
                toast("error")
        }


      /*  Picasso.get()
                .load(image)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageHome)

        tvHome.text = text.toString()*/


      /*  Picasso.get()
                .load(promos.promoImageLink)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.ivPromoImage)*/



    }
}







