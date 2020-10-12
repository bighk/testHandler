package cm.hk.testhandler

import android.os.*
import android.util.Log
import androidx.annotation.RequiresApi
import java.lang.reflect.Method

const val TAG = "MyHandlerThread"

class MyHandlerThread(name:String):HandlerThread(name) {

    var token = -1
    var handler:MyHandler? = null

    @RequiresApi(Build.VERSION_CODES.M)
    fun addBar(){
        if(handler == null){
            handler = MyHandler(looper)
        }
        val queue = handler?.looper?.queue
        val method: Method = MessageQueue::class.java.getDeclaredMethod("postSyncBarrier")
        method.isAccessible = true
        token = method.invoke(queue) as Int
        Log.i(TAG,"token = $token ")
    }

    fun sendMessage(){
        handler?.sendEmptyMessageDelayed(10,0)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun removeBlock() {
        val queue = handler?.looper?.queue
        val method: Method = MessageQueue::class.java.getDeclaredMethod("removeSyncBarrier",
            Int::class.javaPrimitiveType)
        method.isAccessible = true
        method.invoke(queue,token)
    }


    fun sendNormalMessage() {
        if(handler == null){
            handler = MyHandler(looper)
        }
        handler?.sendEmptyMessageDelayed(10,0)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun addAsyMessage() {
        val asyHandle =  Handler.createAsync(looper) {
            when(it.what){
                10->Log.i(TAG,"asyHandle 同步消息接受")

                11->Log.i(TAG,"asyHandle 异步消息接受")
                else -> Log.i(TAG,"asyHandle 其他消息接受")
            }
             false
        }
        asyHandle.sendEmptyMessageDelayed(11,0)
    }


    class MyHandler(looper: Looper):Handler(looper){
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when(msg?.what){
                10->Log.i(TAG,"同步消息接受")

                11->Log.i(TAG,"异步消息接受")
            }
        }

    }
}