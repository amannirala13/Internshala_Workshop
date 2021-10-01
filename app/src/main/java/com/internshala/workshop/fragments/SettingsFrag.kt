package com.internshala.workshop.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.internshala.auth.activities.Authentication
import com.internshala.utils.isValidUser
import com.internshala.utils.logoutUser
import com.internshala.workshop.R

/**
 * This fragment contains some basic details and an option to login or logout.
 * This can be used later to add more customization and settings.
 *
 * @property authBtn Button
 * @property startActivityResultListener ActivityResultLauncher<Intent>
 */
class SettingsFrag : Fragment() {

    private lateinit var authBtn: Button
    private lateinit var startActivityResultListener: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authBtn = view.findViewById(R.id.settings_auth_btn)

        startActivityResultListener = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data?.getBooleanExtra("success",false) == true){
                    loadValidUserUI()
                }
            }
        }

        if(isValidUser(requireActivity())) loadValidUserUI()
        else loadInvalidUserUI()

    }

    /**
     * Updates the UI if the user id logged in
     */
    private fun loadValidUserUI() {
        authBtn.text = "Logout"
        authBtn.setBackgroundColor(requireActivity()
            .getColor(R.color.red))
        authBtn.setOnClickListener{
            logoutUser(requireActivity())
            loadInvalidUserUI()
        }
    }

    /**
     * Updates the UI if the user is not logged in
     */
    private fun loadInvalidUserUI(){
        authBtn.text = "Sign in"
        authBtn.setBackgroundColor(requireActivity()
            .getColor(R.color.primary))
        authBtn.setOnClickListener{
            startActivityResultListener.launch(Intent(requireActivity(), Authentication::class.java))
        }
    }
}