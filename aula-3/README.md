# Links da Aula 3

* Maven em 5 minutos: https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
* Maven Getting Started: https://maven.apache.org/guides/getting-started/index.html
* Build lifecycle: https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html

--- 
Provided scope
---

Jakarta EE API:

* Github project link: https://github.com/jakartaee/servlet/tree/main

* Link para baixar o Tomcat Server: https://tomcat.apache.org/download-10.cgi

* Kubernetes novo Servidor de Aplicação: https://rafabene.com/2019/03/15/kubernetes-servidor-aplicacao

```xml
<dependency>
    <groupId>jakarta.servlet</groupId>
    <artifactId>jakarta.servlet-api</artifactId>
    <version>6.0.0</version>
    <scope>provided</scope> <!-- provisionado pelo Tomcat Server -->
</dependency>
```