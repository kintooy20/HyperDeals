package com.example.kent.hyperdeals.Home

import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kent.hyperdeals.Adapters.PromoListAdapter
import com.example.kent.hyperdeals.Adapters.PromoModel
import com.example.kent.hyperdeals.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.notification_layout_row.view.*
import kotlinx.android.synthetic.main.zzhomelistitem.view.*

class HomeAdapter( private val selectedItem: SparseBooleanArray, private val promolist : ArrayList<PromoModel>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            HomeAdapter.ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.zzhomelistitem,parent,false))

    override fun getItemCount(): Int  = promolist.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val promos = promolist[position]


        Picasso.get()
                .load(promos.promoImageLink)
                .placeholder(R.drawable.hyperdealslogofinal)
                .into(holder.ivHomeImage)

        holder.tvHomeText.text = promos.promoname
        holder.tvHomeStore.text = promos.promoStore

    }


    inner class ViewHolder (view: View): RecyclerView.ViewHolder(view){
        val ivHomeImage = view.homePromoImage!!
        val tvHomeText = view.homePromoName!!
        val tvHomeStore = view.homePromoStore!!
    }
}