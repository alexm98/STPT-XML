# The project and its implementation is based on a subject at your choice!

## Your project should follow some simple requirements:

~~* Firstly, it is necessary to define an XML information base (an XML document and its structure), on a subject at your choice. You’ll include in this information base at least three categories of information.~~
* Once you have the information base (the XML document with its associated structure), it will be necessary to define some basic services. 
  * These services have to implement, for example, basic abilities for XML document manipulation (such as create/delete/add/update nodes, as well as search and filtering of information).
  * The use of XPath and XSLT for the implementation of these services is mandatory!
* However, your project is not limited to the manipulation of some XML documents! Your project should perform some “useful” tasks (computing).
  * Hint: you can start from the XML document from laboratories...
* Next, you’ll have to identify at least 2 different APIs for your application. One of these could offer a REST API, another a WSDL-based (Web services) API, or maybe some ad-hoc APIs. 
It is supposed that your implementation from #2 does not depend on the used API, so you’ll use Apache Camel to expose some of these APIs.
  * Hint: you can use the Producer-Consumer model from Apache Camel (via akka)!
  * Additional notice: there will be a penalty if you do not use Apache Camel (50% for this requirement)
* Now you have to show that your basic services are accessible, by using some REST/WS testing frameworks (such as ~~SoapUI~~ Postman).
* However, a fully implemented project will require a user interface for service invocation. This interface must be built on XForms, and must be able to consume/invoke at least one of the exposed APIs (REST/WS). Preferably both!
  * Hint: Orbeon, as an XForms player, is capable to invoke/consume both REST and WS/CXF endpoints. However, there will be a light penalty (at most 25% for this component) if your implementation is not using XForms, and you replace it with an AJAX solution.
* Also, it will be necessary to implement some complex scenarios (exposed via APIs, of course), to integrate some of the basic services. Find the most appropriate integration patterns and use them in order to offer an implementation of this requirement (http://camel.apache.org/enterprise-integration-patterns.html). Your project documentation should emphasize how these EIPs were used, and the context of their use.

--- 

* In your project documentation you must show how and where the various XML-based technologies were used. Also, any EIP (even the simple ones) must be documented (see above).
* Also, it is strongly suggested the use of some message queues (JMS, ActiveMQ -- which may offer you additional points) to support the management of services messages. See, for example,  http://servicemix.apache.org/docs/5.x/quickstart/activemq.html
* Apache Camel is made available via ServiceMIX (see previous link), and is a key component in JBoss Fuse. JBoss Fuse also offers a nice route editor and a good set of documentation pages. You can use Apache Camel with static routes (basically, XML files with some EIP-based recipes), or with programmable routes (there is support for various programming languages, including Java or Scala, or the actor library Akka).
 
* The subject of your project is at your choice. The team of a project may include up to 3 students, preferably each of them responsible with one of the major components: basic implementation of services, services integration and API exposure, user interface.

* From an architectural point of view, there are at least three distinct, separable layers: the UI (XForms), an integration middleware (servicemix/Apache Camel + support tools, like a message queue), and your developed services.

That’s all, folks!
