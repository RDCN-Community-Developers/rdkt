@file:OptIn(ExperimentalSerializationApi::class)

package cn.rdlevel.rdkt.core.data.action

import cn.rdlevel.rdkt.core.annotations.RDDeprecated
import cn.rdlevel.rdkt.core.data.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.jvm.JvmField

@Serializable
@JsonClassDiscriminator("preset")
public sealed interface VFXPreset {
    public val rooms: RoomsOrTopLayer

    @Serializable
    @SerialName("Vignette")
    public class Vignette(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("VignetteFlicker")
    public class VignetteFlicker(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("CutsceneMode")
    public class CutsceneMode(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("WavyRows")
    public class WavyRows(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, SpeedSpecificVFXPreset,
        DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var speedPerc: Double? = 100.0
            set(value) {
                checkSpeed(value)
                field = value
            }
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR
    }

    @Serializable
    @SerialName("LightStripVert")
    public class LightStripVert(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("SilhouettesOnHBeat")
    public class SilhouettesOnHBeat(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("ColourfulShockwaves")
    public class ColourfulShockwaves(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("BassDropOnHit")
    public class BassDropOnHit(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("ShakeOnHeartBeat")
    public class ShakeOnHeartBeat(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("ShakeOnHit")
    public class ShakeOnHit(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("NumbersAbovePulses")
    public class NumbersAbovePulses(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("FallingPetals")
    public class FallingPetals(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("FallingPetalsInstant")
    public class FallingPetalsInstant(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("FallingPetalsSnow")
    public class FallingPetalsSnow(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("FallingLeaves")
    public class FallingLeaves(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("Rain")
    public class Rain(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR
    }

    @Serializable
    @SerialName("Snow")
    public class Snow(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("Blizzard")
    public class Blizzard(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR
    }

    @Serializable
    @SerialName("Embers")
    public class Embers(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, ColorSpecificVFXPreset,
        DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var color: ColorOrPalette? = DEFAULT_COLOR
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR

        public companion object {
            @JvmField
            public val DEFAULT_COLOR: ColorWithAlpha = colorOf(208, 73, 73, 255)
        }
    }

    @Serializable
    @SerialName("Matrix")
    public class Matrix(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("Diamonds")
    public class Diamonds(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, ColorSpecificVFXPreset,
        DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var color: ColorOrPalette? = DEFAULT_COLOR
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR

        public companion object {
            @JvmField
            public val DEFAULT_COLOR: ColorWithAlpha = WHITE
        }
    }

    @Serializable
    @SerialName("Confetti")
    public class Confetti(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("ConfettiBurst")
    public class ConfettiBurst(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("Balloons")
    public class Balloons(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("VHS")
    public class VHS(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("VHSRewind")
    public class VHSRewind(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR
    }

    @Serializable
    @SerialName("Scanlines")
    public class Scanlines(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("Aberration")
    public class Aberration(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR
    }

    @Serializable
    @SerialName("JPEG")
    public class JPEG(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR
    }

    @Serializable
    @SerialName("Grain")
    public class Grain(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR
    }

    @Serializable
    @SerialName("Blur")
    public class Blur(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR
    }

    @Serializable
    @SerialName("Blur")
    public class RadialBlur(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, Vector2AmountSpecificVFXPreset,
        DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var amount: Vector2 = Vector2.ONE
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR
    }

    @Serializable
    @SerialName("Fisheye")
    public class Fisheye(
        override var enable: Boolean,
        override var rooms: RoomsOrTopLayer,
    ) : RoomsOrTopLayerSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR
    }

    @Serializable
    @SerialName("HallOfMirrors")
    public class HallOfMirrors(
        override var enable: Boolean,
        override var rooms: RoomsOrTopLayer,
    ) : RoomsOrTopLayerSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("TileN")
    public class TileN(
        override var enable: Boolean,
        override var rooms: RoomsOrTopLayer,
    ) : RoomsOrTopLayerSpecificVFXPreset, ToggleableVFXPreset, Vector2AmountSpecificVFXPreset,
        DurationSpecificVFXPreset {
        override var amount: Vector2 = Vector2.ONE
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR
    }

    @Serializable
    @SerialName("CustomScreenScroll")
    public class CustomScreenScroll(
        override var enable: Boolean,
        override var rooms: RoomsOrTopLayer,
    ) : RoomsOrTopLayerSpecificVFXPreset, ToggleableVFXPreset, Vector2SpeedSpecificVFXPreset,
        DurationSpecificVFXPreset {
        override var xySpeed: Vector2 = Vector2.ZERO
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR
    }

    @Serializable
    @SerialName("ScreenWaves")
    public class ScreenWaves(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR
    }

    @Serializable
    @SerialName("HeatDistortion")
    public class HeatDistortion(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR
    }

    @Serializable
    @SerialName("Pixelate")
    public class Pixelate(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset, Vector2AmountSpecificVFXPreset, DurationSpecificVFXPreset {
        override var amount: Vector2 = Vector2.ONE
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR
    }

    @Serializable
    @SerialName("Mosaic")
    public class Mosaic(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR
    }

    @Serializable
    @SerialName("GlassShatter")
    public class GlassShatter(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("GlitchObstruction")
    public class GlitchObstruction(
        override var enable: Boolean,
        override var rooms: RoomsOrTopLayer,
    ) : RoomsOrTopLayerSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("Noise")
    public class Noise(
        override var enable: Boolean,
        override var rooms: RoomsOrTopLayer,
    ) : RoomsOrTopLayerSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("HueShift")
    public class HueShift(
        override var enable: Boolean,
        override var rooms: RoomsOrTopLayer,
    ) : RoomsOrTopLayerSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR
    }

    @Serializable
    @SerialName("Brightness")
    public class Brightness(
        override var enable: Boolean,
        override var rooms: RoomsOrTopLayer,
    ) : RoomsOrTopLayerSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR
    }

    @Serializable
    @SerialName("Contrast")
    public class Contrast(
        override var enable: Boolean,
        override var rooms: RoomsOrTopLayer,
    ) : RoomsOrTopLayerSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR
    }

    @Serializable
    @SerialName("Saturation")
    public class Saturation(
        override var enable: Boolean,
        override var rooms: RoomsOrTopLayer,
    ) : RoomsOrTopLayerSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR
    }

    @Serializable
    @SerialName("BlackAndWhite")
    public class BlackAndWhite(
        override var enable: Boolean,
        override var rooms: RoomsOrTopLayer,
    ) : RoomsOrTopLayerSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("Sepia")
    public class Sepia(
        override var enable: Boolean,
        override var rooms: RoomsOrTopLayer,
    ) : RoomsOrTopLayerSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("Bloom")
    public class Bloom(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset, ThresholdSpecificVFXPreset, IntensitySpecificVFXPreset,
        ColorSpecificVFXPreset, DurationSpecificVFXPreset {
        override var threshold: Double? = 0.3
        override var intensity: Double? = 5.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var color: ColorOrPalette? = DEFAULT_COLOR
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR

        public companion object {
            @JvmField
            public val DEFAULT_COLOR: ColorWithAlpha = WHITE
        }
    }

    @Serializable
    @SerialName("OrangeBloom")
    public class OrangeBloom(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("BlueBloom")
    public class BlueBloom(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("Funk")
    public class Funk(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("Drawing")
    public class Drawing(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, ColorSpecificVFXPreset,
        SpeedSpecificVFXPreset, DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var color: ColorOrPalette? = DEFAULT_COLOR
        override var speedPerc: Double? = 100.0
            set(value) {
                checkSpeed(value)
                field = value
            }
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR

        public companion object {
            @JvmField
            public val DEFAULT_COLOR: ColorWithAlpha = BLACK
        }
    }

    @Serializable
    @SerialName("Dots")
    public class Dots(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR
    }

    @Serializable
    @SerialName("EyesBig")
    public class EyesBig(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, ColorSpecificVFXPreset,
        Vector2SpeedSpecificVFXPreset, DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var color: ColorOrPalette? = DEFAULT_COLOR
        override var xySpeed: Vector2 = Vector2.ZERO
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR

        public companion object {
            @JvmField
            public val DEFAULT_COLOR: ColorWithAlpha = WHITE
        }
    }

    @Serializable
    @SerialName("EyesSmall")
    public class EyesSmall(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, ColorSpecificVFXPreset,
        Vector2SpeedSpecificVFXPreset, DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var color: ColorOrPalette? = DEFAULT_COLOR
        override var xySpeed: Vector2 = Vector2.ZERO
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR

        public companion object {
            @JvmField
            public val DEFAULT_COLOR: ColorWithAlpha = WHITE
        }
    }

    @Serializable
    @SerialName("Tutorial")
    public class Tutorial(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset, IntensitySpecificVFXPreset, ColorSpecificVFXPreset,
        DurationSpecificVFXPreset {
        override var intensity: Double? = 100.0
            set(value) {
                checkIntensity(value)
                field = value
            }
        override var color: ColorOrPalette? = DEFAULT_COLOR
        override var duration: Double = 0.0
        override var ease: Easing = Easing.LINEAR

        public companion object {
            @JvmField
            public val DEFAULT_COLOR: ColorWithAlpha = WHITE
        }
    }

    @Serializable
    @SerialName("Tile2")
    @RDDeprecated
    public class Tile2(
        override var enable: Boolean,
        override var rooms: RoomsOrTopLayer,
    ) : RoomsOrTopLayerSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("Tile3")
    @RDDeprecated
    public class Tile3(
        override var enable: Boolean,
        override var rooms: RoomsOrTopLayer,
    ) : RoomsOrTopLayerSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("Tile4")
    @RDDeprecated
    public class Tile4(
        override var enable: Boolean,
        override var rooms: RoomsOrTopLayer,
    ) : RoomsOrTopLayerSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("ScreenScrollX")
    @RDDeprecated
    public class ScreenScrollX(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("ScreenScroll")
    @RDDeprecated
    public class ScreenScroll(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("ScreenScrollSansVHS")
    @RDDeprecated
    public class ScreenScrollSansVHS(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("ScreenScrollXSansVHS")
    @RDDeprecated
    public class ScreenScrollXSansVHS(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("RowGlowWhite")
    @RDDeprecated
    public class RowGlowWhite(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("RowOutline")
    @RDDeprecated
    public class RowOutline(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("RowShadow")
    @RDDeprecated
    public class RowShadow(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("RowAllWhite")
    @RDDeprecated
    public class RowAllWhite(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("RowSilhouetteGlow")
    @RDDeprecated
    public class RowSilhouetteGlow(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("RowPlain")
    @RDDeprecated
    public class RowPlain(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("Blackout")
    @RDDeprecated
    public class Blackout(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("MiawMiaw")
    @RDDeprecated
    public class MiawMiaw(
        override var enable: Boolean,
        override var rooms: Rooms,
    ) : RoomsSpecificVFXPreset, ToggleableVFXPreset

    @Serializable
    @SerialName("DisableAll")
    public class DisableAll(
        override var rooms: RoomsOrTopLayer,
    ) : RoomsOrTopLayerSpecificVFXPreset
}

public sealed interface RoomsOrTopLayerSpecificVFXPreset : VFXPreset {
    override var rooms: RoomsOrTopLayer
}

public sealed interface RoomsSpecificVFXPreset : VFXPreset {
    override var rooms: Rooms
}

public sealed interface ToggleableVFXPreset : VFXPreset {
    public var enable: Boolean
}

public sealed interface ThresholdSpecificVFXPreset : VFXPreset {
    public var threshold: Double?
}

public sealed interface IntensitySpecificVFXPreset : VFXPreset {
    public var intensity: Double?
}

private fun IntensitySpecificVFXPreset.checkIntensity(value: Double? = intensity) {
    require(value == null || value in -9999.0..9999.0) { "Intensity must be between -9999 and 9999, but was $value." }
}

public sealed interface ColorSpecificVFXPreset : VFXPreset {
    public var color: ColorOrPalette?
}

public sealed interface Vector2AmountSpecificVFXPreset : VFXPreset {
    public var amount: Vector2
}

public sealed interface SpeedSpecificVFXPreset : VFXPreset {
    public var speedPerc: Double?
}

private fun SpeedSpecificVFXPreset.checkSpeed(value: Double? = speedPerc) {
    require(value == null || value in -9999.0..9999.0) { "Speed must be between -9999 and 9999, but was $value." }
}

public sealed interface Vector2SpeedSpecificVFXPreset : VFXPreset {
    public var xySpeed: Vector2
}

public sealed interface DurationSpecificVFXPreset : VFXPreset {
    public var duration: Double
    public var ease: Easing
}