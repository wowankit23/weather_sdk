package com.mobileprogramming.weather_sdk

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector


class MyApplication : Application(), HasAndroidInjector  {


    //    @Inject
//    lateinit var  mInjector : DispatchingAndroidInjector<Any>
//
//
//    init {
//        instance = this
//
//    }
//
//    companion object {
//        private var instance: MyApplication? = null
//
//
//
//
//        fun applicationContext() : Context {
//            return instance!!.applicationContext
//        }
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//
//        DaggerAppComponent.builder()
//            .appModule(AppModule(this))
//            .build()
//            .inject(this)
//
//
//       // val context: Context = MyApplication.applicationContext()
//
////        DaggerAppComponent.builder().
////        application(this).
////        build().
////        inject(this)
//
//    }
//
//    override fun androidInjector(): AndroidInjector<Any> {
//      return mInjector
//    }
    override fun androidInjector(): AndroidInjector<Any> {
        TODO("Not yet implemented")
    }


}