package com.example.androidpbl

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpbl.databinding.ActivityFriendSearchBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FriendSearchActivity : AppCompatActivity() {
    private var loginUserEmail: String? = null
    private var searchName: String? = null
    val firestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFriendSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.friendSearch.adapter = RecyclerViewAdapter2()
        binding.friendSearch.layoutManager = LinearLayoutManager(this)
        binding.friendSearch.setHasFixedSize(true)
        binding.friendSearch.addItemDecoration(VerticalItemDecorator(10))
        binding.friendSearch.addItemDecoration(HorizontalItemDecorator(30))

//        val intent = intent //전달할 데이터를 받을 Intent
//        val searchName = intent.getStringExtra("searchName")
        loginUserEmail= intent?.getStringExtra("loginUserEmail") ?: ""
        searchName= intent?.getStringExtra("searchName") ?: ""

        binding.postAddButton3.setOnClickListener {
            val intent = Intent(this, PostAddActivity::class.java)
            intent.putExtra("loginUserEmail", loginUserEmail)
            startActivity(intent)
        }

        binding.showFriendButton3.setOnClickListener {
            val intent = Intent(this, FriendListActivity::class.java)
            intent.putExtra("loginUserEmail",loginUserEmail.toString())
            startActivity(intent) // 검색창의 이름을 전달한다
        }

        binding.textView3.setText("'${searchName}' 검색 결과")

        binding.logout.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }
    }
        inner class RecyclerViewAdapter2 : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var UserArray: ArrayList<SearchFriend> = arrayListOf()
//        val searchName = intent.getStringExtra("searchName")
        init { // 검색결과 출력
            val searchName = intent.getStringExtra("searchName").toString()
            firestore?.collection("users")?.orderBy("name", Query.Direction.ASCENDING)
                ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    // ArrayList 비워줌
                    UserArray.clear()
                    for (snapshot in querySnapshot!!.documents) {
                        var item = snapshot.toObject(SearchFriend::class.java)
                        if (item?.name!!.contains(searchName)) {// 문서의 name 속성이 검색한 이름과 같으면 넣는다
                            UserArray.add(item!!)
                        }
                    }
                    notifyDataSetChanged()
                }
        }

        // xml파일을 inflate하여 ViewHolder를 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//            val clickName = view.findViewById<TextView>(R.id.textView2) // 클릭 텍스트뷰 이름
//            val clickEmail = view.findViewById<TextView>(R.id.textView7)

            init{
                val loginUserEmail = intent.getStringExtra("loginUserEmail").toString()
                view.findViewById<Button>(R.id.button).setOnClickListener{
                    val clickName = view.findViewById<TextView>(R.id.textView2).text // 클릭 텍스트뷰 이름
                    val clickEmail = view.findViewById<TextView>(R.id.textView7).text
                    val itemMap = hashMapOf(
                        "name" to clickName,
                        "email" to clickEmail
                    )
                    firestore?.collection("users")
                        ?.document(loginUserEmail)?.collection("friends")?.document(clickEmail.toString())?.set(itemMap)
                        ?.addOnSuccessListener {  }
                    Snackbar.make(view, "${clickName}님이 팔로우 목록에 추가되었습니다.", Snackbar.LENGTH_LONG). show();
                }
            }

        }

        // onCreateViewHolder에서 만든 view와 실제 데이터를 연결
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView
            viewHolder.findViewById<TextView>(R.id.textView2).text = UserArray[position].name
            viewHolder.findViewById<TextView>(R.id.textView7).text = UserArray[position].email
//            holder.bind(UserArray[position])
        }

        // 리사이클러뷰의 아이템 총 개수 반환
        override fun getItemCount(): Int {
            return UserArray.size
        }
    }
}