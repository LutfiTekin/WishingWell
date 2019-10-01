package dev.into.wishing.well.db

import android.content.Context
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.into.wishing.well.model.Product

@Entity(tableName = "Collections")
class ProductEntity(val productData: String,
                    val collection: String,
                    val purchased: Boolean,
                    @PrimaryKey(autoGenerate = true)
                    var id: Long = 0) {


    val product: Product
        get() {
            return Product.productFrom(productData)
        }

    @WorkerThread
    fun mark(context: Context,isPurchased: Boolean = true){
        CollectionsDB.db(context).data().markProduct(isPurchased,id)
    }

    @WorkerThread
    fun push(context: Context): Long{
        return CollectionsDB.db(context).data().insert(this)
    }

    @WorkerThread
    fun update(context: Context){
        CollectionsDB.db(context).data().update(productData,collection)
    }
}