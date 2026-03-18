@file:OptIn(RDKTInternalAPI::class)

package cn.rdlevel.rdkt.core.data

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import cn.rdlevel.rdkt.core.serialization.TransformSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlin.jvm.JvmSynthetic

/**
 * Represents a vector in 2D space with x and y components.
 *
 * @property x The x component of the vector.
 * @property y The y component of the vector.
 */
@Serializable(Vector2.Serializer::class)
public data class Vector2(val x: Double, val y: Double) {
    override fun toString(): String {
        return "[$x, $y]"
    }

    /**
     * Returns a new vector2 with the x component changed to [newX].
     */
    public fun withX(newX: Double): Vector2 = copy(x = newX)

    /**
     * Returns a new vector2 with the y component changed to [newY].
     */
    public fun withY(newY: Double): Vector2 = copy(y = newY)

    /**
     * Returns a new vector2 added by [other].
     */
    public operator fun plus(other: Vector2): Vector2 = Vector2(x + other.x, y + other.y)

    /**
     * Returns a new vector2 subtracted by [other].
     */
    public operator fun minus(other: Vector2): Vector2 = Vector2(x - other.x, y - other.y)

    /**
     * Returns a new vector2 multiplied by [scalar].
     */
    public operator fun times(scalar: Double): Vector2 = Vector2(x * scalar, y * scalar)

    /**
     * Returns a new vector2 divided by [scalar].
     */
    public operator fun div(scalar: Double): Vector2 = Vector2(x / scalar, y / scalar)

    public companion object {
        public val ZERO: Vector2 = Vector2(0.0, 0.0)
        public val ONE: Vector2 = Vector2(1.0, 1.0)
    }

    public object Serializer :
        TransformSerializer<Vector2, List<Double>>("Vector2", ListSerializer(Double.serializer())) {
        override fun toData(value: Vector2): List<Double> = listOf(value.x, value.y)

        override fun fromData(data: List<Double>): Vector2 {
            require(data.size == 2) { "Expected list of size 2 for vector2, got ${data.size}" }
            return Vector2(data[0], data[1])
        }
    }
}

/**
 * Creates a [Vector2] with the given x and y components.
 */
@JvmSynthetic
public fun vectorOf(x: Double, y: Double): Vector2 = Vector2(x, y)