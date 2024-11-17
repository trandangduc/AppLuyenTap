package data

import java.io.Serializable

data class Exercise(
    val name: String,
    val reps: String,       // Số lần hoặc số set
    val time: String,       // Thời gian
    val videoRes: Int?      // Tài nguyên video (có thể là null)
) : Serializable
