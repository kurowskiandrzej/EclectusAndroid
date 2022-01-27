package com.kurowskiandrzej.eclectusandroid.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kurowskiandrzej.eclectusandroid.R
import com.kurowskiandrzej.eclectusandroid.common.Constants
import com.kurowskiandrzej.eclectusandroid.common.Resource
import com.kurowskiandrzej.eclectusandroid.common.Status
import com.kurowskiandrzej.eclectusandroid.data.remote.dto.RegisterResponseDto
import com.kurowskiandrzej.eclectusandroid.databinding.FragmentRegisterBinding
import com.kurowskiandrzej.eclectusandroid.presentation.view_model.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
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
            registerViewModel.updateEmailInput((input ?: "").toString())
        }

        binding.etPassword.doOnTextChanged { input, _, _, _ ->
            registerViewModel.updatePasswordInput(
                (input ?: "").toString(),
                binding.etPasswordConfirmation.text.toString()
            )
        }

        binding.etPasswordConfirmation.doOnTextChanged { input, _, _, _ ->
            registerViewModel.updatePasswordConfirmationInput(
                (input ?: "").toString(),
                binding.etPassword.text.toString()
            )
        }

        binding.btnSendRegisterRequest.setOnClickListener {
            registerViewModel.sendRegisterRequest()
        }

        binding.btnOpenLoginFragment.setOnClickListener {
            findNavController().navigate(
                RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            )
        }
    }

    private fun subscribeToObservers() {
        observeRegisterResponse()
        observeEmailInputValidationResult()
        observePasswordInputValidationResult()
        observePasswordConfirmationInputValidationResult()
        observeSendLoginRequestButtonVisibilityState()
    }

    private fun observeRegisterResponse() {
        registerViewModel.registerResponse.observe(viewLifecycleOwner, {
            it?.let { response ->
                processApiResponse(response)
            }
        })
    }

    private fun processApiResponse(response: Resource<Pair<RegisterResponseDto, String>>) {
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

    private fun onApiResponseSuccess(response: Resource<Pair<RegisterResponseDto, String>>) {
        registerUser(response.data!!.first.userId, response.data.second)
    }

    private fun registerUser(userId: Long, token: String) {
        registerViewModel.registerUser(
            userId,
            null,
            null,
            token
        ) {
            findNavController().navigate(
                RegisterFragmentDirections.actionRegisterFragmentToHomeFragment()
            )
        }
    }

    private fun onApiResponseError(errorMessage: String?) {
        setViewsEnableState(true)
        setProgressBarVisibilityState(false)
        Toast.makeText(requireContext(), errorMessage ?: getString(R.string.unexpected_error), Toast.LENGTH_LONG).show()
    }

    private fun observeEmailInputValidationResult() {
        registerViewModel.emailInputValidationResult.observe(viewLifecycleOwner, {
            it?.let { validationResult ->
                val (result, resourceId) = validationResult
                processInputValidationResult(
                    result,
                    binding.tvInvalidEmailMessgae,
                    getString(resourceId!!)
                )
            }
        })
    }

    private fun observePasswordInputValidationResult() {
        registerViewModel.passwordInputValidationResult.observe(viewLifecycleOwner, {
            it?.let { validationResult ->
                val (result, resourceId) = validationResult
                processInputValidationResult(
                    result,
                    binding.tvPasswordValidationMessage,
                    getString(resourceId!!, Constants.PASSWORD_MINIMUM_LENGTH)
                )
            }
        })
    }

    private fun observePasswordConfirmationInputValidationResult() {
        registerViewModel.passwordConfirmationInputValidationResult.observe(viewLifecycleOwner, {
            it?.let { validationResult ->
                val (result, resourceId) = validationResult
                processInputValidationResult(
                    result,
                    binding.tvPasswordConfirmationValidationMesage,
                    getString(resourceId!!)
                )
            }
        })
    }

    private fun processInputValidationResult(
        result: Boolean,
        textView: TextView,
        message: String
    ) {
        textView.text = if (!result) message else ""
    }

    private fun observeSendLoginRequestButtonVisibilityState() {
        registerViewModel.buttonSendRegisterRequestEnableState.observe(viewLifecycleOwner, { state ->
            binding.btnSendRegisterRequest.isEnabled = state
        })
    }

    private fun setViewsEnableState(state: Boolean) {
        binding.apply {
            btnOpenLoginFragment.isEnabled = state
            etEmail.isEnabled = state
            etPassword.isEnabled = state
        }
    }

    private fun setProgressBarVisibilityState(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}