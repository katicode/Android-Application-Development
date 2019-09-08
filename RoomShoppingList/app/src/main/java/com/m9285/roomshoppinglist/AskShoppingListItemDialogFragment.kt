package com.m9285.roomshoppinglist

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.dialog_ask_new_shopping_list_item.view.*

/*
This will be used to create a custom dialog to ask a new shopping list item from the user.
If you are not a familiar with a dialogs yet, you should read Dialogs material from the Android Developer site.
 */

class AskShoppingListItemDialogFragment : DialogFragment() {
    // Use this instance of the interface to deliver action events
    private lateinit var mListener: AddDialogListener

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    interface AddDialogListener {
        fun onDialogPositiveClick(item: ShoppingListItem)
    }

    // Override the Fragment.onAttach() method to instantiate the AddDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the AddDialogListener so we can send events to the host
            mListener = context as AddDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(
                (context.toString() +
                        " must implement AddDialogListener")
            )
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Load the custom layout for dialog
            val customView = LayoutInflater.from(context).inflate(R.layout.dialog_ask_new_shopping_list_item, null)

            // Build a new dialog
            val builder = AlertDialog.Builder(it)
            builder.setView(customView)
            builder.setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.dialog_ok) { dialog, id ->
                    // create a ShoppingList item
                    val name = customView.nameEditText.text.toString()
                    val count = customView.countEditText.text.toString().toInt()
                    val price = customView.priceEditText.text.toString().toDouble()
                    val item = ShoppingListItem(0, name, count, price)
                    mListener.onDialogPositiveClick(item)
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.dialog_cancel) { dialog, id ->
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}