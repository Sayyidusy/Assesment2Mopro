package com.assesment2.mopro.data

import android.app.Application


class DatabaseApplication : Application() {
    val database: BarangDb by lazy { BarangDb.getDatabase(this) }
}
