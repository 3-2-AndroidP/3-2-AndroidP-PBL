package com.example.androidpbl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PostDetailActivity : AppCompatActivity() {
    private var loginUserEmail: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)
        val postTitleFromMain= intent?.getStringExtra("postTitle") ?: ""
        val postContentFromMain= intent?.getStringExtra("postContent") ?: ""
        loginUserEmail= intent?.getStringExtra("loginUserEmail") ?: ""

        val postDetailTitle = findViewById<TextView>(R.id.postDetailTitle)
        val postDetailContent = findViewById<TextView>(R.id.postDetailContent)

        postDetailTitle.text = postTitleFromMain
        postDetailContent.text = postContentFromMain

        val logOutButton = findViewById<Button>(R.id.logout5)
        logOutButton.setOnClickListener {
            Firebase.auth.signOut()
            finishAffinity()
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }
        val postAddButton = findViewById<Button>(R.id.postAddButton4)
        postAddButton.setOnClickListener {
            val intent = Intent(this, PostAddActivity::class.java)
            intent.putExtra("loginUserEmail",  loginUserEmail)
            startActivity(intent)
        }

        val showFriendButton = findViewById<Button>(R.id.showFriendButton4)
        showFriendButton.setOnClickListener {
            val intent = Intent(this, FriendListActivity::class.java)
            intent.putExtra("loginUserEmail",  loginUserEmail)
            startActivity(intent)
        }

        val searchFriendButton = findViewById<ImageButton>(R.id.imageButton)
        searchFriendButton.setOnClickListener {
            val searchName = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
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