package com.internshala.auth.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.internshala.auth.R
import com.internshala.database.helpers.DBHelper
import com.internshala.utils.loginUser

class SignInFrag : Fragment() {

    private lateinit var emailTextContainer: TextInputLayout
    private lateinit var emailText: TextInputEditText
    private lateinit var passwordTextContainer: TextInputLayout
    private lateinit var passwordText: TextInputEditText
    private lateinit var continueBtn: Button

    private var email: String? = null
    private var password: String? = null

    private lateinit var myDBHelper: DBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailTextContainer = view.findViewById(R.id.sign_in_email_text_holder)
        emailText = view.findViewById(R.id.sign_in_email_text)
        passwordTextContainer = view.findViewById(R.id.sign_in_password_text_holder)
        passwordText = view.findViewById(R.id.sign_in_password_text)
        continueBtn = view.findViewById(R.id.sign_in_continue_btn)

        myDBHelper = DBHelper(this.context)

        continueBtn.setOnClickListener{
            if(isInputValid()){
                val user = myDBHelper.getLoginUser(email!!, password!!)
                if(user!=null){
                    loginUser( this.requireActivity(), user.id?:-1)
                    Toast.makeText(context, "Welcome ${user.name}!", Toast.LENGTH_SHORT).show()
                    val result = Intent().putExtra("success", true)
                    this.requireActivity().setResult(Activity.RESULT_OK,result)
                    this.requireActivity().finish() }
                else Toast.makeText(context, "Your credentials didn't match. Please try again!", Toast.LENGTH_LONG).show()
            }
        }
    }


    /**
     * Checks if all the inputs are valid or not.
     * I am not using proper security checks and pattern check
     * as this is just a demo application. I will just be checking for nulls
     *
     * @return [Boolean] - [true] is all the inputs are valid else [false]
     */
    private fun isInputValid():Boolean {
        var validity = true
        email = emailText.text.toString()
        password = passwordText.text.toString()

        if(email.isNullOrEmpty()) {
            emailTextContainer.error = "Enter valid email"
            validity = false
        }
        else emailTextContainer.error = null

        if(password.isNullOrEmpty()) {
            passwordTextContainer.error = "Enter valid password"
            validity = false
        }
        else passwordTextContainer.error = null

        return validity
    }

}