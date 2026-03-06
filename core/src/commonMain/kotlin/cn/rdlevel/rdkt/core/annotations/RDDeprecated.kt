package cn.rdlevel.rdkt.core.annotations

/**
 * This annotation marks APIs that are not recommended for use in levels, either because they are broken, or there are better alternatives available. Use with caution.
 */
@MustBeDocumented
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.TYPEALIAS,
    AnnotationTarget.CONSTRUCTOR,
)
@Retention(AnnotationRetention.BINARY)
@RequiresOptIn(
    "This API is not recommended for use in levels, either because it is broken, or there are better alternatives available. Use with caution.",
    RequiresOptIn.Level.WARNING,
)
public annotation class RDDeprecated