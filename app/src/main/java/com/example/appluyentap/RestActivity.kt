package com.example.appluyentap

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import data.Exercise

class RestActivity : AppCompatActivity() {
    private lateinit var restTimerTextView: TextView
    private lateinit var nextExerciseTextView: TextView
    private lateinit var videoView: VideoView
    private var restCountDownTimer: CountDownTimer? = null
    private var remainingRestTimeInMillis: Long = 30000L
    private var isTransitioning: Boolean = false // Cờ kiểm tra chuyển tiếp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest)

        restTimerTextView = findViewById(R.id.restTimerTextView)
        nextExerciseTextView = findViewById(R.id.nextExerciseTextView)
        videoView = findViewById(R.id.videoView)
        val add20SecondsButton: Button = findViewById(R.id.add20SecondsButton)
        val skipButton: Button = findViewById(R.id.skipButton)

        // Lấy dữ liệu từ Intent với giá trị mặc định nếu không có dữ liệu
        val nextExerciseName = intent.getStringExtra("nextExerciseName") ?: "N/A"
        val nextExerciseReps = intent.getStringExtra("nextExerciseReps") ?: ""
        val nextExerciseTime = intent.getStringExtra("nextExerciseTime") ?: ""
        val nextExerciseVideoRes = intent.getIntExtra("nextExerciseVideoRes", -1)

        val nextExerciseInfo = if (nextExerciseReps.isNotEmpty()) {
            "$nextExerciseName - $nextExerciseReps"
        } else {
            "$nextExerciseName - $nextExerciseTime"
        }
        nextExerciseTextView.text = "Bài tập tiếp theo: $nextExerciseInfo"

        // Kiểm tra video trước khi phát
        if (nextExerciseVideoRes != -1) {
            val videoUri = Uri.parse("android.resource://$packageName/$nextExerciseVideoRes")
            videoView.setVideoURI(videoUri)
            videoView.start()
            videoView.setOnCompletionListener {
                videoView.start() // Lặp lại video khi kết thúc
            }
        }

        startRestTimer(remainingRestTimeInMillis)

        add20SecondsButton.setOnClickListener {
            if (!isTransitioning) {
                remainingRestTimeInMillis += 20000L
                startRestTimer(remainingRestTimeInMillis)
            }
        }

        skipButton.setOnClickListener {
            goToNextExercise()
        }
    }

    private fun startRestTimer(timeInMillis: Long) {
        restCountDownTimer?.cancel()
        restCountDownTimer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingRestTimeInMillis = millisUntilFinished
                val secondsRemaining = millisUntilFinished / 1000
                restTimerTextView.text = String.format("00:%02d", secondsRemaining)
            }

            override fun onFinish() {
                if (!isTransitioning) {
                    goToNextExercise()
                }
            }
        }.start()
    }

    private fun goToNextExercise() {
        if (isTransitioning) return // Đảm bảo chỉ chuyển một lần
        isTransitioning = true

        restCountDownTimer?.cancel()

        // Lấy dữ liệu từ Intent và kiểm tra null
        val nextExerciseName = intent.getStringExtra("nextExerciseName") ?: "Bài tập không xác định"
        val nextExerciseReps = intent.getStringExtra("nextExerciseReps") ?: ""
        val nextExerciseTime = intent.getStringExtra("nextExerciseTime") ?: ""
        val nextExerciseVideoRes = intent.getIntExtra("nextExerciseVideoRes", -1)

        // Kiểm tra trước khi sử dụng video
        if (nextExerciseVideoRes == -1) {
            // Xử lý trường hợp không có video (nếu cần)
        }
     
        startActivity(intent)
        finish() // Đóng RestActivity sau khi chuyển đi
    }

    override fun onDestroy() {
        super.onDestroy()
        restCountDownTimer?.cancel() // Huỷ bộ đếm khi activity bị huỷ
    }
}
