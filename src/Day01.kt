fun main() {
    fun part1(input: List<String>): Int {
        var count = 0

        for (index in 1 until input.size) {
            if (input[index-1].toInt() < input[index].toInt()) {
                count++
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        var count = 0

        for (index in 2 until input.size-1) {
            val firstSum = input[index-2].toInt() + input[index-1].toInt() + input[index].toInt()
            val secondSum = input[index-1].toInt() + input[index].toInt() + input[index+1].toInt()
            if (firstSum < secondSum) {
                count++
            }
        }
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
