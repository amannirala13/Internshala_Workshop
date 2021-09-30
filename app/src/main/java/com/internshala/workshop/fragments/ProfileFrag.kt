package com.internshala.workshop.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.internshala.auth.activities.Authentication
import com.internshala.utils.isValidUser
import com.internshala.utils.logoutUser
import com.internshala.workshop.R
import com.internshala.workshop.activities.MainActivity

class ProfileFrag : Fragment() {

    private lateinit var baseActivity: MainActivity

    private lateinit var inValidUserUIHolder: ConstraintLayout
    private lateinit var validUserUIHolder: ConstraintLayout

    private lateinit var signInBtn: Button
    private lateinit var logoutBtn: ImageButton
    private lateinit var enrolledRecycler: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseActivity = this.requireActivity() as MainActivity

        inValidUserUIHolder = view.findViewById(R.id.profile_invalid_user_holder)
        validUserUIHolder = view.findViewById(R.id.profile_valid_user_holder)

        signInBtn = view.findViewById(R.id.profile_sign_in_btn)
        logoutBtn = view.findViewById(R.id.profile_logout_btn)

        val startActivityResultListener = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data?.getBooleanExtra("success",false) == true){
                    loadValidUserData()
                }
            }
        }

        signInBtn.setOnClickListener{
            startActivityResultListener.launch(Intent(this.requireActivity(), Authentication::class.java ))
        }

        logoutBtn.setOnClickListener{
            logoutUser(this.requireActivity())
            showInvalidUserUI()
        }

        if(isValidUser(this.requireActivity())){
            loadValidUserData()
        }

    }

    /**
     *
     */
    private fun loadValidUserData() {
        inValidUserUIHolder.visibility = View.GONE
        validUserUIHolder.visibility = View.VISIBLE
    }

    /**
     *
     */
    private fun showInvalidUserUI(){
        inValidUserUIHolder.visibility = View.VISIBLE
        validUserUIHolder.visibility = View.GONE
    }
}