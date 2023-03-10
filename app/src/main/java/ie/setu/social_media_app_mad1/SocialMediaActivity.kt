package ie.setu.social_media_app_mad1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.NumberPicker
import android.widget.Spinner
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.setu.social_media_app_mad1.databinding.ActivitySocialmediaBinding
import ie.setu.social_media_app_mad1.helpers.showImagePicker
import ie.setu.social_media_app_mad1.main.MainApp
import ie.setu.social_media_app_mad1.models.UserModel
import timber.log.Timber
import timber.log.Timber.i


class SocialMediaActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySocialmediaBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>

    var user = UserModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivitySocialmediaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerImagePickerCallback()
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp

        i("Social Media Activity started...")

        val socialMediaSpinner = findViewById<Spinner>(R.id.dropdownMenu)
        socialMediaSpinner.adapter = ArrayAdapter.createFromResource(
            this, R.array.media_array, android.R.layout.simple_spinner_item)

        if (intent.hasExtra("user_edit")) {
            edit = true
            user = intent.extras?.getParcelable("user_edit")!!
            binding.accountUsername.setText(user.username)
            binding.accountPassword.setText(user.password)
            binding.accountCaption.setText(user.caption)
            binding.numberPicker.value = user.followers
            binding.btnAdd.setText(R.string.update_user)
            Picasso.get()
                .load(user.profilepic)
                .into(binding.userImage)

        }

        binding.btnAdd.setOnClickListener() {

            user.username = binding.accountUsername.text.toString()
            user.password = binding.accountPassword.text.toString()
            user.caption = binding.accountCaption.text.toString()
                user.followers = binding.numberPicker.value.toInt()
            val socialMediaName = socialMediaSpinner.selectedItem.toString()
            if (socialMediaName.isEmpty() || user.username.isEmpty() || user.password.isEmpty()) {
                Snackbar.make(it, R.string.fill_all_fields, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                user.socialmedia = socialMediaName

                if (edit) {
                    app.users.update(user.copy())
                } else {
                    app.users.create(user.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
        }


        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }


        val numberPicker = findViewById<NumberPicker>(R.id.numberPicker)
        numberPicker.minValue = 0
        numberPicker.maxValue = 10000
        numberPicker.wrapSelectorWheel = false

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cancel, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.item_delete -> {
                app.users.delete(user.copy())
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }


    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            user.profilepic = result.data!!.data!!
                            Picasso.get()
                                .load(user.profilepic)
                                .into(binding.userImage)
                        } // end of if
                    }
                    RESULT_CANCELED -> { }
                    else -> { }
                }
            }
    }

}
