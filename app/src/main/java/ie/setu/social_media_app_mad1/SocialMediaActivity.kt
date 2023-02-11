package ie.setu.social_media_app_mad1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.setu.social_media_app_mad1.databinding.ActivitySocialmediaBinding
import ie.setu.social_media_app_mad1.main.mainApp
import ie.setu.social_media_app_mad1.models.UserModel
import timber.log.Timber
import timber.log.Timber.i


class SocialMediaActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySocialmediaBinding
    var user = UserModel()
    lateinit var app: mainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySocialmediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as mainApp
        i("Placemark Activity started...")
        binding.btnAdd.setOnClickListener() {
            user.username = binding.accountUsername.text.toString()
            user.password = binding.accountPassword.text.toString()
            if (user.username.isNotEmpty()) {
                app.users.add(user.copy())
                i("add Button Pressed: ${user}")
                for (i in app.users.indices)
                { i("User[$i]:${this.app.users[i]}") }
            }
            else {
                Snackbar.make(it,"Please Enter a Username", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        
    }

}
