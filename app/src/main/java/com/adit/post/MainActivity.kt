package com.adit.post

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {

    private lateinit var storyAdapter: StoryAdapter
    private lateinit var postAdapter: PostAdapter
    private val postList = mutableListOf<Post>()
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val storyRecyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.storyRecyclerView)
        val postRecyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.postRecyclerView)
        val addPostButton = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.addPostButton)

        // === Data story ===
        val storyList = listOf(
            Story("intan_dwi", R.drawable.adit325),
            Story("minda_04", R.drawable.profil1),
            Story("rubi_community", R.drawable.adit325),
            Story("rizka", R.drawable.profil1),
            Story("amel", R.drawable.adit325)
        )

        // === Data post awal ===
        postList.add(
            Post("intan_dwi", "Liburan ke pantai 🌊", R.drawable.adit325, postImageResId = R.drawable.gunung)
        )
        postList.add(
            Post("minda_04", "Hari yang menyenangkan ☀️", R.drawable.profil1, postImageResId = R.drawable.mobil)
        )

        // === Set Adapter Story ===
        storyRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        storyAdapter = StoryAdapter(storyList)
        storyRecyclerView.adapter = storyAdapter

        // === Set Adapter Post ===
        postRecyclerView.layoutManager = LinearLayoutManager(this)
        postAdapter = PostAdapter(postList)
        postRecyclerView.adapter = postAdapter

        // === Tombol tambah post ===
        addPostButton.setOnClickListener {
            showAddPostDialog()
        }
    }

    private fun showAddPostDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_add_post, null)
        val etUsername = view.findViewById<EditText>(R.id.etUsername)
        val etCaption = view.findViewById<EditText>(R.id.etCaption)
        val btnTambahGambar = view.findViewById<Button>(R.id.btnTambahGambar)
        val btnSimpan = view.findViewById<Button>(R.id.btnSimpan)

        val dialog = AlertDialog.Builder(this)
            .setView(view)
            .create()

        btnTambahGambar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 100)
        }

        btnSimpan.setOnClickListener {
            val username = etUsername.text.toString()
            val caption = etCaption.text.toString()

            if (username.isEmpty() || caption.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newPost = Post(username, caption, R.drawable.profil1, postImageUri = selectedImageUri)
            postList.add(0, newPost)
            postAdapter.notifyItemInserted(0)
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            selectedImageUri = data?.data
        }
    }
}
