package co.parsl.android.boilerplate.remote

import co.parsl.android.boilerplate.remote.model.Ledger
import co.parsl.android.boilerplate.remote.model.ProductContent
import co.parsl.android.boilerplate.remote.model.TagInfoModel
import co.parsl.android.boilerplate.remote.model.TagInfoPojo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ParslService {

    @GET("secured/tags/{tagId}")
    fun getTagInfo(
            @Path("tagId") tagId: String,
            @Header("Authorization") token: String
    ): Call<TagInfoPojo>

    data class GetTagInfoResponse(val tagInfo: TagInfoModel)

    @GET("secured/tags/{tagId}/ledger")
    fun getLedger(@Path("tagId") tagId: String,
                  @Header("Authorization") token: String): Call<Ledger>

    @GET("secured/product_categories/")
    fun getProductCategories(@Header("Authorization") token: String): Call<ProductContent>

    @GET("secured/product_categories/{productCategoryCode}/products")
    fun getProductsByCatogries(@Path("productCategoryCode") productCategoryCode: String,
                               @Header("Authorization") token: String): Call<ProductContent>

    @GET("secured/products/{productCode}/batches")
    fun getBatchesByProduct(@Path("productCode") productCode: String,
                            @Header("Authorization") token: String): Call<ProductContent>

    @POST("secured/tags/{tagId}/handover?action={handoverAction}")
    fun postHandoverAction(@Path("tagId") tagId : String,
                           @Path("handoverAction") handoverAction: String,
                           @Header("Authorization") token : String) : Call<Void>


}
