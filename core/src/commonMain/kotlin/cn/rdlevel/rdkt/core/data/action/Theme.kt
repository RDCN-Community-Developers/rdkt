@file:OptIn(RDKTInternalAPI::class)

package cn.rdlevel.rdkt.core.data.action

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import cn.rdlevel.rdkt.core.data.Easing
import cn.rdlevel.rdkt.core.serialization.EnumIndexSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

/**
 * Themes that can be used to set theme in [SetThemeEvent][cn.rdlevel.rdkt.core.events.SetThemeEvent].
 */
@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("preset")
@JsonIgnoreUnknownKeys
public sealed interface Theme {
    /**
     * Whether to skip row painting effects when applying the theme.
     */
    public var skipPaintEffects: Boolean

    @Serializable
    @SerialName("None")
    public class None : AbstractTheme()

    @Serializable
    @SerialName("OrientalTechno")
    public class OrientalTechno : AbstractTheme(), VariedTheme<OrientalTechno.Variant> {
        override var variant: Variant = Variant.OPEN

        @Serializable(Variant.Serializer::class)
        public enum class Variant {
            OPEN, CLOSED;

            public object Serializer : KSerializer<Variant> by EnumIndexSerializer()
        }
    }

    @Serializable
    @SerialName("BoyWard")
    public class BoyWard : AbstractTheme()

    @Serializable
    @SerialName("GirlWard")
    public class GirlWard : AbstractTheme()

    @Serializable
    @SerialName("Skyline")
    public class Skyline : AbstractTheme(), VariedTheme<Skyline.Variant> {
        override var variant: Variant = Variant.PINK

        @Serializable(Variant.Serializer::class)
        public enum class Variant {
            PINK, BLUE;

            public object Serializer : KSerializer<Variant> by EnumIndexSerializer()
        }
    }

    @Serializable
    @SerialName("InsomniacDay")
    public class InsomniacDay : AbstractTheme(), VariedTheme<InsomniacDay.Variant> {
        override var variant: Variant = Variant.DAY

        @Serializable(Variant.Serializer::class)
        public enum class Variant {
            DAY, NIGHT;

            public object Serializer : KSerializer<Variant> by EnumIndexSerializer()
        }
    }

    @Serializable
    @SerialName("ColeWardNight")
    public class ColeWardNight : AbstractTheme(), VariedTheme<ColeWardNight.Variant> {
        override var variant: Variant = Variant.NIGHT

        @Serializable(Variant.Serializer::class)
        public enum class Variant {
            NIGHT, SUNRISE;

            public object Serializer : KSerializer<Variant> by EnumIndexSerializer()
        }
    }

    @Serializable
    @SerialName("Kaleidoscope")
    public class Kaleidoscope : AbstractTheme()

    @Serializable
    @SerialName("CoffeeShop")
    public class CoffeeShop : AbstractTheme(), VariedTheme<CoffeeShop.Variant> {
        override var variant: Variant = Variant.DAY

        @Serializable(Variant.Serializer::class)
        public enum class Variant {
            DAY, NIGHT;

            public object Serializer : KSerializer<Variant> by EnumIndexSerializer()
        }
    }

    @Serializable
    @SerialName("PoliticiansRally")
    public class PoliticiansRally : AbstractTheme()

    @Serializable
    @SerialName("TrainDay")
    public class TrainDay : AbstractPositionedTheme(), VariedTheme<TrainDay.Variant> {
        override val positionXRange: ClosedFloatingPointRange<Double> get() = -528.0..176.0

        override var variant: Variant = Variant.DAY

        @Serializable(Variant.Serializer::class)
        public enum class Variant {
            DAY, NIGHT;

            public object Serializer : KSerializer<Variant> by EnumIndexSerializer()
        }
    }

    @Serializable
    @SerialName("DesertDay")
    public class DesertDay : AbstractPositionedTheme(), VariedTheme<DesertDay.Variant> {
        override val positionXRange: ClosedFloatingPointRange<Double> get() = -528.0..176.0

        override var variant: Variant = Variant.DAY

        @Serializable(Variant.Serializer::class)
        public enum class Variant {
            DAY, NIGHT;

            public object Serializer : KSerializer<Variant> by EnumIndexSerializer()
        }
    }

    @Serializable
    @SerialName("Stadium")
    public class Stadium : AbstractTheme(), VariedTheme<Stadium.Variant> {
        override var variant: Variant = Variant.SUNNY

        @Serializable(Variant.Serializer::class)
        public enum class Variant {
            SUNNY, STORMY;

            public object Serializer : KSerializer<Variant> by EnumIndexSerializer()
        }
    }

    @Serializable
    @SerialName("HospitalWard")
    public class HospitalWard : AbstractPositionedTheme(), VariedTheme<HospitalWard.Variant> {
        override val positionXRange: ClosedFloatingPointRange<Double> get() = -490.0..1172.0

        override var variant: Variant = Variant.DAY

        @Serializable(Variant.Serializer::class)
        public enum class Variant {
            DAY, NIGHT;

            public object Serializer : KSerializer<Variant> by EnumIndexSerializer()
        }
    }

    @Serializable
    @SerialName("PaigeOffice")
    public class PaigeOffice : AbstractPositionedTheme() {
        override val positionXRange: ClosedFloatingPointRange<Double> get() = -358.0..42.0
    }

    @Serializable
    @SerialName("Basement")
    public class Basement : AbstractPositionedTheme() {
        override val positionXRange: ClosedFloatingPointRange<Double> get() = -32.0..320.0
    }

    @Serializable
    @SerialName("Garden")
    public class Garden : AbstractTheme(), VariedTheme<Garden.Variant> {
        override var variant: Variant = Variant.DAY

        @Serializable(Variant.Serializer::class)
        public enum class Variant {
            DAY, NIGHT;

            public object Serializer : KSerializer<Variant> by EnumIndexSerializer()
        }
    }

    @Serializable
    @SerialName("Rooftop")
    public class Rooftop : AbstractTheme(), VariedTheme<Rooftop.Variant> {
        override var variant: Variant = Variant.STANDARD

        @Serializable(Variant.Serializer::class)
        public enum class Variant {
            STANDARD, SUMMER, AUTUMN;

            public object Serializer : KSerializer<Variant> by EnumIndexSerializer()
        }
    }

    @Serializable
    @SerialName("AthleteWard")
    public class AthleteWard : AbstractPositionedTheme(), VariedTheme<AthleteWard.Variant> {
        override val positionXRange: ClosedFloatingPointRange<Double> get() = 0.0..732.0

        override var variant: Variant = Variant.DAY

        @Serializable(Variant.Serializer::class)
        public enum class Variant {
            DAY, NIGHT;

            public object Serializer : KSerializer<Variant> by EnumIndexSerializer()
        }
    }

    @Serializable
    @SerialName("RecordsRoom")
    public class RecordsRoom : AbstractPositionedTheme() {
        override val positionXRange: ClosedFloatingPointRange<Double> get() = -475.0..0.0
    }

    @Serializable
    @SerialName("AbandonedWard")
    public class AbandonedWard : AbstractPositionedTheme() {
        override val positionXRange: ClosedFloatingPointRange<Double> get() = -42.0..706.0
    }

    @Serializable
    @SerialName("Intimate")
    public class Intimate : AbstractTheme(), VariedTheme<Intimate.Variant> {
        override var variant: Variant = Variant.STANDARD

        @Serializable(Variant.Serializer::class)
        public enum class Variant {
            STANDARD, SIMPLE;

            public object Serializer : KSerializer<Variant> by EnumIndexSerializer()
        }
    }

    @Serializable
    @SerialName("Sky")
    public class Sky : AbstractTheme(), VariedTheme<Sky.Variant> {
        override var variant: Variant = Variant.DAY

        @Serializable(Variant.Serializer::class)
        public enum class Variant {
            DAY, NIGHT;

            public object Serializer : KSerializer<Variant> by EnumIndexSerializer()
        }
    }

    @Serializable
    @SerialName("Space")
    public class Space : AbstractTheme()

    @Serializable
    @SerialName("NeonMuseum")
    public class NeonMuseum : AbstractTheme()

    @Serializable
    @SerialName("BackAlley")
    public class BackAlley : AbstractTheme()

    @Serializable
    @SerialName("Airport")
    public class Airport : AbstractPositionedTheme() {
        override val positionXRange: ClosedFloatingPointRange<Double> get() = 0.0..248.0
    }

    @Serializable
    @SerialName("RollerDisco")
    public class RollerDisco : AbstractPositionedTheme() {
        override val positionXRange: ClosedFloatingPointRange<Double> get() = -1000.0..1000.0
    }

    @Serializable
    @SerialName("Matrix")
    public class Matrix : AbstractTheme()

    @Serializable
    @SerialName("CrossesStraight")
    public class CrossesStraight : AbstractTheme(), VariedTheme<CrossesStraight.Variant> {
        override var variant: Variant = Variant.STRAIGHT

        @Serializable(Variant.Serializer::class)
        public enum class Variant {
            STRAIGHT, FALLING;

            public object Serializer : KSerializer<Variant> by EnumIndexSerializer()
        }
    }

    @Serializable
    @SerialName("CubesFalling")
    public class CubesFalling : AbstractTheme(), VariedTheme<CubesFalling.Variant> {
        override var variant: Variant = Variant.STANDARD

        @Serializable(Variant.Serializer::class)
        public enum class Variant {
            STANDARD, NICE_BLUE;

            public object Serializer : KSerializer<Variant> by EnumIndexSerializer()
        }
    }

    @Serializable
    @SerialName("HallOfMirrors")
    public class HallOfMirrors : AbstractTheme()

    @Serializable
    @SerialName("FloatingHeart")
    public class FloatingHeart : AbstractTheme(), VariedTheme<FloatingHeart.Variant> {
        override var variant: Variant = Variant.STANDARD

        @Serializable(Variant.Serializer::class)
        public enum class Variant {
            STANDARD, WITH_CUBES;

            public object Serializer : KSerializer<Variant> by EnumIndexSerializer()
        }
    }

    @Serializable
    @SerialName("FloatingHeartBroken")
    public class FloatingHeartBroken : AbstractTheme(), VariedTheme<FloatingHeartBroken.Variant> {
        override var variant: Variant = Variant.STANDARD

        @Serializable(Variant.Serializer::class)
        public enum class Variant {
            STANDARD, WITH_CUBES;

            public object Serializer : KSerializer<Variant> by EnumIndexSerializer()
        }
    }

    @Serializable
    @SerialName("ZenGarden")
    public class ZenGarden : AbstractTheme()

    @Serializable
    @SerialName("Vaporwave")
    public class Vaporwave : AbstractTheme()

    @Serializable
    @SerialName("ProceduralTree")
    public class ProceduralTree : AbstractTheme()
}

/**
 * A theme that has variants.
 *
 * @property variant The variant of the theme.
 */
public sealed interface VariedTheme<E : Enum<E>> : Theme {
    public var variant: E
}

/**
 * A theme that can have its position adjusted.
 */
public sealed interface PositionedTheme : Theme {
    /**
     * Whether to enable position adjustment for the theme. If false, the position of the theme will be default or unchanged.
     */
    public var enablePosition: Boolean

    /**
     * The valid range for the [positionX].
     */
    public val positionXRange: ClosedFloatingPointRange<Double>

    /**
     * The X position of the theme pixels. The default position is 0.
     * The valid range depends on the theme, and should be within [positionXRange].
     *
     * Will be ignored if [enablePosition] is false.
     */
    public var positionX: Double

    /**
     * The duration of the position change animation in beats.
     *
     * Will be ignored if [enablePosition] is false.
     */
    public var positionDuration: Double

    /**
     * The easing function for the position change animation.
     *
     * Will be ignored if [enablePosition] is false.
     */
    public var positionEase: Easing
}

@RDKTInternalAPI
public sealed class AbstractTheme : Theme {
    override var skipPaintEffects: Boolean = false
}

@RDKTInternalAPI
public sealed class AbstractPositionedTheme : AbstractTheme(), PositionedTheme {
    override var enablePosition: Boolean = false

    override var positionX: Double = 0.0
        set(value) {
            require(value in positionXRange) { "positionX must be within the range of ${positionXRange.start} to ${positionXRange.endInclusive}." }
            field = value
        }

    override var positionDuration: Double = 0.0

    override var positionEase: Easing = Easing.LINEAR
}