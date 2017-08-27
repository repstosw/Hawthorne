package wtf.jessebanks.hawthorne.dsp

import java.util.concurrent.LinkedBlockingQueue

/**
 * Buffer queue - allows ShortArrays to be accumulated until bufferSize
 * samples exist. At that point a complete ShortArray will be added to the queue
 * and made available for consuming routines.
 * Created by jessebanks on 2/11/17.
 */

val MAX_BUFFER = 1 // Maximum amount of sample arrays to buffer

class BufferQueue(val bufferSize: Int) : LinkedBlockingQueue<ShortArray>(MAX_BUFFER) {

    private var internalArray = ShortArray(0)

    /**
     * Add an array.
     */
    fun addArray(newArray: ShortArray) {
        if (size == MAX_BUFFER) return
        processArray(newArray)
    }

    override fun clear() {
        internalArray = ShortArray(0)
        super.clear()
    }

    private fun processArray(newArray: ShortArray) {
        // Base case: if adding to the array will not result in a full array
        if (internalArray.size + newArray.size < bufferSize) {
            internalArray += newArray
            return
        }

        // Copy up to a full array
        val index = bufferSize - internalArray.size
        val copiedArray = internalArray + newArray.copyOfRange(0, index)
        super.put(copiedArray)

        internalArray = ShortArray(0)
        // Other base case - nothing left to copy
        if (index == newArray.size) {
            return
        }
        val nextArray = newArray.copyOfRange(index, newArray.size)
        // continue until all is gone
        processArray(nextArray)
    }
}