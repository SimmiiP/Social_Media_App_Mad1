package ie.setu.social_media_app_mad1.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(var id: Long = 0,
                     var socialmedia: String = "",
                     var username: String ="",
                     var password: String = "",
                     var caption: String = "",
                     var followers: Int = 0,
                     var activeNow: Boolean = true,
                     var profilepic: Uri = Uri.EMPTY,
                     var lat: Double = 0.0,
                     var lng: Double = 0.0,
                     var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat:Double = 0.0,
                    var lng:Double = 0.0,
                    var zoom:Float = 0f) : Parcelable

