package com.sonikpalms.testingarage.adapter

import android.os.Build
import android.view.*
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.sonikpalms.testingarage.R
import com.sonikpalms.testingarage.pojo.Contact
import kotlinx.android.synthetic.main.one_item_contact.view.*

class AdapterContacts (var list: MutableList<Contact>) : RecyclerView.Adapter<AdapterContacts.RecyclerViewHolder>() {

    var onItemClickListener: ((pos: Int, aView: View) -> Unit)? = null
    private var contactList : List<Contact> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.one_item_contact,parent,false))
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


    inner class RecyclerViewHolder(v: View) : RecyclerView.ViewHolder(v) , View.OnClickListener {

        private var view : View = v
        private var contact : Contact? = null

        init {
            v.setOnClickListener(this)
        }
        override fun onClick(v: View) {
            onItemClickListener?.invoke(adapterPosition, v)
        }

        fun bind(contact: Contact) {
            this.contact = contact

            view.pointMenu.setOnClickListener {


                val popup: PopupMenu
                val wrapper = ContextThemeWrapper(view.pointMenu.context, R.style.popupMenu)
                popup = PopupMenu(wrapper, view.pointMenu)



                popup.setOnMenuItemClickListener { item: MenuItem? ->
                    when (item?.itemId) {
                        R.id.delete -> {
                           /* val dialog   =  OfferAcceptDialog()
                            val activity =  itemView.context as? MainActivity
                            activity?.supportFragmentManager?.let { it1 -> dialog.show(it1, "OfferAcceptDialog") }*/
                        }
                        R.id.detail -> {
                            /*//TODO ("just test my auto dialog fragment, delete in future")
                            //val dialog   =  OfferMyAuto()
                            val activity =  itemView.context as? MainActivity
                            //activity?.supportFragmentManager?.let { it1 -> dialog.show(it1, "OfferMyAuto") }

                            val resultragment = OfferScreenFragment()
                            val transaction = activity?.supportFragmentManager?.beginTransaction()
                            transaction?.replace(R.id.fragment_container, resultragment)
                            transaction?.commit()*/
                        }


                    }
                    true
                }
               // popup.inflate(R.menu.popup_offer_menu)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    popup.gravity = Gravity.END
                }


                popup.show()

            }
        }
    }

}





