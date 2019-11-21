package me.spryn.noded.screens.login

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import me.spryn.noded.MainActivity
import me.spryn.noded.R
import me.spryn.noded.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var progressBar: ProgressDialog

    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText

    private lateinit var loginButton: Button
    private lateinit var loginGoogleButton: SignInButton
    private lateinit var createAccount: TextView

    private lateinit var email: String
    private lateinit var password: String

    private var fAuth = FirebaseAuth.getInstance()

    private val RC_SIGN_IN: Int = 1
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mGoogleSignInOptions: GoogleSignInOptions

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )

        configureGoogleSignIn()

        progressBar = ProgressDialog(context)

        emailField = binding.emailInput
        passwordField = binding.passwordInput

        loginButton = binding.login
        loginGoogleButton = binding.googleLogin
        createAccount = binding.createAccountText

        loginButton.setOnClickListener {
            email = emailField.text.toString().trim()
            password = passwordField.text.toString()
            login()
        }
        createAccount.setOnClickListener { register() }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (FirebaseAuth.getInstance().currentUser != null) {
            view?.findNavController()?.navigate(R.id.action_loginActivity_to_notebookFragment)
        }
    }

    private fun login() {
        progressBar.isIndeterminate = true
        progressBar.setMessage("Logging in")
        progressBar.show()
        loginButton.isEnabled = false
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                loginButton.isEnabled = true
                progressBar.hide()
                view?.findNavController()?.navigate(R.id.action_loginActivity_to_notebookFragment)
            } else {
                loginButton.isEnabled = true
                progressBar.hide()
                Toast.makeText(context, "ERROR: ${task.exception}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun googleLogin() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun configureGoogleSignIn() {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(context!!, mGoogleSignInOptions)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Toast.makeText(context, "Google sign in failed:(", Toast.LENGTH_LONG).show()
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
