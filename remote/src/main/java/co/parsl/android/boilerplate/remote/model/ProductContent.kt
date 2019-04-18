package co.parsl.android.boilerplate.remote.model

data class ProductContent constructor(val content: ArrayList<ProductCategory>,
                                      val last: Boolean,
                                      val totalPages: Int,
                                      val totalElements: Int,
                                      val numberOfElements: Int)