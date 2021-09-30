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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.internshala.auth.activities.Authentication
import com.internshala.database.helpers.DBHelper
import com.internshala.utils.getCurrentUserID
import com.internshala.utils.isValidUser
import com.internshala.utils.logoutUser
import com.internshala.workshop.R
import com.internshala.workshop.activities.MainActivity
import com.internshala.workshop.adapters.ProfileEnrolledRecyclerAdapter
import com.internshala.workshop.adapters.WorkshopRecyclerAdapter

class ProfileFrag : Fragment() {

    private lateinit var baseActivity: MainActivity

    private lateinit var inValidUserUIHolder: ConstraintLayout
    private lateinit var validUserUIHolder: ConstraintLayout

    private lateinit var signInBtn: Button
    private lateinit var logoutBtn: ImageButton

    private lateinit var enrolledRecycler: RecyclerView

    private lateinit var myDBHelper: DBHelper


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

        enrolledRecycler = view.findViewById(R.id.profile_enrolled_workshop_recycler)

        myDBHelper = DBHelper(context)

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

        val enrolledList = myDBHelper.getStudentEnrollments(
            getCurrentUserID(requireActivity()))

        enrolledRecycler.apply{
            setHasFixedSize(true)
            val recyclerLayoutManager = LinearLayoutManager(requireActivity())
            recyclerLayoutManager.orientation = RecyclerView.VERTICAL
            layoutManager = recyclerLayoutManager
            adapter = ProfileEnrolledRecyclerAdapter(requireActivity(),
                enrolledList?:mutableListOf())
        }

    }

    /**
     *
     */
    private fun showInvalidUserUI(){
        inValidUserUIHolder.visibility = View.VISIBLE
        validUserUIHolder.visibility = View.GONE
    }
}