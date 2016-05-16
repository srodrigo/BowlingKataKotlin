package bowlingkata

private const val numNormalThrows = 18
private const val MISS = '-'
private const val SPARE = '/'
private const val STRIKE = 'X'

fun score(allThrows: String): Int =
        allThrows.foldIndexed(0) { idx, acc, aThrow ->
            var score = aThrow.toScore() + bonuses(aThrow, idx, allThrows)
            if (aThrow == SPARE) {
                score -= allThrows.previous(idx).toScore()
            }
            score + acc
        }

private fun bonuses(aThrow: Char, idx: Int, allThrows: String): Int =
        if (isLastFrame(allThrows, idx)) 0
        else {
            val nextThrow = allThrows.next(idx)
            when (aThrow) {
                SPARE -> nextThrow.toScore()
                STRIKE -> {
                    val nextThrowButOne = allThrows.next(idx + 1)
                    var score = nextThrow.toScore() + nextThrowButOne.toScore()
                    if (nextThrowButOne == SPARE) {
                        score -= nextThrow.toScore()
                    }
                    score
                }
                else -> 0
            }
        }

private fun isLastFrame(allThrows: String, idx: Int): Boolean =
        numNormalThrows <= allThrows.substring(0, idx).sumBy { it.countThrows() }

private fun Char.toScore(): Int =
        when (this) {
            MISS -> 0
            SPARE, STRIKE -> 10
            else -> String(charArrayOf(this)).toInt()
        }

private fun Char.countThrows(): Int =
        if (this == STRIKE) 2 else 1

private fun String.previous(idx: Int): Char =
        if (idx > 0) this[idx - 1] else '-'

private fun String.next(idx: Int): Char =
        if (idx < length - 1) this[idx + 1] else '-'
