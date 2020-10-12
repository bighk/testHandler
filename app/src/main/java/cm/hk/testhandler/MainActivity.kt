package cm.hk.testhandler

import android.os.*
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.lang.reflect.Method


class MainActivity : AppCompatActivity() {

    var textView:TextView? = null
    var barToken = -1
    var thread:MyHandlerThread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.title)
        thread = MyHandlerThread("ac")
        thread?.start()

    }

    override fun onResume() {
        super.onResume()

    }



    @RequiresApi(Build.VERSION_CODES.M)
    fun addBlock(view: View) {
        thread?.addBar()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun removeBlock(view: View) {
        thread?.removeBlock()
    }


    fun sendMessage(view: View) {
        thread?.sendNormalMessage()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun addAsyMessage(view: View) {
        thread?.addAsyMessage()
    }
}
