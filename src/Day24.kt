import java.util.*
import kotlin.collections.ArrayList

/**
 * Sample section:
 *
 * Step 0: inp w        // input w
 * Step 1: mul x 0      // x = 0
 * Step 2: add x z      // x = z
 * Step 3: mod x 26     // x = z%26         ** z%26 = w0+c0
 * Step 4: div z a      // z /= 1 or 26     ** a is either 1 or 26
 * Step 5: add x b      // x = x+b          ** b can be negative or positive (if positive then the number >9)
 * Step 6: eql x w      // x = 1 or 0       ** if the number in Step 5 is >9, x cannot equal to w, x = 0
 * Step 7: eql x 0      // x = 0 or 1       ** opposite to the result of Step 6
 * Step 8: mul y 0      // y = 0
 * Step 9: add y 25     // y = 25
 * Step 10: mul y x     // y = 0 or 25      ** depend on the result of Step 7 (or the result of Step 5 at root)
 * Step 11: add y 1     // y = 1 or 26      ** depend on the result of Step 7 (or the result of Step 5 at root)
 * Step 12: mul z y     // z *= 1 or 26     ** depend on the result of Step 7 (or the result of Step 5 at root)
 * Step 13: mul y 0     // y = 0
 * Step 14: add y w     // y = w
 * Step 15: add y c     // y = w+c          ** c is a positive number
 * Step 16: mul y x     // y = 0 or w+c     ** depend on the result of Step 7 (or the result of Step 5 at root)
 * Step 17: add z y     // z += 0 or w+c    ** depend on the result of Step 7 (or the result of Step 5 at root)
 *
 * Analysis:
 * z's value varies a lot and can be passed to the next section. x is a switch to determine y's value, y is a container to pass value to z
 * Step 5 is the key. If the number in step 5 is >9, x cannot equal to w in Step 6, x = 1 in the following steps, result it z *= 26 in Step 12, z += w+c in Step 17
 * If the number in step 5 is <9, x is possible to equal to w in Step 6, x = 0 in the following steps, result in z *= 1 in Step 12, z += 0 in Step 17
 * To let z = 0 in the last step, either c is a negative number (since w cannot be negative) or z += 0 in Step 17, which means either b is negative or c is negative
 * z may decrease in Step 4 if a = 26
 * If b is negative, z won't increase in Step 12 or Step 17
 * If b is positive, z will increase by multiply 26 and add (w+c). So x will be (w+c) in Step 3 of the next section, if (w0+c0+b) = w, z won't increase
 *
 * Findings:
 * a = 1 always combines with b > 9, a = 26 always combines with b < 0, c always is positive
 *
 * The will be 7 sections of z = 26z + w+c (a = 1). To let z = 0 in the end, we need to ensure 7 sections of z = z/26 (a = 26, w0 + c0 + b= w)
 */

fun main() {
    fun part1(input: List<String>): String {
        val sections: ArrayList<ArrayList<String>> = arrayListOf()

        var section: ArrayList<String> = arrayListOf()
        for (index in input.indices) {
            if (input[index].startsWith("inp")){
                if (section.isNotEmpty()) sections.add(section)
                section = arrayListOf()
            }
            section.add(input[index])
        }
        sections.add(section)

        val a : ArrayList<Int> = arrayListOf()
        val b : ArrayList<Int> = arrayListOf()
        val c : ArrayList<Int> = arrayListOf()

        val regex = Regex("-?\\d+")

        for (s in sections) {
            for ((index, line) in s.withIndex()) {
                val value = regex.find(line)?.value?.toInt()
                when (index) {
                    4 -> a.add(value!!)
                    5 -> b.add(value!!)
                    15 -> c.add(value!!)
                }
            }
        }

        val modelNumbers = Array(14){0}
        val stack = ArrayDeque<Pair<Int,Int>>()

        for (i in a.indices){
            if (a[i] == 1){
                modelNumbers[i] = 9             // assume max value
                stack.push(Pair(i, 9+c[i]))     // w+c
            }else{
                val out = stack.pop()
                // w = (w0 +c0) + b
                val value = out.second + b[i]
                if (value < 10){
                    modelNumbers[i] = value
                }
                // w0 too big
                else {
                    modelNumbers[i] = 9
                    modelNumbers[out.first] = 9 - (value - 9)
                }
            }
        }

        return modelNumbers.joinToString("") { it.toString() }
    }

    fun part2(input: List<String>): String {
        val sections: ArrayList<ArrayList<String>> = arrayListOf()

        var section: ArrayList<String> = arrayListOf()
        for (index in input.indices) {
            if (input[index].startsWith("inp")){
                if (section.isNotEmpty()) sections.add(section)
                section = arrayListOf()
            }
            section.add(input[index])
        }
        sections.add(section)

        val a : ArrayList<Int> = arrayListOf()
        val b : ArrayList<Int> = arrayListOf()
        val c : ArrayList<Int> = arrayListOf()

        val regex = Regex("-?\\d+")

        for (s in sections) {
            for ((index, line) in s.withIndex()) {
                val value = regex.find(line)?.value?.toInt()
                when (index) {
                    4 -> a.add(value!!)
                    5 -> b.add(value!!)
                    15 -> c.add(value!!)
                }
            }
        }

        val modelNumbers = Array(14){0}
        val stack = ArrayDeque<Pair<Int,Int>>()

        for (i in a.indices){
            if (a[i] == 1){
                modelNumbers[i] = 1             // assume min value
                stack.push(Pair(i, 1+c[i]))     // w+c
            }else{
                val out = stack.pop()
                // w = (w0 +c0) + b
                val value = out.second + b[i]
                if (value > 0){
                    modelNumbers[i] = value
                }
                // w0 too small
                else {
                    modelNumbers[i] = 1
                    modelNumbers[out.first] = 1 + (1 - value)
                }
            }
        }

        return modelNumbers.joinToString("") { it.toString()}
    }

    val input = readInput("Day24")
    println(part1(input))
    println(part2(input))
}

