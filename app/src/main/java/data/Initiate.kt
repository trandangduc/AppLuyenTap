package data

import java.io.Serializable

data class Initiate(
    val BoPhan: String,
    var ID: Int,
    val TenBaiTap: String,
    var ThoiGian: Int,
    val Video: String?
) : Serializable
