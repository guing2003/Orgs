package com.guilhermedelecrode.orgs.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.guilhermedelecrode.orgs.database.converter.Converters
import com.guilhermedelecrode.orgs.database.dao.ProdutoDAO
import com.guilhermedelecrode.orgs.model.Produto

@Database(entities = [Produto::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase :RoomDatabase() {
    abstract fun produtoDao() : ProdutoDAO
}