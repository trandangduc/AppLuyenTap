package com.example.appluyentap

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import data.Exercise
import data.Initiate
import data.Stretching

class ExerciseAdapter(
    private val mainExercises: List<Exercise>,
    private val stretchingExercises: List<Stretching>,
    private val warmupExercises: List<Initiate>
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    // Kết hợp tất cả các bài tập thành một danh sách duy nhất
    private val allExercises = mutableListOf<Any>().apply {
        addAll(mainExercises)
        addAll(stretchingExercises)
        addAll(warmupExercises)
    }

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.exerciseName)
        val repsTextView: TextView = itemView.findViewById(R.id.exerciseReps)
        val videoView: VideoView = itemView.findViewById(R.id.exerciseVideo)

        // Hàm dừng phát video
        fun stopVideo() {
            if (videoView.isPlaying) {
                videoView.stopPlayback()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exercise_item, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = allExercises[position]

        // Xử lý hiển thị tên bài tập và số rep/thời gian dựa trên kiểu dữ liệu
        when (exercise) {
            is Exercise -> {
                holder.nameTextView.text = exercise.TenBaiTap
                holder.repsTextView.text = exercise.SoRep.toString()
                setVideo(holder, exercise.Video)
            }
            is Initiate -> {
                holder.nameTextView.text = exercise.TenBaiTap
                holder.repsTextView.text = "${exercise.ThoiGian} giây"
                setVideo(holder, exercise.Video)
            }
            is Stretching -> {
                holder.nameTextView.text = exercise.TenBaiTap
                holder.repsTextView.text = "${exercise.ThoiGian} giây"
                setVideo(holder, exercise.Video)
            }
        }
    }

    override fun getItemCount() = allExercises.size

    // Dừng video khi ViewHolder bị tái chế
    override fun onViewRecycled(holder: ExerciseViewHolder) {
        super.onViewRecycled(holder)
        holder.stopVideo()
    }

    // Hàm thiết lập video cho VideoView
    private fun setVideo(holder: ExerciseViewHolder, videoUrl: String?) {
        // Kiểm tra videoUrl hợp lệ
        if (!videoUrl.isNullOrEmpty()) {
            val videoUri = Uri.parse(videoUrl)
            holder.videoView.setVideoURI(videoUri)
            holder.videoView.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.isLooping = true // Video lặp lại
                holder.videoView.start() // Bắt đầu phát video
            }
            holder.videoView.setOnErrorListener { mp, what, extra ->
                // Xử lý lỗi video nếu không thể phát
                Log.e("ExerciseAdapter", "Error playing video: $what, $extra")
                true // Đánh dấu lỗi đã được xử lý
            }
        } else {
            // Nếu không có video, ẩn VideoView
            holder.videoView.visibility = View.GONE
        }
    }
}

