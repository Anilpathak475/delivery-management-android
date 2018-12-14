package co.parsl.android.ui

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.parsl.android.boilerplate.remote.BufferooServiceFactory
import co.parsl.android.boilerplate.remote.ParslService
import co.parsl.android.boilerplate.ui.adapter.AssignProductTypeAdapter
import co.parsl.android.boilerplate.ui.model.AssignProductType
import co.parsl.android.boilerplate.ui.widget.ConformationPopUp
import co.parsl.android.boilerplate.ui.widget.DialogCallback
import co.parsl.android.boilerplate.ui.widget.NotifyPopUp
import kotlinx.android.synthetic.main.select_product_catogries.*

class SelectProductCategories : Fragment() {

    val parslService: ParslService = BufferooServiceFactory.makeParslService(BuildConfig.DEBUG)

    lateinit var adapter: AssignProductTypeAdapter
    lateinit var activityInstance :Activity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.select_product_catogries, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        activityInstance = this.activity!!
        edt_product_type.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                updateAdapter(edt_product_type.text.toString())
            }
        })
    }

    private fun setAdapter() {

        adapter = AssignProductTypeAdapter(this.context!!, object : AssignProductTypeAdapter.BtnClickListener {
            override fun onBtnClick(position: Int) {
                ConformationPopUp(activityInstance, 0,object :DialogCallback{
                    override fun onYes() {
                        NotifyPopUp(activityInstance, R.drawable.ok,object :DialogCallback{
                            override fun onNO() {
                            }

                            override fun onYes() {
                            }
                        })
                    }

                    override fun onNO() {
                    }
                })
            }
        })
        recycler_product_results.adapter = adapter
    }


    fun updateAdapter(text: String) {
        val products = mutableListOf<AssignProductType>()
        products[0] = AssignProductType("$text 1")
        products[1] = AssignProductType("$text 2")
        products[2] = AssignProductType("$text 3")
        products[3] = AssignProductType("$text 4")
        products[4] = AssignProductType("$text 5")
        products[5] = AssignProductType("$text 6")
        adapter.assignProductTypes = products
    }
}