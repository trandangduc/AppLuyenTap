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


        startCountDown(remainingTimeInMillis) // Đặt lại đếm ngược
        isPaused = false
    }

    private fun onFinishExercise() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        countDownTimer?.cancel()
    }
}
