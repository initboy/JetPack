package com.abala.nav.fragment

import androidx.navigation.Navigation
import com.abala.nav.R
import com.abala.nav.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val layoutId: Int = R.layout.fragment_home

    override fun onViewCreated() {
        binding?.toSecond?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_second)
        }
    }

}