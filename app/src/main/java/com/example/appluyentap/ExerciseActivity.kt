package com.example.appluyentap

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import data.Exercise
import data.ExerciseKhamPha
import data.Initiate
import data.Stretching
import java.io.Serializable

class ExerciseActivity : AppCompatActivity() {
    private lateinit var mainExercises: ArrayList<Exercise>
    private lateinit var stretchingExercises: ArrayList<Stretching>
    private lateinit var warmupExercises: ArrayList<Initiate>
    private lateinit var khamphaExercises: ArrayList<ExerciseKhamPha>
    private lateinit var timerTextView: TextView
    private lateinit var nametext: TextView
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var videoView: VideoView
    private lateinit var pauseButton: Button
    private var isPaused = false
    private var countDownTimer: CountDownTimer? = null
    private var remainingTimeInMillis: Long = 0L
    private var time: Long = 0L
    private var startTime: Long = 0L
    private lateinit var exercise: Exercise
    private lateinit var currentStretchingExercise: Stretching
    private lateinit var currentWarmupExercise: Initiate
    private lateinit var khamphaexercise: ExerciseKhamPha
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        timerTextView = findViewById(R.id.timerTextView)
        nametext = findViewById(R.id.exerciseName)
        videoView = findViewById(R.id.videoView)
        pauseButton = findViewById(R.id.pauseButton)
        val skipButton: Button = findViewById(R.id.skipButton)

        // Lấy dữ liệu bài tập từ Intent
        mainExercises = intent.getSerializableExtra("mainExercises") as? ArrayList<Exercise> ?: ArrayList()
        stretchingExercises = intent.getSerializableExtra("stretchingExercises") as? ArrayList<Stretching> ?: ArrayList()
        warmupExercises = intent.getSerializableExtra("warmupExercises") as? ArrayList<Initiate> ?: ArrayList()
        startTime = intent.getLongExtra("startTime", -1L)
        khamphaExercises = intent.getSerializableExtra("Exercises") as? ArrayList<ExerciseKhamPha> ?: ArrayList()

        Log.d("ExerciseActivity", "mainExercises: $mainExercises")
        Log.d("ExerciseActivity", "stretchingExercises: $stretchingExercises")
        Log.d("ExerciseActivity", "warmupExercises: $warmupExercises")
        Log.d("ExerciseActivity", "startTime: $khamphaExercises")
        if (khamphaExercises.isNotEmpty()) {
            khamphaexercise = khamphaExercises[0] // Lấy bài tập khởi động đầu tiên
            startExercise(khamphaexercise)
        }
        // Duyệt qua warmupExercises (khởi động) trước
        else if (warmupExercises.isNotEmpty()) {
            currentWarmupExercise = warmupExercises[0] // Lấy bài tập khởi động đầu tiên
            startExercise(currentWarmupExercise)
        }
        else if (mainExercises.isNotEmpty()) {
            exercise = mainExercises[0] // Lấy bài tập khởi động đầu tiên
            startExercise(exercise)
        }
        else if (stretchingExercises.isNotEmpty()) {
            currentStretchingExercise = stretchingExercises[0] // Lấy bài tập khởi động đầu tiên
            startExercise(currentStretchingExercise)
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
            onFinishExercise() // Chuyển sang trang nghỉ ngơi khi bỏ qua
        }
    }

    private fun startExercise(exercise: Any) {
        // Xử lý việc bắt đầu bài tập cho mỗi loại bài tập (khởi động, giãn cơ)
        when (exercise) {
            is Initiate -> {
                // Xử lý bài tập khởi động
                nametext.text = exercise.TenBaiTap
                time = exercise.ThoiGian.toLong() *1000L
                startCountDown(exercise.ThoiGian.toLong() *1000L)
                playVideo(exercise.Video)
            }
            is Exercise -> {
                nametext.text = exercise.TenBaiTap
                startRepetitions(exercise);

            }
            is Stretching -> {
                nametext.text = exercise.TenBaiTap
                time = exercise.ThoiGian.toLong() *1000L
                // Xử lý bài tập giãn cơ
                startCountDown(exercise.ThoiGian.toLong()*1000L)
                playVideo(exercise.Video)
            }
            is ExerciseKhamPha -> {
                // Kiểm tra SoRep có phải null hoặc chuỗi rỗng không
                if (!exercise.SoRep.isNullOrEmpty()) {
                    // Nếu SoRep không phải là null hoặc chuỗi rỗng, xử lý bình thường
                    nametext.text = exercise.TenBaiTap
                    var repetitions = exercise.SoRep // Số lần lặp
                    timerTextView.text = "Số lần: $repetitions"
                    nametext.text = exercise.TenBaiTap
                    playVideo(exercise.Video)
                } else {
                    // Xử lý trường hợp SoRep là null hoặc chuỗi rỗng
                    // Bạn có thể gán giá trị mặc định hoặc hiển thị thông báo cho người dùng
                    nametext.text = exercise.TenBaiTap
                    // Hoặc chỉ gán các giá trị khác mà không cần SoRep
                    time = exercise.ThoiGian?.toLong()?.times(1000) ?: 0L
                    startCountDown(time)
                    playVideo(exercise.Video)
                }
            }

        }
    }

    private fun startRepetitions(exercise: Exercise) {
        // Xử lý bài tập chính (hiển thị số lần lặp thay vì đếm ngược)
        var repetitions = exercise.SoRep // Số lần lặp
        timerTextView.text = "Số lần: $repetitions"
        nametext.text = exercise.TenBaiTap
        playVideo(exercise.Video)
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
                onFinishExercise() // Chuyển sang bài tập tiếp theo hoặc màn hình nghỉ ngơi
            }
        }.start()
    }

    private fun playVideo(videoName: String?) {
        videoName?.let {
            val resId = resources.getIdentifier(it, "raw", packageName)
            if (resId != 0) {
                val videoUri = Uri.parse("android.resource://$packageName/$resId")
                videoView.setVideoURI(videoUri)

                videoView.setOnPreparedListener {
                    videoView.start() // Bắt đầu video tự động
                }

                // Lặp lại video khi kết thúc
                videoView.setOnCompletionListener {
                    videoView.start() // Phát lại video khi video kết thúc
                }
            } else {
                Log.e("ExerciseActivity", "Không tìm thấy video trong raw folder")
            }
        }
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
        if (time != 0L) {
            videoView.seekTo(0) // Đặt lại video về đầu
            videoView.start()

            startCountDown(time) // Đặt lại đếm ngược
            isPaused = false
        }

    }

    private fun onFinishExercise() {
        when {
            // Nếu còn bài tập khởi động
            warmupExercises.isNotEmpty() -> {
                val currentExercise = warmupExercises.removeAt(0) // Loại bỏ bài tập đã hoàn thành
                if ( warmupExercises.isNotEmpty()) {
                    navigateToRestActivity(warmupExercises[0], "warmup") // Chuyển sang RestActivity
                }
                else navigateToRestActivity(mainExercises[0], "main") // Chuyển sang RestActivity

            }

            // Nếu còn bài tập chính
            mainExercises.isNotEmpty() -> {
                val currentExercise = mainExercises.removeAt(0) // Loại bỏ bài tập đã hoàn thành
                    if (mainExercises.isNotEmpty() ) {
                        navigateToRestActivity(mainExercises[0], "main") // Chuyển sang RestActivity

                    }
                else navigateToRestActivity(stretchingExercises[0], "stretching") // Chuyển sang RestActivity

            }

            // Nếu còn bài tập giãn cơ
            stretchingExercises.isNotEmpty() -> {
                val currentExercise = stretchingExercises.removeAt(0) // Loại bỏ bài tập đã hoàn thành
                if (stretchingExercises.isNotEmpty()) {
                    navigateToRestActivity(stretchingExercises[0], "stretching") // Chuyển sang RestActivity

                }
                else onFinishExercise()
            }
            // Nếu còn bài tập giãn cơ
            khamphaExercises.isNotEmpty() -> {
                val currentExercise = khamphaExercises.removeAt(0) // Loại bỏ bài tập đã hoàn thành
                if (khamphaExercises.isNotEmpty()) {
                    navigateToRestActivity(khamphaExercises[0], "khampha") // Chuyển sang RestActivity

                }
                else onFinishExercise()
            }
            // Nếu không còn bài tập nào
            else -> {

                Log.d("startTimeex", "startTime: $startTime")
                val intent = Intent(this, Succes::class.java)
                intent.putExtra("startTime", startTime)
                startActivity(intent)
                finish() // Kết thúc Activity hiện tại
            }
        }
    }
    private fun navigateToRestActivity(nextExercise: Any, exerciseType: String) {
        val intent = Intent(this, RestActivity::class.java)
        intent.putExtra("mainExercises", mainExercises)
        intent.putExtra("stretchingExercises", stretchingExercises)
        intent.putExtra("warmupExercises", warmupExercises)
        intent.putExtra("Exercises", khamphaExercises)

        intent.putExtra("startTime", startTime)
        when (exerciseType) {
            "warmup" -> {
                if (nextExercise is Initiate) {
                    intent.putExtra("nextExerciseName", nextExercise.TenBaiTap)
                    intent.putExtra("nextExerciseTime", nextExercise.ThoiGian)
                    intent.putExtra(
                        "nextExerciseVideoRes",
                       nextExercise.Video
                    )
                }
            }
            "main" -> {
                if (nextExercise is Exercise) {
                    intent.putExtra("nextExerciseName", nextExercise.TenBaiTap)
                    intent.putExtra("nextExerciseReps", nextExercise.SoRep)
                    intent.putExtra(
                        "nextExerciseVideoRes",
                       nextExercise.Video
                    )
                }
            }
            "stretching" -> {
                if (nextExercise is Stretching) {
                    intent.putExtra("nextExerciseName", nextExercise.TenBaiTap)
                    intent.putExtra("nextExerciseTime", nextExercise.ThoiGian)
                    intent.putExtra(
                        "nextExerciseVideoRes",
                        nextExercise.Video
                    )
                }
            }
            "khampha" -> {
                if (nextExercise is ExerciseKhamPha) {
                    intent.putExtra("nextExerciseName", nextExercise.TenBaiTap)
                    val time = nextExercise.ThoiGian
                    if (!time.isNullOrEmpty()) {
                        // Nếu time hợp lệ, truyền time vào intent
                        intent.putExtra("nextExerciseTime", time)
                    } else {
                        // Nếu time là null hoặc chuỗi rỗng, truyền SoRep vào intent
                        val soRep = nextExercise.SoRep
                        intent.putExtra("nextExerciseReps", soRep)
                    }

                    intent.putExtra(
                        "nextExerciseVideoRes",
                        nextExercise.Video
                    )
                }
            }
        }

        intent.putExtra("exerciseType", exerciseType) // Truyền loại bài tập (tùy chọn)
        startActivity(intent)
        finish() // Kết thúc Activity hiện tại
    }



    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        countDownTimer?.cancel()
    }
}

