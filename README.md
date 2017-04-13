# Hippo External Document Picker Example Implementation
Example implementation, using the Hippo Forge plugin "External Document Picker" to retrieve external documents from a JSON REST endpoint

## How it was created
1. Add the neccesary dependencies to pom [[1]](#1)
2. Implement your DocumentServiceFacede from which the picker will recieve it's documents [[2]](cms/src/main/java/com/incentro/externaldocumentpicker/field/DocumentServiceFacade.java)
3. Point it to your JSON endpoint by editing the public static final String constant JSON_REST_URL


### <a name="1"></a> Dependencies
#### main pom.xml:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
    <properties>
        <forge.exdocpickerbase.version>3.1.1</forge.exdocpickerbase.version>
    </properties>
    <dependencyManagement>
        <dependencies>
          <!--External document picker dependency-->
          <dependency>
            <groupId>org.onehippo.forge.exdocpickerbase</groupId>
            <artifactId>exdocpickerbase-field</artifactId>
            <version>${forge.exdocpickerbase.version}</version>
          </dependency>
          
          <!-- Json support -->
           <dependency>
            <groupId>net.sf.json-lib</groupId>
            <artifactId>json-lib</artifactId>
            <version>${json-lib.version}</version>
            <classifier>jdk15</classifier>
          </dependency>
          
          <!-- Spring Framework -->
          <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>${spring.version}</version>
          </dependency>
          <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>${spring.version}</version>
          </dependency>
          <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>${spring.version}</version>
          </dependency>
          <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
          </dependency>
          <dependency>
             <groupId>org.springframework</groupId>
            <artifactId>spring-context-support</artifactId>
            <version>${spring.version}</version>
          </dependency>
          <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-expression</artifactId>
            <version>${spring.version}</version>
          </dependency>
          <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
             <version>${spring.version}</version>
          </dependency>
          <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>${spring.version}</version>
          </dependency>
          <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>${spring.version}</version>
            <scope>test</scope>
          </dependency>
        </dependencies>
    </dependencyManagement>
</project>
```
#### cms pom.xml:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
  <depedencies>
    <dependency>
      <groupId>net.sf.json-lib</groupId>
      <artifactId>json-lib</artifactId>
      <classifier>jdk15</classifier>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-web</artifactId>
    </dependency>
  </depedencies>
</project>
```