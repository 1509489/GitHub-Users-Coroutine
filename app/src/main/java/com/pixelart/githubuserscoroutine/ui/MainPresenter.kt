package com.pixelart.githubuserscoroutine.ui

import com.pixelart.githubuserscoroutine.network.NetworkHelper
import com.pixelart.githubuserscoroutine.network.NetworkService
import kotlinx.coroutines.*

class MainPresenter(/*private val networkService: NetworkService, */private val view: MainContract.View):MainContract.Presenter {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun getUsers(since: Int?) {
        scope.launch {
            val response = NetworkHelper.networkService().getUsers(since)
            withContext(Dispatchers.Main){
                if (response.code() == 200)
                    response.body()?.let { view.showUsrs(it) }
                else
                    view.showMessage(response.message())
            }
        }
    }

    override fun onStop() {
        job.cancel()
    }
}