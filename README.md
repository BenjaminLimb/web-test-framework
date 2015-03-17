Introduction to using web-test-framework
=================

This project is a framework for building Java Maven TestNG Selenium UI Tests that makes it easy to get started and provides robust functionality to help you write powerful automated web UI tests without a lot of code.

To get started download the framework and create a project on your local machine as follows:

```
git clone https://github.com/BenjaminLimb/web-test-framework.git
mvn install -fweb-test-framework/archetype/pom.xml 
mvn archetype:generate -DarchetypeGroupId=org.syftkog.archetype -DarchetypeArtifactId=web-test-framework-archetype

```
Enter your desired properties as propmpted.
Press [ENTER] to accept the properties configuration.
Ex
```
~/sandbox/temp git clone https://github.com/BenjaminLimb/web-test-framework.git
Cloning into 'web-test-framework'...
remote: Counting objects: 358, done.
remote: Compressing objects: 100% (17/17), done.
remote: Total 358 (delta 3), reused 0 (delta 0), pack-reused 335
Receiving objects: 100% (358/358), 10.15 MiB | 842.00 KiB/s, done.
Resolving deltas: 100% (108/108), done.
Checking connectivity... done.
~/sandbox/temp mvn install -fweb-test-framework/archetype/pom.xml 
[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building web-test-framework-archetype 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.7:resources (default-resources) @ web-test-framework-archetype ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] Copying 6 resources
[INFO] 
[INFO] --- maven-resources-plugin:2.7:testResources (default-testResources) @ web-test-framework-archetype ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory /Users/benjaminlimb/sandbox/temp/web-test-framework/archetype/src/test/resources
[INFO] 
[INFO] --- maven-archetype-plugin:2.2:jar (default-jar) @ web-test-framework-archetype ---
[INFO] Building archetype jar: /Users/benjaminlimb/sandbox/temp/web-test-framework/archetype/target/web-test-framework-archetype-1.0-SNAPSHOT
[INFO] 
[INFO] --- maven-archetype-plugin:2.2:integration-test (default-integration-test) @ web-test-framework-archetype ---
[WARNING] No Archetype IT projects: root 'projects' directory not found.
[INFO] 
[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ web-test-framework-archetype ---
[INFO] Installing /Users/benjaminlimb/sandbox/temp/web-test-framework/archetype/target/web-test-framework-archetype-1.0-SNAPSHOT.jar to /Users/benjaminlimb/.m2/repository/org/syftkog/archetype/web-test-framework-archetype/1.0-SNAPSHOT/web-test-framework-archetype-1.0-SNAPSHOT.jar
[INFO] Installing /Users/benjaminlimb/sandbox/temp/web-test-framework/archetype/pom.xml to /Users/benjaminlimb/.m2/repository/org/syftkog/archetype/web-test-framework-archetype/1.0-SNAPSHOT/web-test-framework-archetype-1.0-SNAPSHOT.pom
[INFO] 
[INFO] --- maven-archetype-plugin:2.2:update-local-catalog (default-update-local-catalog) @ web-test-framework-archetype ---
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.083 s
[INFO] Finished at: 2015-03-17T09:34:34-06:00
[INFO] Final Memory: 13M/245M
[INFO] ------------------------------------------------------------------------
~/sandbox/temp mvn archetype:generate -DarchetypeGroupId=org.syftkog.archetype -DarchetypeArtifactId=web-test-framework-archetype
[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building Maven Stub Project (No POM) 1
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] >>> maven-archetype-plugin:2.3:generate (default-cli) > generate-sources @ standalone-pom >>>
[INFO] 
[INFO] <<< maven-archetype-plugin:2.3:generate (default-cli) < generate-sources @ standalone-pom <<<
[INFO] 
[INFO] --- maven-archetype-plugin:2.3:generate (default-cli) @ standalone-pom ---
[INFO] Generating project in Interactive mode
[INFO] Archetype [org.syftkog.archetype:web-test-framework-archetype:1.0-SNAPSHOT] found in catalog local
Define value for property 'groupId': : org.example
Define value for property 'artifactId': : automated-ui-tests
Define value for property 'version':  1.0-SNAPSHOT: : 
Define value for property 'package':  org.example: : 
Confirm properties configuration:
groupId: org.example
artifactId: automated-ui-tests
version: 1.0-SNAPSHOT
package: org.example
 Y: : Y
[INFO] ----------------------------------------------------------------------------
[INFO] Using following parameters for creating project from Archetype: web-test-framework-archetype:1.0-SNAPSHOT
[INFO] ----------------------------------------------------------------------------
[INFO] Parameter: groupId, Value: org.example
[INFO] Parameter: artifactId, Value: automated-ui-tests
[INFO] Parameter: version, Value: 1.0-SNAPSHOT
[INFO] Parameter: package, Value: org.example
[INFO] Parameter: packageInPathFormat, Value: org/example
[INFO] Parameter: package, Value: org.example
[INFO] Parameter: version, Value: 1.0-SNAPSHOT
[INFO] Parameter: groupId, Value: org.example
[INFO] Parameter: artifactId, Value: automated-ui-tests
[INFO] project created from Archetype in dir: /Users/benjaminlimb/sandbox/temp/automated-ui-tests
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 22.688 s
[INFO] Finished at: 2015-03-17T09:34:58-06:00
[INFO] Final Memory: 15M/245M
[INFO] ------------------------------------------------------------------------
~/sandbox/temp 

```


