package org.kman.jnibench

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mButtonSmallArray = findViewById(R.id.bench_small_array)
        mButtonLargeArray = findViewById(R.id.bench_large_array)
        mOutputScroll = findViewById(R.id.output_scroll)
        mOutputText = findViewById(R.id.output_text)

        mButtonSmallArray.setOnClickListener {
            lifecycleScope.launch { doBenchmark(1000) }
        }
        mButtonLargeArray.setOnClickListener {
            lifecycleScope.launch { doBenchmark(1 * 1024 * 1024) }
        }
    }

    private suspend fun doBenchmark(size: Int) {
        mOutputText.text = null
        outputText("Benchmarking $size array size...")

        val average = withContext(Dispatchers.IO) {
            doBenchmarkImpl(size)
        }

        outputText("Performed $ITER_COUNT iterations")
        outputText("Average per iteration: %.4f ms", average)
    }

    private fun doBenchmarkImpl(size: Int): Float {
        val array = ByteArray(size)
        val benchmark = ArrayAccessBenchmark()

        val ms0 = SystemClock.elapsedRealtime()

        for (i in 0 until ITER_COUNT) {
            benchmark.doit(array, i)
        }

        val ms1 = SystemClock.elapsedRealtime()
        return (ms1 - ms0).toFloat() / ITER_COUNT
    }

    private fun outputText(format: String, vararg args: Any?) {
        val msg = String.format(Locale.US, format, *args)
        outputText(msg)
    }

    private fun outputText(msg: String) {
        mOutputText.append(msg + "\n")
        mHandler.postDelayed({
            mOutputScroll.scrollBy(0, 1000)
        }, 150)
    }

    companion object {
        private val ITER_COUNT = 100000
    }

    private val mHandler = Handler(Looper.getMainLooper())

    private lateinit var mButtonSmallArray: Button
    private lateinit var mButtonLargeArray: Button
    private lateinit var mOutputScroll: ScrollView
    private lateinit var mOutputText: TextView

}