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
import com.example.hospitalmagapp.databinding.FragmentAddDoctorProfileBinding
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask


class AddDoctorProfileFragment : Fragment() {

    private lateinit var binding: FragmentAddDoctorProfileBinding

    private var imageUri: Uri? = null
    private var myUrl = ""
    private lateinit var db: FirebaseFirestore
    private var storagePostPicRef: StorageReference?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddDoctorProfileBinding.inflate(layoutInflater)
        db = FirebaseFirestore.getInstance()
        storagePostPicRef = FirebaseStorage.getInstance().reference.child("Doctor Picture")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addDoctorProfileImgId.setOnClickListener{
            val intent = Intent()
            intent.setAction(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(intent, 77)
        }

        binding.addDoctorProfileSaveBtnId.setOnClickListener {
            saveDoctorProfile()
        }
    }

    private fun saveDoctorProfile() {

        val name = binding.addDorctorProfileNameId.text.toString()
        val address = binding.addDorctorProfileAddressId.text.toString()
        val experience = binding.addDorctorProfileExperienceId.text.toString()
        val qualification = binding.addDorctorProfileQualificationId.text.toString()
        val email = binding.addDorctorProfileEmailId.text.toString()
        val fees = binding.addDorctorProfileFeesId.text.toString()
        val speciality = binding.addDorctorProfileSpecialityId.text.toString()

        if (name.isEmpty()){binding.addDorctorProfileNameId.setError("Enter your Name")}
        else if (address.isEmpty()){binding.addDorctorProfileAddressId.setError("Enter your Address")}
        else if (experience.isEmpty()){binding.addDorctorProfileExperienceId.setError("Enter your Experience")}
        else if (qualification.isEmpty()){binding.addDorctorProfileQualificationId.setError("Enter your Qualification")}
        else if (email.isEmpty()){binding.addDorctorProfileEmailId.setError("Enter your Email")}
        else if (fees.isEmpty()){binding.addDorctorProfileFeesId.setError("Enter your Fees")}
        else if (speciality.isEmpty()){
            binding.addDorctorProfileSpecialityId.setError("Enter your Speciality")
        }
        else if (imageUri == null){
            Toast.makeText(requireContext(), "Select your Image", Toast.LENGTH_SHORT).show()
        }
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

                    val doctorInfo: HashMap<String, Any> = HashMap()

                    doctorInfo["image"] = myUrl
                    doctorInfo["name"] = name
                    doctorInfo["address"] = address
                    doctorInfo["experience"] = experience.toLong()
                    doctorInfo["fees"] = fees.toLong()
                    doctorInfo["email"] = email
                    doctorInfo["qualification"] = qualification
                    doctorInfo["speciality"] = speciality.capitalize()
                    doctorInfo["type"] = speciality

                    db.collection("doctorProfile").add(doctorInfo).addOnCompleteListener { task->
                        if (task.isSuccessful){
                            Toast.makeText(requireContext(),"Profile added successfull", Toast.LENGTH_SHORT).show()
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
            binding.addDoctorProfileImgId.setImageURI(imageUri)

            Toast.makeText(requireContext(),"picture uploaded", Toast.LENGTH_SHORT).show()
        }
    }

}