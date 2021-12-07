import kotlin.math.absoluteValue

fun main() {

    fun part1(input: List<String>): Int {
        val numbers = input[0].split(",").map{ it.toInt() }.toIntArray()
        var minDist = Int.MAX_VALUE
        for (point in numbers.minOrNull()!!..numbers.maxOrNull()!!){
            var sum = 0
            for (number in numbers){
                sum += (number - point).absoluteValue
            }
            if (sum < minDist){
                minDist = sum
            }
        }
        return minDist
    }

    fun part2(input: List<String>): Int {
        val numbers = input[0].split(",").map{ it.toInt() }.toIntArray()
        var minDist = Int.MAX_VALUE
        for (point in numbers.minOrNull()!!..numbers.maxOrNull()!!){
            var sum = 0
            for (number in numbers){
                val distance = (number - point).absoluteValue
                sum += distance*(distance+1)/2
            }
            if (sum < minDist){
                minDist = sum
            }
        }
        return minDist
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
