fun main() {
    fun part1(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        for (move in input){
            val command = move.split(" ")
            val direction = command[0]
            val number = command[1].toInt()
            when (direction) {
                "forward" -> {
                    horizontal += number
                }
                "up" -> {
                    depth -= number
                }
                "down" -> {
                    depth += number
                }
            }
        }
        return depth*horizontal
    }

    fun part2(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        var aim = 0
        for (move in input){
            val command = move.split(" ")
            val direction = command[0]
            val number = command[1].toInt()
            when (direction) {
                "forward" -> {
                    horizontal += number
                    depth += aim * number
                }
                "up" -> {
                    aim -= number
                }
                "down" -> {
                    aim += number
                }
            }
        }
        return depth*horizontal
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
