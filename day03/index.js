const fs = require('fs')
const Console = require("console");

function inputDataLines(filename="input.txt") {
    return fs.readFileSync(filename).toString().trim().split("\n")
}

function getSolutionPart1() {
    let gamma = "", epsilon = "", bits = inputDataLines(), sums = Array(bits[0].length).fill(0)
    for (let bit of bits) {
        for (let i = 0; i < bit.length; i++) {
            sums[i] += parseInt(bit[i])
        }
    }
    sums.forEach(sum => sum > bits.length/2 ? (gamma += "1", epsilon += "0") : (gamma += "0", epsilon += "1"))
    return parseInt(gamma, 2) * parseInt(epsilon, 2);
}

function getSolutionPart2() {
    let bits = inputDataLines()
    const oxygen = extractBit(bits, "1", "0")
    const carbon = extractBit(bits, "0", "1")
    return oxygen * carbon
}

function extractBit(bits, filerOnValue, filterOnValue2) {
    let count = 0
    while (bits.length !== 1) {
        let sum = 0
        for (let bit of bits) {
            sum += parseInt(bit[count])
        }
        sum >= bits.length / 2 ? bits = bits.filter(bit => bit.charAt(count) === filerOnValue) : bits = bits.filter(bit => bit.charAt(count) === filterOnValue2)
        count += 1
    }
    return parseInt(bits, 2);
}

console.log("Javascript")

process.env.part ||"part1" === "part2" ? console.log(getSolutionPart1()) : console.log(getSolutionPart2())

module.exports = {
    getSolutionPart1, getSolutionPart2, inputDataLinesIntegers: inputDataLines
}