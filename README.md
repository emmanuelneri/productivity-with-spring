Projeto utilizado na exemplificação da palestra no TDC São Paulo, segue o link da apresentação: https://pt.slideshare.net/emmanuelnerisouza/construindo-apis-de-forma-produtiva-com-spring-boot-spring-data-e-spring-mvc

# Visão geral

O projeto é uma aplicação back-end com objetivo de demonstrar a produtividade de construir APIs utilizando os frameworks [Spring Boot](https://projects.spring.io/spring-boot), [Spring MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html) e [Spring Data](http://projects.spring.io/spring-data) em conjunto.

## Tecnologias

- [Spring Boot](https://projects.spring.io/spring-boot) é uma ferramenta que simplifica a configuração e execução de aplicações Java stand-alone,  com conceitos de dependências “starters”, auto configuração e servlet container embutidos é proporcionado uma grande produtividade desde o start-up da aplicação até sua ida a produção.
 
- [Spring MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html) é um framework já consolidado no mercado, que a partir da versão fornece mecanismos simplificados para a criação de APIs RESTful através de anotação, além disso, também possui recursos de serialização e deserialização de objetos de forma transparente 
 
- [Spring Data](http://projects.spring.io/spring-data/) é um framework que abstrai o acesso ao modelo de dados, independente a tecnologia de base de dados.

 
# Setup da aplicação (local)

## Pré-requisito

Antes de rodar a aplicação é preciso garantir que as seguintes dependências estejam corretamente instaladas:
```
Java 8
PostgreSQL 9.6
MongoDB 3.5.6
Maven 3.3.3 
```

## Preparando ambiente

É necessário a criação da base de dados relacional no Postgres

```
CREATE DATABASE "productivity-with-spring";
```

Para os testes de integração também é necessario criar uma base de dados para os testes não interferirem na base de desenvolvimento.
```
CREATE DATABASE "productivity-with-spring-test";
```

Observação: No MongoDB não é preciso criar as bases de dados, pois a aplicação cria caso não exista.

## Instalação da aplicação

Primeiramente, faça o clone do repositório:
```
https://github.com/emmanuelneri/productivity-with-spring.git
```
Feito isso, acesse o projeto:
```
cd productivity-with-spring
```
É preciso compilar o código e baixar as dependências do projeto:
```
mvn clean package
```
Finalizado esse passo, vamos iniciar a aplicação:
```
mvn spring-boot:run
```
Pronto. A aplicação está disponível em http://localhost:8080
```
Tomcat started on port(s): 8080 (http)
Started AppConfig in xxxx seconds (JVM running for xxxx)
```

# Setup da aplicação com docker

## Pré-requisito

Antes de rodar a aplicação é preciso garantir que as seguintes dependências estejam corretamente instaladas:

```
Java 8
Docker 17.06.0 
Maven 3.3.3 
```

## Preparando ambiente

Criar e executar container do Posgres
```
 docker run -d \
    --name productivity-postgres \
    -e POSTGRES_DB=productivity-with-spring \
    -e POSTGRES_USER=postgres \
    -e POSTGRES_PASSWORD=postgres \
   postgres:9.6
```

Criar e executar container do MongoDB
```
docker run -d \
    --name productivity-mongodb \
   mongo:3.5
```

## Instalação da aplicação

Baixar as dependência e criar imagem da aplicação

```
mvn clean package -Dmaven.test.skip=true dockerfile:build
```

Executar container da aplicação

```
docker run -it \
    --link productivity-postgres  \
    --link productivity-mongodb  \
    -p 8080:8080 \
    emmanuelneri/productivity-with-spring-app 
```

Pronto. A aplicação está disponível em http://localhost:8080

# APIs

O projeto disponibiliza algumas APIs em 3 contextos diferentes: Customer, Carriers e BilLs, onde utilizam o padrão Rest de comunicação, produzindo e consumindo arquivos no formato JSON.

Segue abaixo as APIs disponíveis no projeto:

#### Customer

 - /customers/search (GET)
 - /customers (GET)
 - /customers/paged/{page}/{size} (GET)
 - /customers/search/pagenable (POST)
     - Espera atributos para serem critérios de busca no body da requisição, exemplo:
    ```
    {
      "name":"Customer"
    }
    ```

#### Carriers

 - /carriers (GET)
 
 #### Bills
 
  - /bills/{id} (DELETE)
  - /bills (GET)
  - /bills/byUk/{customerId}/{identifier}/{yearMonth} (GET)
  - /bills/{id} (GET)
  - /bills/search (POST)
    - Espera atributos para serem critérios de busca no body da requisição, exemplo:
      ```
         {
           "initYearMonth":"2017-01",
           "endYearMonth":"2017-07"
         }
         ```
  - /bills  (POST)
    - Espera as informações do modelo de dados Bill, exemplo:
        ```
             {
               "customer":{
                 "id":1,
                 "name":"Customer 1"
               },
               "carrier":{
                 "id":1,
                 "name":"TIM"
               },
               "identifier":"29302",
               "yearMonth":"2017-07",
               "items":[
                 {
                 "dataTime":"2017-07-01 15:30:00",
                 "description":"Ligação fora do plano",
                 "originNumber":"4499898484",
                 "destinationNumber":"1199848248",
                 "duration":"10",
                 "value":"50",
                 "type":"SERVICE"
                 },
                 {
                   "dataTime":"2017-07-15 00:00:00",
                   "description":"Pacote",
                   "originNumber":"4499898484",
                   "value":"50",
                   "type":"SERVICE"
                 }
               ],
               "total":70.00
             }
        ```                

#### File Bills

 - /files/bills (GET)
 - /files/bills (POST)
    - Espera as informações de Bill como um arquivo, exemplo:
    ```
    {"customer":{"id":1,"name":"Customer 1"},"carrier":{"id":1,"name":"TIM"},"identifier":"29302","yearMonth":"2017-07","items":[{"dataTime":"2017-07-01 15:30:00","description":"Ligação fora do plano","originNumber":"4499898484","destinationNumber":"1199848248","duration":"10","value":"50","type":"SERVICE"},{"dataTime":"2017-07-15 00:00:00","description":"Pacote","originNumber":"4499898484","value":"50","type":"SERVICE"}],"total":70.00}
    ```
