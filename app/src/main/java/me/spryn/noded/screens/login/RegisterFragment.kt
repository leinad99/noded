package me.spryn.noded.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import me.spryn.noded.MainActivity
import me.spryn.noded.R
import me.spryn.noded.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var registerButton: Button

    private var fAuth = FirebaseAuth.getInstance()
    private var fUsersDatabase = FirebaseDatabase.getInstance().reference.child("Users")


    private lateinit var nameField: EditText
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText

    private lateinit var login: TextView

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String

    private lateinit var progressDialog: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRegisterBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_register, container, false
        )

        nameField = binding.nameInput
        emailField = binding.emailInput
        passwordField = binding.passwordInput

        progressDialog = binding.progressDialogue

        login = binding.loginText
        login.setOnClickListener { login() }

        registerButton = binding.register
        registerButton.setOnClickListener {
            name = nameField.text.toString().trim()
            email = emailField.text.toString().trim()
            password = passwordField.text.toString()
            registerUser()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val mainActivity = activity as? MainActivity
        mainActivity?.let {
            it.window.navigationBarColor =
                ContextCompat.getColor(mainActivity, R.color.colorPrimaryDark)
        }
    }

    private fun login() {
        view?.findNavController()?.navigate((R.id.action_registerActivity_to_loginActivity))
    }

    private fun registerUser() {
        registerButton.isEnabled = false

        if (!validate()) {
            registerButton.isEnabled = true
            return
        }

        progressDialog.visibility = View.VISIBLE

        fAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressDialog.visibility = View.GONE
                if (task.isSuccessful) {
                    registerButton.isEnabled = true
                    val userID = fAuth.currentUser!!.uid
                    val currentUserDb = fUsersDatabase.child(userID)
                    Toast.makeText(context, "Success creating account!", Toast.LENGTH_SHORT).show()
                    view?.findNavController()
                        ?.navigate(R.id.action_registerActivity_to_notebookFragment)
                } else {
                    registerButton.isEnabled = true
                    Toast.makeText(context, "ERROR: ${task.exception}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun validate(): Boolean {
        var isValid = true

        registerButton.isEnabled = false

        //name
        if (name.isEmpty() || name.length < 3) {
            nameField.error = "at least 3 characters"
            isValid = false
        } else {
            nameField.error = null
        }

        //email
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.error = "enter a valid email address"
            isValid = false
        } else {
            emailField.error = null
        }

        //password
        if (password.isEmpty() || password.length < 6 || !strongPassword()) {
            passwordField.error =
                "Password Must be at least 6 characters long, and contain the following: a capital, a lowercase, a number, a special character, and no spaces."
            isValid = false
        } else {
            passwordField.error = null
        }

        return isValid
    }

    private fun strongPassword(): Boolean {
        return true //TODO Implement API to check
    }
}
