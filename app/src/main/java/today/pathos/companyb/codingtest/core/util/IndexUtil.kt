package today.pathos.companyb.codingtest.core.util

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class IndexUtil @Inject constructor() {
    private val initialList = charArrayOf(
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ',
        'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    )

    private fun isKorean(char: Char): Boolean = char.toInt() in 0xAC00..0xD7a3

    @Suppress("UNUSED_VARIABLE")
    fun extractIndex(target: String): String {
        val char = target[0]
        return if (isKorean(char)) {
            (char.toInt() - 0xAC00).let {
                var v = it
                val f = v % 28 // 중성
                v /= 28
                val m = v % 21 // 종성
                v /= 21
                val i = v % 19
                initialList[i].toString()
            }
        } else {
            char.toUpperCase().toString()
        }
    }
}
