package wtf.jessebanks.hawthorne.dsp

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Process.THREAD_PRIORITY_AUDIO
import android.util.Log
import java.util.concurrent.BlockingQueue

/**
 * Audio recording thread
 * Takes a BlockingQueue as a constructor arguments on which audio data will be enqueued one
 * array at a time.
 * Created by jessebanks on 1/7/17.
 */
class RecordThread(val produceQueue: BufferQueue) : Thread("RecordThread") {

    private val TAG = "RecordThread"
    val SAMPLE_RATE = 22050

    var exit: Boolean = false
        get() = synchronized(this, { field })
        set(value) = synchronized(this, {
            Log.i(TAG, "Setting quit value: $value")
            field = value })

    override fun run() {
        android.os.Process.setThreadPriority(THREAD_PRIORITY_AUDIO)

        Log.i(TAG, "Starting record thread")

        val bufferSize = AudioRecord.getMinBufferSize(
                SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT)

        Log.i(TAG, "Buffer size: $bufferSize")

        val audioRecord = AudioRecord(MediaRecorder.AudioSource.DEFAULT,
                SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                2048)

        if (audioRecord.state != AudioRecord.STATE_INITIALIZED) {
            Log.e(TAG, "Can't initialize audio recording device")
            return
        }

        Log.i(TAG, "Entering record loop")

        audioRecord.startRecording()

        while (!exit) {
            val buffer = ShortArray(bufferSize / 2)
            val samplesRead = audioRecord.read(buffer, 0, buffer.size)
            produceQueue.addArray(buffer)
        }

        Log.d(TAG, "Exiting thread")
        audioRecord.stop()
        audioRecord.release()
    }
}