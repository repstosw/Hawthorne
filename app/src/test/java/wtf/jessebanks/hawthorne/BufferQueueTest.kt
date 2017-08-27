package wtf.jessebanks.hawthorne

import org.junit.Assert.assertEquals
import org.junit.Test
import wtf.jessebanks.hawthorne.dsp.BufferQueue

/**
 * Tests for BufferQueue
 * Created by jessebanks on 2/13/17.
 */
class BufferQueueTest {

    @Test
    fun testLessThanSize() {
        val bufferQueue = BufferQueue(5)

        // Add two sequences of two
        bufferQueue.addArray(shortArrayOf(0, 1))
        assertEquals(0, bufferQueue.size)
        bufferQueue.addArray(shortArrayOf(2, 3))
        assertEquals(0, bufferQueue.size)

        // Add one more, should addArray to the queue
        bufferQueue.addArray(shortArrayOf(4))
        assertEquals(1, bufferQueue.size)

        // Add five more, should addArray another to the queue
        bufferQueue.addArray(shortArrayOf(0, 1, 2, 3, 4))
        assertEquals(2, bufferQueue.size)

        bufferQueue.addArray(shortArrayOf(0))
        assertEquals(2, bufferQueue.size)
    }

    @Test
    fun testGreaterThanSize() {
        val bufferQueue = BufferQueue(3)

        // Add one element
        bufferQueue.addArray(shortArrayOf(0))
        assertEquals(0, bufferQueue.size)
        // Add a bunch more
        bufferQueue.addArray(shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0))
        assertEquals(3, bufferQueue.size)

        bufferQueue.addArray(shortArrayOf(1))
        assertEquals(4, bufferQueue.size)

        bufferQueue.clear()
        assertEquals(0, bufferQueue.size)
        bufferQueue.addArray(shortArrayOf(0, 1, 2, 3, 4, 5))
        assertEquals(2, bufferQueue.size)
        bufferQueue.addArray(shortArrayOf(0))
        assertEquals(2, bufferQueue.size)

    }
}