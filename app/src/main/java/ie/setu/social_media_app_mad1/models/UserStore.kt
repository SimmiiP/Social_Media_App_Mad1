package ie.setu.social_media_app_mad1.models

interface UserStore {
    fun findAll(): List<UserModel>
    fun create(user: UserModel)

    fun update(user:UserModel)

    fun delete(user: UserModel)

    fun findById(id:Long): UserModel?
}