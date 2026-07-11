# League Finder Backend API

# 1. Overview 

  The League Finder backend exposes a RESTful API for the League Finder platform.

  The backend is build with Spring Boot, Spring Data JPA, PostgreSQL, and Maven. This application is deployed using Docker and Render. The PostgreSQL database is hosted live on Neon

  The API currently supports the league creation, the updates, retrieval, and the deletion.

  # Postman Link:
  http://localhost:8081

  # The Deployed Backend:
  https://league-finder-backendapi.onrender.com/

  # The Frontend Website:
  https://amariiscool1234.github.io/su-team6/index.html


# 2. Used Technologies
* Java
* SpringBoot
* Spring Data JPA
* PostgreSQL
* Maven
* Postman
* Docker
* Render



# 3. API Endpoints
3.1 League Endpoints
Get All Leagues

GET /leagues

Retrieves all leagues stored in the database.

Example request:

GET https://league-finder-backendapi.onrender.com/leagues

Example response:

[
  {
    "id": 1,
    "name": "336 Rangerz Updated",
    "sport": "Soccer",
    "location": "High Point",
    "description": "Updated league information"
  }
]
Create a League

POST /leagues

Creates a new league and stores it in the database.

Example request body:

{
  "name": "Greensboro Basketball League",
  "sport": "Basketball",
  "location": "Greensboro",
  "description": "Competitive basketball league for local players."
}

Example response:

{
  "id": 2,
  "name": "Greensboro Basketball League",
  "sport": "Basketball",
  "location": "Greensboro",
  "description": "Competitive basketball league for local players."
}
Update a League

PUT /leagues/{id}

Updates an existing league using its ID.

Example endpoint:

PUT /leagues/1

Example request body:

{
  "name": "336 Rangerz",
  "sport": "Soccer",
  "location": "High Point",
  "description": "Updated league information"
}
Delete a League

DELETE /leagues/{id}

Deletes an existing league using its ID.

Example endpoint:

DELETE /leagues/1

Example response:

League deleted successfully

Customer Endpoints
Retrieve All Customers

GET /customers

Retrieves all customer profiles stored in the database.

Example request:

GET https://league-finder-backendapi.onrender.com/customers

Example response:

{
  "id": 1,
  "name": "De'Saun Avent",
  "email": "desaun@example.com",
  "location": "Greensboro",
  "favoriteSport": "Basketball"
}
Retrieve a Customer by ID

GET /customers/{id}

Retrieves a specific customer profile using its ID.

Example request:

GET https://league-finder-backendapi.onrender.com/customers/1

Example response:

{
  "id": 1,
  "name": "De'Saun Avent",
  "email": "desaun@example.com",
  "location": "Greensboro",
  "favoriteSport": "Basketball"
}
Create a Customer

POST /customers

Creates a new customer profile and stores it in the database.

Example request body:

{
  "name": "De'Saun Avent",
  "email": "desaun@example.com",
  "location": "Greensboro",
  "favoriteSport": "Basketball"
}

Example response:

{
  "id": 1,
  "name": "De'Saun Avent",
  "email": "desaun@example.com",
  "location": "Greensboro",
  "favoriteSport": "Basketball"
}
Update a Customer

PUT /customers/{id}

Updates an existing customer profile using its ID.

Example endpoint:

PUT /customers/1

Example request body:

{
  "name": "De'Saun Avent",
  "email": "desaun@example.com",
  "location": "High Point",
  "favoriteSport": "Soccer"
}

Example response:

{
  "id": 1,
  "name": "De'Saun Avent",
  "email": "desaun@example.com",
  "location": "High Point",
  "favoriteSport": "Soccer"
}

Retrieve a Provider by ID

3.3 Getting Provider endpoints
GET /providers/{id}

Retrieves a specific provider profile using its ID.

Example request:

GET https://league-finder-backendapi.onrender.com/providers/1

Example response:

{
  "id": 1,
  "name": "Amari Ames",
  "email": "amari@example.com",
  "accountStatus": "active",
  "organizationName": "336 Rangerz",
  "biography": "Local sports league organizer."
}
Retrieve a Provider by Email

GET /providers/email/{email}

Retrieves a provider profile using its email address.

Example request:

GET https://league-finder-backendapi.onrender.com/providers/email/amari@example.com
Create a Provider

POST /providers

Creates a new provider profile and stores it in the database.

Example request body:

{
  "name": "Amari Ames",
  "email": "amari@example.com",
  "password": "password123",
  "accountStatus": "active",
  "organizationName": "336 Rangerz",
  "biography": "Local sports league organizer."
}

Example response:

{
  "id": 1,
  "name": "Amari Ames",
  "email": "amari@example.com",
  "password": "password123",
  "accountStatus": "active",
  "organizationName": "336 Rangerz",
  "biography": "Local sports league organizer."
}
Update a Provider

PUT /providers/{id}

Updates an existing provider profile using its ID.

Example endpoint:

PUT /providers/1

Example request body:

{
  "name": "Amari Ames",
  "email": "amari@example.com",
  "password": "newpassword123",
  "accountStatus": "active",
  "organizationName": "336 Rangerz Updated",
  "biography": "Organizer of local soccer and basketball leagues."
}

Example response:

{
  "id": 1,
  "name": "Amari Ames",
  "email": "amari@example.com",
  "password": "newpassword123",
  "accountStatus": "active",
  "organizationName": "336 Rangerz Updated",
  "biography": "Organizer of local soccer and basketball leagues."
}
Delete a Provider

DELETE /providers/{id}

Deletes an existing provider profile using its ID.

Example endpoint:

DELETE /providers/1

Example response:

Provider deleted successfully
# 4 UML Diagram

<img width="1536" height="1024" alt="UML diagram League Finder" src="https://github.com/user-attachments/assets/df0c1a4b-b0e8-45de-80ea-fe538f6fe208" />
#5 Use Case Model
<img width="1402" height="1122" alt="Use case model League Finder" src="https://github.com/user-attachments/assets/998727d2-20a4-4526-bd86-99224ac62af3" />
