package ie.setu.social_media_app_mad1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.setu.social_media_app_mad1.databinding.CardUserBinding
import ie.setu.social_media_app_mad1.models.UserModel

class UserAdapter constructor(private var users: List<UserModel>) :
    RecyclerView.Adapter<UserAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val user = users[holder.adapterPosition]
        holder.bind(user)
    }

    override fun getItemCount(): Int = users.size

    class MainHolder(private val binding: CardUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserModel) {
            binding.accountUsername.text = user.username
            binding.accountCaption.text = user.caption
        }
    }

}