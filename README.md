VmMonitor
=========

## Requirements 
Java 14 or newer

JavaFX 14 or newer

Maven 3.6.3 or newer

## Building the project

 JavaFX is not bundled in Java SDK so it needs to be added manually to the path. We can do this by adding the following arguments to VM options

```
--module-path <project_directory>/server/javafx-sdk-14.0.1/lib --add-modules=javafx.controls,javafx.fxml
```
