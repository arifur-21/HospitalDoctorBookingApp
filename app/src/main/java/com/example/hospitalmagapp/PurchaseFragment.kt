package com.example.hospitalmagapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.hospitalmagapp.databinding.FragmentPurchaseBinding

class PurchaseFragment : Fragment() {

    private lateinit var binding: FragmentPurchaseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPurchaseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val fees : String? = bundle!!.getString("fees")

        binding.purchaseTkId.text = fees

        binding.purchaseBtnId.setOnClickListener {


               Navigation.findNavController(it).navigate(R.id.action_purchaseFragment_to_cartPaymentFragment)

           }

    }

}