package data

import java.io.Serializable

data class Stretching(
    val BoPhan: String,
    var ID: Int,
    val TenBaiTap: String,
    var ThoiGian: Int,
    val Video: String?
) : Serializable
