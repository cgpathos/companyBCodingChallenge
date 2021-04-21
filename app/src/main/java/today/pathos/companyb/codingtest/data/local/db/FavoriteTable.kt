package today.pathos.companyb.codingtest.data.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "FAVORITE_TABLE",
    indices = [Index("nickname")]
)
data class FavoriteTable(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "profile_url") val profileUrl: String,
    @ColumnInfo(name = "nickname") val nickname: String,
    @ColumnInfo(name = "index") val index: String,
)
