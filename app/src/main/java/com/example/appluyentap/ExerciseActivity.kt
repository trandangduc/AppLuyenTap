package com.example.appluyentap

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import data.Exercise

class ExerciseActivity : AppCompatActivity() {
    private lateinit var exercise: Exercise
    private lateinit var timerTextView: TextView
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var videoView: VideoView
    private lateinit var pauseButton: Button
    private var isPaused = false
    private var countDownTimer: CountDownTimer? = null
    private var remainingTimeInMillis: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        // Lấy dữ liệu bài tập từ Intent
        exercise = intent.getSerializableExtra("exercise") as Exercise

        // Khởi tạo các view
        val exerciseNameTextView: TextView = findViewById(R.id.exerciseName)
        timerTextView = findViewById(R.id.timerTextView)
        videoView = findViewById(R.id.videoView)
        pauseButton = findViewById(R.id.pauseButton)
        val skipButton: Button = findViewById(R.id.skipButton)

        // Cập nhật tên bài tập
        exerciseNameTextView.text = exercise.name

        // Cập nhật video hướng dẫn nếu có
        exercise.videoRes?.let {
            val videoUri = Uri.parse("android.resource://" + packageName + "/" + it)
            videoView.setVideoURI(videoUri)
            videoView.start()

            // Lặp lại video khi kết thúc
            videoView.setOnCompletionListener {
                videoView.start()
            }
        }

        // Bắt đầu đếm ngược nếu thời gian được cung cấp
        if (exercise.time.contains("00:")) {
            val timeInSeconds = exercise.time.split(":")[1].toInt()
            remainingTimeInMillis = timeInSeconds * 1000L
            startCountDown(remainingTimeInMillis)
        }

        // Thiết lập hành động cho nút "TẠM DỪNG"
        pauseButton.setOnClickListener {
            if (isPaused) {
                resumeExercise()
            } else {
                pauseExercise()
                showPauseDialog()
            }
        }

        // Thiết lập hành động cho nút "Bỏ qua"
        skipButton.setOnClickListener {
            onFinishExercise() // Gọi hàm chuyển sang trang nghỉ ngơi
        }
    }

    private fun startCountDown(timeInMillis: Long) {
        countDownTimer?.cancel() // Hủy bỏ bộ hẹn giờ cũ
        countDownTimer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeInMillis = millisUntilFinished
                val secondsRemaining = millisUntilFinished / 1000
                timerTextView.text = secondsRemaining.toString()
            }

            override fun onFinish() {
                timerTextView.text = "Hoàn thành!"
                mediaPlayer = MediaPlayer.create(this@ExerciseActivity, R.raw.sound_complete)
                mediaPlayer?.start()
                onFinishExercise() // Chuyển sang bài tập tiếp theo
            }
        }.start()
    }

    private fun pauseExercise() {
        videoView.pause() // Tạm dừng video
        countDownTimer?.cancel() // Tạm dừng đếm ngược
        isPaused = true
    }

    private fun resumeExercise() {
        videoView.start() // Tiếp tục phát video
        startCountDown(remainingTimeInMillis) // Tiếp tục đếm ngược với thời gian còn lại
        isPaused = false
    }

    private fun showPauseDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Tạm dừng")
            .setMessage("Bạn muốn làm gì tiếp theo?")
            .setPositiveButton("Tiếp tục") { _, _ ->
                resumeExercise()
            }
            .setNegativeButton("Bắt đầu lại") { _, _ ->
                restartExercise()
            }
            .setNeutralButton("Thoát") { _, _ ->
                finish()
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }

    private fun restartExercise() {
        videoView.seekTo(0) // Đặt lại video về đầu
        videoView.start()
        val initialTimeInSeconds = exercise.time.split(":")[1].toInt()
        remainingTimeInMillis = initialTimeInSeconds * 1000L
        startCountDown(remainingTimeInMillis) // Đặt lại đếm ngược
        isPaused = false
    }

    private fun onFinishExercise() {
        val exercises = listOf(
            Exercise("Bật nhảy", "", "00:20", R.raw.ic_jump),
            Exercise("Tập cơ bụng", "x16", "", R.raw.ic_crunch),
            Exercise("Gập bụng chéo kiểu Nga", "x20", "", R.raw.ic_russian_twist),
            Exercise("Leo núi", "x16", "", R.raw.ic_mountain_climber),
            Exercise("Chạm gót chân", "x20", "", R.raw.ic_heel_touch),
            Exercise("Nâng chân", "x16", "", R.raw.ic_leg_raise),
            Exercise("Đo sàn", "", "00:20", R.raw.ic_plank)
        )

        val currentIndex = exercises.indexOfFirst { it.name == exercise.name }
        val nextExercise = if (currentIndex < exercises.size - 1) exercises[currentIndex + 1] else null

        val intent = Intent(this, RestActivity::class.java).apply {
            putExtra("nextExerciseName", nextExercise?.name ?: "Hoàn thành!")
            putExtra("nextExerciseReps", nextExercise?.reps ?: "0")
            putExtra("nextExerciseTime", nextExercise?.time ?: "0")
            putExtra("nextExerciseVideoRes", nextExercise?.videoRes ?: -1)
        }
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        countDownTimer?.cancel()
    }
}
