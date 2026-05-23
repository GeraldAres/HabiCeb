package Screens.ProfileScreen

import models.UserPost

interface ProfileContract {
    interface View {
        fun updateUserInfo(name: String)
        fun displayPosts(posts: List<UserPost>)
        fun showPostDetail(post: UserPost)
        fun toggleEditSection(show: Boolean)
        fun showMessage(msg: String)
        fun navigateToHome()
        fun navigateToCart()
        fun navigateToSplash()
    }
    interface Presenter {
        fun init()
        fun onLogoutClicked()
        fun onEditClicked()
        fun onSaveClicked(newName: String)
        fun onPostClicked(post: UserPost)
        fun onHomeClicked()
        fun onCartClicked()
    }
}
