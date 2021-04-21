package today.pathos.companyb.codingtest.domain.entity

/**
 * Entity 모델 클래스
 * Clean Architecture 기반으로 구현하였지만 개발편의상 앱 전반에 이 클래스를 사용함.
 */
data class GithubUser(
    val profileUrl: String = "",
    val nickname: String = "",
    val index: String = "",
    var isFavorite: Boolean = false,
    var isHeader: Boolean = false,
)