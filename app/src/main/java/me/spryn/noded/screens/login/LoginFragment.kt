package me.spryn.noded.screens.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import me.spryn.noded.MainActivity
import me.spryn.noded.R
import me.spryn.noded.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    private lateinit var email: String
    private lateinit var password: String

    private lateinit var progressDialog: ConstraintLayout

    private val RC_SIGN_IN: Int = 1

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInOptions: GoogleSignInOptions

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )

        configureGoogleSignIn()

        progressDialog = binding.progressDialogue

        binding.login.setOnClickListener {
            email = binding.emailInput.text.toString().trim()
            password = binding.passwordInput.text.toString()
            if (validate())
                login()
        }
        binding.createAccountText.setOnClickListener { register() }
        binding.googleLogin.setOnClickListener { googleLogin() }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        //if user already signed in, navigate to Notebooks Page
        if (mAuth.currentUser != null) {
            view?.findNavController()?.navigate(R.id.action_loginActivity_to_notebookFragment)
        }
        val mainActivity = activity as? MainActivity
        mainActivity?.let {
            it.window.navigationBarColor = ContextCompat.getColor(it, R.color.colorPrimaryDark)
        }
    }

    private fun login() {
        progressDialog.visibility = View.VISIBLE
        binding.login.isEnabled = false
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            binding.login.isEnabled = true
            progressDialog.visibility = View.GONE
            if (task.isSuccessful) {
                view?.findNavController()?.navigate(R.id.action_loginActivity_to_notebookFragment)
            } else {
                Toast.makeText(context, "ERROR: ${task.exception}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validate(): Boolean {
        var isValid = true

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
                "password must at least 6 characters long, and contain the following: a capital, a lowercase, a number, a special character, and no spaces."
            isValid = false
        } else {
            binding.passwordInput.error = null
        }

        return isValid
    }

    private fun strongPassword(): Boolean {
        return true //TODO Implement API to check
    }


    private fun googleLogin() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun configureGoogleSignIn() {
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        context?.let {
            googleSignInClient = GoogleSignIn.getClient(it, googleSignInOptions)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Log.w("Google Login", "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("Google Login", "firebaseAuthWithGoogle:" + acct.id!!)
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential).addOnCompleteListener {task ->
            if (task.isSuccessful) {
                Log.d("Google Login", "signInWithCredential:success")
                view?.findNavController()?.navigate(R.id.action_loginActivity_to_notebookFragment)
            } else {
                Log.w("Google Login", "signInWithCredential:failure", task.exception)
                view?.let { Snackbar.make(it, "Authentication Failed.", Snackbar.LENGTH_SHORT).show() }
            }
        }
    }

    private fun register() {
        view?.findNavController()?.navigate(R.id.action_loginActivity_to_registerActivity)
    }
}
