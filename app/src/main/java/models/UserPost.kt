package models

import com.example.habiceb_ares_finalproject.R

data class UserPost(
    val id: Int,
    val imageRes: Int,
    val likes: Int,
    val comments: Int,
    val shares: Int,
    val caption: String
)

object PostRepository {
    val userPosts = listOf(
        UserPost(1, R.drawable.bag1, 125, 12, 5, "Loving the classic silhouette!"),
        UserPost(2, R.drawable.bag2, 89, 4, 2, "Eco-friendly and chic."),
        UserPost(3, R.drawable.bag3, 210, 25, 15, "Minimalism is key."),
        UserPost(4, R.drawable.bag4, 45, 2, 1, "Weekend vibes."),
        UserPost(5, R.drawable.bag5, 156, 18, 8, "New arrivals are here!"),
        UserPost(6, R.drawable.bag1, 312, 40, 22, "My daily essential.")
    )
}
