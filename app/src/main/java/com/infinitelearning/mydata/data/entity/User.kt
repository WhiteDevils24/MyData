package com.infinitelearning.mydata.data.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey (autoGenerate = true) var uid: Int? = null,
    @ColumnInfo (name = "NIK") val nik: String?,
    @ColumnInfo (name = "nama_lengkap") val namaLengkap: String,
    @ColumnInfo (name = "nomor_handphone") val nomorHandphone: String,
    @ColumnInfo (name = "jenis-kelamin") val jenisKelamin: String,
    @ColumnInfo (name = "tanggal-lahir") val tanggalLahir: String,
    @ColumnInfo (name = "alamat") val alamat: String,
)


