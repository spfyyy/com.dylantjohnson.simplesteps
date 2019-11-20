package com.dylantjohnson.simplesteps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * The only Activity for this app.
 * <p>
 * It houses the NavHostFragment that is in charge of switching Fragments in and out according to
 * its navigation graph.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
