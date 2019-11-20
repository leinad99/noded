package me.spryn.noded.screens.login

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import me.spryn.noded.MainActivity
import me.spryn.noded.R
import me.spryn.noded.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var registerButton: Button

    private var fAuth = FirebaseAuth.getInstance()
    private var fUsersDatabase = FirebaseDatabase.getInstance().reference.child("Users")

    private lateinit var progressBar: ProgressDialog

    private lateinit var nameField: EditText
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText

    private lateinit var login: TextView

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRegisterBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_register, container, false
        )


        progressBar = ProgressDialog(context)

        nameField = binding.nameInput
        emailField = binding.emailInput
        passwordField = binding.passwordInput

        login = binding.loginText
        login.setOnClickListener { login() }

        registerButton = binding.registerButton
        registerButton.setOnClickListener {
            name = nameField.text.toString().trim()
            email = emailField.text.toString().trim()
            password = passwordField.text.toString()
            registerUser()
        }

        return binding.root
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

        progressBar.isIndeterminate = true
        progressBar.setMessage("Creating Account...")
        progressBar.setCanceledOnTouchOutside(false)
        progressBar.show()

        fAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressBar.hide()
                if (task.isSuccessful) {
                    registerButton.isEnabled = true
                    val userID = fAuth.currentUser!!.uid
                    val currentUserDb = fUsersDatabase.child(userID)
                    Toast.makeText(context, "Success creating account!", Toast.LENGTH_SHORT).show()
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
                "Password Must be at least 6 characters long, at least 1 capital, 1 lowercase, and 1 number"
            isValid = false
        } else {
            passwordField.error = null
        }

        return isValid
    }

    private fun strongPassword(): Boolean {
        val pattern = "^(?=.*[0-9])(?=.*[a-z])$"
        return password.matches(pattern.toRegex())
    }

    private fun onSignUpFailed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
