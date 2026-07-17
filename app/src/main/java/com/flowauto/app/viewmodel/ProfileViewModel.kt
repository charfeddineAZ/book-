package com.flowauto.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flowauto.app.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // محاكاة تحميل ملف المستخدم
                val mockUser = User(
                    id = 1,
                    name = "مستخدم",
                    email = "user@example.com",
                    bio = "قارئ متحمس"
                )
                _user.value = mockUser
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateUserProfile(user: User) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // محاكاة تحديث ملف المستخدم
                _user.value = user
            } finally {
                _isLoading.value = false
            }
        }
    }
}
