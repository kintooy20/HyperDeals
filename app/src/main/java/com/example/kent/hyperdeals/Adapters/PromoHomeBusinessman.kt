package com.example.kent.hyperdeals.Adapters

import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kent.hyperdeals.Model.HomeBusinessmanModel
import com.example.kent.hyperdeals.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.zzlayouthomebusinessman.view.*

class PromoHomeBusinessman (private val selectedItem: SparseBooleanArray, private val promoImageBusinessman : ArrayList<HomeBusinessmanModel>) : RecyclerView.Adapter<PromoHomeBusinessman.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):

            ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.zzlayouthomebusinessman,parent,false))

    override fun getItemCount(): Int = promoImageBusinessman.size


    override fun onBindViewHolder(holder:ViewHolder, position: Int) {

        val promoImageHomeB = promoImageBusinessman[position]

        Picasso.get()
                .load(promoImageHomeB.PromoHomeBusinessman)
                .placeholder(R.drawable.hyperdealslogofinal)
                .into(holder.homePromoimagebusinessman)


    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val homePromoimagebusinessman = view.homePromoImageBusinessman!!
    }


}