package com.example.simplechat.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplechat.R
import com.example.simplechat.databinding.MainFragmentBinding
import org.koin.android.ext.android.inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by inject()
    private val messagesAdapter = MessagesAdapter()
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(LayoutInflater.from(container?.context), R.layout.main_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val messageRv = binding.messagesRv
        messageRv.layoutManager = LinearLayoutManager(requireContext())
        messageRv.adapter = messagesAdapter

        viewModel.messageList.observe(viewLifecycleOwner, {
            messagesAdapter.updateList(it.reversed())
            if (it.size > 3)
                messageRv.smoothScrollToPosition(it.size - 1)
        })


    }

}