package data

import java.io.Serializable

data class ExerciseKhamPha(
    var ID: Int? = null,
    var SoRep: String? = null,
    val TenBaiTap: String? = null,
    val ThoiGian: String? = null,
    val Video: String? = null

) : Serializable
