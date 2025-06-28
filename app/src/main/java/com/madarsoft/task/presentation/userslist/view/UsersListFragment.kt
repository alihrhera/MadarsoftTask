package com.madarsoft.task.presentation.userslist.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.madarsoft.task.R
import com.madarsoft.task.databinding.FragmentUsersListBinding
import com.madarsoft.task.presentation.userslist.adapter.UsersRecyclerAdapter
import com.madarsoft.task.presentation.userslist.viewmodel.UsersListVIewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersListFragment : Fragment() {

    private lateinit var binding: FragmentUsersListBinding
    private val usersListVIewModel: UsersListVIewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_users_list, container, false
        )
        return binding.apply {
            lifecycleOwner = viewLifecycleOwner
            executePendingBindings()
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val usersRecyclerAdapter = UsersRecyclerAdapter()
        binding.usersList.adapter =usersRecyclerAdapter
        binding.viewModel = usersListVIewModel


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                usersListVIewModel.toastMessage.collectLatest {
                    it?.let {
                        if (it.isNotBlank()) Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }

                usersListVIewModel.state.collectLatest {
                    Log.e("getAllUsers", "getAllUsers:${it.users} ", )
                    usersRecyclerAdapter.submitList(it.users)
                }
            }
        }
        usersListVIewModel.users.observe(viewLifecycleOwner) {
            Log.e("getAllUsers", "getAllUsers:${it} ", )
            usersRecyclerAdapter.submitList(it)
        }

    }
}