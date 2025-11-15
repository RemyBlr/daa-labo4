package ch.heigvd.iict.daa.template.database

import androidx.room.TypeConverter
import ch.heigvd.iict.daa.labo4.models.State
import ch.heigvd.iict.daa.labo4.models.Type
import java.util.Calendar

/**
 * Converters to allow Room to handle complex data types.
 */
class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Calendar? {
        return value?.let {
            Calendar.getInstance().apply {
                timeInMillis = it
            }
        }
    }

    @TypeConverter
    fun fromState(state: State): String {
        return state.name
    }

    @TypeConverter
    fun toState(value: String): State {
        return State.valueOf(value)
    }

    @TypeConverter
    fun fromType(type: Type): String {
        return type.name
    }

    @TypeConverter
    fun toType(value: String): Type {
        return Type.valueOf(value)
    }

    @TypeConverter
    fun calendarToTimestamp(calendar: Calendar?): Long? {
        return calendar?.timeInMillis
    }
}