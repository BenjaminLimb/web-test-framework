Introduction to using web-test-framework
=================
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.syftkog/web-test-framework/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/org.syftkog/web-test-framework)

This project is a framework for building Java Maven TestNG Selenium UI Tests that makes it easy to get started and provides robust functionality to help you write powerful automated web UI tests without a lot of code.

To get started download the framework and create a project on your local machine as follows:

```
git clone https://github.com/BenjaminLimb/web-test-framework.git
mvn install -fweb-test-framework/archetype/pom.xml 
mvn archetype:generate -DarchetypeGroupId=org.syftkog.archetype -DarchetypeArtifactId=web-test-framework-archetype
```

Or simply include the maven dependency in your existing project pom.xml
```xml
<dependency>
	<groupId>org.syftkog</groupId>
	<artifactId>web-test-framework</artifactId>
	<version>1.8.1</version>
</dependency>

```

See the wiki for more https://github.com/BenjaminLimb/web-test-framework/wiki

