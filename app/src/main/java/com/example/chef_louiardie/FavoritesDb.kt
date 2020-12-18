package com.example.chef_louiardie

import java.util.*
import kotlin.collections.ArrayList

class FavoritesDb {
    companion object Factory{
        @JvmStatic
        var favorites: MutableList<String> = ArrayList()
        fun addFavorite(id: String) {
            if(!favorites.contains(id)){
                favorites.add(id)
            }
        }
    }
}