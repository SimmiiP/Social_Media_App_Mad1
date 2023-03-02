package ie.setu.social_media_app_mad1.helpers

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import ie.setu.social_media_app_mad1.R

fun showImagePicker(intentLauncher : ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.select_profile_image.toString())
    intentLauncher.launch(chooseFile)
}

