fun main() {

    fun foldPaper(paper: Array<Array<String>>, instruction: Pair<String, Int>): Array<Array<String>>{
        val afterFold: Array<Array<String>>
        val v = instruction.second

        if (instruction.first == "x"){
            afterFold = Array(paper.size){Array(v+1){" "} }
            for (y in paper.indices){
                for (x in paper[y].indices){
                    if (paper[y][x] == "x" && x < v){
                        afterFold[y][x] = "x"
                    } else if (paper[y][x] == "x" && x >= v){
                        afterFold[y][v - (x - v)] = "x"
                    }
                }
            }
        } else {
            afterFold = Array(v+1){Array(paper[0].size){" "} }
            for (y in paper.indices){
                for (x in paper[y].indices){
                    if (paper[y][x] == "x" && y < v){
                        afterFold[y][x] = "x"
                    } else if (paper[y][x] == "x" && y >= v){
                        afterFold[v - (y - v)][x] = "x"
                    }
                }
            }
        }
        return afterFold
    }

    fun part1(input: List<String>): Int {

        val xs: ArrayList<Int> = arrayListOf()
        val ys: ArrayList<Int> = arrayListOf()
        val fold : ArrayList<Pair<String, Int>> = arrayListOf()

        for (line in input){
            if (!line.startsWith("fold") && line.isNotEmpty()){
                val x = line.split(",")[0].toInt()
                val y = line.split(",")[1].toInt()
                xs.add(x)
                ys.add(y)
            }
            if (line.startsWith("fold")){
                val instruction = line.split(" ")[2]
                fold.add(Pair(instruction.split("=")[0], instruction.split("=")[1].toInt()))
            }
        }

        var paper : Array<Array<String>> = Array(ys.maxOrNull()!!+1){ Array(xs.maxOrNull()!!+1){" "} }

        for (index in xs.indices){
            paper[ys[index]][xs[index]] = "x"
        }

        paper = foldPaper(paper, fold[0])

        var count = 0
        for (index in paper.indices){
            for (item in paper[index]){
                if (item == "x") count++
            }
        }
        return count
    }

    fun part2(input: List<String>) {
        val xs: ArrayList<Int> = arrayListOf()
        val ys: ArrayList<Int> = arrayListOf()
        val fold : ArrayList<Pair<String, Int>> = arrayListOf()

        for (line in input){
            if (!line.startsWith("fold") && line.isNotEmpty()){
                val x = line.split(",")[0].toInt()
                val y = line.split(",")[1].toInt()
                xs.add(x)
                ys.add(y)
            }
            if (line.startsWith("fold")){
                val instruction = line.split(" ")[2]
                fold.add(Pair(instruction.split("=")[0], instruction.split("=")[1].toInt()))
            }
        }

        var paper : Array<Array<String>> = Array(ys.maxOrNull()!!+1){ Array(xs.maxOrNull()!!+1){" "} }

        for (index in xs.indices){
            paper[ys[index]][xs[index]] = "x"
        }

        for (pair in fold){
            paper = foldPaper(paper, pair)
        }

        for (line in paper){
            println(line.joinToString(separator = ""))
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)

    val input = readInput("Day13")
    println(part1(input))
    part2(input)
}
