package com.kurowskiandrzej.eclectusandroid.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kurowskiandrzej.eclectusandroid.data.roomdb.entity.User
import com.kurowskiandrzej.eclectusandroid.databinding.FragmentLoadUserBinding
import com.kurowskiandrzej.eclectusandroid.presentation.view_model.LoadUserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoadUserFragment : Fragment() {
    private var _binding: FragmentLoadUserBinding? = null
    private val binding get() = _binding!!
    private val loadUserViewModel: LoadUserViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoadUserBinding.inflate(inflater, container, false)
        val view = binding.root

        loadLastLoggedUser()

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun loadLastLoggedUser() {
        lifecycleScope.launch {
            val currentUser = loadUserViewModel.getLastLoggedUser()
            validateUser(currentUser)
        }
    }

    private fun validateUser(currentUser: User?) {
        findNavController().navigate(
            if (currentUser?.token != null) {
                LoadUserFragmentDirections.actionLoadUserFragmentToHomeFragment()
            } else {
                LoadUserFragmentDirections.actionLoadUserFragmentToLoginFragment()
            }
        )
    }
}