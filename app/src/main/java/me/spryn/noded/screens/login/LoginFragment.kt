package me.spryn.noded.screens.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import me.spryn.noded.MainActivity
import me.spryn.noded.R
import me.spryn.noded.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var existingAccountBtn: Button
    private lateinit var newAccountBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )

        existingAccountBtn = binding.existingAccountButton
        newAccountBtn = binding.newAccountButton

        existingAccountBtn.setOnClickListener { login() }
        newAccountBtn.setOnClickListener { register() }

        return binding.root
    }


    private fun login() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun register() {
        view?.findNavController()?.navigate(R.id.action_loginActivity_to_registerActivity)
    }
}
