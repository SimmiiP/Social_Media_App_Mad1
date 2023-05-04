package ie.setu.social_media_app_mad1.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import ie.setu.social_media_app_mad1.R
import ie.setu.social_media_app_mad1.views.socialMediaList.SocialMediaListView

class SocialMediaSplashScreen : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val startButton = findViewById<Button>(R.id.button)
        val switchDarkMode = findViewById<Switch>(R.id.switchDarkMode)
        startButton.setOnClickListener{
            val intent = Intent(this, SocialMediaListView::class.java)
            startActivity(intent)
            finish()
        }

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.Theme_Social_Media_App_Mad1)
        } else{
            setTheme(R.style.Theme_Social_Media_App_Mad1)
        }

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }

    }

}