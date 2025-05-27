package com.guilhermedelecrode.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.guilhermedelecrode.orgs.model.Produto

@Dao
interface ProdutoDAO {

    @Query("SELECT * FROM Produto")
    fun buscaTodos() : List<Produto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun salva(vararg produto: Produto)

    @Delete
    fun deleta(produto: Produto)

    @Query("SELECT * FROM PRODUTO WHERE id = :id")
    fun buscaPorId(id: Long) : Produto?
}