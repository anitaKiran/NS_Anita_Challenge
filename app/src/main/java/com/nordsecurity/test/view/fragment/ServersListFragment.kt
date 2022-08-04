package com.nordsecurity.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nordsecurity.test.databinding.FragmentServersListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServersListFragment : Fragment() {
    private var _binding: FragmentServersListBinding? = null
    private val binding get() = _binding!!
    private val listViewModel : ServerListViewModel by viewModels()
    private var listAdapter = ServerListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentServersListBinding.inflate(inflater,container, false)
        setupUI()
        observeServerList()
        return binding.root
    }

    private fun setupUI(){
        // initialize recyclerview
        binding.recyclerview.apply {
            this.hasFixedSize()
            adapter = listAdapter
        }
    }

    // observe server list data
    private fun observeServerList(){
        listViewModel.serverList.observe(viewLifecycleOwner, Observer {
            when (it?.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        // set server list to adapter
                        listAdapter.setContentList(it)
                    }
                }

                Status.LOADING -> { }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}

