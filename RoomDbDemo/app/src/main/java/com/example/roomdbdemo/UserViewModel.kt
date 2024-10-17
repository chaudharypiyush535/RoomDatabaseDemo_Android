package com.example.roomdbdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    fun insertUser(user: User) =
        viewModelScope.launch {
            repository.insertUser(user)
        }

    fun getAllUsers(callback : (List<User>) -> Unit) =
        viewModelScope.launch {
            val users = repository.getAllUsers()
            callback(users)
        }
}

class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}