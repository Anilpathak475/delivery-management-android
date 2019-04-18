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


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SelectProductFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SelectProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SelectProductFragment : Fragment() {
    val parslService: ParslService = BufferooServiceFactory.makeParslService(BuildConfig.DEBUG)

    lateinit var adapter: AssignProductTypeAdapter
    lateinit var activityInstance: Activity
    private lateinit var navController: NavController
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.select_product_catogries, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        setAdapter()
        val productCategoriesCode = SelectProductFragmentArgs.fromBundle(arguments).productCategoriesCode
        getProductsByCategoryId(productCategoriesCode)
        activityInstance = this.activity!!
    }


    private fun getProductsByCategoryId(productCategoriesCode: String) {
        val call: Call<ProductContent> = parslService.getProductsByCatogries(productCategoriesCode, getToken())

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

        adapter = AssignProductTypeAdapter(this.context!!, object : AssignProductTypeAdapter.BtnClickListener {
            override fun onBtnClick(productCategory: ProductCategory) {
                val action: SelectProductFragmentDirections.ActionSelectProductFragmentToSelectProductBatch = SelectProductFragmentDirections.actionSelectProductFragmentToSelectProductBatch(productCategory.code)
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
