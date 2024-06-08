package com.org.capstone.nutrifish.helper

class NormalizationOptions(
    val mean: FloatArray,
    val std: FloatArray
) {
    companion object {
        fun create(mean: FloatArray, std: FloatArray): NormalizationOptions {
            return NormalizationOptions(mean, std)
        }
    }
}