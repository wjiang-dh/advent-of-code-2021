fun main() {
    fun calculateFish(timer: Array<Long>, days: Int): Long {
        var oldTimer = timer
        for (day in 1..days){
            val newTimer = Array<Long>(9) {0}
            for (dayToGiveBirth in oldTimer.indices){
                if (dayToGiveBirth == 0){
                    newTimer[6] += oldTimer[dayToGiveBirth]
                    newTimer[8] += oldTimer[dayToGiveBirth]
                } else {
                    newTimer[dayToGiveBirth-1] += oldTimer[dayToGiveBirth]
                }
            }
            oldTimer = newTimer
        }
        return oldTimer.sum()
    }

    fun part1(input: List<String>): Long {
        val timer = Array<Long>(9) {0}
        for (number in input[0].split(",")) {
            timer[number.toInt()]++
        }
        return calculateFish(timer, 80)
    }

    fun part2(input: List<String>): Long {
        val timer = Array<Long>(9) {0}
        for (number in input[0].split(",")){
            timer[number.toInt()]++
        }
        return calculateFish(timer, 256)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == (5934).toLong())
    check(part2(testInput) == 26984457539)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
