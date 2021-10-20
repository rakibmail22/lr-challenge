# lr-challenge
The work in this repository demonstrate standard ways to write rest api using spring boot and data jpa with integration tests

## Work Log
### Phase 1 (Time Spent: 3 Hours)
#### Phase Deliverables
1. Project Structure with - JAVA 17, JUnit 5, Spring, Gradle
2. Functional Domain Model along with DB Structure
3. Configure separate db for dev and test environment
4. Integration tests to validate domain mappings
5. Configure Github project & CI script to use Github Action as CI Tool
#### Design Choices
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
   