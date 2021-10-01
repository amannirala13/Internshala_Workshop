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
import com.internshala.database.models.Student
import com.internshala.utils.loginUser

/**
 * Fragment responsible for user registration
 *
 * @property nameTextContainer TextInputLayout
 * @property nameText TextInputEditText
 * @property emailTextContainer TextInputLayout
 * @property emailText TextInputEditText
 * @property phoneTextContainer TextInputLayout
 * @property phoneText TextInputEditText
 * @property passwordTextContainer TextInputLayout
 * @property passwordText TextInputEditText
 * @property continueBtn Button
 * @property myDBHelper DBHelper
 * @property name String?
 * @property email String?
 * @property phone String?
 * @property password String?
 */
class RegisterFrag : Fragment() {

    private lateinit var nameTextContainer: TextInputLayout
    private lateinit var nameText: TextInputEditText
    private lateinit var emailTextContainer: TextInputLayout
    private lateinit var emailText: TextInputEditText
    private lateinit var phoneTextContainer: TextInputLayout
    private lateinit var phoneText: TextInputEditText
    private lateinit var passwordTextContainer: TextInputLayout
    private lateinit var passwordText: TextInputEditText
    private lateinit var continueBtn: Button

    private lateinit var myDBHelper: DBHelper

    private var name: String? = null
    private var email: String? = null
    private var phone: String? = null
    private var password: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { return inflater.inflate(R.layout.fragment_register, container, false) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameTextContainer = view.findViewById(R.id.register_name_text_holder)
        nameText = view.findViewById(R.id.register_name_text)
        phoneTextContainer = view.findViewById(R.id.register_phone_text_holder)
        phoneText = view.findViewById(R.id.register_phone_text)
        emailTextContainer = view.findViewById(R.id.register_email_text_holder)
        emailText = view.findViewById(R.id.register_email_text)
        passwordTextContainer = view.findViewById(R.id.register_password_text_holder)
        passwordText = view.findViewById(R.id.register_password_text)
        continueBtn = view.findViewById(R.id.register_btn)

        myDBHelper = DBHelper(this.context)

        continueBtn.setOnClickListener {
            if(isInputValid()){
                val student = Student(
                    name = name,
                    phone = phone,
                    password = password,
                    email = email)

                 val success = myDBHelper.registerStudent(student)
                 if(success){
                     Toast.makeText(this.requireActivity(), "Registration successful", Toast.LENGTH_SHORT).show()
                     val user = myDBHelper.getLoginUser(email!!, password!!)
                     loginUser( this.requireActivity(), user?.id?:-1)
                     val result = Intent().putExtra("success", true)
                     this.requireActivity().setResult(Activity.RESULT_OK,result)
                     this.requireActivity().finish()
                 }else{
                     Toast.makeText(this.requireActivity(), "Unable to register user", Toast.LENGTH_LONG).show()
                 }

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
        name = nameText.text.toString()
        email = emailText.text.toString()
        phone = phoneText.text.toString()
        password = passwordText.text.toString()

        if(name.isNullOrEmpty()) {
            nameTextContainer.error = "Enter valid name"
            validity = false
        }
        else nameTextContainer.error = null

        if(email.isNullOrEmpty()) {
            emailTextContainer.error = "Enter valid email"
            validity = false
        }
        else if (!myDBHelper.checkUniqueEmail(email!!)){
            emailTextContainer.error = "Email already existing"
            validity = false
        }
        else emailTextContainer.error = null

        if(phone.isNullOrEmpty()) {
            phoneTextContainer.error = "Enter valid phone"
            validity = false
        }
        else phoneTextContainer.error = null

        if(password.isNullOrEmpty()) {
            passwordTextContainer.error = "Enter valid password"
            validity = false
        }
        else passwordTextContainer.error = null

        return validity
    }
}