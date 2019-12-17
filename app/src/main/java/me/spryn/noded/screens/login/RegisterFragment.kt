package me.spryn.noded.screens.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import me.spryn.noded.R
import me.spryn.noded.databinding.FragmentRegisterBinding
import java.util.regex.Pattern


class RegisterFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String

    private lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_register, container, false
        )

        binding.loginText.setOnClickListener { navigateToLogin() }

        binding.register.setOnClickListener {
            name = binding.nameInput.text.toString().trim()
            email = binding.emailInput.text.toString().trim()
            password = binding.passwordInput.text.toString()
            registerUser()
        }

        return binding.root
    }


    override fun onPause() {
        view?.let { hideKeyboard(it) }
        super.onPause()
    }

    private fun navigateToLogin() {
        view?.findNavController()?.navigate((R.id.action_registerActivity_to_loginActivity))
    }

    private fun registerUser() {
        if (!validate()) {
            return
        }

        binding.progressDialogue.visibility = View.VISIBLE

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                binding.progressDialogue.visibility = View.GONE
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Register", "createUserWithEmail:success")
                    view?.findNavController()
                        ?.navigate(R.id.action_registerActivity_to_notebookFragment)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Register", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        context, "Authentication failed.", Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun validate(): Boolean {
        var isValid = true

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
        if (password.isEmpty() || password.length < 6 || !strongPassword(password)) {
            binding.passwordInput.error =
                "password must be at least 6 characters long, and contain the following: a capital, a lowercase, a number, a special character, and no spaces."
            isValid = false
        } else {
            binding.passwordInput.error = null
        }

        return isValid
    }

    private fun strongPassword(password: String): Boolean {
        //regex expression to find if password has at least one uppercase, lowercase, and special character
        val regex = "^(?=.*?\\p{Lu})(?=.*?\\p{Ll})(?=.*?\\d)" +
                "(?=.*?[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]).*$"
        //return if it follows the regex expression and contains no spaces
        return(Pattern.compile(regex).matcher(password).matches() && !password.contains(" "))
    }

    private fun hideKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
