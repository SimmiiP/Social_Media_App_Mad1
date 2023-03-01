package ie.setu.social_media_app_mad1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import ie.setu.social_media_app_mad1.databinding.ActivitySocialmediaBinding
import ie.setu.social_media_app_mad1.main.MainApp
import ie.setu.social_media_app_mad1.models.UserModel
import timber.log.Timber
import timber.log.Timber.i


class SocialMediaActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySocialmediaBinding
    var user = UserModel()
    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySocialmediaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)


        app = application as MainApp
        i("Social Media Activity started...")
        binding.btnAdd.setOnClickListener() {
            user.username = binding.accountUsername.text.toString()
            user.password = binding.accountPassword.text.toString()
            user.caption = binding.accountCaption.text.toString()
            if (user.username.isNotEmpty() && user.password.isNotEmpty()) {
                app.users.add(user.copy())
                i("add Button Pressed: $user")
                for (i in app.users.indices)
                { i("User[$i]:${this.app.users[i]}")
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,"Please input a username and password", Snackbar.LENGTH_LONG)
                    .show()
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cancel, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
