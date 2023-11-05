package com.infinitelearning.mydata

import android.Manifest
import android.app.DatePickerDialog
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Geocoder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.textfield.TextInputLayout
import com.infinitelearning.mydata.data.AppDatabase
import com.infinitelearning.mydata.data.entity.User
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class FormDataActivity : AppCompatActivity() {

    private val PICK_IMAGE= 1
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var database: AppDatabase

    private var imageFilePath: String? = null
    private lateinit var imgAddImage: ImageView
    private lateinit var tilNIK: TextInputLayout
    private lateinit var tilNamaLengkap: TextInputLayout
    private lateinit var tilNomorHandphone: TextInputLayout
    private lateinit var rgJenisKelamin: RadioGroup
    private lateinit var tilTanggalLahir: TextInputLayout
    private lateinit var tilAlamat: TextInputLayout


    @RequiresApi(34)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_data)

        imgAddImage = findViewById(R.id.img_imageAdd)
        tilNIK = findViewById(R.id.til_NIK)
        tilNamaLengkap = findViewById(R.id.til_namaLengkap)
        tilNomorHandphone = findViewById(R.id.til_nomorHanphone)
        rgJenisKelamin = findViewById(R.id.rg_jenisKelamin)
        tilTanggalLahir = findViewById(R.id.til_tanggalLahir)
        tilAlamat = findViewById(R.id.til_alamat)

        database = AppDatabase.getInstance(applicationContext)

        val toolbarFormData: Toolbar = findViewById(R.id.tb_formData)
        setSupportActionBar(toolbarFormData)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val btnaddImage: Button = findViewById(R.id.btn_pilihGambar)
        btnaddImage.setOnClickListener{
            openGallery()
        }

        val btnDatePicker: Button = findViewById(R.id.btn_pilihTanggal)
        btnDatePicker.setOnClickListener{
            showDataPicker()
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val btnMapPick: Button = findViewById(R.id.btn_pilihAlamat)
        btnMapPick.setOnClickListener{
            if (checkLocationPermisson()){
                getLocation()
            }
        }

        val btnSave: Button = findViewById(R.id.btn_simpan)
        btnSave.setOnClickListener {
            if (imageFilePath != null &&
                tilNIK.editText?.text?.isNotEmpty() == true &&
                tilNamaLengkap.editText?.text?.isNotEmpty() == true &&
                tilNomorHandphone.editText?.text?.isNotEmpty() == true &&
                rgJenisKelamin.checkedRadioButtonId != -1 &&
                tilTanggalLahir.editText?.text?.isNotEmpty() == true &&
                tilAlamat.editText?.text?.isNotEmpty() == true) {

                val selectedRadioButton = findViewById<RadioButton>(rgJenisKelamin.checkedRadioButtonId)
                val selectedRadioButtonText = selectedRadioButton.text.toString()

                if (intent?.hasExtra("id") == true) {
                    // Edit existing data
                    val id = intent.getIntExtra("id", 0)
                    val user = User(
                        id,
                        tilNIK.editText!!.text.toString(),
                        tilNamaLengkap.editText!!.text.toString(),
                        tilNomorHandphone.editText!!.text.toString(),
                        selectedRadioButtonText,
                        tilTanggalLahir.editText!!.text.toString(),
                        tilAlamat.editText!!.text.toString(),
                        imageFilePath
                    )

                    database.userDao().update(user)
                } else {
                    // Add new data
                    val user = User(
                        null,
                        tilNIK.editText!!.text.toString(),
                        tilNamaLengkap.editText!!.text.toString(),
                        tilNomorHandphone.editText!!.text.toString(),
                        selectedRadioButtonText,
                        tilTanggalLahir.editText!!.text.toString(),
                        tilAlamat.editText!!.text.toString(),
                        imageFilePath
                    )

                    database.userDao().insertAll(user)
                }

                finish()
                Toast.makeText(applicationContext, "Data Berhasil Di Simpan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Data Tidak Lengkap. Mohon Isi Data Dengan Lengkap", Toast.LENGTH_SHORT).show()
            }
        }

        val intentData = intent.extras
        if (intentData != null && intentData.containsKey("id")) {
            // Editing existing data
            val id = intentData.getInt("id", 0)
            val user = database.userDao().get(id)

            // Populate the form with existing data
            Glide.with(this)
                .load(user.imagePath)
                .into(imgAddImage)
            tilNIK.editText?.setText(user.nik)
            tilNamaLengkap.editText?.setText(user.namaLengkap)
            tilNomorHandphone.editText?.setText(user.nomorHandphone)
            when (user.jenisKelamin) {
                "Male" -> rgJenisKelamin.check(R.id.rb_kelaminPria)
                "Female" -> rgJenisKelamin.check(R.id.rb_kelaminWanita)
            }
            tilTanggalLahir.editText?.setText(user.tanggalLahir)
            tilAlamat.editText?.setText(user.alamat)
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            val selectedImageUri = data.data
            imgAddImage.setImageURI(selectedImageUri)

            // Save the image to internal storage and get the file path
            imageFilePath = selectedImageUri?.let { saveImageToInternalStorage(it) }

            // Display the selected image
            imgAddImage.setImageURI(selectedImageUri)

        }

    }

    //Pick image from Gallery
    private fun openGallery() {
        val galleryIntent = Intent (
            Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, PICK_IMAGE)
    }
    private fun saveImageToInternalStorage(imageUri: Uri): String? {
        // Create a directory for image storage
        val directory = File(filesDir, "images")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        // Generate a unique file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_$timeStamp.jpg"

        // Copy the image to the internal storage
        val destFile = File(directory, imageFileName)
        contentResolver.openInputStream(imageUri)?.use { input ->
            FileOutputStream(destFile).use { output ->
                input.copyTo(output)
            }
        }

        return destFile.absolutePath
    }
    //DatePick Function
    //Add data Date to Edit Text
    private fun showDataPicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = android.icu.util.Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                updateDateEditText(selectedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }
    private fun updateDateEditText(calendar: android.icu.util.Calendar) {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        tilTanggalLahir.editText?.setText(dateFormat.format(calendar.time))
    }

    //Location Permission
    //Get Location Access and add to EditText
    private fun checkLocationPermisson(): Boolean {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        val granted = PackageManager.PERMISSION_GRANTED
        if (ContextCompat.checkSelfPermission(this,permission)!=granted){
            ActivityCompat.requestPermissions(this, arrayOf(permission),1)
            return false
        }
        return true
    }
    @RequiresApi(34)
    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: android.location.Location? ->
            location?.let {
                val latitude = it.latitude
                val longitude = it.longitude
                val geocoder = Geocoder(this, Locale.getDefault())
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)

                if (addresses != null) {
                    if (addresses.isNotEmpty()) {
                        val address = addresses[0]
                        val addressText = address?.getAddressLine(0)
                        tilAlamat.editText?.setText(addressText)
                    } else {
                        tilAlamat.editText?.setText("Address not found")
                    }
                }
            }
        }

    }
    









}


