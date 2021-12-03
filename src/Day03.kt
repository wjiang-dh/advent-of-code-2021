fun main() {

    fun part1(input: List<String>): Int {
        val bitLength = input[0].length
        val numOfZero = IntArray(bitLength){0}

        var gamma = ""
        var epsilon = ""

        for (line in input){
            for (index in 0 until bitLength){
                if (line[index].digitToInt() == 0){
                    numOfZero[index]++
                }
            }
        }

        for (num in numOfZero){
            gamma += if (num > input.size/2) "0" else "1"
            epsilon += if (num > input.size/2) "1" else "0"
        }

        return gamma.toInt(2) * epsilon.toInt(2)
    }

    fun findRating(input: List<String>, switch: String) : String {
        val bitLength = input[0].length
        var list = input

        for (index in 0..bitLength){
            val zeroList : MutableList<String> = mutableListOf()
            val oneList : MutableList<String> = mutableListOf()

            for (line in list) {
                if (line[index].digitToInt() == 0) {
                    zeroList.add(line)
                } else {
                    oneList.add(line)
                }
            }

            list = if (switch == "o2"){
                if (zeroList.size > oneList.size) zeroList else oneList
            } else {
                if (zeroList.size > oneList.size) oneList else zeroList
            }

            if (list.size == 1){
                break
            }
        }
        return list[0]
    }

    fun part2(input: List<String>): Int {

        val o2 = findRating(input, "o2")
        val co2 = findRating(input, "co2")

        return o2.toInt(2) * co2.toInt(2)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}