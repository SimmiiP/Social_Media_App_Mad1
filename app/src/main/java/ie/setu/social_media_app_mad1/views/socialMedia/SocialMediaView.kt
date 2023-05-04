package ie.setu.social_media_app_mad1.views.socialMedia

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.NumberPicker
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatToggleButton
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.setu.social_media_app_mad1.R
import ie.setu.social_media_app_mad1.databinding.ActivitySocialmediaBinding
import ie.setu.social_media_app_mad1.models.Location
import ie.setu.social_media_app_mad1.models.UserModel
import timber.log.Timber.Forest.i


class SocialMediaView : AppCompatActivity() {


    private lateinit var binding: ActivitySocialmediaBinding
    private lateinit var presenter: SocialMediaPresenter
    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySocialmediaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        presenter = SocialMediaPresenter(this)


        i("Social Media Activity started...")

        val socialMediaSpinner = findViewById<Spinner>(R.id.dropdownMenu)
        socialMediaSpinner.adapter = ArrayAdapter.createFromResource(
            this, R.array.media_array, android.R.layout.simple_spinner_item)

        presenter = SocialMediaPresenter(this)

        binding.btnAdd.setOnClickListener() {

            if (binding.accountUsername.text.toString().isEmpty()){
                Snackbar.make(binding.root, R.string.fill_all_fields, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                // presenter.cacheUser(binding.accountUsername.text.toString(), binding.accountCaption.text.toString())
                presenter.doAddOrSave(binding.accountUsername.text.toString(),
                    binding.accountPassword.text.toString(),
                    binding.accountCaption.text.toString(),
                    binding.numberPicker.value.toInt(),
                    binding.dropdownMenu.selectedItem.toString())
            }
        }

        binding.chooseImage.setOnClickListener {
            presenter.cacheUser(binding.accountUsername.text.toString(), binding.accountCaption.text.toString())
            presenter.doSelectImage()
        }

        binding.photoLocation.setOnClickListener{
            val location = Location(48.8584, 2.2945, 15f)
            presenter.cacheUser(binding.accountUsername.text.toString(), binding.accountCaption.text.toString())
            presenter.doSetLocation()
            }

        val numberPicker = findViewById<NumberPicker>(R.id.numberPicker)
        numberPicker.minValue = 0
        numberPicker.maxValue = 10000
        numberPicker.wrapSelectorWheel = false

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cancel, menu)
        val deleteMenu: MenuItem = menu.findItem(R.id.item_delete)
        deleteMenu.isVisible = presenter.edit
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
            } R.id.item_cancel -> { presenter.doCancel()}
        }
        return super.onOptionsItemSelected(item)
    }

    fun showUser(user: UserModel){
        binding.accountUsername.setText(user.username)
        binding.accountPassword.setText(user.password)
        binding.accountCaption.setText(user.caption)
        binding.numberPicker.value = user.followers
        binding.btnAdd.setText(R.string.update_user)
        Picasso.get()
            .load(user.profilepic)
            .into(binding.userImage)
        if (user.profilepic != Uri.EMPTY){
            binding.chooseImage.setText(R.string.select_profile_image)
        }
    }

    fun updateImage(image:Uri){
        i("Image updated")
        Picasso.get()
            .load(image)
            .into(binding.userImage)
        binding.chooseImage.setText(R.string.select_profile_image)
    }
}
