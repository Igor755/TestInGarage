package com.sonikpalms.testingarage.adapter

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sonikpalms.testingarage.MainActivity
import com.sonikpalms.testingarage.R
import com.sonikpalms.testingarage.fragments.DetailFragment
import com.sonikpalms.testingarage.fragments.MainFragment
import com.sonikpalms.testingarage.pojo.Contact
import com.sonikpalms.testingarage.sqllite.DatabaseHelper
import kotlinx.android.synthetic.main.one_item_contact.view.*

class AdapterContacts(var list: MutableList<Contact>) :
    RecyclerView.Adapter<AdapterContacts.RecyclerViewHolder>() {

    var onItemClickListener: ((pos: Int, aView: View) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.one_item_contact, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    fun refreshContactList(list: MutableList<Contact>) {
        this.list = list
        notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val contact: Contact = list[position]
        holder.bind(contact)
    }


    inner class RecyclerViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view: View = v
        private var contact: Contact? = null

        private var user_image: ImageView? = null
        private var user_name: TextView? = null
        private var last_name: TextView? = null
        private var email: TextView? = null


        init {
            v.setOnClickListener(this)
            user_image = itemView.findViewById(R.id.image)
            user_name = itemView.findViewById(R.id.name)
            last_name = itemView.findViewById(R.id.lastName)
            email = itemView.findViewById(R.id.email)

        }

        override fun onClick(v: View) {
            onItemClickListener?.invoke(adapterPosition, v)
        }

        fun bind(contact: Contact) {
            this.contact = contact


            user_name?.text = contact.user_name
            last_name?.text = contact.last_name
            email?.text = contact.email
            Glide.with(view.context).load(contact.user_image).into(view.image)



            view.pointMenu.setOnClickListener {

                val popup: PopupMenu
                val wrapper = ContextThemeWrapper(view.pointMenu.context, R.style.popupMenu)
                popup = PopupMenu(wrapper, view.pointMenu)
                popup.setOnMenuItemClickListener { item: MenuItem? ->
                    when (item?.itemId) {
                        R.id.delete -> {
                            val databaseHelper = itemView.context?.let { DatabaseHelper(it) }
                            databaseHelper?.deleteData(contact.user_id)
                            val data = databaseHelper?.readData()
                            list.clear()
                            if (data != null) {
                                for (i in 0 until data.size) {
                                    list.add(data[i])
                                }
                            }
                            refreshContactList(list)
                        }

                    }
                    true
                }
                popup.inflate(R.menu.popup_offer_menu)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    popup.gravity = Gravity.END
                }
                popup.show()

            }
        }
    }

}





