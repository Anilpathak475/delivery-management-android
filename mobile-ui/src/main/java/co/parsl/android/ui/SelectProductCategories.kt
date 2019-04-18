package co.parsl.android.ui

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import co.parsl.android.boilerplate.remote.BufferooServiceFactory
import co.parsl.android.boilerplate.remote.ParslService
import co.parsl.android.boilerplate.remote.model.ProductCategory
import co.parsl.android.boilerplate.remote.model.ProductContent
import co.parsl.android.boilerplate.ui.adapter.AssignProductTypeAdapter
import co.parsl.android.boilerplate.ui.home.MainActivity
import kotlinx.android.synthetic.main.select_product_catogries.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectProductCategories : Fragment() {

    val parslService: ParslService = BufferooServiceFactory.makeParslService(BuildConfig.DEBUG)
    private lateinit var navController: NavController

    lateinit var adapter: AssignProductTypeAdapter
    lateinit var activityInstance: Activity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.select_product_catogries, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        activityInstance = this.activity!!
        setAdapter()
        getCategories()


    }


    private fun getCategories() {
        val call: Call<ProductContent> = parslService.getProductCategories(getToken())

        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Getting tag info...")
        progressDialog.show()

        call.enqueue(object : Callback<ProductContent> {
            override fun onFailure(call: Call<ProductContent>, t: Throwable) {
                progressDialog.hide()
                Log.d("Retrofit", "onFailure: ")
            }

            override fun onResponse(call: Call<ProductContent>, response: Response<ProductContent>) {
                progressDialog.hide()
                if (response.isSuccessful) {
                    updateAdapter(response.body()!!.content)
                }
            }

        })

    }

    private fun setAdapter() {

        adapter = AssignProductTypeAdapter(activityInstance, object : AssignProductTypeAdapter.BtnClickListener {
            override fun onBtnClick(productCategory: ProductCategory) {
                val action: SelectProductCategoriesDirections.ActionGetProductCategoriesToSelectProductFragment = SelectProductCategoriesDirections.actionGetProductCategoriesToSelectProductFragment(productCategory.code)
                navController.navigate(action)
            }
        })
        recycler_product_results.adapter = adapter
    }


    private fun getToken(): String {
        val idToken = MainActivity.readSharedSetting(activity!!.applicationContext, MainActivity.PREF_USER_ID_TOKEN, "")
        return "Bearer $idToken"
    }

    fun updateAdapter(products: ArrayList<ProductCategory>) {
        adapter.productCategorys = products
        adapter.notifyDataSetChanged()
    }
}