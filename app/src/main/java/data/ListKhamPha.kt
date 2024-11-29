    package data

    data class ListKhamPha(
        var ID: Int? = 0,
        var IDKhamPha : Int? = 0,
        val Ten: String? = null,
        val ListBaiTapKhamPha: List<Int>? = null
    )
