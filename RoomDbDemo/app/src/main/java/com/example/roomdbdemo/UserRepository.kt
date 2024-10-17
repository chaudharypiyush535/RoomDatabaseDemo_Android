package com.example.roomdbdemo

class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getAllUsers() : List<User> {
        return userDao.getAllUsers()
    }
}