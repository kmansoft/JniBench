package org.kman.jnibench;

public class ArrayAccessBenchmark {

    static {
        System.loadLibrary("array_access_benchmark");
    }

    public native void doit(byte[] array, int runIndex);
}
