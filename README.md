# gwt-pushstate

[![Build Status](https://buildhive.cloudbees.com/job/jbarop/job/gwt-pushstate/badge/icon)](https://buildhive.cloudbees.com/job/jbarop/job/gwt-pushstate/)


## Description

gwt-pushstate implements easy to use HTML5 pushState support for GWT projects.


## License

    Copyright 2012 Johannes Barop
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
          http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


## Features

### Easy to use

Add the gwt-pushstate dependency to your project:

    <dependency>
      <groupId>de.barop.gwt</groupId>
      <artifactId>gwt-pushstate</artifactId>
      <version>1.0.0</version>
    </dependency>

And inherit the PushState module in your GWT module:

    <inherits name="de.barop.gwt.PushState" />


### Integrates well with GWT

gwt-pushstate hooks onto GWT's standard History API. Because of that it plays nice with existing applications and the place mechanism of GWTP-MVP or GWT-Places still works.


### Development mode supported

The development mode still works and is not left accidently when pushing or popping history states.


### Hyperlinks with nice URLs

Overriden ``Hyperlink`` and ``InlineHyperlink`` widgets for nice URLs without hashbangs.


### Oldskool Browser support

Transparent fallback to URLs with hashes for browsers without pushState support.


## Caveats

* Always the complete path of an URL is treated as history token.
* Path depended stuff might break (``GWT.getHostPageBaseURL()`` or GWTP-dispatch's auto discovery of service URLs).
* In order to get deeplinking to work some sort of server configuration is needed (URL rewriting, see the sample).


## Sample


There is a sample application based on GWTP-0.7's [gwtp-sample-hplace](https://github.com/ArcBees/GWTP/tree/391aaa1cfdee94564ab1a6438b482054e076a84c/gwtp-samples/gwtp-sample-hplace). Only a few changes where needed to get work nicely. These are documented as [commit here](https://github.com/jbarop/gwt-pushstate/commit/a3d278b2fae71adc4ea7fb22c5eb121ada36b644).

The sources are located in ``src/examples`` however the sample is deactivated by default. You can activate it by specifying the ``examples`` maven profile.


### Running

You can run the sample with the development mode by executing ``mvn gwt:run -Pexamples`` or the staticly compiled version with ``mvn jetty:run-exploded -Pexamples``.


### URL rewriting

To get deeplinking to work all incoming URLs expect the service paths are rewritten to the GWT application [here](https://github.com/jbarop/gwt-pushstate/blob/master/src/examples/webapp/WEB-INF/urlrewrite.xml). This is done using the [UrlRewriteFilter](http://tuckey.org/urlrewrite/) for Java which works nice for local testing and development.


### Importing to your IDE

If you want to import the sample application into your IDE make sure you activate the profile.

As the example uses GWT Platforms GenEvents you may have to run ``mvn clean compile -Pexamples`` to get the generated sources generated.
