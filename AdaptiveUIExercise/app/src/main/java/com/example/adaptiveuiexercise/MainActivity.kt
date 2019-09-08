package com.example.adaptiveuiexercise

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.* //voidaan hakea id:n avulla muuttujia activity_mainista


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showAphorism(0)
    }

    val aphorisms = arrayOf("You've gotta dance like there's nobody watching,\n" + // 0
            "Love like you'll never be hurt,\n" +
            "Sing like there's nobody listening,\n" +
            "And live like it's heaven on earth.\n" +
            "― William W. Purkey",
            "Be the change that you wish to see in the world.\n" + "― Mahatma Gandhi", // 1
            "There are only two ways to live your life. One is as though nothing is a miracle. The other is as though everything is a miracle.\n" + "― Albert Einstein", // 2
            "Everything you can imagine is real.\n" + "― Pablo Picasso") // 3

    fun showAphorism(index: Int) {
        aphorismText.text = aphorisms[index]
    }

    fun newAphorism(view: View) {
        val int = (0..3).random()
        showAphorism(int)
    }

}
