/**
 * packaging rules take precedence over name rules
 */
class Configuration {
    String[] APPLICATION_GROUP = ["application"];
    String[] APP_UTIL_GROUP = ["application", "util"];
    String[] API_GROUP = ["lib", "api"];
    String[] SERVICES_GROUP = ["lib", "services"];
    String[] BOUNDARY_GROUP = ["lib", "boundary"];
    String[] BEANS_GROUP = ["lib", "beans"];
    String[] EXT_GROUP = ["lib", "ext"];
    String[] SUPPLEMENTAL_GROUP = ["supplemental"];
    String[] ASPECT_GROUP = ["supplemental", "aspect"];
    String[] DEFAULT_GROUP = ["lib"];

    def packagingRules = [
            "pom": SUPPLEMENTAL_GROUP,
            "war": APPLICATION_GROUP,
    ]

    def nameRules = [
            "utils": APP_UTIL_GROUP,
            "squirtle": APP_UTIL_GROUP,
            "shell": APP_UTIL_GROUP,
            "saiku": APP_UTIL_GROUP,
            "reusable.utils": APP_UTIL_GROUP,


            ".*-api": API_GROUP,
            ".*-service": SERVICES_GROUP,

            ".*-boundary": BOUNDARY_GROUP,
            "boundary": BOUNDARY_GROUP,

            ".*-beans": BEANS_GROUP,
            "beans": BEANS_GROUP,

            ".*-ext": EXT_GROUP,

            "reports-resources": SUPPLEMENTAL_GROUP,
            "fonts": SUPPLEMENTAL_GROUP,
            "update_scripts": SUPPLEMENTAL_GROUP,

            ".*-aspect": ASPECT_GROUP,
    ]
}
