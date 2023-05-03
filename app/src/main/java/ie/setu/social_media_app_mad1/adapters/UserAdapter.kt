package ie.setu.social_media_app_mad1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.setu.social_media_app_mad1.databinding.CardUserBinding
import ie.setu.social_media_app_mad1.models.UserModel

interface UserListener {
    fun onUserClick(user: UserModel, position : Int)
}
class UserAdapter constructor(private var users: List<UserModel>,
private val listener: UserListener) :
    RecyclerView.Adapter<UserAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val user = users[holder.adapterPosition]
        holder.bind(user, listener)
    }

    override fun getItemCount(): Int = users.size

    class MainHolder(private val binding: CardUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserModel, listener: UserListener) {
            binding.mediaName.text = user.socialmedia
            binding.accountUsername.text = user.username
            binding.accountCaption.text = user.caption
            binding.accountFollowing.text = user.followers.toString()
            Picasso.get().load(user.profilepic).resize(400,400).into(binding.userImage)
            binding.root.setOnClickListener{listener.onUserClick(user, adapterPosition)}
        }
    }

}