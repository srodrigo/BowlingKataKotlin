package bowlingkata

import bowlingkata.ScoreType.*

private const val numNormalThrows = 18

fun score(allThrows: String): Int =
        allThrows.foldIndexed(0) { idx, acc, aThrow ->
            var score = aThrow.toScore() + bonuses(aThrow, idx, allThrows)
            if (aThrow.scoreType() == SPARE) {
                score -= allThrows.previous(idx).toScore()
            }
            score + acc
        }

private fun bonuses(aThrow: Char, idx: Int, allThrows: String): Int =
        if (isLastFrame(allThrows, idx)) 0
        else {
            val nextThrow = allThrows.next(idx)
            when (aThrow.scoreType()) {
                SPARE -> nextThrow.toScore()
                STRIKE -> {
                    val nextThrowButOne = allThrows.next(idx + 1)
                    var score = nextThrow.toScore() + nextThrowButOne.toScore()
                    if (nextThrowButOne.scoreType() == SPARE) {
                        score -= nextThrow.toScore()
                    }
                    score
                }
                else -> 0
            }
        }

private fun isLastFrame(allThrows: String, idx: Int): Boolean =
        numNormalThrows <= allThrows.substring(0, idx).sumBy { it.countThrows() }

private fun Char.scoreType(): ScoreType =
        when (this) {
            in ('1'..'9') -> BALLS
            '/' -> SPARE
            'X' -> STRIKE
            else -> MISS
        }

enum class ScoreType {
    MISS,
    BALLS,
    SPARE,
    STRIKE
}

private fun Char.toScore(): Int =
        when (scoreType()) {
            BALLS -> String(charArrayOf(this)).toInt()
            SPARE, STRIKE -> 10
            else -> 0
        }

private fun Char.countThrows(): Int =
        if (scoreType() == STRIKE) 2 else 1

private fun String.previous(idx: Int): Char =
        if (idx > 0) this[idx - 1] else '_'

private fun String.next(idx: Int): Char =
        if (idx < length - 1) this[idx + 1] else '_'
