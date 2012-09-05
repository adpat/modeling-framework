modeling-framework
==================

Base and mock classes for model code


## Javadocs

To run javadoc, do any of:

    mvn site
    
    mvn javadoc:javadoc
    
    mvn javadoc:jar

Note: if you see an error like

    MODEL_MOCK: Could not resolve dependencies for project edu.berkeley.path:MODEL_MOCK:jar:0.1-SNAPSHOT: Could not find artifact edu.berkeley.path:MODEL_BASE:jar:0.1-SNAPSHOT -> [Help 1

then try doing a local install first:

    mvn install
