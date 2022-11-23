package com.example.androidpbl

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.androidpbl.databinding.ActivityFriendListBinding
import com.example.androidpbl.databinding.ActivityPostAddBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.core.DatabaseInfo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PostAddActivity : AppCompatActivity() {
    private var loginUserEmail: String? = null
    val firestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPostAddBinding.inflate(layoutInflater)
        setSupportActionBar(findViewById(R.id.toolbar))
        setContentView(binding.root)

        loginUserEmail= intent?.getStringExtra("loginUserEmail") ?: ""

        val logOutButton = findViewById<Button>(R.id.logout3)
        logOutButton.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }

        val completeButton = findViewById<Button>(R.id.completeButton)
        completeButton.setOnClickListener{
            val title = binding.editTitle.text.toString()
            val data = hashMapOf(
                "content" to binding.editPost.text.toString()
            )

            firestore?.collection("users")
                ?.document(loginUserEmail!!)?.collection("posts")?.document(title)
                ?.set(data)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}