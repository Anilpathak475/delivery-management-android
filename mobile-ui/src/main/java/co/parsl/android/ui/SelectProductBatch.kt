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
import co.parsl.android.boilerplate.ui.widget.ConformationPopUp
import co.parsl.android.boilerplate.ui.widget.DialogCallback
import co.parsl.android.boilerplate.ui.widget.NotifyPopUp
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
 * [SelectProductBatch.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SelectProductBatch.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SelectProductBatch : Fragment() {
    val parslService: ParslService = BufferooServiceFactory.makeParslService(BuildConfig.DEBUG)
    private lateinit var navController: NavController

    lateinit var adapter: AssignProductTypeAdapter
    lateinit var activityInstance: Activity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.select_product_catogries, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityInstance = this.activity!!
        setAdapter()
        navController = Navigation.findNavController(view)

        val productCode = SelectProductBatchArgs.fromBundle(arguments).productCode
        getCategories(productCode)

        /* edt_product_type.addTextChangedListener(object : TextWatcher {
             override fun afterTextChanged(p0: Editable?) {
                 TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
             }

             override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                 TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
             }

             override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
              //   updateAdapter(edt_product_type.text.toString())
             }
         })*/
    }


    private fun getCategories(productCode: String) {
        val call: Call<ProductContent> = parslService.getBatchesByProduct(productCode, getToken())

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
                ConformationPopUp(activityInstance, 0, object : DialogCallback {
                    override fun onYes() {
                        NotifyPopUp(activityInstance, R.drawable.ok, object : DialogCallback {
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


    private fun getToken(): String {
        val idToken = MainActivity.readSharedSetting(activity!!.applicationContext, MainActivity.PREF_USER_ID_TOKEN, "")
        return "Bearer $idToken"
    }

    fun updateAdapter(products: ArrayList<ProductCategory>) {
        adapter.productCategorys = products
        adapter.notifyDataSetChanged()
    }
}
