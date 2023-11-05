package com.infinitelearning.mydata

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.infinitelearning.mydata.adapter.UserAdapter
import com.infinitelearning.mydata.data.AppDatabase
import com.infinitelearning.mydata.data.entity.User

class LihatDataActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var list= mutableListOf<User>()
    private lateinit var adapter: UserAdapter
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lihat_data)

        val toolbarFormData: Toolbar = findViewById(R.id.tb_lihatData)
        setSupportActionBar(toolbarFormData)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.rv_listData)

        database = AppDatabase.getInstance(applicationContext)
        adapter = UserAdapter(list)

        adapter.setDialog(object: UserAdapter.Dialog{
            override fun onHoldClick(position: Int) {

                adapter.setDialog(object : UserAdapter.Dialog {
                    override fun onHoldClick(position: Int) {
                        val dialog = AlertDialog.Builder(this@LihatDataActivity)
                        dialog.setTitle(list[position].namaLengkap)
                        dialog.setItems(R.array.item_options, DialogInterface.OnClickListener { dialog, which ->
                            if (which == 0) {
                                // Change
                                val intent = Intent(this@LihatDataActivity, FormDataActivity::class.java)
                                intent.putExtra("id", list[position].uid)
                                startActivity(intent)
                            } else if (which == 1) {
                                // Delete
                                database.userDao().delete(list[position])
                                list.removeAt(position) // Remove from the local list
                                adapter.notifyItemRemoved(position) // Update the RecyclerView
                            } else {
                                dialog.dismiss()
                            }
                        })
                        val dialogView = dialog.create()
                        dialogView.show()
                    }
                })

            }

        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, VERTICAL))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume(){
        super.onResume()
        getData()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getData(){
        list.clear()
        list.addAll(database.userDao().getAll())
        adapter.notifyDataSetChanged()
    }
}