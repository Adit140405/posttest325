package com.adit.post

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.post.databinding.ItemPostBinding

class PostAdapter(private val postList: MutableList<Post>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]
        val binding = holder.binding

        binding.postUsername.text = post.username
        binding.postCaption.text = post.caption
        binding.postProfileImage.setImageResource(post.profileImageResId)

        // tampilkan gambar post
        if (post.postImageUri != null) {
            binding.postImage.setImageURI(post.postImageUri)
        } else if (post.postImageResId != null) {
            binding.postImage.setImageResource(post.postImageResId)
        } else {
            binding.postImage.setImageDrawable(null)
        }
    }

    override fun getItemCount(): Int = postList.size

    // fungsi menambah post baru
    fun addPost(post: Post) {
        postList.add(0, post)
        notifyItemInserted(0)
    }
}
