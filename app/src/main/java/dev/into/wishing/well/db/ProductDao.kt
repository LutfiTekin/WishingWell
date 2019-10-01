package dev.into.wishing.well.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import dev.into.wishing.well.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM Collections")
    fun fetch(): LiveData<List<ProductEntity>>

    @Query("SELECT * FROM Collections WHERE collection = :collection")
    fun fetch(collection: String): LiveData<List<ProductEntity>>

    @Query("SELECT DISTINCT collection FROM Collections")
    fun fetchCollections(): LiveData<List<String>>

    @Delete
    fun delete(productEntity: ProductEntity)

    @Query("UPDATE Collections SET productData = :productData WHERE collection = :collection")
    fun update(productData: String, collection: String)

    @Query("UPDATE Collections SET purchased = :purchased WHERE id = :id")
    fun markProduct(purchased: Boolean, id: Long)

    @Insert(onConflict = REPLACE)
    fun insert(productEntity: ProductEntity): Long

    @Query("DELETE FROM Collections")
    fun deleteAll()
}