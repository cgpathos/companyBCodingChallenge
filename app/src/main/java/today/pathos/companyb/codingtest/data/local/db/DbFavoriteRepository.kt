package today.pathos.companyb.codingtest.data.local.db

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.internal.operators.completable.CompletableFromAction
import io.reactivex.rxjava3.schedulers.Schedulers
import today.pathos.companyb.codingtest.core.domain.toDataSingle
import today.pathos.companyb.codingtest.domain.entity.GithubUser
import today.pathos.companyb.codingtest.domain.repository.FavoriteRepository
import javax.inject.Inject

class DbFavoriteRepository @Inject constructor(
    private val favoriteDao: FavoriteDao,
    private val dbFavoriteMapper: DbFavoriteMapper,
) : FavoriteRepository {
    override fun getFavoriteList(keyword: String): Single<List<GithubUser>> {
        return favoriteDao.getFavoriteList(keyword)
            .subscribeOn(Schedulers.io())
            .map { dbFavoriteMapper.map(it) }
    }

    override fun addFavorite(user: GithubUser): Single<GithubUser> {
        return CompletableFromAction { favoriteDao.addFavorite(dbFavoriteMapper.reverseMap(user)) }
            .subscribeOn(Schedulers.io())
            .toSingle { user.copy(isFavorite = true) }
            .onErrorReturn { user }
    }

    override fun delFavorite(user: GithubUser): Single<GithubUser> {
        return favoriteDao.delFavorite(user.nickname)
            .subscribeOn(Schedulers.io())
            .concatMap { user.copy(isFavorite = false).toDataSingle() }
            .onErrorReturn { user }
    }

    override fun isInFavorite(user: GithubUser): Single<Boolean> {
        return favoriteDao.isInFavorite(user.nickname)
            .subscribeOn(Schedulers.io())
            .onErrorReturn { false }
    }
}