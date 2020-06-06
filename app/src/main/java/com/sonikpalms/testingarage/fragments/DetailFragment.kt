package com.sonikpalms.testingarage.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sonikpalms.testingarage.R
import com.sonikpalms.testingarage.adapter.AdapterContacts
import com.sonikpalms.testingarage.pojo.Contact
import com.sonikpalms.testingarage.sqllite.DatabaseHelper
import kotlinx.android.synthetic.main.fragment_dialog_change_contact.*


class DetailFragment : DialogFragment() {

    private var databaseHelper = this.context?.let { DatabaseHelper(it) }
    private val newList: ArrayList<Contact> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_change_contact, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cancel.setOnClickListener { dismiss() }




        val contact: Contact
        val bundle: Bundle? = arguments
        contact = bundle?.getSerializable("contact") as Contact
        val position: Int = bundle.getInt("position")

        Glide.with(view.context).load(contact.user_image).into(photo_user)
        edit_name.setText(contact.user_name)
        edit_last_name.setText(contact.last_name)
        edit_email.setText(contact.email)

        save.setOnClickListener {
            contact.user_name = edit_name.text.toString()
            contact.last_name = edit_last_name.text.toString()
            contact.email = edit_email.text.toString()

            databaseHelper = context?.let { DatabaseHelper(it) }

            databaseHelper?.updateData(contact.user_id, contact.user_image, contact.user_name, contact.last_name, contact.email)

            MainFragment.listContacts.clear()

            val data = databaseHelper?.readData()
            if (data != null) {
                for (i in 0 until data.size) {
                    MainFragment.listContacts.add(data[i])
                }
            }
            MainFragment().update(position, contact)
            dismiss()

        }


    }


}