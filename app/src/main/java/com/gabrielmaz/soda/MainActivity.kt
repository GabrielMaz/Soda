package com.gabrielmaz.soda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gabrielmaz.soda.controllers.DiscoverController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope{
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val discoverController = DiscoverController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        a()
    }

    fun a(){
        launch(Dispatchers.IO) {
            try {
                val movies = discoverController.getDiscovers()
                println("asd")
            } catch (e: Exception){
                println("asd")
            }
        }
    }
}
