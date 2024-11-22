import android.util.Log
import com.google.firebase.database.*
import data.Exercise

class ExerciseHelper {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Exercises")

    // Thêm bài tập vào Firebase
    fun addExercise(exercise: Exercise, callback: (Boolean) -> Unit) {
        val exerciseId = database.push().key // Tạo ID ngẫu nhiên cho bài tập
        if (exerciseId != null) {
            database.child(exerciseId).setValue(exercise).addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }
        }
    }

    // Cập nhật bài tập
    fun updateExercise(exerciseId: String, updatedExercise: Exercise, callback: (Boolean) -> Unit) {
        if (exerciseId.isNotEmpty()) {
            database.child(exerciseId).setValue(updatedExercise).addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }
        }
    }

    // Xóa bài tập
    fun deleteExercise(exerciseId: String, callback: (Boolean) -> Unit) {
        database.child(exerciseId).removeValue().addOnCompleteListener { task ->
            callback(task.isSuccessful)
        }
    }

    // Lấy tất cả bài tập từ Firebase và hiển thị qua callback
    fun getAllExercises(callback: (List<Exercise>) -> Unit) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val exerciseList = mutableListOf<Exercise>()
                for (exerciseSnapshot in snapshot.children) {
                    val exercise = exerciseSnapshot.getValue(Exercise::class.java)
                    if (exercise != null) {
                        exerciseList.add(exercise)
                    }
                }
                callback(exerciseList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ExerciseHelper", "Error loading exercises", error.toException())
            }
        })
    }

    // Lấy bài tập theo ID từ Firebase
    fun getExerciseById(exerciseId: String, callback: (Exercise?) -> Unit) {
        database.child(exerciseId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val exercise = snapshot.getValue(Exercise::class.java)
                callback(exercise) // Trả về bài tập tìm thấy hoặc null nếu không tồn tại
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ExerciseHelper", "Error loading exercise by ID", error.toException())
                callback(null) // Gọi callback với null nếu có lỗi
            }
        })
    }
}
