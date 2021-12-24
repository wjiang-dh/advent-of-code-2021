fun transformImage(input: List<String>): Array<Array<Int>>{
    val image = Array(input.size){Array(input[0].length){0} }
    for (i in input.indices) {
        for (j in input[i].indices){
            if (input[i][j] == '#'){
                image[i][j] = 1
            }
        }
    }
    return image
}

fun enlarge(input: Array<Array<Int>>, background: Int = 0): Array<Array<Int>>{
    val image = Array(input.size+4){Array(input[0].size+4){background} }
    for (i in input.indices) {
        for (j in input[i].indices){
            image[i+2][j+2] = input[i][j]
        }
    }
    return image
}

/** Enlarged image in, enlarged new image out
 *  Use image[0][0]*9 to determine the background color after this enhancement
 */
fun enhance(image: Array<Array<Int>>, algorithm: String): Array<Array<Int>>{
    // determine the background color
    val background = if (algorithm[(image[0][0].toString().repeat(9)).toInt(2)] == '.') 0 else 1
    val vector = listOf(-1, 0, 1).flatMap { i -> listOf(-1, 0, 1).map { j -> i to j } }

    // new image size is shrank in all directions by 1
    val newImage = Array(image.size-2){Array(image[0].size-2){0} }

    for (i in 1 until image.size-1) {
        for (j in 1 until image[0].size - 1) {
            var binary = ""
            for (v in vector) {
                binary += image[i + v.first][j + v.second].toString()
            }
            newImage[i - 1][j - 1] = if (algorithm[binary.toInt(2)] == '#') 1 else 0
        }
    }

    return enlarge(newImage, background)
}

fun main() {
    fun part1(input: List<String>): Int {
        val algorithm = input[0]
        // construct the initial image with 2 empty lines and columns around it
        var image = transformImage(input.subList(2,input.size))
        image = enlarge(image)

        for (turn in 1..2){
            image = enhance(image, algorithm)
        }

        var sum = 0
        for (i in image.indices){
            sum += image[i].sum()
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val algorithm = input[0]
        // construct the initial image with 2 empty lines and columns around it
        var image = transformImage(input.subList(2,input.size))
        image = enlarge(image)

        for (turn in 1..50){
            image = enhance(image, algorithm)
        }

        var sum = 0
        for (i in image.indices){
            sum += image[i].sum()
        }

        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day20_test")
    check(part1(testInput) == 35)
    check(part2(testInput) == 3351)

    val input = readInput("Day20")
    println(part1(input))
    println(part2(input))
}
