import java.util.ArrayDeque

fun main() {

    fun part1(input: List<String>): Int {
        val points : HashMap<Char, Int> = hashMapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
        var sum = 0
        for (line in input){
            val stack = ArrayDeque<Char>()
            for (char in line){
                if (char == '(' || char == '[' || char == '{' || char == '<') stack.push(char)
                else {
                    if (stack.isEmpty()) {
                        break
                    }
                    if ((stack.peek() == '(' && char == ')') ||
                        (stack.peek() == '[' && char == ']') ||
                        (stack.peek() == '{' && char == '}') ||
                        (stack.peek() == '<' && char == '>')) stack.pop() else {
                        sum += points[char]!!
                        break
                    }
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        val scores : MutableList<Long> = arrayListOf()
        val points : HashMap<Char, Long> = hashMapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)
        for (line in input){
            var sum = (0).toLong()
            val stack = ArrayDeque<Char>()
            var corrupted = false
            for (char in line){
                if (char == '(' || char == '[' || char == '{' || char == '<') stack.push(char)
                else {
                    if (stack.isEmpty()) {
                        corrupted = true
                        break
                    }
                    if ((stack.peek() == '(' && char == ')') ||
                        (stack.peek() == '[' && char == ']') ||
                        (stack.peek() == '{' && char == '}') ||
                        (stack.peek() == '<' && char == '>')) stack.pop() else {
                        corrupted = true
                        break
                    }
                }
            }
            if (!corrupted){
                while (stack.isNotEmpty()){
                    when (stack.peek()) {
                        '(' -> sum = sum * 5 + points[')']!!
                        '[' -> sum = sum * 5 + points[']']!!
                        '{' -> sum = sum * 5 + points['}']!!
                        '<' -> sum = sum * 5 + points['>']!!
                    }
                    stack.pop()
                }
                scores.add(sum)
            }
        }
        scores.sort()
        return scores[scores.size/2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == (288957).toLong())

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
