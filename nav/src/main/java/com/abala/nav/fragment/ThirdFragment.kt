package com.abala.nav.fragment

import androidx.navigation.Navigation
import com.abala.nav.R
import com.abala.nav.databinding.FragmentThirdBinding

class ThirdFragment : BaseFragment<FragmentThirdBinding>() {

    override val layoutId: Int = R.layout.fragment_third

    override fun onViewCreated() {
        binding?.toSecond?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_second)
        }
    }
}