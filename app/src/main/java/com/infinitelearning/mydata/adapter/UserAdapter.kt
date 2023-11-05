package com.infinitelearning.mydata.adapter

import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.infinitelearning.mydata.R
import com.infinitelearning.mydata.UserDataActivity
import com.infinitelearning.mydata.data.entity.User
import org.w3c.dom.Text

class UserAdapter(var list: List<User>): RecyclerView.Adapter<UserAdapter.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var imagePath: ImageView
        var nik: TextView
        var namaLengkap: TextView
        var nomorHanphone: TextView
        var jenisKelamin: TextView
        var tanggalLahir: TextView
        var alamat: TextView


        init {
            imagePath = view.findViewById(R.id.img_imageOutput)
            nik = view.findViewById(R.id.tv_nikOutput)
            namaLengkap = view.findViewById(R.id.tv_namaLengkapOutput)
            nomorHanphone = view.findViewById(R.id.tv_nomerHandphoneOutput)
            jenisKelamin = view.findViewById(R.id.tv_jenisKelaminOutput)
            tanggalLahir = view.findViewById(R.id.tv_tanggalLahirOutput)
            alamat = view.findViewById(R.id.tv_alamatOutput)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_view_lihat_data, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = list[position]

        val imagePath = user.imagePath
        val imageBitmap = BitmapFactory.decodeFile(imagePath)



        holder.nik.text = user.nik
        holder.namaLengkap.text = user.namaLengkap
        holder.nomorHanphone.text = user.nomorHandphone
        holder.jenisKelamin.text = user.jenisKelamin
        holder.tanggalLahir.text = user.tanggalLahir
        holder.alamat.text = user.alamat
        holder.imagePath.setImageBitmap(imageBitmap)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, UserDataActivity::class.java)
            intent.putExtra("imagePath", user.imagePath)
            intent.putExtra("nik",user.nik)
            intent.putExtra("namaLengkap",user.namaLengkap)
            intent.putExtra("nomorHandphone",user.nomorHandphone)
            intent.putExtra("jenisKelamin", user.jenisKelamin)
            intent.putExtra("tanggalLahir", user.tanggalLahir)
            intent.putExtra("alamat",user.alamat)


            holder.itemView.context.startActivity(intent)

        }

    }


}