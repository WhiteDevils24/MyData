package com.infinitelearning.mydata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnInformasi: Button = findViewById(R.id.btn_informasi)
        btnInformasi.setOnClickListener(this)

        val btnFormData: Button = findViewById(R.id.btn_formData)
        btnFormData.setOnClickListener(this)

        val btnLihatData: Button = findViewById(R.id.btn_lihatData)
        btnLihatData.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.btn_informasi->{
                val intentInformasi = Intent(this@MainActivity,InformasiActivity::class.java)
                startActivity(intentInformasi)
            }
            R.id.btn_formData->{
                val intentFormData = Intent(this@MainActivity,FormDataActivity::class.java)
                startActivity(intentFormData)
            }
            R.id.btn_lihatData->{
                val intentLihatData = Intent(this@MainActivity, LihatDataActivity::class.java)
                startActivity(intentLihatData)
            }
        }

    }


}