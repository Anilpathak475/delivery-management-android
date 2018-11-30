package co.parsl.android.boilerplate.remote

import co.parsl.android.boilerplate.remote.model.Ledger
import co.parsl.android.boilerplate.remote.model.TagInfoModel
import co.parsl.android.boilerplate.remote.model.TagInfoPojo
import retrofit2.Call
import retrofit2.http.*

interface ParslService {

    @GET("secured/tags/{tagId}")
    fun getTagInfo(
            @Path("tagId") tagId : String,
            @Header("Authorization") token : String
    ) : Call<TagInfoPojo>

    data class GetTagInfoResponse(val tagInfo: TagInfoModel)

    @GET("secured/tags/{tagId}/ledger")
    fun getLedger(@Path("tagId") tagId : String,
                      @Header("Authorization") token : String) : Call<Ledger>

    /*
    {
    "elements": [
        {
            "user": "4b8357bb-f817-4546-a61a-0f9cfbcfc9e6",
            "transactionTs": "2018-11-28 11:43:45.796",
            "action": "TAKEOVER"
        },
        {
            "user": "4b8357bb-f817-4546-a61a-0f9cfbcfc9e6",
            "transactionTs": "2018-11-28 11:43:45.823",
            "action": "HANDOVER_SEND"
        }
    ],
    "totalPages": 1,
    "totalElements": 2
    }
     */

    @POST("secured/tags/{tagId}/handover")
    fun postHandoverAction(@Path("tagId") tagId : String,
                           @Query("action") handoverAction: String,
                           @Header("Authorization") token : String) : Call<Void>


}

// EditText TagId
// Token Shared Prefs

//Buttons
//SEND
//RECIEVE
//SEND_CONFIRM
//RECEIVE_CONFIRM

//LEDGER

//TAKEOVER, HANDOVER_SEND

//Get products - Select Product
//Get product batches by product
////Product batch assign
