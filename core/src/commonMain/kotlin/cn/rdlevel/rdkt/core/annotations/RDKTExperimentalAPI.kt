package cn.rdlevel.rdkt.core.annotations

/**
 * Marks an API as experimental. This means that the API is still in development and may be changed or removed in a future release.
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
    "This API is experimental and may be changed or removed in a future release. Use with caution.",
    RequiresOptIn.Level.WARNING,
)
public annotation class RDKTExperimentalAPI