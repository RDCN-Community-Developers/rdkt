package cn.rdlevel.rdkt.core.util

/**
 * Converts a BigCamelCase string to smallCamelCase.
 *
 * @return The converted smallCamelCase string.
 */
public fun String.bigCamelToSmallCamel(): String {
    if (this.isEmpty()) return this
    return this[0].lowercaseChar() + this.substring(1)
}

/**
 * Converts a smallCamelCase string to BigCamelCase.
 *
 * @return The converted BigCamelCase string.
 */
public fun String.smallCamelToBigCamel(): String {
    if (this.isEmpty()) return this
    return this[0].uppercaseChar() + this.substring(1)
}