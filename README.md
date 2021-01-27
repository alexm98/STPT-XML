# STPT-XML Project

# Useful commands:

## TeXDoclet

https://doclet.github.io/

Download the .jar from: https://doclet.github.io/resources/bin/TeXDoclet.jar 

Since JAXB is deprecated in Java 11 one has to use the Java 8 binaries to run the jar

```$bash
/usr/lib/jvm/java-8-openjdk-amd64/bin/javadoc -docletpath <TeXDoclet.jar> \
-doclet org.stfm.texdoclet.TeXDoclet \
-noindex \
-tree \
-hyperref \
-private \
-output xml-project/doc-tex/main.tex \
-title "STPT: XML Technologies Project"  \
-author "Alexandru Munteanu\\\\Maria Vonica\\\\Zouel Fikar Jahjah" \
-sourcepath /xml-project/src/main/java \
-subpackages models parsers core 
```

The output is going to be in `doc-tex/main.tex`, compile it twice using `pdflatex main.tex` and enjoy!