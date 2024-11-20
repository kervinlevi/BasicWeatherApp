cmake_minimum_required(VERSION 3.22.1)
add_library(
        keyholder
        SHARED
        keyholder.cpp
)
target_link_libraries(
        keyholder
        ${log-lib}
)
