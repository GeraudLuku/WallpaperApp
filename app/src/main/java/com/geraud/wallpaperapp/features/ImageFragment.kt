package com.geraud.wallpaperapp.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.geraud.wallpaperapp.R
import kotlinx.android.synthetic.main.fragment_image.*


class ImageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //make fragment fullscreen
//        activity?.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        activity?.window?.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        //get argument
        val photo = arguments?.let { ImageFragmentArgs.fromBundle(it).src }

        Glide.with(view.context).load(photo?.original).into(wallpaer_view)

    }


}
