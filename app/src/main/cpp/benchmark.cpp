//
// Created by kman on 5/19/22.
//

#include "../jni/org_kman_jnibench_Benchmark.h"

#include <android/log.h>

static const char TAG[] = "benchmark";
static unsigned int nextIndex = 0;
static int nextCall = 0;

extern "C" {

JNIEXPORT jint JNICALL Java_org_kman_jnibench_Benchmark_doitNoArray
        (JNIEnv *, jobject, jint) {

    nextCall += 1;
    return nextCall;
}

JNIEXPORT jint JNICALL Java_org_kman_jnibench_Benchmark_doitArray
        (JNIEnv *env, jobject self, jbyteArray array, jint runIndex) {

    jboolean isCopy = false;
    jsize size = env->GetArrayLength(array);
    jbyte *ptr = env->GetByteArrayElements(array, &isCopy);

    // __android_log_print(ANDROID_LOG_INFO, TAG, "isCopy = %d", isCopy);

    for (int i = 0; i < 1000; i += 1) {
        unsigned int index = nextIndex % size;
        ptr[index] += 1;
        nextIndex += 1;
    }

    env->ReleaseByteArrayElements(array, ptr, JNI_COMMIT);

    nextCall += 1;
    return nextCall;
}

}
