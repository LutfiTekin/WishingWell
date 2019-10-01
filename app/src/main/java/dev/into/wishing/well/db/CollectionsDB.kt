package dev.into.wishing.well.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val DB_NAME = "INTODEVCollectionDB"
const val TABLE_COLLECTIONS = "Collections"

@Database(entities = [ProductEntity::class],version = 1,exportSchema = false)
abstract class CollectionsDB : RoomDatabase() {

    abstract fun data(): ProductDao

    companion object{
        private var instance: CollectionsDB? = null

        fun db(context: Context): CollectionsDB{
            if (instance == null){
                instance = Room.databaseBuilder(context.applicationContext,
                    CollectionsDB::class.java,
                    DB_NAME).build()
            }
            return instance as CollectionsDB
        }
    }
}