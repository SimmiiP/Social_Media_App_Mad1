package ie.setu.social_media_app_mad1.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import ie.setu.social_media_app_mad1.R
import ie.setu.social_media_app_mad1.views.socialMediaList.SocialMediaListView

class SocialMediaSplashScreen : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val startButton = findViewById<Button>(R.id.button)
        startButton.setOnClickListener{
            val intent = Intent(this, SocialMediaListView::class.java)
            startActivity(intent)
            finish()
        }
    }

}