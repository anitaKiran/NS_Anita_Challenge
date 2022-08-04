package com.nordsecurity.test

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nordsecurity.test.AppConstants.LOGIN_PASSWORD
import com.nordsecurity.test.AppConstants.LOGIN_USERNAME
import com.nordsecurity.test.databinding.FragmentLoginBinding
import com.nordsecurity.test.databinding.LoaderLayoutBinding
import com.nordsecurity.test.utils.Common
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var bLoader : LoaderLayoutBinding
    private val viewModel: LoginViewModel by viewModels()
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        bLoader = binding.layoutLoader

        setupUI()

        return binding.root
    }

    private fun setupUI() {
        // hide status bar in login activity
        Common.hideStatusBar(requireActivity())

        // set default username and password in edittexts
        binding.etUsername.setText(LOGIN_USERNAME)
        binding.etPassword.setText(LOGIN_PASSWORD)

        // click listener for login btn
        binding.btnLogin.setOnClickListener {
            validateUserInput()
        }
    }

    // check if user has entered all the credentials
    private fun validateUserInput() {
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (username.isEmpty() && username.length == 0) {
            Toast.makeText(context, AppConstants.USERNAME_EMPTY, Toast.LENGTH_LONG).show()
        }
        if (password.isEmpty() && password.length > 0) {
            Toast.makeText(context, AppConstants.PASSWORD_EMPTY, Toast.LENGTH_LONG).show()
        }
        if (username.isNotEmpty() && password.isNotEmpty()) {
            getLoginToken(username, password)
        }
    }

    private fun getLoginToken(username: String, password: String) {
        // show
        showLoader()
        // call login api
        viewModel.getLoginToken(LoginRequest(username, password))

        // observer login response
        viewModel.loginToken.observe(viewLifecycleOwner, Observer {
            when (it?.status) {
                Status.SUCCESS -> {
                    hideLoader()
                    it.data?.token?.let {
                        // store token in shared preference file
                        sharedPreferences.edit().putString(AppConstants.LOGIN_TOKEN, it).apply()

                        // open server list fragment
                        findNavController().navigate(R.id.serverFragment)
                    }
                }

                Status.LOADING -> { showLoader() }

                Status.ERROR -> {
                    hideLoader()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun showLoader(){
        bLoader.loader.visibility = View.VISIBLE
    }

    private fun hideLoader(){
        bLoader.loader.visibility = View.GONE
        binding.layoutLogin.visibility = View.VISIBLE
    }
}