package com.part.sportpartapp.sportList.domain.model

enum class FitnessCategory {
    All,
    MIND_BODY,
    HIGH_INTENSITY,
    COMPREHENSIVE,
    FUNCTIONAL
}

fun FitnessCategory.toDisplayString(): String {
    return name.lowercase()
}

fun selectFitnessCategoryByValue(value: Int): FitnessCategory {
    return when (value) {
        1 -> FitnessCategory.HIGH_INTENSITY
        2 -> FitnessCategory.MIND_BODY
        3 -> FitnessCategory.FUNCTIONAL
        4 -> FitnessCategory.COMPREHENSIVE
        else -> FitnessCategory.All
    }
}