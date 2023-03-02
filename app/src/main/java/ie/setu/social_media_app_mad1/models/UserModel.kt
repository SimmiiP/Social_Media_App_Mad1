package ie.setu.social_media_app_mad1.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(var id: Long = 0,
                     var username: String ="",
                     var password: String = "",
                     var caption: String = "",
                     var profilepic: Uri = Uri.EMPTY) : Parcelable
// var following: Int = 0, for an int use value instead of text


