package fi.m9285.sumcalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }



    fun summa(x: Int, y: Int): Int {
        return x + y;
    }


    fun calculateSum(view: View){
        var num1 = number1.text.toString()
        var num2 = number2.text.toString()

        var num_1 = num1.toInt()
        var num_2 = num2.toInt()

        sum.text = summa(num_1,num_2).toString()



    }


}

