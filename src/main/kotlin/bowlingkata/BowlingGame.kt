package bowlingkata

private const val NUM_NORMAL_THROWS = 18
private const val MISS = '-'
private const val SPARE = '/'
private const val STRIKE = 'X'

fun score(allRolls: String): Int =
        allRolls.foldIndexed(0) { idx, acc, roll ->
            acc + roll.toScore() + bonuses(roll, idx, allRolls) - spareDifference(roll, idx, allRolls)
        }

private fun bonuses(roll: Char, idx: Int, allRolls: String): Int =
        if (isLastFrame(allRolls, idx)) 0
        else {
            val nextRoll = allRolls.next(idx)
            when (roll) {
                SPARE -> nextRoll.toScore()
                STRIKE -> {
                    val nextRollButOne = allRolls.next(idx + 1)
                    nextRoll.toScore() + nextRollButOne.toScore() - spareDifference(nextRollButOne, idx + 2, allRolls)
                }
                else -> 0
            }
        }

private fun spareDifference(roll: Char, idx: Int, allRolls: String): Int =
        if (roll == SPARE) allRolls.previous(idx).toScore() else 0

private fun isLastFrame(allRolls: String, idx: Int): Boolean =
        NUM_NORMAL_THROWS <= allRolls.substring(0, idx).sumBy { it.countRolls() }

private fun Char.toScore(): Int =
        when (this) {
            MISS -> 0
            SPARE, STRIKE -> 10
            else -> String(charArrayOf(this)).toInt()
        }

private fun Char.countRolls(): Int =
        if (this == STRIKE) 2 else 1

private fun String.previous(idx: Int): Char =
        if (idx > 0) this[idx - 1] else '-'

private fun String.next(idx: Int): Char =
        if (idx < length - 1) this[idx + 1] else '-'
