package com.abala.nav.fragment

import androidx.navigation.Navigation
import com.abala.nav.R
import com.abala.nav.databinding.FragmentSecondBinding

class SecondFragment : BaseFragment<FragmentSecondBinding>() {

    override val layoutId: Int = R.layout.fragment_second

    override fun onViewCreated() {
        binding?.toHome?.setOnClickListener {
            //返回上一次的fragment
            Navigation.findNavController(it).navigateUp()
            //Navigation.findNavController(it).navigate(R.id.action_home)
        }
        binding?.toThird?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_third)
        }

    }
}