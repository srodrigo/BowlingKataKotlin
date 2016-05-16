package bowlingkata

private const val NUM_NORMAL_THROWS = 18
private const val MISS = '-'
private const val SPARE = '/'
private const val STRIKE = 'X'

fun score(allThrows: String): Int =
        allThrows.foldIndexed(0) { idx, acc, aThrow ->
            acc + aThrow.toScore() + bonuses(aThrow, idx, allThrows) - spareDifference(aThrow, idx, allThrows)
        }

private fun bonuses(aThrow: Char, idx: Int, allThrows: String): Int =
        if (isLastFrame(allThrows, idx)) 0
        else {
            val nextThrow = allThrows.next(idx)
            when (aThrow) {
                SPARE -> nextThrow.toScore()
                STRIKE -> {
                    val nextThrowButOne = allThrows.next(idx + 1)
                    nextThrow.toScore() + nextThrowButOne.toScore() - spareDifference(nextThrowButOne, idx + 2, allThrows)
                }
                else -> 0
            }
        }

private fun spareDifference(aThrow: Char, idx: Int, allThrows: String): Int =
        if (aThrow == SPARE) allThrows.previous(idx).toScore() else 0

private fun isLastFrame(allThrows: String, idx: Int): Boolean =
        NUM_NORMAL_THROWS <= allThrows.substring(0, idx).sumBy { it.countThrows() }

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
