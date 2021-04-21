package today.pathos.companyb.codingtest.data.remote.dto

data class DtoResult(
    val totalCount: Int = 0,
    val incompleteResults: Boolean = false,
    val items: List<DtoGithubUser>? = null
)
