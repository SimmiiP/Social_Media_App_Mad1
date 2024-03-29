package ie.setu.social_media_app_mad1.main

import android.app.Application
import com.github.ajalt.timberkt.Timber
import ie.setu.social_media_app_mad1.models.UserJSONStore
import ie.setu.social_media_app_mad1.models.UserMemStore
import ie.setu.social_media_app_mad1.models.UserModel
import ie.setu.social_media_app_mad1.models.UserStore
import timber.log.Timber.Forest.i


class MainApp : Application() {

    lateinit var users: UserStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        // users = UserMemStore()
        users = UserJSONStore(applicationContext)
        i("Social Media started")
    }
}