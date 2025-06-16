package cn.rdlevel.rdkt.core.annotations

/**
 * This annotation marks APIs that are intended for internal use within the rdkt library.
 *
 * It is not recommended for general use and may change without notice.
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
    "This API is intended for internal use within the rdkt library and may change without notice. Use with caution.",
    RequiresOptIn.Level.WARNING,
)
public annotation class RDKTInternalAPI