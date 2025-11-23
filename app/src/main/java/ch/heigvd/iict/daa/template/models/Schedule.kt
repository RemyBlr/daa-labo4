/**
 * DAA - labo4
 * Autors : Bleuer Rémy, Changanaqui Yoann, Rajadurai Thirusan
 * Date : 23.11.2025
 * Description : Entité Note qui définie la structure d'une schedule
 */
package ch.heigvd.iict.daa.labo4.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Schedule(
    @PrimaryKey(autoGenerate = true) var scheduleId : Long?,
    var ownerId : Long,
    var date : Calendar
)