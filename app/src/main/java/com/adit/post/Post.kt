package com.adit.post

import android.net.Uri

data class Post(
    val username: String,
    val caption: String,
    val profileImageResId: Int,
    val postImageUri: Uri? = null,
    val postImageResId: Int? = null
)
