package wtf.jessebanks.hawthorne.dsp

/**
 * DSP functions
 * Created by jessebanks on 1/16/17.
 */

/**
 * Vanilla Cooley-Tukey FFT algorithm, without scaling.
 * Modified from pseudocode at https://en.wikipedia.org/wiki/Cooley-Tukey_FFT_algorithm
 */
fun fft(x: ComplexArray) : ComplexArray {

    val n = x.size

    // Base case
    if (n == 1) {
        return x
    }

    // check radix
    if (n % 2 != 0) {
        throw IllegalArgumentException("Input data length must have radix 2")
    }

    var even = ComplexArray(n / 2)
    for (i in 0 until (n / 2)) {
        even[i] = x[2 * i]
    }
    val evenResult = fft(even)

    var odd = ComplexArray(n / 2)
    for (i in 0 until (n / 2)) {
        odd[i] = x[(2 * i) + 1]
    }
    val oddResult = fft(odd)

    var final = ComplexArray(n)
    for (k in 0 until (n / 2)) {
        val thetak = -2.0 * Math.PI * k / n
        val root = Complex(Math.cos(thetak), Math.sin(thetak))
        final[k] = evenResult[k] + (root * oddResult[k])
        final[k + (n/2)]  = evenResult[k] - (root * oddResult[k])
    }

    return final
}

/**
 * Apply a Hamming window to an input of integers.
 */
fun hamming(input: ShortArray) : ComplexArray {
    val N = input.size

    val array = ComplexArray(N)
    input.forEachIndexed {
        i, sh ->
            val scalar = 0.54 - (0.46 * Math.cos((2 * Math.PI * i) / N))
            array[i] = Complex(sh * scalar, 0.0)
    }

    return array
}
