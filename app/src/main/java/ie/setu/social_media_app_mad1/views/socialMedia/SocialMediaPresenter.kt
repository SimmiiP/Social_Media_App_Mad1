package ie.setu.social_media_app_mad1.views.socialMedia

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import ie.setu.social_media_app_mad1.databinding.ActivitySocialmediaBinding
import ie.setu.social_media_app_mad1.helpers.showImagePicker
import ie.setu.social_media_app_mad1.main.MainApp
import ie.setu.social_media_app_mad1.models.Location
import ie.setu.social_media_app_mad1.models.UserModel
import ie.setu.social_media_app_mad1.views.editLocation.EditLocationView
import timber.log.Timber

class SocialMediaPresenter(private val view: SocialMediaView) {

    var user = UserModel()
    var app: MainApp = view.application as MainApp
    var binding: ActivitySocialmediaBinding = ActivitySocialmediaBinding.inflate(view.layoutInflater)

    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>
    var edit = false;

    init {
        if (view.intent.hasExtra("user_edit")) {
            edit = true
            user = view.intent.extras?.getParcelable("user_edit")!!
            view.showUser(user)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }

    fun doAddOrSave(username: String, password: String, caption: String, followers: Int, socialMedia: String) {
        user.username = username
        user.password = password
        user.caption = caption
        user.followers = followers
        user.socialmedia = socialMedia
        if (edit) {
            app.users.update(user)
        } else {
            app.users.create(user)
        }
        view.setResult(RESULT_OK);
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }
    fun doDelete() {
        view.setResult(99)
        app.users.delete(user)
        view.finish()
    }
    fun doSelectImage() {
        showImagePicker(imageIntentLauncher,view)
    }
    fun doSetLocation() {
        val location = Location(52.245696, -7.139102, 15f)
        if (user.zoom != 0f) {
            location.lat =  user.lat
            location.lng = user.lng
            location.zoom = user.zoom
        }
        val launcherIntent = Intent(view, EditLocationView::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }
    fun cacheUser (username: String, password: String) {
        user.username = username;
        user.password = password
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            user.profilepic = result.data!!.data!!
                            view.contentResolver.takePersistableUriPermission(user.profilepic,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            view.updateImage(user.profilepic)
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { }
                    else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            user.lat = location.lat
                            user.lng = location.lng
                            user.zoom = location.zoom
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}
