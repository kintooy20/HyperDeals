package com.example.kent.hyperdeals.Model

import android.os.Parcel
import android.os.Parcelable
import android.net.Uri


import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.firestore.GeoPoint
import java.io.Serializable


class    HomeBusinessmanModel() : Parcelable {
    var PromoHomeBusinessman : String = ""


    constructor(parcel: Parcel) : this() {
        PromoHomeBusinessman = parcel.readString()

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(PromoHomeBusinessman)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HomeBusinessmanModel> {
        override fun createFromParcel(parcel: Parcel): HomeBusinessmanModel {
            return HomeBusinessmanModel(parcel)
        }

        override fun newArray(size: Int): Array<HomeBusinessmanModel?> {
            return arrayOfNulls(size)
        }
    }

}

