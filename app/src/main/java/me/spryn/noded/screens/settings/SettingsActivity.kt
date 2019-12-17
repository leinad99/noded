package me.spryn.noded.screens.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import me.spryn.noded.R
import me.spryn.noded.navigation.clearBackStackAndNavigateTo


class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportActionBar?.title = "Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val logoutButton = findViewById<Button>(R.id.logout_button)
        val deleteAccountButton = findViewById<Button>(R.id.delete_account_button)

        logoutButton.setOnClickListener { logout() }
        deleteAccountButton.setOnClickListener { confirmDeleteAccount() }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        var fAuth = FirebaseAuth.getInstance()

        //Google Sign in
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            lateinit var googleSignInClient: GoogleSignInClient
            val googleSignInOptions: GoogleSignInOptions =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()

            googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

            googleSignInClient.revokeAccess()
        }
        fAuth.signOut()

        onBackPressed()
    }


    private fun confirmDeleteAccount() {

        AlertDialog.Builder(this)
            .setTitle("Delete Account")
            .setMessage("Once you delete your account, all notebooks, notes will be permanently deleted.")
            .setIcon(R.drawable.ic_warn)
            .setPositiveButton("Delete Account") { _, _ ->
                AlertDialog.Builder(this)
                    .setTitle("Are you sure?")
                    .setMessage("Deleting your account cannot be undone!")
                    .setIcon(R.drawable.ic_warn)
                    .setPositiveButton("Confirm") { _, _ ->
                        deleteAccount()
                        Toast.makeText(this, "YO MITCH DELETE THE ACCOUNT", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .setNegativeButton(android.R.string.cancel, null).show()
            }
            .setNegativeButton(android.R.string.cancel, null).show()

    }

    private fun deleteAccount() {
        onBackPressed()
        //TODO("Call Firebase to delete account @Mitchell")
    }

}