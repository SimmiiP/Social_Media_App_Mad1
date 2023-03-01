package ie.setu.social_media_app_mad1.models

import timber.log.Timber.i

class UserMemStore : UserStore {

    val users = ArrayList<UserModel>()

    override fun findAll(): List<UserModel> {
        return users
    }

    override fun create(user: UserModel) {
        users.add(user)
    }

    fun logAll(){
        users.forEach{ i("${it}")}
    }
}