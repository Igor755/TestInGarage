package com.sonikpalms.testingarage.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sonikpalms.testingarage.R
import com.sonikpalms.testingarage.adapter.AdapterContacts
import com.sonikpalms.testingarage.pojo.Contact
import com.sonikpalms.testingarage.sqllite.DatabaseHelper
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.toolbar_main_fragment.*

class MainFragment : Fragment() {


    private var databaseHelper = this.context?.let { DatabaseHelper(it) }
    var mAdapter = AdapterContacts(emptyArray<Contact>().toMutableList())

    companion object {
        val listContacts: ArrayList<Contact> = ArrayList()
        var recyclerView: RecyclerView? = null

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return    inflater.inflate(R.layout.fragment_main, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView  =  view.findViewById(R.id.rvContacts)

        databaseHelper = context?.let { DatabaseHelper(it) }

        first()

        refresh.setOnClickListener {
            context?.let { it1 -> databaseHelper?.dropDb(it1) }
            listContacts.clear()
            first()
            Toast.makeText(context, "dropDb", Toast.LENGTH_SHORT).show()
        }

        mAdapter = AdapterContacts(listContacts)
        mAdapter.onItemClickListener = { pos, _ ->
            val fragment: DialogFragment = DetailFragment()
            val bundle = Bundle()
            val contact: Contact = listContacts[pos]
            bundle.putSerializable("contact", contact)
            bundle.putInt("position", pos)
            fragment.arguments = bundle
            childFragmentManager.let { fragment.show(it, "DetailFragment") }

        }
        recyclerView?.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        recyclerView?.adapter = mAdapter


    }

    fun first() {
        if (databaseHelper?.proverka() == 0) {

            val contact = Contact(R.drawable.avatar, "Igor", "Metlin", "Metlin@rambler.ru")
            val contact1 = Contact(R.drawable.avatar, "Alex", "Smith", "Smith@yandex.ru")
            val contact2 = Contact(R.drawable.avatar, "Johnny", "Williams", "Williams@gmail.ru")
            val contact3 = Contact(R.drawable.avatar, "Charlie", "Taylor", "Taylor@ukr.net")
            val contact4 = Contact(R.drawable.avatar, "Chloe", "Thompson", "Thompson@ukr.net")
            val contact5 = Contact(R.drawable.avatar, "Evie", "Lewis", "Lewis@ukr.net")


            databaseHelper!!.insertData(contact)
            databaseHelper!!.insertData(contact1)
            databaseHelper!!.insertData(contact2)
            databaseHelper!!.insertData(contact3)
            databaseHelper!!.insertData(contact4)
            databaseHelper!!.insertData(contact5)


            listContacts.add(contact)
            listContacts.add(contact1)
            listContacts.add(contact2)
            listContacts.add(contact3)
            listContacts.add(contact4)
            listContacts.add(contact5)
            mAdapter.notifyDataSetChanged()


        } else {
            val data = databaseHelper?.readData()
            if (data != null) {
                for (i in 0 until data.size) {
                    listContacts.add(data[i])
                }
                
                mAdapter.notifyDataSetChanged()
            }
        }

    }


    fun update(pos: Int, contact: Contact){
        listContacts[pos] = contact
        mAdapter.list = listContacts
        recyclerView?.adapter?.notifyItemChanged(pos, contact)

    }

}