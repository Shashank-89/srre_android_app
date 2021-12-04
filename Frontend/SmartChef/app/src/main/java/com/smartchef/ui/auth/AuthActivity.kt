package com.smartchef.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.smartchef.BuildConfig
import com.smartchef.R
import com.smartchef.databinding.ActivityAuthBinding
import com.smartchef.ui.common.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class AuthActivity : AppCompatActivity() {

    private val authViewModel by viewModels<AuthViewModel>()
    private val appViewModel by viewModels<AppViewModel>()
    private lateinit var binding: ActivityAuthBinding

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result ->
        this.onSignInResult(result)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(BuildConfig.DEBUG) Log.d("AuthAct", "onCreate called!")
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeAuthState()
    }


    private fun observeAuthState(){
        authViewModel.authenticationState.observe(this, Observer { authenticationState ->
            when (authenticationState) {
                AuthViewModel.AuthenticationState.AUTHENTICATED ->{
                    if(BuildConfig.DEBUG) Log.i("AuthAct", "observeAuthState ==> AUTHENTICATED");
                }
                else ->{
                    if(BuildConfig.DEBUG) Log.i("AuthAct", "launching signin workflow!");
                    createSignInIntent()
                }
            }
        })
    }


    private fun createSignInIntent(){
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }


    fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
    }


    private fun onSignInResult(result : FirebaseAuthUIAuthenticationResult){
        if(result.resultCode == RESULT_OK){
            val nav = findNavController(R.id.nav_host_fragment)
            val navGraph = nav.navInflater.inflate(R.navigation.nav_graph)
            if(result.idpResponse?.isNewUser == true){
                navGraph.startDestination = R.id.onboarding_fragment
            }else{
                navGraph.startDestination = R.id.search_fragment
            }
            nav.graph = navGraph
        }
    }


}