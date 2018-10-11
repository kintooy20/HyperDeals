package com.example.kent.hyperdeals.BusinessActivities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.kent.hyperdeals.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.zaddpromobusinessman.*
import android.webkit.MimeTypeMap
import android.content.ContentResolver
import com.example.kent.hyperdeals.Adapters.PromoModel
import com.example.kent.hyperdeals.Adapters.PromoModelBusinessman
import com.google.android.gms.common.api.Status
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.fragment_add_promo.*
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.*
import kotlinx.android.synthetic.main.zaddpromobusinessman.view.*
import org.jetbrains.anko.toast
import java.util.*
import android.support.annotation.NonNull
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.example.kent.hyperdeals.FragmentActivities.FragmentAddPromo
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.google.android.gms.drive.Metadata
import com.google.android.gms.location.places.PlaceBuffer
import com.google.android.gms.location.places.Places
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.GeoPoint
import kotlinx.android.synthetic.main.dialogbox.*


class AddPromo : AppCompatActivity() {

    private var mImageLink : UploadTask.TaskSnapshot?=null
    var ref=FirebaseDatabase.getInstance().getReference("Geofences")
    var geoFire: GeoFire=GeoFire(ref)
    var  myGeolocation:GeoLocation= GeoLocation(2.2,2.2)
    val PICK_IMAGE_REQUEST = 11
    var imageUri: Uri?=null
    val place:PlaceSelectionListener?=null
    private var mStorage: FirebaseStorage?=null
    private var mStorageReference : StorageReference?=null
    private var mFirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zaddpromobusinessman)

        var subsubCategory = arrayListOf("Boots","Formal Shoes","Sneakers","Bomber Jackets","Lightweight Jackets","Jeans","Joggers")

       var adapter =ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,subsubCategory)
        subsubTag.setAdapter(adapter)
        addPromoImage.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Image"),  PICK_IMAGE_REQUEST)
        }

        addPromoPublish.setOnClickListener{
            storeDatatoFirestore()

        finish()

        }

        set.setOnClickListener{
            uploadFile()

        }

        val autocompleteFragment = fragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as PlaceAutocompleteFragment

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {


                val getlat= place.latLng.latitude.toString()
                val getlong = place.latLng.longitude.toString()

                val getPlaceName = place.name.toString()

                addPromoLocation.setText(getPlaceName)

                addPromoPlace.setText(getlat + "," + getlong)
            myGeolocation = GeoLocation(place.latLng.latitude,place.latLng.longitude)
                toast("Success")
            }

            override fun onError(status: Status) {
                toast("Error")
            }
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, dataa: Intent?) {
        super.onActivityResult(requestCode, resultCode, dataa)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && dataa != null){
            imageUri = dataa.data

            Picasso.get().load(imageUri).into(addPromoImage)


        }
    }

    private fun getFileExtension(uri: Uri): String? {
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }

    private fun uploadFile() {

        mStorage = FirebaseStorage.getInstance()
        mStorageReference = mStorage!!.reference

        if (imageUri!=null){
           val ref = mStorageReference!!.child("images/" + UUID.randomUUID().toString())
            ref.putFile(imageUri!!)
                    .addOnSuccessListener {

                            addPromoProgressBar.visibility = View.VISIBLE

                        val image = it.downloadUrl.toString()

                        addPromoImageLink.setText(image)

                        toast("Image Uploaded Successfully")

                        addPromoProgressBar.visibility = View.INVISIBLE

                    }
                    .addOnFailureListener{
                        toast("Uploading Failed")
                    }

        }




    }
private fun addGeofence(key:String,location:GeoLocation){
        geoFire.setLocation(key,location, GeoFire.CompletionListener { key, error ->

            Log.d("HyperDeals",key)


        })


}

    private fun storeDatatoFirestore(){


        val place:Place?=null

        addPromoProgressBar.visibility = View.VISIBLE
        Log.d("HyperDeals",subsubTag.text.toString())
        val pStore = addPromoStore.text.toString()
        val pContact = addPromoContact.text.toString()
        val pName = addPromoName.text.toString()
        val pDescription = addPromoDescription.text.toString()
        val pLatLng = addPromoPlace.text.toString()
        val pPromoPlace = addPromoLocation.text.toString()
        val pPromoImageLink = addPromoImageLink.text.toString()


        val pEntity = PromoModelBusinessman(downloadImageLink().toString(),pStore,pContact,pDescription,pPromoPlace,pName,pLatLng,pPromoImageLink,
                GeoPoint(myGeolocation.latitude,myGeolocation.longitude),subsubTag.text.toString(),0,0,0,0)



        mFirebaseFirestore.collection("PromoDetails").document(pStore).set(pEntity)

        toast("Success")

        addPromoProgressBar.visibility = View.INVISIBLE
        Log.d("HyperDeals",myGeolocation.latitude.toString()+myGeolocation.longitude.toString())
        addGeofence(pStore,myGeolocation)

    }

    private fun downloadImageLink(){
            mStorageReference!!.child("images/").downloadUrl.addOnSuccessListener {


                mImageLink!!.downloadUrl

                addPromoImageLink.setText(mImageLink.toString())

            toast("Nice")

            }



    }



}

/*     val autocompleteFragment = fragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as PlaceAutocompleteFragment

     autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
         override fun onPlaceSelected(place: Place) {
             toast("Success")
         }

         override fun onError(status: Status) {
             toast("Error")
         }
     })

*/


