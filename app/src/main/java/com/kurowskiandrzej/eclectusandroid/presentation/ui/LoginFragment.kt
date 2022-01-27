package com.kurowskiandrzej.eclectusandroid.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kurowskiandrzej.eclectusandroid.R
import com.kurowskiandrzej.eclectusandroid.common.Resource
import com.kurowskiandrzej.eclectusandroid.common.Status
import com.kurowskiandrzej.eclectusandroid.data.remote.dto.LoginResponseDto
import com.kurowskiandrzej.eclectusandroid.databinding.FragmentLoginBinding
import com.kurowskiandrzej.eclectusandroid.presentation.view_model.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        initializeViews()
        subscribeToObservers()

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initializeViews() {
        binding.etEmail.doOnTextChanged { input, _, _, _ ->
            loginViewModel.updateEmailInput((input ?: "").toString())
        }

        binding.etPassword.doOnTextChanged { input, _, _, _ ->
            loginViewModel.updatePasswordInput((input ?: "").toString())
        }

        binding.btnSendLoginRequest.setOnClickListener {
            loginViewModel.sendLoginRequest()
        }

        binding.btnOpenRegisterFragment.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            )
        }
    }

    private fun subscribeToObservers() {
        observeLoginResponse()
        observeEmailValidationResult()
        observeSendLoginRequestButtonVisibilityState()
    }

    private fun observeLoginResponse() {
        loginViewModel.loginResponse.observe(viewLifecycleOwner, {
            it?.let { response ->
                processApiResponse(response)
            }
        })
    }

    private fun processApiResponse(response: Resource<Pair<LoginResponseDto, String>>) {
        when (response.status) {
            Status.LOADING -> onApiResponseLoading()
            Status.SUCCESS -> onApiResponseSuccess(response)
            Status.ERROR -> onApiResponseError(response.message)
        }
    }

    private fun onApiResponseLoading() {
        setViewsEnableState(false)
        setProgressBarVisibilityState(true)
    }

    private fun onApiResponseSuccess(response: Resource<Pair<LoginResponseDto, String>>) {
        loginUser(response.data!!.first.userId, response.data.second)
    }

    private fun loginUser(userId: Long, token: String) {
        loginViewModel.logInUser(
            userId,
            token
        ) {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            )
        }
    }

    private fun onApiResponseError(errorMessage: String?) {
        setViewsEnableState(true)
        setProgressBarVisibilityState(false)
        Toast.makeText(requireContext(), errorMessage  ?: getString(R.string.unexpected_error), Toast.LENGTH_LONG).show()
    }

    private fun observeEmailValidationResult() {
        loginViewModel.emailValidationResult.observe(viewLifecycleOwner, {
            it?.let { validationResult ->
                val (result, resource) = validationResult
                if (!result) {
                    resource?.let { resourceId ->
                        binding.tvInvalidEmailMessage.text = getString(resourceId)
                    }
                } else {
                    binding.tvInvalidEmailMessage.text = ""
                }
            }
        })
    }

    private fun observeSendLoginRequestButtonVisibilityState() {
        loginViewModel.buttonSendLoginRequestEnableState.observe(viewLifecycleOwner, { state ->
            binding.btnSendLoginRequest.isEnabled = state
        })
    }

    private fun setViewsEnableState(state: Boolean) {
        binding.apply {
            btnOpenRegisterFragment.isEnabled = state
            etEmail.isEnabled = state
            etPassword.isEnabled = state
        }
    }

    private fun setProgressBarVisibilityState(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}