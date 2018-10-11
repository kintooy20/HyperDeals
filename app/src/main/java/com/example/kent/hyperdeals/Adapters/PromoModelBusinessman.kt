package com.example.kent.hyperdeals.Adapters

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint

class PromoModelBusinessman (

        var promoimage: String,
        var promoStore: String,
        var promoContactNumber: String,
        var promodescription: String,
       /* var promoPlace:LatLng,*/
        var promoPlace:String,

        var promoname:String,
        var promoLatLng:String,
        var promoImageLink: String,


    var promoGeo:GeoPoint,
        var subsubTag:String,

        var viewed:Int,
        var sent:Int,
        var interested:Int,
        var dismissed:Int
)