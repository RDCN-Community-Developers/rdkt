@file:OptIn(RDKTInternalAPI::class)
@file:JvmName("ColorOrPalettes")

package cn.rdlevel.rdkt.core.data

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import cn.rdlevel.rdkt.core.serialization.PolymorphicDelegatedSerializer
import cn.rdlevel.rdkt.core.serialization.TransformSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlin.jvm.JvmField
import kotlin.jvm.JvmName
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * A color that can be either a specific RGB color or a palette index.
 */
@Serializable(ColorOrPalette.Serializer::class)
public sealed interface ColorOrPalette {
    public object Serializer : TransformSerializer<ColorOrPalette, String>("ColorOrPalette", String.serializer()) {
        override fun toData(value: ColorOrPalette): String = when (value) {
            is Palette -> "pal${value.index}"
            is Color -> colorToHex(value.red, value.green, value.blue)
        }

        override fun fromData(data: String): ColorOrPalette {
            return if (data.startsWith("pal")) {
                val index = data.substring(3).toIntOrNull()
                    ?: throw IllegalArgumentException("Invalid palette format: $data. Expected format is 'pal{index}'.")
                Palette.of(index)
            } else {
                Color.of(data)
            }
        }
    }
}

/**
 * A color that can be either a specific RGBA color or a palette index.
 */
@Serializable(ColorWithAlphaOrPalette.Serializer::class)
public sealed interface ColorWithAlphaOrPalette : ColorOrPalette {
    public object Serializer :
        TransformSerializer<ColorWithAlphaOrPalette, String>("ColorWithAlphaOrPalette", String.serializer()) {
        override fun toData(value: ColorWithAlphaOrPalette): String = when (value) {
            is Palette -> "pal${value.index}"
            is ColorWithAlpha -> colorToHex(value.red, value.green, value.blue, value.alpha)
        }

        override fun fromData(data: String): ColorWithAlphaOrPalette {
            return if (data.startsWith("pal")) {
                val index = data.substring(3).toIntOrNull()
                    ?: throw IllegalArgumentException("Invalid palette format: $data. Expected format is 'pal{index}'.")
                Palette.of(index)
            } else {
                ColorWithAlpha.of(data)
            }
        }
    }
}

/**
 * A color that is a palette index.
 *
 * @property index The index of the palette color, which must be between 0 and 20 inclusive.
 */
@Serializable(Palette.Serializer::class)
public sealed interface Palette : ColorWithAlphaOrPalette {
    public val index: Int

    public object Serializer : PolymorphicDelegatedSerializer<Palette, ColorOrPalette>(ColorOrPalette.serializer())

    public companion object {
        /**
         * Creates a [Palette] instance with the specified index.
         */
        @JvmStatic
        public fun of(index: Int): Palette = PaletteImpl(index)
    }
}

private fun Palette.checkIndex() {
    require(index in 0..20) { "Palette index must be between 0 and 20, but was $index." }
}

private data class PaletteImpl(override val index: Int) : Palette {
    init {
        checkIndex()
    }

    override fun toString(): String = "pal$index"
}

/**
 * A color that is a specific RGB color.
 *
 * @property red The red component of the color, which must be between 0 and 255 inclusive.
 * @property green The green component of the color, which must be between 0 and 255 inclusive.
 * @property blue The blue component of the color, which must be between 0 and 255 inclusive.
 */
@Serializable(Color.Serializer::class)
public sealed interface Color : ColorOrPalette {
    public val red: Int
    public val green: Int
    public val blue: Int

    /**
     * Converts the color to a hexadecimal string representation.
     * Depending on the implementation, this may return a 6-character hex string (for RGB) or an 8-character hex string (for RGBA).
     */
    public fun toHex(): String

    /**
     * Creates a new [Color] instance with the same green and blue components but a different red component.
     */
    public fun withRed(red: Int): Color = of(red, green, blue)

    /**
     * Creates a new [Color] instance with the same red and blue components but a different green component.
     */
    public fun withGreen(green: Int): Color = of(red, green, blue)

    /**
     * Creates a new [Color] instance with the same red and green components but a different blue component.
     */
    public fun withBlue(blue: Int): Color = of(red, green, blue)

    /**
     * Creates a new [ColorWithAlpha] instance with the same red, green, and blue components but a specified alpha component.
     * The alpha component is optional and defaults to 255 (fully opaque) if not provided.
     */
    public fun withAlpha(alpha: Int = 255): ColorWithAlpha = ColorWithAlpha.of(red, green, blue, alpha)

    public object Serializer : PolymorphicDelegatedSerializer<Color, ColorOrPalette>(ColorOrPalette.serializer())

    public companion object {
        /**
         * Creates a [Color] instance with the specified RGB components.
         */
        @JvmStatic
        public fun of(r: Int, g: Int, b: Int): Color = ColorImpl(r, g, b)

        /**
         * Creates a [Color] instance from a hexadecimal string representation. The hex string must be 6 characters long, representing the RGB components in the format RRGGBB.
         */
        @JvmStatic
        public fun of(hex: String): Color {
            require(hex.length == 6) { "Hex color must be 6 characters long, but was ${hex.length}." }
            val r = hex.substring(0, 2).toInt(16)
            val g = hex.substring(2, 4).toInt(16)
            val b = hex.substring(4, 6).toInt(16)
            return of(r, g, b)
        }
    }
}

private fun Color.checkComponents() {
    require(red in 0..255) { "Red component must be between 0 and 255, but was $red." }
    require(green in 0..255) { "Green component must be between 0 and 255, but was $green." }
    require(blue in 0..255) { "Blue component must be between 0 and 255, but was $blue." }
}

/**
 * A color that is a specific RGBA color.
 *
 * @property alpha The alpha component of the color, which must be between 0 and 255 inclusive.
 */
@Serializable(ColorWithAlpha.Serializer::class)
public sealed interface ColorWithAlpha : Color, ColorWithAlphaOrPalette {
    public val alpha: Int

    public override fun toHex(): String

    /**
     * Creates a new [ColorWithAlpha] instance with a different red component.
     */
    public override fun withRed(red: Int): ColorWithAlpha = of(red, green, blue, alpha)

    /**
     * Creates a new [ColorWithAlpha] instance with a different green component.
     */
    public override fun withGreen(green: Int): ColorWithAlpha = of(red, green, blue, alpha)

    /**
     * Creates a new [ColorWithAlpha] instance with a different blue component.
     */
    public override fun withBlue(blue: Int): ColorWithAlpha = of(red, green, blue, alpha)

    /**
     * Creates a new [ColorWithAlpha] instance with a different alpha component.
     */
    public override fun withAlpha(alpha: Int): ColorWithAlpha = of(red, green, blue, alpha)

    public object Serializer :
        PolymorphicDelegatedSerializer<ColorWithAlpha, ColorWithAlphaOrPalette>(ColorWithAlphaOrPalette.serializer())

    public companion object {
        /**
         * Creates a [ColorWithAlpha] instance with the specified RGBA components. The alpha component is optional and defaults to 255 (fully opaque) if not provided.
         */
        @JvmStatic
        @JvmOverloads
        public fun of(r: Int, g: Int, b: Int, a: Int = 255): ColorWithAlpha = ColorImpl(r, g, b, a, true)

        /**
         * Creates a [ColorWithAlpha] instance from a hexadecimal string representation. The hex string must be 8 characters long, representing the RGBA components in the format RRGGBBAA.
         */
        @JvmStatic
        public fun of(hex: String): ColorWithAlpha {
            require(hex.length == 8) { "Hex color with alpha must be 8 characters long, but was ${hex.length}." }
            val r = hex.substring(0, 2).toInt(16)
            val g = hex.substring(2, 4).toInt(16)
            val b = hex.substring(4, 6).toInt(16)
            val a = hex.substring(6, 8).toInt(16)
            return of(r, g, b, a)
        }
    }
}

private fun ColorWithAlpha.checkComponentsWithAlpha() {
    checkComponents()
    require(alpha in 0..255) { "Alpha component must be between 0 and 255, but was $alpha." }
}

private data class ColorImpl(
    override val red: Int,
    override val green: Int,
    override val blue: Int,
    override val alpha: Int = 255,
    private val withAlpha: Boolean = false,
) : ColorWithAlpha {
    init {
        checkComponentsWithAlpha()
    }

    override fun toHex(): String = if (withAlpha) colorToHex(red, green, blue, alpha) else colorToHex(red, green, blue)

    override fun toString(): String = toHex()
}

private fun colorToHex(r: Int, g: Int, b: Int): String {
    val rHex = r.toString(16).padStart(2, '0')
    val gHex = g.toString(16).padStart(2, '0')
    val bHex = b.toString(16).padStart(2, '0')
    return "$rHex$gHex$bHex"
}

private fun colorToHex(r: Int, g: Int, b: Int, a: Int): String {
    val rHex = r.toString(16).padStart(2, '0')
    val gHex = g.toString(16).padStart(2, '0')
    val bHex = b.toString(16).padStart(2, '0')
    val aHex = a.toString(16).padStart(2, '0')
    return "$rHex$gHex$bHex$aHex"
}

/**
 * A palette color that allows specifying RGBA components.
 * Instances of this class is created by [LevelSettings][cn.rdlevel.rdkt.core.settings.LevelSettings], and can be assigned to events that need colors to specify palette colors.
 */
public sealed interface PaletteColor : ColorWithAlpha, Palette {
    override var red: Int
    override var green: Int
    override var blue: Int
    override var alpha: Int

    public var color: ColorWithAlpha
        get() = this
        set(value) {
            red = value.red
            green = value.green
            blue = value.blue
            alpha = value.alpha
        }

    /**
     * Detaches the palette color from the palette and returns a new [ColorWithAlpha] instance with the same RGBA components.
     * This is useful when you want to use the color of a palette color without referencing the palette index.
     */
    public fun asColorWithAlpha(): ColorWithAlpha = ColorWithAlpha.of(red, green, blue, alpha)
}

private data class PaletteColorImpl(
    override val index: Int,
    override var red: Int,
    override var green: Int,
    override var blue: Int,
    override var alpha: Int = 255,
) : PaletteColor {
    init {
        checkIndex()
        checkComponentsWithAlpha()
    }

    override fun toHex(): String = colorToHex(red, green, blue, alpha)

    override fun toString(): String = "pal$index=${toHex()}"
}

public object PaletteColorListSerializer : TransformSerializer<List<PaletteColor>, List<String>>(
    "PaletteColorList",
    ListSerializer(String.serializer())
) {
    override fun toData(value: List<PaletteColor>): List<String> = value.map { it.toHex() }

    override fun fromData(data: List<String>): List<PaletteColor> = data.mapIndexed { index, hexString ->
        val color = ColorWithAlpha.of(hexString)
        PaletteColorImpl(index, color.red, color.green, color.blue, color.alpha)
    }
}

/**
 * A list of palette colors. This is used for serializing and deserializing the palette colors in [LevelSettings][cn.rdlevel.rdkt.core.settings.LevelSettings].
 * In [LevelSettings][cn.rdlevel.rdkt.core.settings.LevelSettings] this list must contain exactly 21 colors, which correspond to the palette indices from 0 to 20.
 */
public typealias PaletteColorList = @Serializable(PaletteColorListSerializer::class) List<PaletteColor>

/**
 * Creates a [Palette] instance with the specified index.
 */
@JvmName("ofPalette")
public fun colorOf(paletteIndex: Int): Palette = Palette.of(paletteIndex)

/**
 * Creates a [Color] instance with the specified RGB components.
 */
@JvmName("ofColor")
public fun colorOf(r: Int, g: Int, b: Int): Color = Color.of(r, g, b)

/**
 * Creates a [ColorWithAlpha] instance with the specified RGBA components. The alpha component is optional and defaults to 255 (fully opaque) if not provided.
 */
@JvmName("ofColor")
public fun colorOf(r: Int, g: Int, b: Int, a: Int = 255): ColorWithAlpha = ColorWithAlpha.of(r, g, b, a)

@JvmField
public val WHITE: ColorWithAlpha = colorOf(255, 255, 255, 255)

@JvmField
public val BLACK: ColorWithAlpha = colorOf(0, 0, 0, 255)