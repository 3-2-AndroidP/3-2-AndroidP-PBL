package com.example.androidpbl

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(findViewById(R.id.toolbar))

        val signupButton = findViewById<Button>(R.id.signupButton)
        signupButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            val emailInput = findViewById<EditText>(R.id.userEmailInput).text.toString()
            val password = findViewById<EditText>(R.id.passwordInput).text.toString()
            if(emailInput == "" || password==""){
                AlertDialog.Builder(this)
                    .setTitle("로그인 실패")
                    .setMessage("이메일 또는 비밀번호가 일치하지 않습니다.")
                    .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, id ->

                    })
                    .show()
            }
            else
                doLogin(emailInput, password)
        }
    }

    private fun doLogin(userEmail: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("loginUserEmail", userEmail)
                    startActivity(intent)
                    finish()
                } else {
                    Log.w("LoginActivity", "signInWithEmail", it.exception)
                    AlertDialog.Builder(this)
                        .setTitle("로그인 실패")
                        .setMessage("이메일 또는 비밀번호가 일치하지 않습니다.")
                        .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, id ->

                        })
                        .show()
                }
            }
    }
}