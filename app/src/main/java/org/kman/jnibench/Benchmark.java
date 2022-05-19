package org.kman.jnibench;

public class Benchmark {

    static {
        System.loadLibrary("benchmark");
    }

    public native int doitNoArray(int runIndex);
    public native int doitArray(byte[] array, int runIndex);
}
