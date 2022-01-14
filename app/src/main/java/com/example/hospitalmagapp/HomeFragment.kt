package com.example.hospitalmagapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.hospitalmagapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageSlider = view.findViewById<ImageSlider>(R.id.imageSlider)
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.sliderimg1))
        imageList.add(SlideModel(R.drawable.sliderimg2))
        imageList.add(SlideModel(R.drawable.sliderimg3))
        imageList.add(SlideModel(R.drawable.sliderimg4))

        imageSlider.setImageList(imageList, ScaleTypes.FIT)


        binding.specialitiesId.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_specialitiesFragment)
        }

        binding.serviceId.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_serviceFragment)
        }
        binding.emergencyId.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_emergentyFragment)
        }
    }

    }

