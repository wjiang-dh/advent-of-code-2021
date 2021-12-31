import java.util.*
import kotlin.collections.ArrayList

val regex = Regex("\\d+")

fun reduce(input: String): String{
    var output = input
    val stack = ArrayDeque<Int>()
    var stop = false

    while (!stop){
        stop = true
        var goSplit = true

        // check for explode
        for (i in output.indices){
            if (output[i] == '['){
                stack.push(i)
            } else if (output[i] == ']'){
                stack.pop()
            }
            if (stack.size == 5){
                // into explode mode
                stop = false
                goSplit = false
                // find target
//                println("Before explode: $output")
                val close = output.indexOf(']', i)
                val start = output.slice(0..close).lastIndexOf('[')
                val values = regex.findAll(output.slice(start..close)).map { it.value.toInt() }.toList()

                var firstHalf = output.slice(0 until start)
                var secondHalf = output.slice(close+1 until output.length)

                val firstNumbers = regex.findAll(firstHalf).map { it.value.toInt() }.toList()
                if (firstNumbers.isNotEmpty()){
                    val n1 = firstNumbers[firstNumbers.size-1]
                    val i1 = firstHalf.lastIndexOf(n1.toString())
                    firstHalf = if (n1 > 9){
                        firstHalf.slice(0 until i1) + (n1+values[0]).toString() + firstHalf.slice(i1+2 until firstHalf.length)
                    } else {
                        firstHalf.slice(0 until i1) + (n1+values[0]).toString() + firstHalf.slice(i1+1 until firstHalf.length)
                    }
                }

                val secondNumbers = regex.findAll(secondHalf).map { it.value.toInt() }.toList()

                if (secondNumbers.isNotEmpty()){
                    val n2 = secondNumbers[0]
                    val i2 = secondHalf.indexOf(n2.toString())
                    secondHalf = if (n2 > 9){
                        secondHalf.slice(0 until i2) + (n2+values[1]).toString() + secondHalf.slice(i2+2 until secondHalf.length)
                    } else {
                        secondHalf.slice(0 until i2) + (n2+values[1]).toString() + secondHalf.slice(i2+1 until secondHalf.length)
                    }
                }

                output = firstHalf + "0" + secondHalf
                stack.clear()
//                println("After explode: $output")
//                println()
                break
            }
        }

        // check for split
        if (goSplit){
            val numbers = regex.findAll(output).map { it.value.toInt() }.toList()
            for (num in numbers) {
                if (num > 9) {
//                    println("Before split: $output")
                    stop = false
                    val index = output.indexOf(num.toString())
                    val first = num / 2
                    val second = num - first
                    output =
                        output.slice(0 until index) + "[$first,$second]" + output.slice(index + 2 until output.length)
//                    println("After split: $output")
//                    println()
                    break
                }
            }
        }
    }
    return output
}

fun magnitude(input: String): Int{
    val stack = ArrayDeque<String>()
    for (i in input.indices){
        when {
            input[i].isDigit() -> {
                stack.push(input[i].toString())
            }
            input[i] == '[' -> {
                stack.push("[")
            }
            input[i] == ',' -> {
                stack.push(",")
            }
            input[i] == ']' -> {
                val v2 = stack.pop().toInt()
                stack.pop() // ","
                val v1 = stack.pop().toInt()
                stack.pop() // "["
                stack.push((v1*3 + v2*2).toString())
            }
        }
    }
    return stack.pop().toInt()
}

fun main() {
    fun part1(input: List<String>): Int {
        var result = input[0]
        result = reduce(result)

        for (i in 1 until input.size){
            result = "[" + result + "," + input[i] + "]"
            result = reduce(result)
        }

        return magnitude(result)
    }

    fun part2(input: List<String>): Int {

        val magnitudes = ArrayList<Int>()

        for (i in input.indices){
            for (j in input.indices){
                if (i != j){
                    val result = reduce("[" + input[i] + "," + input[j] + "]")
                    magnitudes.add(magnitude(result))
                }
            }
        }

        return magnitudes.maxOrNull()!!
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day18_test")
    check(part1(testInput) == 4140)
    check(part2(testInput) == 3993)

    val input = readInput("Day18")
    println(part1(input))
    println(part2(input))
}
