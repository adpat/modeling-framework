modeling-framework
==================

Base and mock classes for model code


## Javadocs

To run javadoc, do any of:

    mvn site
    
    mvn javadoc:javadoc
    
    mvn javadoc:jar

Note: if you see an error like

    model_mock: Could not resolve dependencies for project edu.berkeley.path:model_mock:jar:0.1-SNAPSHOT: Could not find artifact edu.berkeley.path:model_base:jar:0.1-SNAPSHOT -> [Help 1

then try doing a local install first:

    mvn install
