package com.madarsoft.task.presentation.adduser.compos

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Scaffold
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.fragment.findNavController
import com.madarsoft.task.R
import com.madarsoft.task.databinding.FragmentAddUserBinding
import com.madarsoft.task.presentation.adduser.AddUserViewModel
import com.madarsoft.task.presentation.home.MainNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddUserFragmentCompos : Fragment() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val mainNavHost = MainNavHost()
                val vm: AddUserViewModel = hiltViewModel()
                Scaffold {
                    AddUserScreen(viewModel = vm) {

                    }
                }
            }
        }

}