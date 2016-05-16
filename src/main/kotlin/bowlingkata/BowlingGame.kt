package bowlingkata

private const val NUM_NORMAL_THROWS = 18
private const val MISS = '-'
private const val SPARE = '/'
private const val STRIKE = 'X'

fun score(allRolls: String): Int =
        allRolls.foldIndexed(0) { idx, acc, roll ->
            acc + roll.score() + bonus(allRolls, roll, idx) - allRolls.spareDifference(roll, idx)
        }

private fun bonus(allRolls: String, roll: Char, idx: Int): Int =
        if (allRolls.isLastFrame(idx)) 0
        else {
            val nextRoll = allRolls.next(idx)
            when (roll) {
                SPARE -> nextRoll.score()
                STRIKE -> {
                    val nextRollButOne = allRolls.next(idx + 1)
                    nextRoll.score() + nextRollButOne.score() - allRolls.spareDifference(nextRollButOne, idx + 2)
                }
                else -> 0
            }
        }

private fun Char.score(): Int =
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

private fun String.spareDifference(roll: Char, idx: Int): Int =
        if (roll == SPARE) this.previous(idx).score() else 0

private fun String.isLastFrame(idx: Int): Boolean =
        NUM_NORMAL_THROWS <= this.substring(0, idx).sumBy { it.countRolls() }
