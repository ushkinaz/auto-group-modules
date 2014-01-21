/**
 * packaging rules take precedence over name rules
 */
class Configuration {
    String[] APPLICATION_GROUP = ["application"];
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
            ".*-api": API_GROUP,
            ".*-service": SERVICES_GROUP,
            ".*-boundary": BOUNDARY_GROUP,
            ".*-beans": BEANS_GROUP,
            "beans": BEANS_GROUP,
            ".*-ext": EXT_GROUP,

            "reports": SUPPLEMENTAL_GROUP,
            "update_scripts": SUPPLEMENTAL_GROUP,
            "app\\.aggregator": SUPPLEMENTAL_GROUP,

            ".*-aspect": ASPECT_GROUP,
    ]
}
