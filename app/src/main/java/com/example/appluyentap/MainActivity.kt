package com.example.appluyentap
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appluyentap.Menu
import com.example.appluyentap.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    companion object {
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo FirebaseAuth và GoogleSignInClient
        mAuth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Web client ID của bạn
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        // Button đăng nhập Google
        val signInButton = findViewById<Button>(R.id.btnSignIn)
        signInButton.setOnClickListener {
            signIn()
        }
    }

    // Hàm đăng nhập Google
    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Đăng nhập Firebase bằng Google
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Đăng nhập thành công, chuyển đến màn hình chính
                    val user = mAuth.currentUser
                    user?.let {
                        // Kiểm tra xem người dùng đã tồn tại trong Firebase hay chưa
                        checkUserExists(it)
                    }
                    Toast.makeText(this, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()

                } else {
                    // Đăng nhập thất bại
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun checkUserExists(user: FirebaseUser) {
        // Lấy tham chiếu đến Firebase Realtime Database
        val userRef = FirebaseDatabase.getInstance().reference.child("users").child(user.uid)

        // Kiểm tra xem người dùng đã tồn tại trong cơ sở dữ liệu hay chưa
        userRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful && task.result.exists()) {
                // Người dùng đã tồn tại, chuyển đến màn hình Gender
                val intent = Intent(this, Menu::class.java)
                startActivity(intent)
                finish() // Đảm bảo không thể quay lại màn hình đăng nhập
            } else {
                createUserInDatabase(user)
            }
        }
    }
    private fun createUserInDatabase(user: FirebaseUser) {
        val userId = user.uid
        val userInfo = mapOf(
            "displayName" to (user.displayName ?: "Anonymous"),
            "email" to (user.email ?: ""),
            "totalWorkoutTime" to 0, // Thời gian tập tổng ban đầu
            "totalWorkouts" to 0, // Tổng số lần tập
            "monthlyWorkouts" to 0, // Số lần tập trong tháng
            "userId" to userId,
            "age" to 0, // Độ tuổi (khởi tạo là 0, có thể cập nhật sau)
            "weight" to 0.0, // Cân nặng (kg)
            "height" to 0.0, // Chiều cao (cm)
            "goal" to "No goal yet" ,// Mục tiêu (chuỗi mặc định)
            "lastWorkoutDate" to ""
        )

        database.child("users").child(userId).setValue(userInfo)
            .addOnSuccessListener {
                Toast.makeText(this, "User data saved successfully!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to save user data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        val intent = Intent(this, Gender::class.java)  // Màn hình chính sau khi đăng nhập thành công
        startActivity(intent)
        finish() // Đảm bảo không thể quay lại màn hình đăng nhập
    }

}
