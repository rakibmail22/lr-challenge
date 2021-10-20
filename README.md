# lr-challenge
The work in this repository demonstrate standard ways to write rest api using spring boot and data jpa with integration tests

## How To Run
##### Pre Requisite: Docker
1. navigate to repository root
2. Make the following scripts executable
`chmod +x dockerBuild.sh`, `chmod +x runProd.sh`
3. Build docker image `./dockerBuild.sh`
4. Run docker image `./runProd.sh`
5. The app can be accessed in the following url http://localhost:8080/swagger-ui/#/

## Work Log
### Phase 1 (Time Spent: 3 Hours)
#### Phase Deliverables
1. Project Structure with - JAVA 17, JUnit 5, Spring, Gradle
2. Functional Domain Model along with DB Structure
3. Configure separate db for dev and test environment
4. Integration tests to validate domain mappings
5. Configure Github project & CI script to use Github Action as CI Tool
#### Design Choices - Phase 1
1. Regular Spring MVC instead of Spring reactive webflux. 
   One driving factor of not choosing webflux is lack of reactive support in hibernate.
   As this project is time limited with very limited resource (Only one developer) I chose to use hibernate for the ease of domain
   mapping and query generation.
   
2. This being a simple poc for production I chose embedded H2 database for both dev, prod and test profile. In a real project
   for dev and prod profile a full fledged RDBMS system (like Oracle, Postgress or Mysql) should be used. For test profile
   embedded H2 is fine.
   
3. In case of database schema design, the relation between Item and Category is ManyToMany as I assumed an item can fall
   into a multiple category and vise versa. On the other hand the relation between `Attribute` and `Value` is kept as `OneToMany`.
   For this choice the value items would be repeated. It can be made reusable with mgirations 
   if the business requirement is explained more, and the data quality of attributes and its further usages
   are well comprehended later.
   
4. Currently `ItemType` is defined by hardcoded `Enum`. In an improvement it can be dynamic. Also, if we notice diverse data element
of different `ItemType` we can create separate tables for different item types with their specialized data. But this approach is needed 
   if bbusiness logic requires tight integration with in features for separate item types.

### Phase 2 (Time Spent: 5 Hours)
#### Phase Deliverables
1. API Endpoint according to business specification
2. Integration test for Endpoint Controllers
3. API validation and exception handling
4. Added request param logging interceptor

#### Design Choices - Phase 2
1. Used Spring Data JPA as repository
2. There is a scope for improvement while `Category` and `Item` but initially to keep the implementation simple didn't
use any raw sql or jpql directly. Need to monitor constantly and if we see Hibernate generating excessive select queries for
selecting the child elements we can replace Hibernate Object loading with optimized jpql
3. The response of API endpoints could be more decorated and beautified. Also, a lot more business validation's and formatted 
validation message could be added. We can use HATEOAS for decorate the API response further and make it more descriptive.
4. API documentation can be improved a lot by adding more Swagger config
5. Logging can be improved a bit
6. All the API endpoint should be protected (ideally with jwt / oauth token). But this should fall into a much broader scope
in terms of both authentication and authorization
7. CORS origin request should be restricted and api should be able to connect from a specific origin 
(if not prepared to distribute in mass population). Spring Security has a lot of simple ways to add these restriction.
8. Another approach of resource create/update/delete would be make these process asynchronous in background and provide client
with a `HTTP.ACCEPTED` status instead of `HTTP.OK` to let them know the request would be processed shortly. But this overall
approach goes very well with event driven architecture. Spring webflux along with Axon
framework are two of the nicest tools to develop scalable event driven app.
9. API field names can be improved & Repository test cases can be improved a lot

### Phase 3 (Time Spent: 1.5 Hours)
#### Phase Deliverables
1. Layered Docker image building script with exploded jar. This layered images are easy to update
when change in code or dependency occurs. 
2. Docker container run script for prod profile with demonstration of property override
on runtime for sensitive information
#### Design Choices - Phase 3
1. There are other approaches to override sensitive information. Like if we deploy our application
in kubernetes cluster we can configure secrets from there to use as runtime environment variable that
eventually will override the existing config, also we can pass external config file explicitly while
deploying spring boot jar / exploded jar.