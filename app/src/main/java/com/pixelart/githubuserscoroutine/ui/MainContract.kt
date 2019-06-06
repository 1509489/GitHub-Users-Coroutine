package com.pixelart.githubuserscoroutine.ui

import com.pixelart.githubuserscoroutine.model.User

interface MainContract {
    interface View{
        fun showUsrs(users: List<User>)
        fun showMessage(message: String)
    }
    interface Presenter{
        fun getUsers(since: Int? = null)
        fun onStop()
    }
}