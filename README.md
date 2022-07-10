# SportIA
Aplication Java Web.
Maven, Spring Boot, Spring Security, Thymeleaf, MongoDB.
</br></br>


### Build and Deploy the Project
```
mvn clean install
```

This is a Spring Boot project, so you can deploy it by simply using the main class: `Application.java`

Once deployed, you can access the app at:

https://localhost:9191


### Set up MongoDB
The project is configured to use MongoDB, so you will need to configure the connection to the database in [application.properties](src/main/resources/application.properties)


### Set up Email
You need to configure the email by providing your own username and password in <i>application.properties</i> or in the <i>init</i> method of [EmailSenderService](src/main/java/unaj/ajsw/sportia/service/EmailSenderService.java).</br>
You also need to use your own host, you can use Amazon or Google for example.


### Set up Sandbox by Mercado Pago
To make use of the MercadoPago payment sandbox you must configure the access_token on the server side and the public_key on the client side. </br>
The access_token must be indicated in the <i>addAccessToken</i> method of [CheckoutProMPService](src/main/java/unaj/ajsw/sportia/service/CheckoutProMPService.java) and the public_key must be indicated in the script [sandbox-mp](src/main/resources/static/js/sandbox-mp.js)



### Report
The project report can be seen at the following [link](Informe) along with its 2 attachments:</br>
Annex I: [Documentation of Requirements](Informe/TP-AJSW-Informe-Anexo%20I-Documentación%20de%20Requisitos.pdf)</br>
Annex II: [Methods, Models and Tests](Informe/TP-AJSW-Informe-Anexo%20II-Métodos,%20Modelos%20y%20Métricas.pdf)