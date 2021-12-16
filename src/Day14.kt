fun <K> increment(map: MutableMap<K, Long>, key: K, number: Long = 1) {
    map.putIfAbsent(key, 0)
    map[key] = map[key]!! + number
}

fun grow(input: List<String>, turns: Int): Long{
    val text = input[0]

    val dict : HashMap<String, String> = hashMapOf()
    for (line in input){
        if (line.contains(" -> ")){
            val a = line.split(" -> ")[0]
            val b = line.split(" -> ")[1]
            dict[a] = b
        }
    }

    var sliceCounts : MutableMap<String, Long> = HashMap()
    for (index in 0..text.length-2){
        val slice = text.slice(index..index+1)
        increment(sliceCounts, slice)
    }

    for (turn in 1..turns){
        val newSliceCounts : HashMap<String, Long> = hashMapOf()
        for (slice in sliceCounts.keys){
            increment(newSliceCounts, slice[0].toString() + dict[slice], sliceCounts[slice]!!)
            increment(newSliceCounts, dict[slice] + slice[1].toString(), sliceCounts[slice]!!)
        }
        sliceCounts = newSliceCounts
    }

    val charCounts : HashMap<Char, Long> = hashMapOf()
    for (slice in sliceCounts.keys){
        increment(charCounts, slice[0], sliceCounts[slice]!!)
    }
    increment(charCounts, text[text.length-1])
    val sorted = charCounts.values.sorted()

    return sorted[sorted.size-1] - sorted[0]
}

fun main() {

    fun part1(input: List<String>): Long {

        return grow(input, 10)
    }

    fun part2(input: List<String>): Long {

        return grow(input, 40)
    }

    // Discarded version:
//    fun part1(input: List<String>): Int {
//
//        var text = input[0]
//
//        val dict : HashMap<String, String> = hashMapOf()
//        for (line in input){
//            if (line.contains(" -> ")){
//                val a = line.split(" -> ")[0]
//                val b = line.split(" -> ")[1]
//                dict[a] = a[0] + b
//            }
//        }
//
//        for (turn in 1..10){
//            var newText = ""
//            for (index in 0..text.length-2){
//                if (dict.containsKey(text.slice(index..index+1))){
//                    newText += dict[text.slice(index..index+1)]
//                }else{
//                    newText += text[index]
//                }
//            }
//            newText += text[text.length-1]
//            text = newText
//        }
//        val charset = text.toCharArray().toSet()
//        val freq: ArrayList<Int> = arrayListOf()
//        for (char in charset){
//            freq.add(text.filter { it == char }.count())
//        }
//
//        println(charset.toString() + " : " + freq.toString())
//        freq.sort()
//
//        return freq[freq.size-1] - freq[0]
//    }



    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == (1588).toLong())
    check(part2(testInput) == 2188189693529)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
