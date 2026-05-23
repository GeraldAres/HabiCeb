package database

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class UserData(
    val email: String = "johndoe@gmail.com",
    val username: String = "SeraPicks",
    val password: String = "12345678"
)

object UserSession {
    private val _userState = MutableStateFlow(UserData())
    val userState: StateFlow<UserData> = _userState.asStateFlow()

    var email: String
        get() = _userState.value.email
        set(value) {
            _userState.value = _userState.value.copy(email = value)
        }

    var username: String
        get() = _userState.value.username
        set(value) {
            _userState.value = _userState.value.copy(username = value)
        }

    var password: String
        get() = _userState.value.password
        set(value) {
            _userState.value = _userState.value.copy(password = value)
        }
    
    fun updateUsername(newName: String) {
        _userState.value = _userState.value.copy(username = newName)
    }
}