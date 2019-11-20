package me.spryn.noded.screens.login

import android.app.ProgressDialog
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
    private lateinit var fAuth: FirebaseAuth
    private lateinit var fUsersDatabase: DatabaseReference

    private lateinit var progressBar: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRegisterBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_register, container, false
        )

        fAuth = FirebaseAuth.getInstance()
        fUsersDatabase = FirebaseDatabase.getInstance().reference.child("Users")

        progressBar = ProgressDialog(context)

        registerButton = binding.registerButton
        registerButton.setOnClickListener {
            registerUser(
                binding.nameInput.text.toString().trim(),
                binding.emailInput.text.toString().trim(),
                binding.passwordInput.text.toString()
            )
        }

        return binding.root
    }

    private fun registerUser(name: String, email: String, password: String) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Please fill enter all fields", Toast.LENGTH_LONG).show()
        } else {
            progressBar.show()
            fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    progressBar.hide()
                    if (task.isSuccessful) {
                        val userID = fAuth.currentUser!!.uid
                        val currentUserDb = fUsersDatabase.child(userID)
                        Toast.makeText(context, "Success creating account!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "ERROR: ${task.exception}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }
}
