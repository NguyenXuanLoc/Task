package com.example.task.ui.partner

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R

class PartnerAdapter(
    private var activity: Activity,
    private var partners: ArrayList<String>,
    private val itemClickListener: (String) -> Unit
) : RecyclerView.Adapter<PartnerAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        var view = LayoutInflater.from(activity).inflate(R.layout.item_partner, parent, false)
        return ItemHolder(view);
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        if (partners.size > 0) {
            var model = partners[position]
            holder.bind(model, position)
        }
    }


    override fun getItemCount(): Int {
        return partners.size;
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var lblPartner: TextView = itemView.findViewById(R.id.lblPartner)
        var cvPartner: CardView = itemView.findViewById(R.id.cvPartner)
        fun bind(ob: String, index: Int) {
            with(ob) {
                lblPartner.text = this
                if (index % 2 == 0) cvPartner.setBackgroundColor(activity.getColor(R.color.orange))
                else cvPartner.setBackgroundColor(
                    activity.getColor(R.color.yellow)
                )
            }
            cvPartner.setOnClickListener(View.OnClickListener {
                itemClickListener(ob)
            })
        }
    }

}