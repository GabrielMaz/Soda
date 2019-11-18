package com.gabrielmaz.soda

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.gabrielmaz.soda.inject.databaseModule
import com.gabrielmaz.soda.inject.moviesModule
import com.gabrielmaz.soda.inject.networkModule
import com.gabrielmaz.soda.presentation.view.MainActivity
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.lang.ref.WeakReference

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(networkModule, moviesModule, databaseModule))
        }

        listenActivityCallbacks()
    }

    private fun listenActivityCallbacks() {
        registerActivityLifecycleCallbacks(Lifecycle())
    }

    inner class Lifecycle : ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity?) {
        }

        override fun onActivityResumed(activity: Activity?) {
            activity?.let {
                currentActivity = WeakReference(it)
            }
        }

        override fun onActivityStarted(activity: Activity?) {
            activity?.let {
                currentActivity = WeakReference(it)
            }
        }

        override fun onActivityDestroyed(activity: Activity?) {
            if (activity == currentActivity.get()) {
                currentActivity.clear()
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }

        override fun onActivityStopped(activity: Activity?) {
        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        }
    }

    companion object {
        var currentActivity = WeakReference<Activity>(null)

        fun goToLoginScreen() {
            currentActivity.get()?.let {
                val intent = Intent(it, MainActivity::class.java)
                it.startActivity(intent)
                it.finish()
            }
        }
    }
}
