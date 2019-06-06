package com.pixelart.githubuserscoroutine.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pixelart.githubuserscoroutine.R
import com.pixelart.githubuserscoroutine.adapter.UsersAdapter
import com.pixelart.githubuserscoroutine.model.User

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), MainContract.View, UsersAdapter.OnItemClickedListener {
    private lateinit var presenter: MainContract.Presenter
    private lateinit var usersAdapter: UsersAdapter
    private var userList = ArrayList<User>()

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        tvInfo.visibility = View.INVISIBLE
        fab.visibility = View.INVISIBLE

        presenter = MainPresenter(this)
        presenter.getUsers()
        usersAdapter = UsersAdapter(this)
    }
    
    override fun onResume() {
        super.onResume()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
            adapter = usersAdapter
        }
    }

    override fun getContext(): Context = this

    override fun showUsrs(users: List<User>) {
        userList = users as ArrayList<User>
        usersAdapter.submitList(users)
    }

    @SuppressLint("RestrictedApi")
    override fun showMessage(message: String) {
        if (message.contains("OK", true)){
            tvInfo.visibility = View.INVISIBLE
            fab.visibility = View.INVISIBLE
        }else if(message.contains("No Internet Connection", true)) {
            tvInfo.visibility = View.VISIBLE
            fab.visibility = View.VISIBLE

            fab.setOnClickListener {
                presenter.getUsers()
            }
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }
    
    override fun onItemClicked(position: Int) {
        Toast.makeText(this, userList[position].login, Toast.LENGTH_SHORT).show()
    }
    
    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
