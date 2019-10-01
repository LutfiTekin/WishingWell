package dev.into.wishing.well.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import dev.into.wishing.well.db.CollectionsDB
import dev.into.wishing.well.db.ProductEntity

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private var collectionsDB = CollectionsDB.db(application)

    fun fetch(collection : String = ""): LiveData<List<ProductEntity>>{
        if (collection.isEmpty())
            return collectionsDB.data().fetch()
        return collectionsDB.data().fetch(collection)
    }



}


/*
        id  pruduct  cat
        1       I    a
        2       II   a
        3       III   b
 */