package com.example.kent.hyperdeals.Adapters

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.firestore.GeoPoint
import java.io.Serializable


class    PromoModel() : Parcelable{
        var promoimage : String = ""
        var promoname : String = ""
        var promodescription: String = ""
        var promoPlace:String= ""
        var promoContactNumber: String= ""
        var promoCount: Int = 0
        var promoStore: String = ""
        var promoLatLng: String = ""
        var promoImageLink : String = ""

        var sent:Int = 0
        var viewed: Int = 0
        var interested: Int = 0
        var dismissed: Int = 0

        constructor(parcel: Parcel) : this() {
                promoimage = parcel.readString()
                promoname = parcel.readString()
                promodescription = parcel.readString()
                promoPlace = parcel.readString()
                promoContactNumber = parcel.readString()
                promoCount = parcel.readInt()
                promoStore = parcel.readString()
                promoLatLng = parcel.readString()
                promoImageLink = parcel.readString()

                sent = parcel.readInt()
                viewed = parcel.readInt()
                interested = parcel.readInt()
                dismissed = parcel.readInt()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(promoimage)
                parcel.writeString(promoname)
                parcel.writeString(promodescription)
                parcel.writeString(promoPlace)
                parcel.writeString(promoContactNumber)
                parcel.writeInt(promoCount)
                parcel.writeString(promoStore)
                parcel.writeString(promoLatLng)
                parcel.writeString(promoImageLink)

                parcel.writeInt(sent)
                parcel.writeInt(viewed)
                parcel.writeInt(interested)
                parcel.writeInt(dismissed)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<PromoModel> {
                override fun createFromParcel(parcel: Parcel): PromoModel {
                        return PromoModel(parcel)
                }

                override fun newArray(size: Int): Array<PromoModel?> {
                        return arrayOfNulls(size)
                }
        }

}

