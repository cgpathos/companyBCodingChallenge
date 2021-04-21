package today.pathos.companyb.codingtest.domain

abstract class Mapper<T1, T2> {
    abstract fun map(value: T1): T2
    abstract fun reverseMap(value: T2): T1

    fun map(values: List<T1>): List<T2> {
        return values
            .asSequence()
            .map { map(it) }
            .toList()
    }

    fun reverseMap(values: List<T2>): List<T1> {
        return values
            .asSequence()
            .map { reverseMap(it) }
            .toList()
    }
}
