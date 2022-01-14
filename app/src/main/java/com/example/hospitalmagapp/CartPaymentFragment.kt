package com.example.hospitalmagapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hospitalmagapp.databinding.FragmentCartPaymentBinding
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCCustomerInfoInitializer
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCProductInitializer
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCShipmentInfoInitializer
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel
import com.sslwireless.sslcommerzlibrary.model.util.SSLCCurrencyType
import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener


class CartPaymentFragment : Fragment(), SSLCTransactionResponseListener {

private lateinit var binding: FragmentCartPaymentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCartPaymentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val sslCommerzInitialization = SSLCommerzInitialization(
            "sbs60605f415c440", "sbs60605f415c440@ssl", 100.0,
            SSLCCurrencyType.BDT, "123456789098765", "yourProductType", SSLCSdkType.TESTBOX
        )

        val customerInfoInitializer = SSLCCustomerInfoInitializer(
            "customer name", "customer email",
            "address", "dhaka", "1214", "Bangladesh", "phoneNumber"
        )

        val productInitializer = SSLCProductInitializer(
            "food", "food", SSLCProductInitializer.ProductProfile.TravelVertical(
                "Travel", "xyz",
                "A", "12", "Dhk-Syl"
            )
        )

        val shipmentInfoInitializer = SSLCShipmentInfoInitializer(
            "Courier", 2, SSLCShipmentInfoInitializer.ShipmentDetails(
                "AA", "Address 1",
                "Dhaka", "1000", "BD"
            )
        )


        IntegrateSSLCommerz
            .getInstance(context)
            .addSSLCommerzInitialization(sslCommerzInitialization) // .addCustomerInfoInitializer(customerInfoInitializer)
            //.addProductInitializer(productInitializer)
            .buildApiCall(this)


    }

    override fun transactionSuccess(p0: SSLCTransactionInfoModel?) {
       binding.successId.setText(p0!!.apiConnect +"--"+ p0!!.status)
    }

    override fun transactionFail(p0: String?) {
        binding.faildId.text = p0
    }

    override fun merchantValidationError(p0: String?) {
        binding.errorId.text = p0
    }

}