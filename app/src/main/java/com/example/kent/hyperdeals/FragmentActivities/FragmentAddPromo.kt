package com.example.kent.hyperdeals.FragmentActivities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kent.hyperdeals.Adapters.PromoHomeBusinessman
import com.example.kent.hyperdeals.Adapters.PromoListAdapter
import com.example.kent.hyperdeals.Adapters.PromoModel
import com.example.kent.hyperdeals.Adapters.PromoModelBusinessman
import com.example.kent.hyperdeals.BusinessActivities.AddPromo
import com.example.kent.hyperdeals.FragmentActivities.FragmentProMap.TAG
import com.example.kent.hyperdeals.Model.HomeBusinessmanModel
import com.example.kent.hyperdeals.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_add_promo.*
import kotlinx.android.synthetic.main.fragmentpromaplist.*
import org.jetbrains.anko.support.v4.toast

class FragmentAddPromo : Fragment() {

    private var myDialog: Dialog? = null
    private var promohomeBusinessman = ArrayList<HomeBusinessmanModel>()
    private var mAdapter : PromoHomeBusinessman? = null
    private var mSelected: SparseBooleanArray = SparseBooleanArray()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_add_promo, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addPromoButton.setOnClickListener{
            val intent = Intent (this.context, AddPromo::class.java)
            startActivity(intent)
        }

        val database = FirebaseFirestore.getInstance()

        promohomeBusinessman = ArrayList()

        val layoutManager = LinearLayoutManager(context)
        recyclerViewHomeBusinessman.layoutManager = layoutManager
        recyclerViewHomeBusinessman.itemAnimator = DefaultItemAnimator()



        database.collection("HomeBusinessman").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (DocumentSnapshot in task.result) {
                    val upload = DocumentSnapshot.toObject(HomeBusinessmanModel::class.java)
                    Log.d(TAG, DocumentSnapshot.getId() + " => " + DocumentSnapshot.getData())
                    promohomeBusinessman.add(upload)
                    toast("success")

                    mAdapter = PromoHomeBusinessman (mSelected,promohomeBusinessman)
                    recyclerViewHomeBusinessman.adapter = mAdapter

                }

            } else
                toast("error")
        }

    }


    }