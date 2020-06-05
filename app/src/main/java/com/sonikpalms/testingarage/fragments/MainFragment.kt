package com.sonikpalms.testingarage.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sonikpalms.testingarage.R
import com.sonikpalms.testingarage.adapter.AdapterContacts
import com.sonikpalms.testingarage.pojo.Contact
import com.sonikpalms.testingarage.sqllite.DatabaseHelper
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.toolbar_main_fragment.*

class MainFragment : Fragment() {

    val databaseHelper = this.context?.let { DatabaseHelper(it) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_main, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        refresh.setOnClickListener {
            context?.let { it1 -> databaseHelper?.dropDb(it1) }
            first()
            Toast.makeText(context, "dropDb", Toast.LENGTH_SHORT).show()
        }


    }
    @SuppressLint("WrongConstant")
    fun first(){
        if (databaseHelper?.proverka() == 0){
            val contact = Contact(R.drawable.avatar, "Igor", "Metlin", "Metlin@rambler.ru")
            val contact1 = Contact(R.drawable.avatar, "Alex", "Smith", "Smith@yandex.ru")
            val contact2 = Contact(R.drawable.avatar, "Johnny", "Williams", "Williams@gmail.ru")
            val contact3 = Contact(R.drawable.avatar, "Charlie", "Taylor", "Taylor@ukr.net")
            val contact4 = Contact(R.drawable.avatar, "Chloe", "Thompson", "Thompson@ukr.net")
            val contact5 = Contact(R.drawable.avatar, "Evie", "Lewis", "Lewis@ukr.net")


            databaseHelper?.insertData(contact)
            databaseHelper?.insertData(contact1)
            databaseHelper?.insertData(contact2)
            databaseHelper?.insertData(contact3)
            databaseHelper?.insertData(contact5)


            val list : ArrayList<Contact>  = ArrayList()
            list.add(contact)
            list.add(contact1)
            list.add(contact2)
            list.add(contact3)
            list.add(contact4)
            list.add(contact5)


            val mAdapter = AdapterContacts(list)
            //mAdapter.refreshContactList(list)
            rvContacts!!.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            rvContacts!!.adapter = mAdapter
        }else{
        /*    var data = databaseHelper.readData
            for (i in 0..(data.si))*/
        }

    }


}