//
// Created by kman on 5/19/22.
//

#include "../jni/org_kman_jnibench_ArrayAccessBenchmark.h"

#include <android/log.h>

static const char TAG[] = "array_access_benchmark";
static int nextIndex = 0;

extern "C" {

JNIEXPORT void JNICALL Java_org_kman_jnibench_ArrayAccessBenchmark_doit
        (JNIEnv *env, jobject self, jbyteArray array, jint runIndex) {

    jboolean isCopy = false;
    jbyte *ptr = env->GetByteArrayElements(array, &isCopy);
    jsize size = env->GetArrayLength(array);

    // __android_log_print(ANDROID_LOG_INFO, TAG, "isCopy = %d", isCopy);

    for (int i = 0; i < 1000; i += 1) {
        int index = nextIndex % size;
        ptr[index] += 1;
        nextIndex += 1;
    }

    env->ReleaseByteArrayElements(array, ptr, JNI_COMMIT);
}

}
