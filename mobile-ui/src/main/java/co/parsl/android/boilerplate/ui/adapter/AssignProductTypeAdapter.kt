package co.parsl.android.boilerplate.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import co.parsl.android.boilerplate.remote.model.ProductCategory
import co.parsl.android.ui.R

class AssignProductTypeAdapter constructor(var context: Context, btnlistener: BtnClickListener) : RecyclerView.Adapter<AssignProductTypeAdapter.CustomViewHolder>() {

    var productCategorys = mutableListOf<ProductCategory>()
    var mClickListener: BtnClickListener? = null

    init {
        this.mClickListener = btnlistener
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val productType = productCategorys[position]
        holder.nameText.text = productType.name
        holder.itemView.setOnClickListener {
            mClickListener!!.onBtnClick(productType)
            holder.imgOk.background = context.getDrawable(R.drawable.ok)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.layout_item_product_type, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productCategorys.size
    }


    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameText: TextView
        var imgOk: ImageView

        init {
            nameText = view.findViewById(R.id.txt_name)
            imgOk = view.findViewById(R.id.img_ok)

        }
    }

    interface BtnClickListener {
        fun onBtnClick(productCategory: ProductCategory)
    }
}