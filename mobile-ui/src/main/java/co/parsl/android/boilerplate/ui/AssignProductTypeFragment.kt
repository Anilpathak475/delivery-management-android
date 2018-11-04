package co.parsl.android.boilerplate.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.parsl.android.boilerplate.ui.adapter.AssignProductTypeAdapter
import co.parsl.android.boilerplate.ui.model.AssignProductType
import co.parsl.android.ui.R
import kotlinx.android.synthetic.main.fragment_assign_product_type.*

class AssignProductTypeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_assign_product_type, container, false)
        setAdapter()
        return view
    }

    private fun setAdapter() {
        val products: ArrayList<AssignProductType> = mutableListOf<AssignProductType>() as ArrayList<AssignProductType>
        val adapter = AssignProductTypeAdapter(products, this.context!!, object : AssignProductTypeAdapter.BtnClickListener {
            override fun onBtnClick(position: Int) {

            }
        })
        recycler_product_results.adapter = adapter
    }
}