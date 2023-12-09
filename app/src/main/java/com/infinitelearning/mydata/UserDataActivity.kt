package com.infinitelearning.mydata


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputLayout
import com.infinitelearning.mydata.data.AppDatabase
import com.infinitelearning.mydata.data.entity.User
import java.text.FieldPosition

class UserDataActivity : AppCompatActivity() {
    private lateinit var database: AppDatabase
    private var list = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_data)

        val toolbarFormData: Toolbar = findViewById(R.id.tb_dataPengguna)
        setSupportActionBar(toolbarFormData)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        database = AppDatabase.getInstance(applicationContext)

//        val editButton: Button = findViewById(R.id.btn_ubahData)
//        editButton.setOnClickListener{
//            val intent = Intent(this@UserDataActivity, FormDataActivity::class.java)
//            intent.putExtra("id", list[position].uid)
//            startActivity(intent)
//        }

//        val deleteData: Button = findViewById(R.id.btn_hapusData)
//        deleteData.setOnClickListener{
//            database.userDao().delete(list[position])
//        }


        val imagePath = intent.getStringExtra("imagePath")
        val nik = intent.getStringExtra("nik")
        val namaLengkap = intent.getStringExtra("namaLengkap")
        val nomorHandphone = intent.getStringExtra("nomorHandphone")
        val jenisKelamin = intent.getStringExtra("jenisKelamin")
        val tanggalLahir = intent.getStringExtra("tanggalLahir")
        val alamat = intent.getStringExtra("alamat")

        val imagePathView = findViewById<ImageView>(R.id.img_imageOutput)
        val nikTextView = findViewById<TextView>(R.id.tv_nikOutput)
        val namaLengkapTextView = findViewById<TextView>(R.id.tv_namaLengkapOutput)
        val nomorHandphoneTextView = findViewById<TextView>(R.id.tv_nomerHandphoneOutput)
        val jenisKelaminTextView = findViewById<TextView>(R.id.tv_jenisKelaminOutput)
        val tanggalLahirTextView = findViewById<TextView>(R.id.tv_tanggalLahirOutput)
        val alamatTextView = findViewById<TextView>(R.id.tv_alamatOutput)

        imagePathView.setImageURI(Uri.parse(imagePath))
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