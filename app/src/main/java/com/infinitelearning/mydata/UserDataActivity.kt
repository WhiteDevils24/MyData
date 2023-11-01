package com.infinitelearning.mydata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        val user = intent.getSerializableExtra("user") as? User

        val nik = findViewById<TextView>(R.id.tv_nikOutput)
        val namaLengkap = findViewById<TextView>(R.id.tv_namaLengkapOutput)
        val nomorHanphone = findViewById<TextView>(R.id.tv_nomerHandphone)
        val jenisKelamin = findViewById<TextView>(R.id.tv_jenisKelaminOutput)
        val tanggalLahir= findViewById<TextView>(R.id.tv_tanggalLahirOutput)
        val alamat = findViewById<TextView>(R.id.tv_alamatOutput)

        if (user != null) {
            nik.text = user.nik
            namaLengkap.text = user.namaLengkap
            nomorHanphone.text = user.nomorHandphone
            jenisKelamin.text = user.jenisKelamin
            tanggalLahir.text = user.tanggalLahir
            alamat.text = user.alamat
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}
