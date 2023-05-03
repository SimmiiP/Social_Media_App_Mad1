package ie.setu.social_media_app_mad1.views.socialMediaList

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ie.setu.social_media_app_mad1.R
import ie.setu.social_media_app_mad1.adapters.UserAdapter
import ie.setu.social_media_app_mad1.adapters.UserListener
import ie.setu.social_media_app_mad1.databinding.ActivitySocialMediaListBinding
import ie.setu.social_media_app_mad1.main.MainApp
import ie.setu.social_media_app_mad1.models.UserModel


class SocialMediaListView : AppCompatActivity(), UserListener{

    private var position: Int = 0
    lateinit var app: MainApp
    private lateinit var binding: ActivitySocialMediaListBinding
    lateinit var presenter: SocialMediaListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySocialMediaListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        presenter = SocialMediaListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadUsers()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddUser() }
            R.id.item_map -> { presenter.doShowUsersMap() }
        }
        return super.onOptionsItemSelected(item)
    }

    private val mapIntentLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { }


    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.users.findAll().size)
            }
        }
    override fun onUserClick(user: UserModel, position: Int) {
        this.position = position
        presenter.doEditUser(user, this.position)
    }

    private fun loadUsers() {
        binding.recyclerView.adapter = UserAdapter(presenter.getUsers(),this)
        onRefresh()
    }

    fun onRefresh() {
        binding.recyclerView.adapter?.
                notifyItemRangeChanged(0,presenter.getUsers().size)
    }

    fun onDelete(position: Int){
        binding.recyclerView.adapter?.
        notifyItemRangeChanged(0,presenter.getUsers().size)
    }

}
