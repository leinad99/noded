package me.spryn.noded.screens.login

import android.content.Intent
import android.os.Bundle
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
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import me.spryn.noded.MainActivity
import me.spryn.noded.R
import me.spryn.noded.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {


    private lateinit var email: String
    private lateinit var password: String

    private lateinit var progressDialog: ConstraintLayout

    private var fAuth = FirebaseAuth.getInstance()

    private val RC_SIGN_IN: Int = 7
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInOptions: GoogleSignInOptions

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )

        configureGoogleSignIn()

        progressDialog = binding.progressDialogue

        binding.login.setOnClickListener {
            email = binding.emailInput.text.toString().trim()
            password = binding.passwordInput.text.toString()
            login()
        }
        binding.createAccountText.setOnClickListener { register() }
        binding.googleLogin.setOnClickListener { googleLogin() }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (fAuth.currentUser != null) {
            view?.findNavController()?.navigate(R.id.action_loginActivity_to_notebookFragment)
        }
    }

    private fun login() {
        progressDialog.visibility = View.VISIBLE
        binding.login.isEnabled = false
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            binding.login.isEnabled = true
            if (task.isSuccessful) {
                progressDialog.visibility = View.GONE
                view?.findNavController()?.navigate(R.id.action_loginActivity_to_notebookFragment)
            } else {
                progressDialog.visibility = View.GONE
                Toast.makeText(context, "ERROR: ${task.exception}", Toast.LENGTH_SHORT).show()
            }
        }
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
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let { firebaseAuthWithGoogle(account) }
            } catch (e: ApiException) {
                Toast.makeText(context, "Google sign in failed :(", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        fAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                view?.findNavController()?.navigate(R.id.action_loginActivity_to_notebookFragment)
            } else {
                Toast.makeText(context, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun register() {
        view?.findNavController()?.navigate(R.id.action_loginActivity_to_registerActivity)
    }
}
