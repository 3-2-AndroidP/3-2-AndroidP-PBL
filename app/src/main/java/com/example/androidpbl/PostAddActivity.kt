package com.example.androidpbl

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.androidpbl.databinding.ActivityFriendListBinding
import com.example.androidpbl.databinding.ActivityPostAddBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.core.DatabaseInfo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime

class PostAddActivity : AppCompatActivity() {
    private var loginUserEmail: String? = null
    val firestore = Firebase.firestore
    val dateAndtime: Long = System.currentTimeMillis()
    private var itemsCollectionRef : CollectionReference? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPostAddBinding.inflate(layoutInflater)
        setSupportActionBar(findViewById(R.id.toolbar))
        setContentView(binding.root)

        loginUserEmail= intent?.getStringExtra("loginUserEmail") ?: ""

        itemsCollectionRef = firestore.collection("users").document(loginUserEmail!!).collection("posts")



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
            val content = binding.editPost.text.toString()

            if(title == "" || content == "") {
                AlertDialog.Builder(this)
                    .setTitle("게시물 저장 실패")
                    .setMessage("제목 또는 내용이 비어있습니다.")
                    .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, id ->
                    })
                    .show()
            } else {
                val data = hashMapOf(
                    "content" to binding.editPost.text.toString(),
                    "postTime" to dateAndtime
                )
                val storeTitle = itemsCollectionRef?.document(title)?.id ?: title
                itemsCollectionRef!!.document(storeTitle).set(data)
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("anotherEmail", loginUserEmail)
                startActivity(intent)
            }
        }

        val searchFriendButton = findViewById<ImageButton>(R.id.imageButton)
        searchFriendButton.setOnClickListener {
            val searchName = binding.editTextTextPersonName2.text.toString()
            if(searchName == ""){
                Snackbar.make(it, "검색할 이름을 입력해주세요.", Snackbar.LENGTH_LONG). show();
            }
            else {
                val intent = Intent(this, FriendSearchActivity::class.java)
                intent.putExtra("searchName", searchName)
                intent.putExtra("loginUserEmail", loginUserEmail)
                startActivity(intent);
            }
        }
    }
}