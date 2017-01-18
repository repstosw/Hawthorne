package wtf.jessebanks.hawthorne.dsp

/**
 * Simple complex number type.
 * Created by jessebanks on 1/16/17.
 */
data class Complex(val real: Double, val imaginary: Double) {

    constructor() : this(0.0, 0.0)

    val magnitude = Math.sqrt(real * real + imaginary * imaginary)
    val phase = Math.atan2(imaginary, real)

    val rectangular: String
        get() {
            if (imaginary < 0) {
                return "$real - ${-imaginary}i"
            }
            else {
                return "$real + ${imaginary}i"
            }
        }

    val polar = "$magnitude \u2220 ${phase * 180 / Math.PI}\u00B0"

    operator fun plus(a: Complex): Complex {
        return Complex(this.real + a.real, this.imaginary + a.imaginary)
    }

    operator fun minus(b: Complex): Complex {
        return Complex(this.real - b.real, this.imaginary - b.imaginary)
    }

    operator fun times(b: Complex): Complex {
        return Complex(
                this.real * b.real - this.imaginary * b.imaginary,
                this.real * b.imaginary + this.imaginary * b.real)
    }

    override fun toString(): String {
        return rectangular
    }
}