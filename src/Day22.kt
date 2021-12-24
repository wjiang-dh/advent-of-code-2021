import kotlin.math.max
import kotlin.math.min

fun main() {
    fun extractNumbers(line: String, range: Boolean): List<Long>?{
        val regex = Regex("-?\\d+")
        val ls = regex.findAll(line).map { it.value.toLong() }.toMutableList()
        if (range){
            if (ls[0] > 50 || ls[2] > 50 || ls[4] > 50) return null
            if (ls[1] < -50 || ls[3] < -50 || ls[5] < -50) return null
            for (i in ls.indices) {
                ls[i] = min(50, ls[i])
                ls[i] = max(-50, ls[i])
            }
        }
        return ls
    }

    fun part1(input: List<String>): Int {
        val cubes : MutableSet<String> = mutableSetOf()

        for (index in input.indices) {
            val ls = extractNumbers(input[index],true)

            if (ls != null && input[index].startsWith("on")) {
                for (x in ls[0]..ls[1]){
                    for (y in ls[2]..ls[3]){
                        for (z in ls[4]..ls[5]){
                            cubes.add("$x $y $z")
                        }
                    }
                }
            } else if (ls != null && input[index].startsWith("off")){
                for (x in ls[0]..ls[1]){
                    for (y in ls[2]..ls[3]){
                        for (z in ls[4]..ls[5]){
                            cubes.remove("$x $y $z")
                        }
                    }
                }
            }
        }
        return cubes.size
    }

    // P2 is finished after looking at https://github.com/elizarov/AdventOfCode2021/blob/main/src/Day22_2.kt
    // reimplemented it (and learned some useful functions) and made some notes for myself
    data class Cube(val x1: Long, val x2: Long, val y1: Long, val y2: Long, val z1: Long, val z2: Long, var on: Boolean){
        fun property(): String{
            return "x1: $x1, x2: $x2, y1: $y1, y2: $y2, z1: $z1, z2: $z2, On?: $on"
        }
    }

    fun part2(input: List<String>): Long {

        val cubes : ArrayList<Cube> = arrayListOf()
        for (index in input.indices) {
            val ls = extractNumbers(input[index],false)

            if (ls != null) cubes.add(Cube(ls[0], ls[1]+1, ls[2], ls[3]+1, ls[4], ls[5]+1, input[index].startsWith("on")))
        }

        // store boundaries
        val xs =  cubes.flatMap { listOf(it.x1, it.x2) }.distinct().sorted()
        val ys =  cubes.flatMap { listOf(it.y1, it.y2) }.distinct().sorted()
        val zs =  cubes.flatMap { listOf(it.z1, it.z2) }.distinct().sorted()

        // divide the space into cubes and use boolean to record their status
        val container = Array(xs.size){Array(ys.size){Array(zs.size){false} } }

        // map cube boundaries to container indices
        val xi = xs.mapIndexed { index, l ->  l to index}.toMap()
        val yi = ys.mapIndexed { index, l ->  l to index}.toMap()
        val zi = zs.mapIndexed { index, l ->  l to index}.toMap()

        // turn on or off cubes according to instructions
        for (cube in cubes){
//            println(cube.property())
            for (x in xi[cube.x1]!! until xi[cube.x2]!!){
                for (y in yi[cube.y1]!! until yi[cube.y2]!!){
                    for (z in zi[cube.z1]!! until zi[cube.z2]!!){
                        container[x][y][z] = cube.on
                    }
                }
            }
        }

        // calculate the size of each cube which is on and sum them up together
        var sum = 0L
        for (x in 0 until container.size-1){
            for (y in 0 until container[x].size-1){
                for (z in 0 until container[x][y].size-1){
                    if (container[x][y][z]) sum += (xs[x+1] - xs[x]) * (ys[y+1] - ys[y]) * (zs[z+1] - zs[z])
                }
            }
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day22_test1")
    check(part1(testInput1) == 39)
    val testInput2 = readInput("Day22_test2")
    check(part2(testInput2) == 2758514936282235)

    val input = readInput("Day22")
    println(part1(input))
    println(part2(input))
}

