module jpms.test.main {
    requires kotlin.stdlib;
    // Xtext currently (2.26.0) doesn't provide explicit modules. Instead, its manifests exhibit the option
    // "Automatic-Module-Name" to open the JAR files as a whole for external accesses.
    requires org.eclipse.xtext;
    exports org.example;
}