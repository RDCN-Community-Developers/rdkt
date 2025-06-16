@file:OptIn(RDKTInternalAPI::class)
@file:JvmName("Rankings")

package cn.rdlevel.rdkt.core.settings

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.jvm.JvmName

/**
 * Represents the maximum number of mistakes allowed for each rank in a level.
 */
@Serializable(RankMaxMistakes.Serializer::class)
public data class RankMaxMistakes(
    /**
     * The maximum number of mistakes allowed for rank A.
     */
    public val rankA: Int = DEFAULT_RANK_A,
    /**
     * The maximum number of mistakes allowed for rank B.
     */
    public val rankB: Int = DEFAULT_RANK_B,
    /**
     * The maximum number of mistakes allowed for rank C.
     */
    public val rankC: Int = DEFAULT_RANK_C,
    /**
     * The maximum number of mistakes allowed for rank D.
     */
    public val rankD: Int = DEFAULT_RANK_D,
) {
    init {
        require(
            listOf(
                DEFAULT_RANK_S <= rankA,
                rankA <= rankB,
                rankB <= rankC,
                rankC <= rankD,
                rankD <= DEFAULT_RANK_F,
            ).all { it }
        ) {
            "Invalid rank max mistakes: S <= A <= B <= C <= D <= F must hold true. " +
                    "Got: S=$DEFAULT_RANK_S, A=$rankA, B=$rankB, C=$rankC, D=$rankD"
        }
    }

    public companion object {
        /**
         * The rank S, which is the highest rank, and always has no mistakes allowed.
         */
        public const val DEFAULT_RANK_S: Int = 0

        /**
         * The default allowed mistakes for rank A.
         */
        public const val DEFAULT_RANK_A: Int = 5

        /**
         * The default allowed mistakes for rank B.
         */
        public const val DEFAULT_RANK_B: Int = 10

        /**
         * The default allowed mistakes for rank C.
         */
        public const val DEFAULT_RANK_C: Int = 15

        /**
         * The default allowed mistakes for rank D.
         */
        public const val DEFAULT_RANK_D: Int = 20

        /**
         * The rank F, which is the lowest rank, and always has any number of mistakes allowed.
         */
        public const val DEFAULT_RANK_F: Int = Int.MAX_VALUE
    }

    public object Serializer : KSerializer<RankMaxMistakes> {
        private val listSerializer = ListSerializer(Int.Companion.serializer())

        override val descriptor: SerialDescriptor = listSerializer.descriptor

        override fun serialize(encoder: Encoder, value: RankMaxMistakes) {
            encoder.encodeSerializableValue(
                listSerializer, listOf(
                    value.rankD,
                    value.rankC,
                    value.rankB,
                    value.rankA,
                )
            )
        }

        override fun deserialize(decoder: Decoder): RankMaxMistakes {
            val list = decoder.decodeSerializableValue(listSerializer)
            return RankMaxMistakes(
                rankA = list.getOrElse(3) { DEFAULT_RANK_A },
                rankB = list.getOrElse(2) { DEFAULT_RANK_B },
                rankC = list.getOrElse(1) { DEFAULT_RANK_C },
                rankD = list.getOrElse(0) { DEFAULT_RANK_D },
            )
        }
    }
}

/**
 * Represents the description for each rank in a level.
 */
@Serializable
public data class RankDescriptions(
    /**
     * Rank S description.
     */
    public val rankS: String = DEFAULT_RANK_S,
    /**
     * Rank A description.
     */
    public val rankA: String = DEFAULT_RANK_A,
    /**
     * Rank B description.
     */
    public val rankB: String = DEFAULT_RANK_B,
    /**
     * Rank C description.
     */
    public val rankC: String = DEFAULT_RANK_C,
    /**
     * Rank D description.
     */
    public val rankD: String = DEFAULT_RANK_D,
    /**
     * Rank F description.
     */
    public val rankF: String = DEFAULT_RANK_F,
) {
    public companion object {
        /**
         * The default description for rank S.
         */
        public const val DEFAULT_RANK_S: String = "Wow! That's awesome!!"

        /**
         * The default description for rank A.
         */
        public const val DEFAULT_RANK_A: String = "You are really good!"

        /**
         * The default description for rank B.
         */
        public const val DEFAULT_RANK_B: String = "We make a good team!"

        /**
         * The default description for rank C.
         */
        public const val DEFAULT_RANK_C: String = "Not bad I guess..."

        /**
         * The default description for rank D.
         */
        public const val DEFAULT_RANK_D: String = "Ugh, you can do better"

        /**
         * The default description for rank F.
         */
        public const val DEFAULT_RANK_F: String = "Better call 911, now!"
    }

    public object Serializer : KSerializer<RankDescriptions> {
        private val listSerializer = ListSerializer(String.serializer())

        override val descriptor: SerialDescriptor = listSerializer.descriptor

        override fun serialize(encoder: Encoder, value: RankDescriptions) {
            encoder.encodeSerializableValue(
                listSerializer, listOf(
                    value.rankF,
                    value.rankD,
                    value.rankC,
                    value.rankB,
                    value.rankA,
                    value.rankS,
                )
            )
        }

        override fun deserialize(decoder: Decoder): RankDescriptions {
            val list = decoder.decodeSerializableValue(listSerializer)
            return RankDescriptions(
                rankS = list.getOrElse(5) { DEFAULT_RANK_S },
                rankA = list.getOrElse(4) { DEFAULT_RANK_A },
                rankB = list.getOrElse(3) { DEFAULT_RANK_B },
                rankC = list.getOrElse(2) { DEFAULT_RANK_C },
                rankD = list.getOrElse(1) { DEFAULT_RANK_D },
                rankF = list.getOrElse(0) { DEFAULT_RANK_F },
            )
        }
    }
}

/**
 * A builder for creating rank settings in a level.
 * This is an internal API and instances should not be created directly.
 */

public class RankBuilder @RDKTInternalAPI constructor() {
    /**
     * Represents a rank configuration with the number of mistakes allowed and a description.
     * This interface can only modify the description of the rank.
     */
    public sealed interface Rank {
        /**
         * The max number of mistakes allowed for this rank.
         */
        public val mistakes: Int

        /**
         * The description for this rank.
         */
        public var description: String

        /**
         * Sets the description for this rank.
         *
         * @param description The description for the rank.
         * @return The current [Rank] instance for chaining.
         */
        public infix fun description(description: String): Rank {
            this.description = description
            return this
        }
    }

    /**
     * Represents a rank configuration that allows both the number of mistakes and the description to be modified.
     */
    public sealed interface MistakesMutableRank : Rank {
        override var mistakes: Int

        /**
         * Sets the number of mistakes allowed for this rank.
         *
         * @param mistakes The number of mistakes allowed.
         * @return The current [Rank] instance for chaining.
         */
        public infix fun mistakes(mistakes: Int): Rank {
            this.mistakes = mistakes
            return this
        }
    }

    private class RankImpl(
        override var mistakes: Int = 0,
        override var description: String = "",
    ) : Rank, MistakesMutableRank

    /**
     * Rank S configuration.
     */
    public val rankS: Rank = RankImpl(RankMaxMistakes.DEFAULT_RANK_S, RankDescriptions.DEFAULT_RANK_S)

    /**
     * Rank A configuration.
     */
    public val rankA: MistakesMutableRank = RankImpl(RankMaxMistakes.DEFAULT_RANK_A, RankDescriptions.DEFAULT_RANK_A)

    /**
     * Rank B configuration.
     */
    public val rankB: MistakesMutableRank = RankImpl(RankMaxMistakes.DEFAULT_RANK_B, RankDescriptions.DEFAULT_RANK_B)

    /**
     * Rank C configuration.
     */
    public val rankC: MistakesMutableRank = RankImpl(RankMaxMistakes.DEFAULT_RANK_C, RankDescriptions.DEFAULT_RANK_C)

    /**
     * Rank D configuration.
     */
    public val rankD: MistakesMutableRank = RankImpl(RankMaxMistakes.DEFAULT_RANK_D, RankDescriptions.DEFAULT_RANK_D)

    /**
     * Rank F configuration.
     */
    public val rankF: Rank = RankImpl(RankMaxMistakes.DEFAULT_RANK_F, RankDescriptions.DEFAULT_RANK_F)

    /**
     * Applies the rank settings to the provided [LevelSettings].
     *
     * @param settings The level settings to apply the rank settings to.
     */
    public fun apply(settings: LevelSettings) {
        settings.rankMaxMistakes = RankMaxMistakes(
            rankA = rankA.mistakes,
            rankB = rankB.mistakes,
            rankC = rankC.mistakes,
            rankD = rankD.mistakes,
        )
        settings.rankDescription = RankDescriptions(
            rankS = rankS.description,
            rankA = rankA.description,
            rankB = rankB.description,
            rankC = rankC.description,
            rankD = rankD.description,
            rankF = rankF.description,
        )
    }
}

/**
 * Configure ranks in a [LevelSettings] instance.
 *
 * @param block The block to configure the ranks.
 * @return The updated [LevelSettings] instance with the configured ranks.
 */
public inline fun LevelSettings.ranks(block: RankBuilder.() -> Unit): LevelSettings {
    val builder = RankBuilder()
    block(builder)
    builder.apply(this)
    return this
}