package today.pathos.companyb.codingtest.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteTable::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}