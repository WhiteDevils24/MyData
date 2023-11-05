package com.infinitelearning.mydata

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.infinitelearning.mydata.data.entity.User

class UserDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_data)

        val toolbarFormData: Toolbar = findViewById(R.id.tb_dataPengguna)
        setSupportActionBar(toolbarFormData)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val imageData = intent.getByteArrayExtra("imageData")

        // Find the ImageView in your layout
        val userImageView = findViewById<ImageView>(R.id.img_imageOutput)

        if (imageData != null) {
            // If image data is available, decode it into a Bitmap and set it in the ImageView
            val imageBitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
            userImageView.setImageBitmap(imageBitmap)
        } else {
            // If image data is not provided, you can set a default image or hide the ImageView.
            userImageView.setImageResource(R.drawable.black_image_24)
        }

        val nik = intent.getStringExtra("nik")
        val namaLengkap = intent.getStringExtra("namaLengkap")
        val nomorHandphone = intent.getStringExtra("nomorHandphone")
        val jenisKelamin = intent.getStringExtra("jenisKelamin")
        val tanggalLahir = intent.getStringExtra("tanggalLahir")
        val alamat = intent.getStringExtra("alamat")

        val nikTextView = findViewById<TextView>(R.id.tv_nikOutput)
        val namaLengkapTextView = findViewById<TextView>(R.id.tv_namaLengkapOutput)
        val nomorHandphoneTextView = findViewById<TextView>(R.id.tv_nomerHandphoneOutput)
        val jenisKelaminTextView = findViewById<TextView>(R.id.tv_jenisKelaminOutput)
        val tanggalLahirTextView = findViewById<TextView>(R.id.tv_tanggalLahirOutput)
        val alamatTextView = findViewById<TextView>(R.id.tv_alamatOutput)

        nikTextView.text = nik
        namaLengkapTextView.text = namaLengkap
        nomorHandphoneTextView.text = nomorHandphone
        jenisKelaminTextView.text = jenisKelamin
        tanggalLahirTextView.text = tanggalLahir
        alamatTextView.text = alamat

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}
