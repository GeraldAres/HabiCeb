package Screens.ProfileScreen

import database.UserSession
import models.PostRepository
import models.UserPost

class ProfilePresenter(private val view: ProfileContract.View) : ProfileContract.Presenter {

    private var isEditVisible = false

    override fun init() {
        view.updateUserInfo(UserSession.username)
        view.displayPosts(PostRepository.userPosts)
    }

    override fun onLogoutClicked() {
        view.navigateToSplash()
    }

    override fun onEditClicked() {
        isEditVisible = !isEditVisible
        view.toggleEditSection(isEditVisible)
    }

    override fun onSaveClicked(newName: String) {
        if (newName.isNotEmpty()) {
            UserSession.updateUsername(newName)
            view.updateUserInfo(newName)
            isEditVisible = false
            view.toggleEditSection(false)
            view.showMessage("Profile Updated")
        }
    }

    override fun onPostClicked(post: UserPost) {
        view.showPostDetail(post)
    }

    override fun onHomeClicked() {
        view.navigateToHome()
    }

    override fun onCartClicked() {
        view.navigateToCart()
    }
}
