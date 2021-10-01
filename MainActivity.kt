package com.example.roomdemo2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var db: CusDatabase? = null
    var id: Int = -1 //cusId
    lateinit var adapter: CustommerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
                applicationContext,
                CusDatabase::class.java, "cus-database"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

        initRecyclerView()
        getAllCustommer()
        handleEvents()
    }

    private fun handleEvents() {
        button_add.setOnClickListener {
            var name = edt_name.text.toString()
            var age = edt_age.text.toString()
            var _age = age.toInt()
            insertCustommer(name, _age)
        }

        button_delete.setOnClickListener {
            var cusDAO = db!!.callDAO()
            cusDAO.deleteCustommer(id)
            Toast.makeText(this@MainActivity, "Deleted 1 row !", Toast.LENGTH_SHORT).show()
            edt_name.setText("")
            edt_age.setText("")
            getAllCustommer()
        }

        button_update.setOnClickListener {
            var cusDAO = db!!.callDAO()
            cusDAO.updateCustommer(edt_name.text.toString(), edt_age.text.toString().toInt(), id)
            Toast.makeText(this@MainActivity, "Updated 1 row !", Toast.LENGTH_SHORT).show()
            edt_name.setText("")
            edt_age.setText("")
            getAllCustommer()
        }

        imageView_deleteAll.setOnClickListener {
            var cusDAO = db!!.callDAO()
            cusDAO.deleteAllCustommer()
            Toast.makeText(this@MainActivity, "Deleted All row !", Toast.LENGTH_SHORT).show()
            edt_name.setText("")
            edt_age.setText("")
            adapter.notifyDataSetChanged()
            getAllCustommer()
        }
    }

    private fun insertCustommer(name: String, _age: Int) {
        var cus = Custommer(cusId = null, cusName = name, cusAge = _age)

        val cusDAO = db!!.callDAO()
        cusDAO.insertCustommer(cus)

        Toast.makeText(this@MainActivity, "Added 1 custommer.", Toast.LENGTH_SHORT).show()

        edt_name.setText("")
        edt_age.setText("")
        edt_name.requestFocus()
        getAllCustommer()
    }

    private fun initRecyclerView() {
        recyclerView_custommers.setHasFixedSize(true)
        var layoutManager = LinearLayoutManager(this@MainActivity,
                LinearLayoutManager.VERTICAL, false)
        recyclerView_custommers.layoutManager = layoutManager
    }

    private fun getAllCustommer() {
        val cusDAO = db!!.callDAO()
        var custommerList: List<Custommer> = cusDAO.getAllCustommer()
        var arrayList_custommer = ArrayList<Custommer>()

        for (cus in custommerList) {
            arrayList_custommer.add(Custommer(cus.cusId, cus.cusName, cus.cusAge))
        }

        adapter = CustommerAdapter(arrayList_custommer)
        recyclerView_custommers.adapter = adapter

        adapter.setOnItemClickListener(object : CustommerAdapter.ClickListener {
            override fun onItemClick(position: Int, v: View) {
                var item = arrayList_custommer[position]
                id = item.cusId!!
                edt_name.text = Editable.Factory.getInstance().newEditable(item.cusName)
                edt_age.text = Editable.Factory.getInstance().newEditable(item.cusAge.toString())
            }
        })
    }
}