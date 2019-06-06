package com.pixelart.githubuserscoroutine.ui

import android.util.Log
import com.pixelart.githubuserscoroutine.common.NoConnectivityException
import com.pixelart.githubuserscoroutine.network.ConnectivityInterceptorImpl
import com.pixelart.githubuserscoroutine.network.NetworkHelper
import kotlinx.coroutines.*

class MainPresenter(private val view: MainContract.View):MainContract.Presenter {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun getUsers(since: Int?) {
        scope.launch {
            try {
                val response = NetworkHelper.networkService(ConnectivityInterceptorImpl(view.getContext())).getUsers(since)
                withContext(Dispatchers.Main){
                    if (response.code() == 200) {
                        response.body()?.let { view.showUsrs(it) }
                        view.showMessage(response.message())
                    }
                    else
                        view.showMessage(response.message())
                }
            }catch (e: NoConnectivityException){
                Log.e("Connectivity", "No Internet Connection", e)
                withContext(Dispatchers.Main){
                    view.showMessage("No Internet Connection")
                }
            }
        }
    }

    override fun onStop() {
        job.cancel()
    }
}