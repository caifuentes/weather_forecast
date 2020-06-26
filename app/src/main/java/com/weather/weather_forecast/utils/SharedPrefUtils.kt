package com.weather.weather_forecast.utils

import com.pixplicity.easyprefs.library.Prefs

class SharedPrefUtils {

    companion object {
        private const val FAVORITE_IDS = "FAVORITE_IDS"

        fun getFavorites() = Prefs.getString(FAVORITE_IDS, "").split(",")

        fun updateFavorites(id: String) {
            val favorites: MutableList<String> = mutableListOf()
            val ids = Prefs.getString(FAVORITE_IDS, "")
            favorites.addAll(ids.split(","))
            if (id in favorites) {
                favorites.remove(id)
            } else {
                favorites.add(id)
            }
            Prefs.putString(FAVORITE_IDS, favorites.joinToString(","))
        }
    }

}