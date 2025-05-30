package com.guilhermedelecrode.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.guilhermedelecrode.orgs.database.converter.Converters
import com.guilhermedelecrode.orgs.database.dao.ProdutoDAO
import com.guilhermedelecrode.orgs.model.Produto

@Database(entities = [Produto::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDAO

    companion object {
        fun instancia(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "org,db"
            ).allowMainThreadQueries()
                .build()
        }
    }
}