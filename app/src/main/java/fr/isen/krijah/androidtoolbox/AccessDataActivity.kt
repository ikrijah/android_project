package fr.isen.krijah.androidtoolbox

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.ContactsContract
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_access_data.*

class AccessDataActivity : AppCompatActivity() {

    lateinit var locationManager: LocationManager
    private var hasGps = false
    private var hasNetwork = false

    val contactList: MutableList<ContactDTO> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_access_data)


        location()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS) ==
                PackageManager.PERMISSION_DENIED
            ) {
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_CONTACTS);
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CONTACT_CODE);
            } else {
                //permission already granted
                contactFonction()
            }
        } else {
            contactFonction()
        }


        listView.layoutManager = LinearLayoutManager(this)

        listView.adapter = NomsAdapter(contactList, this)

        cameraView.setOnClickListener {
            withItems()
        }
    }




    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;

        private val PERMISSION_CONTACT_CODE = 1002;

        private const val CAMERA_PICK_REQUEST = 4444

        private val PERMISSION_ID = 42;
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Granted. Start getting the location information
            }
        }

        if (requestCode == PERMISSION_CONTACT_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Granted. Start getting the contacts information
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            cameraView.setImageURI(data?.data)
        } else if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_PICK_REQUEST) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            cameraView.setImageBitmap(imageBitmap)
        }
    }


    fun imageFromGallery() {
        val intent: Intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    fun imageFromCamera() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                //permission denied
                val permissions = arrayOf(Manifest.permission.CAMERA)
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE)
            } else {
                //permission already granted
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    takePictureIntent.resolveActivity(packageManager)?.also {
                        startActivityForResult(takePictureIntent, CAMERA_PICK_REQUEST)
                    }

                }
            }
        } else {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(takePictureIntent, CAMERA_PICK_REQUEST)
                }
            }
        }
    }


    private val backButtonClick = { _: DialogInterface, _: Int ->
        Toast.makeText(applicationContext, "Aucune photo choisie", Toast.LENGTH_SHORT).show()
    }

    fun withItems() {

        val items = arrayOf("Prendre une photo", "Galerie")
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Choisir : ")
            setItems(items) { _, which ->
                if (items[which] == "Prendre une photo") {
                    imageFromCamera()
                } else {
                    imageFromGallery()
                }
            }
            setPositiveButton("Retour", backButtonClick)
            show()
        }
    }


    fun contactFonction() {

        val cursorN = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        cursorN?.let { cursor ->
            while (cursor.moveToNext()) {
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val number =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                contactList.add(ContactDTO(name, number))
            }
            cursor.close()
        }
    }


    fun location() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_DENIED
            ) {
                //permission denied
                val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CONTACT_CODE)
            } else {
                //permission already granted
                getLocation()
            }
        } else {
            getLocation()
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasGps || hasNetwork) {
            Log.d("CodeAndroidLocation", "hasGps")

            val locationListener : LocationListener = object: LocationListener {
                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                }

                override fun onProviderEnabled(provider: String?) {

                }

                override fun onProviderDisabled(provider: String?) {

                }

                override fun onLocationChanged(location: Location?) {
                    if (location!=null){
                        findViewById<TextView>(R.id.latTextView).text = location.latitude.toString()
                        findViewById<TextView>(R.id.lonTextView).text = location.longitude.toString()
                    }
                }
            }

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,0F,locationListener)

                val localGpsLocation= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (localGpsLocation != null){
                    findViewById<TextView>(R.id.latTextView).text = localGpsLocation.latitude.toString()
                    findViewById<TextView>(R.id.lonTextView).text = localGpsLocation.longitude.toString()
                }
        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }


}


