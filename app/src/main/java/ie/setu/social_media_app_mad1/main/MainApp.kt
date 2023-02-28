package ie.setu.social_media_app_mad1.main

import android.app.Application
import com.github.ajalt.timberkt.Timber
import ie.setu.social_media_app_mad1.models.UserModel
import timber.log.Timber.i

class MainApp : Application() {

    val users = ArrayList<UserModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Social Media started")
    }
}