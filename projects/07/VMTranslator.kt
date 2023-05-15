import java.io.File

var labelCounter = 0

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Missing file to parse")
        return
    }
    val fileName = args[0]
    File(fileName).forEachLine {
        parseLine(it)
    }
}

fun parseLine(line: String) {
    if (line.isEmpty() || line.startsWith("//")) {
        return
    }

    val trimmed = line.trim()
    println("// $trimmed")

    if (trimmed.startsWith("push") || trimmed.startsWith("pop")) {
        parsePushPop(trimmed);
    } else {
        parseArithmetic(trimmed)
    }

    println()
}

fun parsePushPop(cmd: String) {
    val split = cmd.split(' ')
    val segment = split[1]
    val index = split[2].toInt()
    if (split[0] == "push") {
        parsePush(segment, index)
    } else {
        parsePop(segment, index)
    }
}

fun parseArithmetic(cmd: String) {
    println("@SP")
    println("M=M-1")
    when (cmd) {
        "add" -> {
            println("A=M")
            println("D=M")
            println("@SP")
            println("M=M-1")
            println("A=M")
            println("M=D+M")
        }
        "sub" -> {
            println("A=M")
            println("D=M")
            println("@SP")
            println("M=M-1")
            println("A=M")
            println("M=M-D")
        }
        "neg" -> {
            println("A=M")
            println("M=-M")
        }
        "eq" -> {
            println("A=M")
            println("D=M")
            println("@SP")
            println("M=M-1")
            println("A=M")
            println("D=M-D")
            println("@label_${labelCounter}")
            println("D;JEQ")
            println("@SP")
            println("A=M")
            println("M=0")
            println("@label_${labelCounter}_skip")
            println("0;JMP")
            println("(label_${labelCounter})")
            println("@SP")
            println("A=M")
            println("M=-1")
            println("(label_${labelCounter}_skip)")
            labelCounter += 1
        }
        "gt" -> {
            println("A=M")
            println("D=M")
            println("@SP")
            println("M=M-1")
            println("A=M")
            println("D=M-D")
            println("@label_${labelCounter}")
            println("D;JGT")
            println("@SP")
            println("A=M")
            println("M=0")
            println("@label_${labelCounter}_skip")
            println("0;JMP")
            println("(label_${labelCounter})")
            println("@SP")
            println("A=M")
            println("M=-1")
            println("(label_${labelCounter}_skip)")
            labelCounter += 1
        }
        "lt" -> {
            println("A=M")
            println("D=M")
            println("@SP")
            println("M=M-1")
            println("A=M")
            println("D=M-D")
            println("@label_${labelCounter}")
            println("D;JLT")
            println("@SP")
            println("A=M")
            println("M=0")
            println("@label_${labelCounter}_skip")
            println("0;JMP")
            println("(label_${labelCounter})")
            println("@SP")
            println("A=M")
            println("M=-1")
            println("(label_${labelCounter}_skip)")
            labelCounter += 1
        }
        "and" -> {
            println("A=M")
            println("D=M")
            println("@SP")
            println("M=M-1")
            println("A=M")
            println("M=D&M")
        }
        "or" -> {
            println("A=M")
            println("D=M")
            println("@SP")
            println("M=M-1")
            println("A=M")
            println("M=D|M")
        }
        "not" -> {
            println("A=M")
            println("M=!M")
        }
        else -> {
            // ignore unrecognized
        }
    }
    println("@SP")
    println("M=M+1")
}

fun parsePush(segment: String, index: Int) {
    when (segment) {
        "constant" -> {
            println("@$index")
            println("D=A")
            println("@SP")
            println("A=M")
            println("M=D")
            println("@SP")
            println("M=M+1")
        }
        "local" -> {
            println("@$index")
            println("D=A")
            println("@LCL")
            println("D=D+M")
            println("A=D")
            println("D=M")
            println("@SP")
            println("A=M")
            println("M=D")
            println("@SP")
            println("M=M+1")
        }
        "argument" -> {
            println("@$index")
            println("D=A")
            println("@ARG")
            println("D=D+M")
            println("A=D")
            println("D=M")
            println("@SP")
            println("A=M")
            println("M=D")
            println("@SP")
            println("M=M+1")
        }
        "this" -> {
            println("@$index")
            println("D=A")
            println("@THIS")
            println("D=D+M")
            println("A=D")
            println("D=M")
            println("@SP")
            println("A=M")
            println("M=D")
            println("@SP")
            println("M=M+1")
        }
        "that" -> {
            println("@$index")
            println("D=A")
            println("@THAT")
            println("D=D+M")
            println("A=D")
            println("D=M")
            println("@SP")
            println("A=M")
            println("M=D")
            println("@SP")
            println("M=M+1")
        }
        "pointer" -> {
            println("@${index + 3}")
            println("D=M")
            println("@SP")
            println("A=M")
            println("M=D")
            println("@SP")
            println("M=M+1")
        }
        "temp" -> {
            println("@${index + 5}")
            println("D=M")
            println("@SP")
            println("A=M")
            println("M=D")
            println("@SP")
            println("M=M+1")
        }
        "static" -> {
            println("@StaticSegment.${index}")
            println("D=M")
            println("@SP")
            println("A=M")
            println("M=D")
            println("@SP")
            println("M=M+1")
        }
        else -> {
            // ignore unrecognized
        }
    }
}

fun parsePop(segment: String, index: Int) {

    when (segment) {
        "local" -> {
            println("@$index")
            println("D=A")
            println("@LCL")
            println("D=D+M")
        }
        "argument" -> {
            println("@$index")
            println("D=A")
            println("@ARG")
            println("D=D+M")
        }
        "this" -> {
            println("@$index")
            println("D=A")
            println("@THIS")
            println("D=D+M")
        }
        "that" -> {
            println("@$index")
            println("D=A")
            println("@THAT")
            println("D=D+M")
        }
        "pointer" -> {
            println("@${index + 3}")
            println("D=A")
        }
        "temp" -> {
            println("@${index + 5}")
            println("D=A")
        }
        "static" -> {
            println("@StaticSegment.${index}")
            println("D=A")
        }
        else -> {
            // ignore unrecognized
        }
    }


    println("@R13")
    println("M=D")

    println("@SP")
    println("M=M-1")
    println("A=M")
    println("D=M")

    println("@R13")
    println("A=M")
    println("M=D")
}