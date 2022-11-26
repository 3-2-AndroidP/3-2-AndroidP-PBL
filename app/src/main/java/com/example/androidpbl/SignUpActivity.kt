package com.example.androidpbl

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    private val db: FirebaseFirestore = Firebase.firestore
    private val itemsCollectionRef = db.collection("users")
    private val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //회원가입에 대한 오류 처리 아직안함
        val signupDoneButton = findViewById<Button>(R.id.signupDoneButton)
        signupDoneButton.setOnClickListener {
            val signupNameInput = findViewById<EditText>(R.id.signup_NameInput).text.toString()
            val signupBirthInput = findViewById<EditText>(R.id.signup_BirthInput).text.toString()
            val signupEmailInput = findViewById<EditText>(R.id.signup_EmailInput).text.toString()
            val signupPWInput = findViewById<EditText>(R.id.signup_PWInput).text.toString()
            println(signupNameInput == "")
            val birthYear = signupBirthInput.split("/")
            println(birthYear)
            if(signupNameInput == "" || signupBirthInput == "" || signupEmailInput == "" || signupPWInput == ""){
                AlertDialog.Builder(this)
                    .setTitle("회원가입 실패")
                    .setMessage("빈 항목이 있어 회원가입이 불가능합니다.")
                    .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, id ->

                    })
                    .show()
            }
            else if(birthYear[0].toInt() < 1900 || birthYear[0].toInt()>=2100){
                AlertDialog.Builder(this)
                    .setTitle("Warning")
                    .setMessage("생년월일 입력오류")
                    .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, id ->

                    })
                    .show()
            }
            else if(!Pattern.matches(emailValidation, signupEmailInput.trim())){
                AlertDialog.Builder(this)
                    .setTitle("Warning")
                    .setMessage("이메일 입력오류")
                    .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, id ->

                    })
                    .show()
            }
            else
                signUp(signupNameInput, signupBirthInput, signupEmailInput, signupPWInput)
        }
    }
    private fun updateList() {
        itemsCollectionRef.get().addOnSuccessListener {
//            val items = mutableListOf<Item>()
            for (doc in it) {
                println(doc)
            }
        }
    }

    private fun signUp(userName : String, birth : String, userEmail: String, password: String) {
        println(userEmail)
        Firebase.auth.createUserWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    println("######## Sign-up Success")
                    println(Firebase.auth.currentUser?.uid)
                    //firestore에 정보 보관
                    val itemMap = hashMapOf(
                        "name" to userName,
                        "birth" to birth,
                        "email" to userEmail,
                        "password" to password
                    )
                    itemsCollectionRef.document(userEmail).set(itemMap)
                        .addOnSuccessListener { updateList() }.addOnFailureListener {  }
                    startActivity(
                        Intent(this, LoginActivity::class.java)
                    )
                    finish()
                } else {
                    println("######## Sign-up Failed ${it.exception?.message}")
                }
            }
    }
}