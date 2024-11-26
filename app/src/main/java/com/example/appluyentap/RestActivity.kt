package com.example.appluyentap

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import data.Exercise
import data.ExerciseKhamPha
import data.Initiate
import data.Stretching

class RestActivity : AppCompatActivity() {
    private lateinit var restTimerTextView: TextView
    private lateinit var nextExerciseTextView: TextView
    private lateinit var videoView: VideoView
    private var restCountDownTimer: CountDownTimer? = null
    private var remainingRestTimeInMillis: Long = 30000L
    private var isTransitioning: Boolean = false // Cờ kiểm tra chuyển tiếp
    private var startTime: Long = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest)

        restTimerTextView = findViewById(R.id.restTimerTextView)
        nextExerciseTextView = findViewById(R.id.nextExerciseTextView)
        videoView = findViewById(R.id.videoView)
        startTime = intent.getLongExtra("startTime", -1L)
        val add20SecondsButton: Button = findViewById(R.id.add20SecondsButton)
        val skipButton: Button = findViewById(R.id.skipButton)

        // Lấy dữ liệu từ Intent
        val nextExerciseName = intent.getStringExtra("nextExerciseName") ?: "N/A"
        val nextExerciseReps = intent.getStringExtra("nextExerciseReps")
        val nextExerciseTime = intent.getStringExtra("nextExerciseTime")
        val nextExerciseVideoRes = intent.getStringExtra("nextExerciseVideoRes")

        // Cập nhật thông tin bài tập tiếp theo
        val nextExerciseInfo = buildNextExerciseInfo(nextExerciseName, nextExerciseReps, nextExerciseTime)
        nextExerciseTextView.text = "Bài tập tiếp theo: $nextExerciseInfo"

        // Phát video nếu có
        if (nextExerciseVideoRes != null) {
            playExerciseVideo(nextExerciseVideoRes)
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

    private fun buildNextExerciseInfo(name: String, reps: String?, time: String?): String {
        return when {
            !reps.isNullOrEmpty() -> "$name - $reps reps"
            !time.isNullOrEmpty() -> "$name - $time seconds"
            else -> name
        }
    }

    private fun playExerciseVideo(videoFileName: String) {
        // Lấy resource ID từ tên file video
        val resId = resources.getIdentifier(videoFileName, "raw", packageName)

        if (resId != 0) {
            // Nếu tìm thấy video, tạo URI và phát video
            val videoUri = Uri.parse("android.resource://$packageName/$resId")
            videoView.setVideoURI(videoUri)
            videoView.setOnPreparedListener {
                videoView.start() // Bắt đầu video tự động
            }
            videoView.setOnCompletionListener {
                videoView.start() // Lặp lại video khi kết thúc
            }
        } else {
            // Xử lý khi không tìm thấy video (tuỳ theo yêu cầu)
            // Có thể thông báo lỗi hoặc làm gì đó tùy ý
            println("Video không tồn tại trong tài nguyên.")
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
        val mainExercises = intent.getSerializableExtra("mainExercises") as? ArrayList<Exercise> ?: ArrayList()
        val stretchingExercises = intent.getSerializableExtra("stretchingExercises") as? ArrayList<Stretching> ?: ArrayList()
        val warmupExercises = intent.getSerializableExtra("warmupExercises") as? ArrayList<Initiate> ?: ArrayList()
        val khamphaExercises = intent.getSerializableExtra("Exercises") as? ArrayList<ExerciseKhamPha> ?: ArrayList()
        restCountDownTimer?.cancel()

        val intent = Intent(this, ExerciseActivity::class.java)
        intent.putExtra("mainExercises", mainExercises)
        intent.putExtra("stretchingExercises", stretchingExercises)
        intent.putExtra("warmupExercises", warmupExercises)
        intent.putExtra("Exercises", khamphaExercises)
        intent.putExtra("startTime", startTime)
        Log.d("RestActivity", "startTime: $startTime")
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        restCountDownTimer?.cancel() // Huỷ bộ đếm khi activity bị huỷ
    }
}
