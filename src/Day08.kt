fun main() {

    fun part1(input: List<String>): Int {
        var count = 0
        for (line in input) {
            for (digit in line.split(" | ")[1].split((" "))){
                if (digit.length == 2 || digit.length == 3 || digit.length == 4 || digit.length == 7){
                    count++
                }
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {

        val firstHalf: MutableList<String> = ArrayList()
        val secondHalf: MutableList<String> = ArrayList()
        for (line in input) {
            firstHalf.add(line.split(" | ")[0])
            secondHalf.add(line.split(" | ")[1])
        }

        val correspondences: MutableList<HashMap<Char, Char>> = ArrayList()
        for (line in firstHalf){
            val correspondence: HashMap<Char, Char> = hashMapOf()
            for (letter in listOf<Char>('a', 'b', 'c', 'd', 'e', 'f', 'g')){
                when (line.filter { it == letter }.count()) {
                    4 -> {
                        correspondence[letter] = 'e'
                    }
                    6 -> {
                        correspondence[letter] = 'b'
                    }
                    7 -> {
                        // can be "d" or "g": "d" in 4, "g" not in 4
                        for (digit in line.split(" ")){
                            if (digit.length == 4) { // digit is 4
                                correspondence[letter] = if (digit.contains(letter)) 'd' else 'g'
                            }
                        }
                    }
                    8 -> {
                        // can be "a" or "c": c in 1, "a" not in 1
                        for (digit in line.split(" ")){
                            if (digit.length == 2) { // digit is 1
                                correspondence[letter] = if (digit.contains(letter)) 'c' else 'a'
                            }
                        }
                    }
                    9 -> {
                        correspondence[letter] = 'f'
                    }
                }
            }
            correspondences.add(correspondence)
        }

        var count = 0
        val decode : HashMap<String, String> = hashMapOf("abcefg" to "0", "cf" to "1", "acdeg" to "2", "acdfg" to "3", "bcdf" to "4", "abdfg" to "5", "abdefg" to "6", "acf" to "7", "abcdefg" to "8", "abcdfg" to "9")
        for (index in 0 until secondHalf.size){
            val correspondence = correspondences[index]
            val strings = secondHalf[index].split(" ")
            var numbers = ""
            for (string in strings){
                val charArray = string.toCharArray()
                for (i in charArray.indices){
                    charArray[i] = correspondence[charArray[i]]!!
                }
                val sorted = charArray.sorted().joinToString("")
                numbers += decode[sorted]
            }
            count += numbers.toInt()
        }
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
