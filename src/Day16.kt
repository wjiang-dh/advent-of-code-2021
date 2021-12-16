data class Packet(val version: Int, val type: Int, var value: Long, val subpackets: ArrayList<Packet>, var remainText: String, var versionSum: Int)

fun hexToBinary(input: String): String{
    var output = ""
    for (char in input){
        val binaryString = Integer.toBinaryString(char.toString().toInt(16))
        val withLeadingZeros = java.lang.String.format("%4s", binaryString).replace(' ', '0')
        output += withLeadingZeros
    }
    return output
}

fun decodeLiteralPacket(packetText: String): Packet{
//    println("decoding literal packet: " + packetText)
    val result = Packet(packetText.slice(0..2).toInt(2), packetText.slice(3..5).toInt(2), 0, arrayListOf(), "", packetText.slice(0..2).toInt(2))

    val remainText = packetText.slice(6 until packetText.length)
    var literalValue = ""
    for (index in remainText.indices step 5){
        if (remainText[index] == '1'){
            literalValue += remainText.slice(index+1..index+4)
        } else {
            literalValue += remainText.slice(index+1..index+4)
            result.value = literalValue.toLong(2)
//            println("literal value is "+ result.value)
            if (index+5 != remainText.length) {
                result.remainText = remainText.slice(index+5 until remainText.length)
            }
            break
        }
    }
//    println("decoded literal packet: " + result.toString())
    return result
}

fun decodeOperatorPacket(packetText: String): Packet{
    val result = Packet(packetText.slice(0..2).toInt(2), packetText.slice(3..5).toInt(2), 0, arrayListOf(), packetText, packetText.slice(0..2).toInt(2))

    if(packetText[6] == '0'){                           // the next 15 bits are a number that represents the total length in bits
        val length = packetText.slice(7..21).toInt(2)
//        println("decoding operator packet with 0: " + packetText + " with length " + length)
        var subpacketText = packetText.slice(22 until 22+length)
        result.remainText = packetText.slice(22+length until packetText.length)
        while (subpacketText.any { it == '1' }){
            val subpacket = decodePacket(subpacketText)
            result.subpackets.add(subpacket)
            subpacketText = subpacket.remainText
        }
    } else {                                                // the next 11 bits are a number that represents the number of sub-packets
        val number = packetText.slice(7..17).toInt(2)
//        println("decoding operator packet with 1: " + packetText + " with $number sub-packets")
        result.remainText = packetText.slice(18 until packetText.length)
        for (count in 1..number){
//            println("try to find sub-packet $count in " + result.remainText)
            val subpacket = decodePacket(result.remainText)
            result.subpackets.add(subpacket)
            result.remainText = subpacket.remainText
        }
    }
    for (subpacket in result.subpackets){
        result.versionSum += subpacket.versionSum
    }
    return result
}

fun decodePacket(packetText: String): Packet{
    var result = Packet(packetText.slice(0..2).toInt(2), packetText.slice(3..5).toInt(2), 0, arrayListOf(), packetText, packetText.slice(0..2).toInt(2))

    when (result.type){
        0 -> {                                              // sum packet
            result = decodeOperatorPacket(packetText)
            for (subpacket in result.subpackets){
                result.value += subpacket.value
            }
        }
        1 -> {                                              // product packet
            result = decodeOperatorPacket(packetText)
            result.value = 1
            for (subpacket in result.subpackets){
                result.value *= subpacket.value
            }
        }
        2 -> {                                              // minimum packet
            result = decodeOperatorPacket(packetText)
            result.value = Long.MAX_VALUE
            for (subpacket in result.subpackets){
                if (subpacket.value < result.value){
                    result.value = subpacket.value
                }
            }
        }
        3 -> {                                              // maximum packet
            result = decodeOperatorPacket(packetText)
            result.value = Long.MIN_VALUE
            for (subpacket in result.subpackets){
                if (subpacket.value > result.value){
                    result.value = subpacket.value
                }
            }
        }
        4 -> {                                              // literal value packet
            return decodeLiteralPacket(packetText)
        }
        5 -> {                                              // greater than packets (only 2 sub-packets)
            result = decodeOperatorPacket(packetText)
            val value = if (result.subpackets[0].value > result.subpackets[1].value) 1 else 0
            result.value = value.toLong()
        }
        6 -> {                                              // less than packets (only 2 sub-packets)
            result = decodeOperatorPacket(packetText)
            val value = if (result.subpackets[0].value < result.subpackets[1].value) 1 else 0
            result.value = value.toLong()
        }
        7 -> {                                              // equal to packets (only 2 sub-packets)
            result = decodeOperatorPacket(packetText)
            val value = if (result.subpackets[0].value == result.subpackets[1].value) 1 else 0
            result.value = value.toLong()
        }
    }
//    println("decoded operator packet: " + result.toString())
    return result
}

fun main() {

    fun part1(input: List<String>): Int {
        val result = decodePacket(hexToBinary(input[0]))

        return result.versionSum
    }

    fun part2(input: List<String>): Long {
        val result = decodePacket(hexToBinary(input[0]))

        return result.value
    }

    val input = readInput("Day16")
    println(part1(input))
    println(part2(input))
}
