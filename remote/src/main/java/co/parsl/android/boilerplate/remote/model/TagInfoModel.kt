package co.parsl.android.boilerplate.remote.model
/*
{
    "uniqueId": "2V4QK97b8pOd",
    "assignedUrl": null,
    "status": "TAG_ACTIVE",
    "internalId": 8,
    "manufacturerId": "nfc_factory",
    "manufacturerGeneratedUuid": "b419a7ba-8052-434e-854c-3451492e1936",
    "responsibleUser": "test1",
    "orderId": "Gkd97X18B5pb"
}
* */
class TagInfoModel(val uniqueId: String, val assignedUrl:String,

                   val status:String, val internalId: Long,

                   val manufacturerId: String, val manufacturerGeneratedUuid: String,

                   val responsibleUser: String, val orderId: String)