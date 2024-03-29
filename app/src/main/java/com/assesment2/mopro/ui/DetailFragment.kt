package com.assesment2.mopro.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.assesment2.mopro.data.DatabaseApplication
import com.assesment2.mopro.InventoryViewModel
import com.assesment2.mopro.InventoryViewModelFactory
import com.assesment2.mopro.R
import com.assesment2.mopro.data.Item
import com.assesment2.mopro.data.getFormattedPrice
import com.assesment2.mopro.databinding.FragmentDetailBarangBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class DetailFragment : Fragment() {
    private val navigationArgs: DetailFragmentArgs by navArgs()
    lateinit var item: Item

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as DatabaseApplication).database.itemDao()
        )
    }

    private var _binding: FragmentDetailBarangBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBarangBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun bind(item: Item) {
        binding.apply {
            itemName.text = item.itemName
            itemPrice.text = item.getFormattedPrice()
            itemCount.text = item.quantityInStock.toString()
            sellItem.isEnabled = viewModel.isStockAvailable(item)
            sellItem.setOnClickListener { viewModel.sellItem(item) }
            deleteItem.setOnClickListener { showConfirmationDialog() }
            editItem.setOnClickListener { editItem() }
        }
    }


    private fun editItem() {
        val action = DetailFragmentDirections.actionItemDetailFragmentToAddItemFragment(
            getString(R.string.edit_fragment_title),
            item.id
        )
        this.findNavController().navigate(action)
    }


    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteItem()
            }
            .show()
    }


    private fun deleteItem() {
        viewModel.deleteItem(item)
        findNavController().navigateUp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.itemId

        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
            item = selectedItem
            bind(item)
        }
        binding.shareItem.setOnClickListener {
            shareItem()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun shareItem() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Item Details")
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Item Name: ${item.itemName}\n" +
                "Item Price: ${item.getFormattedPrice()}\n" +
                "Item Count: ${item.quantityInStock}")
        startActivity(Intent.createChooser(shareIntent, "Share Item"))
    }


}
