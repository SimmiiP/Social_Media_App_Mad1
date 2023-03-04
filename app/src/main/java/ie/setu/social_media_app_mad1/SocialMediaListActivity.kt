package ie.setu.social_media_app_mad1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ie.setu.social_media_app_mad1.adapters.UserAdapter
import ie.setu.social_media_app_mad1.adapters.UserListener
import ie.setu.social_media_app_mad1.databinding.ActivitySocialMediaListBinding
import ie.setu.social_media_app_mad1.main.MainApp
import ie.setu.social_media_app_mad1.models.UserModel


class SocialMediaListActivity : AppCompatActivity(), UserListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivitySocialMediaListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySocialMediaListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = UserAdapter(app.users.findAll(),this)

        registerRefreshCallback()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, SocialMediaActivity::class.java)
                getResult.launch(launcherIntent)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.users.findAll().size)
            }
        }
    override fun onUserClick(user: UserModel) {
        val launcherIntent = Intent(this, SocialMediaActivity::class.java)
        launcherIntent.putExtra("user_edit", user)
        getClickResult.launch(launcherIntent)
        refreshIntentLauncher.launch(launcherIntent)

    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.users.findAll().size)
            }
        }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadUsers() }
    }

    private fun loadUsers() {
        showLandmarks(app.users.findAll())
    }

    fun showLandmarks (users: List<UserModel>) {
        binding.recyclerView.adapter = UserAdapter(users, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

}
