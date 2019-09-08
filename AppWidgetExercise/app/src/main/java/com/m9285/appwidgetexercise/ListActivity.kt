package com.m9285.appwidgetexercise

import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.AlarmClock
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.ask_new_thing.*
import kotlinx.android.synthetic.main.content_main.*
import java.time.LocalDateTime

class ListActivity : AppCompatActivity(), AskListItemDialogFragment.AddDialogListener {

    // List items
    private var List: MutableList<ListItem> = ArrayList()
    // List adapter
    private lateinit var adapter: ListAdapter

    // List Room database
    private lateinit var db: ListRoomDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        // Use LinearManager as a layout manager for recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Create Adapter for recyclerView
        adapter = ListAdapter(List)
        recyclerView.adapter = adapter


        // Create database and get instance
        db = Room.databaseBuilder(applicationContext, ListRoomDatabase::class.java, "hs_db").build()

        // load list items
        loadListItems()

        // when you click FAB icon (id:fb), will open dialog window
        fab.setOnClickListener { view ->
            // create and show dialog
            val dialog = AskListItemDialogFragment()
            dialog.show(supportFragmentManager, "AskNewItemDialogFragment")
        }

        //deleting row, function below
        initSwipe()

    }

    // Load list items from db
    private fun loadListItems() {
        // Question: How to create a database operation, if UI thread can't be used
        // -> Handler
        // -> AsyncTask
        // -> Thread
        // How about insert, query, delete, update, etc...
        // -> So you might need to do a multiple Handlers or AsyncTask to handle all situations...
        // => Not GOOD!
        // Other options: Android Architecture Components, RXJava, RXAndroid, RXKotlin
        // - You will learn these in other exercises
        // OK - Now, we will use own Thread and Handler for a learning purpose

        // Create a Handler Object
        val handler = Handler(Handler.Callback {
            // Toast message
            Toast.makeText(applicationContext,it.data.getString("message"), Toast.LENGTH_SHORT).show()
            // Notify adapter data change
            adapter.update(List)
            true
        })
        // Create a new Thread to insert data to database
        Thread(Runnable {
            List = db.ListDao().getAll()
            val message = Message.obtain()
            if (List.size > 0)
                message.data.putString("message","All list items read from db!")
            else
                message.data.putString("message","List is empty!")
            handler.sendMessage(message)
        }).start()
    }


    // Add a new list item to db
    override fun onDialogPositiveClick(item: ListItem) {
        // Create a Handler Object
        val handler = Handler(Handler.Callback {
            // Toast message
            Toast.makeText(applicationContext,it.data.getString("message"), Toast.LENGTH_SHORT).show()
            // Notify adapter data change
            adapter.update(List)
            true
        })
        // Create a new Thread to insert data to database
        Thread(Runnable {
            // insert and get autoincrement id of the item
            val id = db.ListDao().insert(item)
            // add to view
            item.id = id.toInt()
            List.add(item)
            val message = Message.obtain()
            message.data.putString("message","Item added to db!")
            handler.sendMessage(message)
        }).start()
    }
    /*
    A final step in this exercise is to delete a row from the recyclerView (also in adapter and db).
    Add a initSwipe() function call at the last line in the onCreate() function and create a following function inside MainActivity.

    Here ItemTouchHelper will be used to determine LEFT swipe in line 128. -> changed to RIGHT
    You need to define onSwiped() and onMoved() functions. Now only onSwiped is used for a deleting.
    RecyclerViews holder position will be used to remove shopping list item from UI and from the database.
    Own Thread will be used in deletion.
    After deletion is ready, Hander object will be used to show a message to end user and notify data change in the adapter.
     */
    // Initialize swipe in recyclerView
    private fun initSwipe() {
        // Create Touch Callback
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            // Swiped
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // adapter delete item position
                val position = viewHolder.adapterPosition
                // Create a Handler Object
                val handler = Handler(Handler.Callback {
                    // Toast message
                    Toast.makeText(applicationContext,it.data.getString("message"), Toast.LENGTH_SHORT).show()
                    // Notify adapter data change
                    adapter.update(List)
                    true
                })
                // Get remove item id
                var id = List[position].id
                // Remove from UI list
                List.removeAt(position)
                // Remove from db
                Thread(Runnable {
                    db.ListDao().delete(id)
                    val message = Message.obtain()
                    message.data.putString("message","Item deleted from db!")
                    handler.sendMessage(message)
                }).start()
            }

            // Moved
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                return true
            }

        }
        // Attach Item Touch Helper to recyclerView
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    /* fab2 onclick funktion, back to main */
    fun buttonClickBack(view: View) {
        val message = "Back to main"
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }
}

