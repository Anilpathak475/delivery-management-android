package co.parsl.android.boilerplate.remote

import co.parsl.android.boilerplate.remote.model.TagInfoModel
import co.parsl.android.boilerplate.remote.model.TagInfoPojo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ParslService {

    @GET("secured/tags/{tagId}")
    fun getTagInfo(
            @Path("tagId") tagId : String,
            @Header("Authorization") token : String
    ) : Call<TagInfoPojo>

    data class GetTagInfoResponse(val tagInfo: TagInfoModel)
}

//Get products - Select Product
//Get product batches by product
////Product batch assign
