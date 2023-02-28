package ie.setu.social_media_app_mad1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.setu.social_media_app_mad1.databinding.ActivitySocialMediaListBinding
import ie.setu.social_media_app_mad1.databinding.CardUserBinding
import ie.setu.social_media_app_mad1.main.MainApp
import ie.setu.social_media_app_mad1.models.UserModel
import ie.setu.social_media_app_mad1.R



class SocialMediaListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivitySocialMediaListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySocialMediaListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title


        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = UserAdapter(app.users)

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
                notifyItemRangeChanged(0,app.users.size)
            }
        }
}

    class UserAdapter constructor(private var users: List<UserModel>) :
        RecyclerView.Adapter<UserAdapter.MainHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
            val binding = CardUserBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return MainHolder(binding)
        }

        override fun onBindViewHolder(holder: MainHolder, position: Int) {
            val user = users[holder.adapterPosition]
            holder.bind(user)
        }

        override fun getItemCount(): Int = users.size

        class MainHolder(private val binding: CardUserBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(user: UserModel) {
                binding.accountUsername.text = user.username
                // binding.description.text = user.
            }
        }

    }