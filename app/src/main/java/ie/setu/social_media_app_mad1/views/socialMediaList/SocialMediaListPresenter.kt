package ie.setu.social_media_app_mad1.views.socialMediaList

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import ie.setu.social_media_app_mad1.activities.SocialMediaMapActivity
import ie.setu.social_media_app_mad1.main.MainApp
import ie.setu.social_media_app_mad1.models.UserModel
import ie.setu.social_media_app_mad1.views.socialMedia.SocialMediaView

class SocialMediaListPresenter (val view: SocialMediaListView){

    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private var position: Int = 0

    init {
        app = view.application as MainApp
        registerMapCallback()
        registerRefreshCallback()
    }

    fun getUsers() = app.users.findAll()

    fun doAddUser() {
        val launcherIntent = Intent(view, SocialMediaView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditUser(user: UserModel, pos: Int) {
        val launcherIntent = Intent(view, SocialMediaView::class.java)
        launcherIntent.putExtra("user_edit", user)
        position = pos
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doShowUsersMap() {
        val launcherIntent = Intent(view, SocialMediaMapActivity::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) view.onRefresh()
                else // Deleting
                    if (it.resultCode == 99) view.onDelete(position)
            }
    }
    private fun registerMapCallback() {
        mapIntentLauncher = view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {  }
    }
}
