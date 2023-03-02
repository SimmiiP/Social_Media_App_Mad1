package ie.setu.social_media_app_mad1.models

import timber.log.Timber.i

var lastId = 0L
internal fun getId(): Long {
    return lastId++
}

class UserMemStore : UserStore {

    val users = ArrayList<UserModel>()

    override fun findAll(): List<UserModel> {
        return users
    }

    override fun create(user: UserModel) {
        users.add(user)
    }

    override fun update(user: UserModel){
        var foundUser: UserModel? = users.find{ p -> p.id == user.id }
        if (foundUser !=null){
            foundUser.socialmedia = user.socialmedia
            foundUser.username = user.username
            foundUser.password = user.password
            foundUser.caption = user.caption
            foundUser.profilepic = user.profilepic
            foundUser.followers = user.followers
            logAll()
        }
    }

    fun logAll(){
        users.forEach{ i("${it}")}
    }
}