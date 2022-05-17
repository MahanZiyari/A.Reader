package com.mahan.compose.areader.model

data class MUser(
    val id: String?,
    val userId: String,
    val displayName: String,
    val avatarUrl: String,
    val quote: String,
    val profession: String
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "user_id" to userId,
            "display_name" to displayName,
            "profession" to profession,
            "quote" to quote,
            "avatar_url" to avatarUrl
        )
    }
}
