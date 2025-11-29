# Backend Eindopracht BakeHub

## Inhoudsopgave

1. [Inleiding](#Inleiding)
2. [Projectstructuur](#Projectstructuur)
3. [Gebruikte technieken](#Gebruikte-technieken)
4. [Benodigdheden](#Benodigdheden)
5. [Installatie](#Installatie)
6. [Tests](#Tests)
7. [Standaard gebruikers](#Standaard-gebruikers)

## Inleiding

Web-API BakeHub helpt thuisbakkers bij het vinden en organiseren van hun favoriete bakrecepten. Gebruikers kunnen nieuwe recepten ontdekken via categorieën en de zoekfunctie, recepten opslaan als favoriet en hun mening delen door middel van beoordelingen, recensies en foto's.

De web-API bevat de volgende kernfunctionaliteiten:

1.	Gebruikers en admins kunnen zich inloggen en registreren
2.	Gebruikers kunnen met behulp van categorieën en de zoekfunctie door recepten bladeren, en deze toevoegen aan hun favorieten 
3.	Gebruikers kunnen een recensie met foto en een sterbeoordeling achterlaten bij recepten
4.	Admins kunnen nieuwe categorieën en recepten toevoegen

## Projectstructuur
```
bakehub/
│
├── src/
│   ├── main/
│   │   ├── java/nl/maritoldenburger/bakehub/
│   │   │   ├── config/       
│   │   │   ├── controllers/
│   │   │   ├── dtos/
│   │   │   ├── enums/
│   │   │   ├── exceptions/
│   │   │   ├── filter/
│   │   │   ├── mappers/
│   │   │   ├── models/
│   │   │   ├── repositories/
│   │   │   ├── services/
│   │   │   └── utils/
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql
│   │
│   └── test/
│       ├── java/nl/maritoldenburger/bakehub/
│       │   ├── controllers/
│       │   └── services/
│       │
│       └── resources/
│           └── application-test.properties
│
├── pom.xml
└── README.md
```
## Gebruikte technieken

- **Java 21**
  - Spring Boot 3.5.7
  - Web
  - Data JPA
  - Validation
  - Security
  - Test
- **Maven**
- **PostgreSQL**
- **JJWT 0.11.5**
- **Hibernate 6.3.1**
- **H2**

## Benodigdheden

- **Java 21**
- **IntelliJ IDEA** of een andere IDE
- **PostgreSQL** en de bijbehorende database
- **pgAdmin**
- **Postman** of een vergelijkbare API-client

## Installatie

1. Zorg dat alle benodigdheden zijn geïnstalleerd.
2. Clone de repository.
    1. Open IntelliJ en ga naar **File** > **New** > **Project from Version Control...**
    2. Selecteer **Git** als versiebeheer en gebruik de volgende link:
    
   ```
    git@github.com:maritoldenburger/backend-eindopdracht-bakehub.git
    ```

    3. Wijzig eventueel de **directory**
    4. Klik op **clone**
3. Maak een database aan
     1. Open pgAdmin, klik met de rechtermuisknop op **Databases** en ga naar **Create** > **Database...**
     2. Vul **bakehub** in als naam voor de database en klik op **save**
4. Open in IntelliJ `application.properties` in de map `resources` en zorg ervoor dat de volgende waarden zijn ingesteld:
   ```
   spring.datasource.url=jdbc:postgresql://localhost:5432/bakehub
   spring.datasource.username=postgres
   spring.datasource.password=jouwwachtwoordhier
   ```

   Zorg dat bij `spring.datasource.password` het wachtwoord staat dat je hebt gekozen tijdens de installatie van PostgreSQL.

   Het instellen van het wachtwoord kan eventueel ook via een environment variable.
   Ga daarvoor naar **Run** > **Edit Configurations...** > **BakehubApplication** > **Environment** > **Environment variables** en vul daar onderstaande regel in:
   ```
   POSTGRESQL_PASSWORD=jouwwachtwoordhier
   ```

5. Start nu de applicatie door naar **Run** > **Run BakehubApplication** te gaan. De API is nu bereikbaar op: http://localhost:8080 en wordt automatisch gevuld met de testdata uit `data.sql`.

## Tests

Om de integratie- en unittests te draaien klik je met de rechtermuisknop op de map `src/test/java` (of op een specifieke testclass). Klik vervolgens op **run tests in 'java'**.

Voor het testen van de applicatie wordt gebruik gemaakt van de volgende tools:
- **Spring Boot Test**
- **JUnit 5**
- **Mockito**
- **H2**

## Standaard gebruikers

De volgende gebruikers zijn beschikbaar voor het testen van de web-API: 

| Role  | Username | Password |
| ------------- | ------------- | ------------- |
| USER  | annie_ananas  | test123  |
| USER  | bobbybanaan  | test123  |
| USER  | lolalimoen95  | test123  |
| ADMIN  | wendywatermeloen  | test123  |
| ADMIN  | fredframb00s  | test123  |
| ADMIN  | brambrownie  | test123  |

