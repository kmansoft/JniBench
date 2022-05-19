cmake_minimum_required(VERSION 3.4.1)

project(ResizeLib)

link_libraries(
        log)

add_library(
        # Specifies the name of the library.
        benchmark

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        src/main/cpp/benchmark.cpp)
