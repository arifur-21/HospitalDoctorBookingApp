package com.example.hospitalmagapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hospitalmagapp.databinding.FragmentAddSpecialityListBinding
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask


class AddSpecialityList : Fragment() {

    private lateinit var binding: FragmentAddSpecialityListBinding
    private var imageUri: Uri? = null
    private var myUrl = ""
    private lateinit var db: FirebaseFirestore
    private var storagePostPicRef: StorageReference?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentAddSpecialityListBinding.inflate(layoutInflater)
        db = FirebaseFirestore.getInstance()
        storagePostPicRef = FirebaseStorage.getInstance().reference.child("Speciality Picture")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addSpecialityServiceImageId.setOnClickListener{
            val intent = Intent()
            intent.setAction(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(intent, 77)
        }

        binding.addSpecialityServicesBtnId.setOnClickListener {

            saveProduct()
        }
    }

    private fun saveProduct() {


        val title = binding.addSpecialityServiceTitleId.text.toString()



        if (title.isEmpty()){
            binding.addSpecialityServiceTitleId.setError("Enter your Product Description")
        }
        /*else if (imageUri == null){
            Toast.makeText(requireContext(),"Select your Image", Toast.LENGTH_SHORT).show()
        }*/
        else{
            /// store image firebaseStorage and get image url
            val fileRef = storagePostPicRef!!.child(System.currentTimeMillis().toString() + ".jpg")
            var uploadTask: StorageTask<*>
            uploadTask = fileRef.putFile(imageUri!!)
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw  it
                    }
                }
                return@Continuation fileRef.downloadUrl
            }).addOnCompleteListener(OnCompleteListener { task ->

                if (task.isSuccessful) {
                    val downloadUrl = task.result
                    myUrl = downloadUrl.toString()

                    val userInfo: HashMap<String, Any> = HashMap()
                    userInfo["title"] = title.capitalize()
                    userInfo["image"] = myUrl
                    userInfo["type"] = title

                    db.collection("specialities").add(userInfo).addOnCompleteListener { task->
                        if (task.isSuccessful){
                            Toast.makeText(requireContext(),"product added successfull", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            })
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode== 77 && resultCode == Activity.RESULT_OK && data != null ){
            imageUri = data.data
            binding.addSpecialityServiceImageId.setImageURI(imageUri)

            Toast.makeText(requireContext(),"picture uploaded", Toast.LENGTH_SHORT).show()
        }
    }

}