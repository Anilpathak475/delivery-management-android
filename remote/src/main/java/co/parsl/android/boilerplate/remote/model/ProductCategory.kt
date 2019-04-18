package co.parsl.android.boilerplate.remote.model

data class ProductCategory constructor(val code: String,
                                       val name: String,
                                       val metaInfoType: String,
                                       val description: String,
                                       var editable: Boolean,
                                       val deletable: Boolean)