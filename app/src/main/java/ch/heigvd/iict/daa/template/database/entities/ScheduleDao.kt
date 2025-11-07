package ch.heigvd.iict.daa.template.database.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ch.heigvd.iict.daa.labo4.models.Schedule

/**
 * Data Access Object for operations on the Schedule entity.
 */
@Dao
interface ScheduleDao {

    @Insert
    suspend fun insertSchedule(schedule: Schedule): Long

    @Query("DELETE FROM Schedule")
    suspend fun deleteAllSchedules()
}