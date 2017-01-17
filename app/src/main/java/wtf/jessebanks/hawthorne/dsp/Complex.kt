package wtf.jessebanks.hawthorne.dsp

/**
 * Simple complex number type.
 * Created by jessebanks on 1/16/17.
 */
data class Complex(val real: Double, val imaginary: Double) {

    constructor() : this(0.0, 0.0)

    val magnitude = Math.sqrt(real * real + imaginary * imaginary)
}