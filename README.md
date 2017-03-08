# TestBench 5 Simplest Demo Possible

This is a simplest demo possible of running automated UI tests in a Vaadin 8 project using TestBench 5. No browser is required: the internal JavaFX browser present in every
Java 8 is used.

# How to run it

Just run
```bash
mvn clean verify
```
An invisible JavaFX Browser will launch. To have a proof that the test actually ran, just see `target/screenshot.png`.

## UI integration tests with Vaadin TestBench
Tests are run using Maven Failsafe plugin. Execute `mvn verify`. Verify phase will run multiple goals e.g. starting Jetty in a port defined in profile's `config.properties` file.

Failsafe's default test inclusion patterns are used.
* `"**/IT*.java"` - includes all of its subdirectories and all Java filenames that start with "IT".
* `"**/*IT.java"` - includes all of its subdirectories and all Java filenames that end with "IT".
* `"**/*ITCase.java"` - includes all of its subdirectories and all Java filenames that end with "ITCase".

## Unit tests
For unit tests the Maven Surefire plugin is used. Surefire's default test inclusion patterns are used.
* `"**/Test*.java"` - includes all of its subdirectories and all Java filenames that start with "Test".
* `"**/*Test.java"` - includes all of its subdirectories and all Java filenames that end with "Test".
* `"**/*TestCase.java"` - includes all of its subdirectories and all Java filenames that end with "TestCase".
