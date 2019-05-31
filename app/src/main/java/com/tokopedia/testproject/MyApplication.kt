package com.tokopedia.testproject

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        setRealm()
    }

    private fun setRealm(){
        Realm.init(this)

        val config = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()

        Realm.setDefaultConfiguration(config)
    }
}