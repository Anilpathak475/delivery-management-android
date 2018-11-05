package co.parsl.android.boilerplate.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import co.parsl.android.boilerplate.ui.model.AssignProductType
import co.parsl.android.ui.R

class AssignProductTypeAdapter constructor(var context: Context, btnlistener: BtnClickListener) : RecyclerView.Adapter<AssignProductTypeAdapter.CustomViewHolder>() {

    var assignProductTypes = mutableListOf<AssignProductType>()
    var mClickListener: BtnClickListener? = null

    init {
        this.mClickListener = btnlistener
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val productType = assignProductTypes[position]
        holder.nameText.text = productType.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.layout_item_product_type, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return assignProductTypes.size
    }


    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameText: TextView
        var imgOk: ImageView

        init {
            nameText = view.findViewById(R.id.text_name)
            imgOk = view.findViewById(R.id.img_ok)
            itemView.setOnClickListener {
                mClickListener!!.onBtnClick(adapterPosition)
                imgOk.background = context.getDrawable(R.drawable.ok)
            }
        }
    }

    interface BtnClickListener {
        fun onBtnClick(position: Int)
    }
}