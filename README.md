# 🐶 Pet Clinic API

A REST API built to manage a veterinary clinic: registration of owners and their pets. Allows you to query, update, and remove both owners and animals, maintaining the relationship between them.

This is the fifth project in my Java and Spring Boot learning portfolio, focusing on bidirectional relationships between entities (`@OneToMany` / `@ManyToOne`) and DTOs that expose only summarized data from a related entity.

## 💡 Problem it solves

Veterinary clinics that control records manually or in a dispersed manner face problems such as:

- Losing the link between a pet and its owner
- Registering a pet without ensuring that the specified owner actually exists
- Lack of a centralized and searchable registry of owners and animals

The Pet Clinic API solves this by centralizing owner and pet registration, ensuring that every pet is always associated with a valid owner.

## 🛠️ Technologies used

- **Java 21**
- **Spring Boot**
- **Spring Web** (REST API)
- **Spring Data JPA**
- **MySQL**
- **Bean Validation** (input data validation)
- **Maven**

## 🏗️ Architecture

```
Controller → Service → Repository → Database
```

- **Controller**: receives HTTP requests and returns responses
- **Service**: contains business logic, including validation that the owner exists before registering a pet
- **Repository**: database communication
- **DTOs**: protect the API — the `PetResponseDTO` exposes only the owner's name (`ownerName`), without nesting the entire `Owner` object
- **Exceptions**: custom exceptions captured globally, ensuring consistent error responses

## 📦 Data model

### Owner
| Field | Type |
|---|---|
| id | Long |
| name | String |
| email | String |
| phone | String |
| pets | List\<Pet\> (`@OneToMany` relationship) |

### Pet
| Field | Type |
|---|---|
| id | Long |
| name | String |
| species | String |
| breed | String |
| age | Integer |
| owner | Owner (`@ManyToOne` relationship) |

## 🧠 Relationship

```
1 Owner
  ↓
  N Pets
```

- `Owner` → `@OneToMany(mappedBy = "owner")` → one owner can have multiple pets
- `Pet` → `@ManyToOne` → each pet belongs to exactly one owner

Unlike previous projects (which used an intermediate entity for N:N relationships), here the relationship is a direct 1:N chain — simpler to model, but this was my first time dealing with a **bidirectional** relationship: the list of pets is visible from the `Owner`, and the `owner` is visible from the `Pet`.

## 📌 Available endpoints

### Owners
| Action | Method | Route |
|---|---|---|
| Register owner | `POST` | `/owners` |
| List owners | `GET` | `/owners` |
| Get owner by ID | `GET` | `/owners/{id}` |
| Update owner | `PUT` | `/owners/{id}` |
| Delete owner | `DELETE` | `/owners/{id}` |

### Pets
| Action | Method | Route |
|---|---|---|
| Register pet | `POST` | `/pets` |
| List pets | `GET` | `/pets` |
| Get pet by ID | `GET` | `/pets/{id}` |
| Update pet | `PUT` | `/pets/{id}` |
| Delete pet | `DELETE` | `/pets/{id}` |

### Example — Register a pet

**POST** `/pets`

```json
{
  "name": "Rex",
  "species": "Dog",
  "breed": "Labrador",
  "age": 3,
  "ownerId": 1
}
```

**Response (201 Created)**

```json
{
  "id": 1,
  "name": "Rex",
  "species": "Dog",
  "breed": "Labrador",
  "age": 3,
  "ownerName": "Camila Souza"
}
```

> The response shows only the owner's name (`ownerName`), not the complete `Owner` object — avoiding unnecessarily large payloads and keeping the response focused on what matters for this endpoint.

## 🔥 Business rule

| Rule | Behavior |
|---|---|
| `ownerId` must exist to register a pet | `404 Not Found` |

## ⚠️ Error handling

The API uses custom exceptions, captured globally through `@RestControllerAdvice`:

- **`ResourceNotFoundException`** → returns `404 Not Found` (resource not found by ID)
- **`BusinessRuleException`** → returns `400 Bad Request` (reserved for future business rules)

## ✅ Validations

Input DTOs have validation via Bean Validation in required fields (`name`, `email`, `species`, among others), returning `400 Bad Request` when violated.

## ⚙️ How to run the project locally

### Prerequisites
- Java 21 installed
- MySQL running locally
- Maven (or use the `mvnw` included in the project)

### Steps

1. Clone the repository
```bash
git clone <repository-url>
```

2. Configure the database in the `src/main/resources/application.properties` file:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pet_clinic?createDatabaseIfNotExist=true
spring.datasource.username=YOUR_USER
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

> ⚠️ This file contains sensitive credentials and should not be versioned with real data in a production project.

3. Run the application:
```bash
./mvnw spring-boot:run
```

4. The API will be available at `http://localhost:8080`

### Testing

It is recommended to use [Postman](https://www.postman.com/) or [Insomnia](https://insomnia.rest/) to test the endpoints.

## 🗺️ Next steps (roadmap)

- [ ] `Appointment` entity (consultations), with relationship to `Pet`
- [ ] Endpoint to list all pets of a specific owner
- [ ] Authentication and authorization with Spring Security + JWT

## 📝 What I learned building this project

- Bidirectional relationship `@OneToMany` / `@ManyToOne`, and the role of `mappedBy` to indicate which side "owns" the relationship that controls the foreign key
- Response DTOs that expose only summarized data from a related entity (`ownerName`), instead of nesting the entire object
- Debugging a real synchronization issue between the Entity and the database schema: when an ID generation annotation is added after the table already exists, it is necessary to recreate the table for the change to take effect
- Reinforcement of separation of concerns between each Service — the rule "the owner must exist" belongs to `PetService`, not `OwnerService`

---

Developed by Petterson Oliveira as part of my Java and Spring Boot learning portfolio.
