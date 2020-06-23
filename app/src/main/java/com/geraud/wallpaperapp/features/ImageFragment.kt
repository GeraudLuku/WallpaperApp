package com.geraud.wallpaperapp.features

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore.Images
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.geraud.wallpaperapp.R
import com.geraud.wallpaperapp.model.Src
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_image.*


class ImageFragment : Fragment() {

    private lateinit var navController: NavController

    private var photo: Src? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        //get argument
        photo = arguments?.let { ImageFragmentArgs.fromBundle(it).src }

        set_wallpaper.setOnClickListener {

            set_wallpaper.isEnabled = false
            set_wallpaper.text = "Wallpaper Set"

            //set image as wallpaper
            val bitmap = wallpaer_view.drawable.toBitmap()
            val task = SetWallpaperTask(it.context, bitmap)
            task.execute(true)

            Toasty.success(it.context, "Wallpaper Set", Toast.LENGTH_SHORT, true).show();

        }

        share.setOnClickListener {
            //share image
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share))
            val path: String =
                Images.Media.insertImage(
                    it.context.contentResolver,
                    wallpaer_view.drawable.toBitmap(),
                    "",
                    null
                )
            val screenshotUri = Uri.parse(path)

            intent.putExtra(Intent.EXTRA_STREAM, screenshotUri)
            intent.type = "image/*"
            startActivity(Intent.createChooser(intent, "Share image via..."))
        }

        cancel_btn.setOnClickListener {
            navController.popBackStack()
        }

    }


    companion object {
        class SetWallpaperTask internal constructor(
            private val context: Context,
            private val bitmap: Bitmap
        ) :
            AsyncTask<Boolean, String, String>() {
            override fun doInBackground(vararg params: Boolean?): String? {
                val wallpaperManager: WallpaperManager = WallpaperManager.getInstance(context)
                wallpaperManager.setBitmap(bitmap)
                return "Wallpaper Set"
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (photo != null) {
            //load image on image view
            Glide.with(this).load(photo?.portrait)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        //failed to load image
                        activity?.let {
                            Toasty.error(it, "Could not load image", Toast.LENGTH_SHORT, true)
                                .show()
                        }
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        //hide progressbar
                        progressBar.visibility = View.GONE
                        return false
                    }

                }).into(wallpaer_view)
        }
    }

    override fun onStop() {
        super.onStop()
        Glide.with(requireContext()).clear(wallpaer_view)
    }


}
