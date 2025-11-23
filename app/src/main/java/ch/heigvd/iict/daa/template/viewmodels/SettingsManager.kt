/**
 * DAA - labo4
 * Autors : Bleuer Rémy, Changanaqui Yoann, Rajadurai Thirusan
 * Date : 23.11.2025
 * Description : Manage SharedPreferences
 */
package ch.heigvd.iict.daa.template.viewmodels

import android.content.Context
import android.content.SharedPreferences

class SettingsManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)

    companion object {
        const val KEY_SORT_TYPE = "sort_type"
        const val SORT_BY_CREATION_DATE = 0
        const val SORT_BY_SCHEDULE_DATE = 1
    }

    // Sauvegarde le type de tri
    fun saveSortType(sortType: Int) {
        prefs.edit().putInt(KEY_SORT_TYPE, sortType).apply()
    }

    // Récupère le type de tri sauvegardé, avec une valeur par défaut
    fun getSortType(): Int {
        return prefs.getInt(KEY_SORT_TYPE, SORT_BY_CREATION_DATE) // Tri par date de création par défaut
    }
}
