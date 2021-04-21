package today.pathos.companyb.codingtest.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
interface FavoriteDao {
    @Query("select * from FAVORITE_TABLE where nickname like '%' || :nickname || '%'")
    fun getFavoriteList(nickname: String): Single<List<FavoriteTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(favoriteUser: FavoriteTable)

    @Query("delete from FAVORITE_TABLE where nickname = :nickname")
    fun delFavorite(nickname: String): Single<Int>

    @Query("select 1=1 from FAVORITE_TABLE where nickname = :nickname")
    fun isInFavorite(nickname: String): Single<Boolean>
}