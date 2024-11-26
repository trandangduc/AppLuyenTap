package data

import java.io.Serializable


data class Exercise(
    var ID: Int = 0,
    val BoPhan: String = "",
    val MucDo: String = "",
    var SoRep: Int = 0,
    val TenBaiTap: String= "",
    val Video: String?

) : Serializable
