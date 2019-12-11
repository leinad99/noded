package me.spryn.noded.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import me.spryn.noded.R
import me.spryn.noded.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var fAuth = FirebaseAuth.getInstance()
    private var fUsersDatabase = FirebaseDatabase.getInstance().reference.child("Users")


    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String

    private lateinit var progressDialog: ConstraintLayout

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_register, container, false
        )


        progressDialog = binding.progressDialogue

        binding.loginText.setOnClickListener { login() }

        binding.register.setOnClickListener {
            name = binding.nameInput.text.toString().trim()
            email = binding.emailInput.text.toString().trim()
            password = binding.passwordInput.text.toString()
            registerUser()
        }

        return binding.root
    }


    private fun login() {
        view?.findNavController()?.navigate((R.id.action_registerActivity_to_loginActivity))
    }

    private fun registerUser() {
        binding.register.isEnabled = false

        if (!validate()) {
            binding.register.isEnabled = true
            return
        }

        progressDialog.visibility = View.VISIBLE

        fAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressDialog.visibility = View.GONE
                if (task.isSuccessful) {
                    binding.register.isEnabled = true
                    val userID = fAuth.currentUser!!.uid
                    val currentUserDb = fUsersDatabase.child(userID)
                    Toast.makeText(context, "Success creating account!", Toast.LENGTH_SHORT).show()
                    view?.findNavController()
                        ?.navigate(R.id.action_registerActivity_to_notebookFragment)
                } else {
                    binding.register.isEnabled = true
                    Toast.makeText(context, "ERROR: ${task.exception}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun validate(): Boolean {
        var isValid = true

        binding.register.isEnabled = false

        //name
        if (name.isEmpty() || name.length < 3) {
            binding.nameInput.error = "at least 3 characters"
            isValid = false
        } else {
            binding.nameInput.error = null
        }

        //email
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInput.error = "enter a valid email address"
            isValid = false
        } else {
            binding.emailInput.error = null
        }

        //password
        if (password.isEmpty() || password.length < 6 || !strongPassword()) {
            binding.passwordInput.error =
                "Password Must be at least 6 characters long, and contain the following: a capital, a lowercase, a number, a special character, and no spaces."
            isValid = false
        } else {
            binding.passwordInput.error = null
        }

        return isValid
    }

    private fun strongPassword(): Boolean {
        return true //TODO Implement API to check
    }
}
