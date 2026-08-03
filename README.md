# 🐶 Pet Clinic API
 
API REST desenvolvida para gerenciar uma clínica veterinária: cadastro de tutores (owners) e de seus pets. Permite consultar, atualizar e remover tanto tutores quanto animais, mantendo o vínculo entre eles.
 
Este é o quinto projeto do meu portfólio de estudos em Java e Spring Boot, com foco em relacionamento bidirecional entre entidades (`@OneToMany` / `@ManyToOne`) e DTOs que expõem apenas dados resumidos de uma entidade relacionada.
 
## 💡 Problema que resolve
 
Clínicas veterinárias que controlam cadastros de forma manual ou dispersa enfrentam problemas como:
 
- Perder o vínculo entre um pet e seu tutor
- Cadastrar um pet sem garantir que o tutor informado realmente existe
- Falta de um cadastro centralizado e consultável de tutores e animais
 
A Pet Clinic API resolve isso centralizando o cadastro de tutores e pets, garantindo que todo pet esteja sempre associado a um tutor válido.
 
## 🛠️ Tecnologias utilizadas
 
- **Java 21**
- **Spring Boot**
- **Spring Web** (REST API)
- **Spring Data JPA**
- **MySQL**
- **Bean Validation** (validação de dados de entrada)
- **Maven**
 
## 🏗️ Arquitetura
 
```
Controller → Service → Repository → Banco de Dados
```
 
- **Controller**: recebe requisições HTTP e devolve respostas
- **Service**: contém as regras de negócio, incluindo a validação de que o tutor existe antes de cadastrar um pet
- **Repository**: comunicação com o banco de dados
- **DTOs**: protegem a API — o `PetResponseDTO` expõe apenas o nome do tutor (`ownerName`), sem aninhar o objeto `Owner` inteiro
- **Exceptions**: exceções customizadas capturadas globalmente, garantindo respostas de erro consistentes
 
## 📦 Modelo de dados
 
### Owner
| Campo | Tipo |
|---|---|
| id | Long |
| name | String |
| email | String |
| phone | String |
| pets | List\<Pet\> (relacionamento `@OneToMany`) |
 
### Pet
| Campo | Tipo |
|---|---|
| id | Long |
| name | String |
| species | String |
| breed | String |
| age | Integer |
| owner | Owner (relacionamento `@ManyToOne`) |
 
## 🧠 Relacionamento
 
```
1 Owner
  ↓
  N Pets
```
 
- `Owner` → `@OneToMany(mappedBy = "owner")` → um tutor pode ter vários pets
- `Pet` → `@ManyToOne` → cada pet pertence a exatamente um tutor
 
Diferente dos projetos anteriores (que usavam uma entidade intermediária para relações N:N), aqui a relação é uma cadeia direta 1:N — mais simples de modelar, mas foi a primeira vez lidando com o relacionamento **bidirecional**: a lista de pets é visível a partir do `Owner`, e o `owner` é visível a partir do `Pet`.
 
## 📌 Endpoints disponíveis
 
### Owners
| Ação | Método | Rota |
|---|---|---|
| Cadastrar tutor | `POST` | `/owners` |
| Listar tutores | `GET` | `/owners` |
| Buscar tutor por ID | `GET` | `/owners/{id}` |
| Atualizar tutor | `PUT` | `/owners/{id}` |
| Deletar tutor | `DELETE` | `/owners/{id}` |
 
### Pets
| Ação | Método | Rota |
|---|---|---|
| Cadastrar pet | `POST` | `/pets` |
| Listar pets | `GET` | `/pets` |
| Buscar pet por ID | `GET` | `/pets/{id}` |
| Atualizar pet | `PUT` | `/pets/{id}` |
| Deletar pet | `DELETE` | `/pets/{id}` |
 
### Exemplo — Cadastrar pet
 
**POST** `/pets`
 
```json
{
  "name": "Rex",
  "species": "Cachorro",
  "breed": "Labrador",
  "age": 3,
  "ownerId": 1
}
```
 
**Resposta (201 Created)**
 
```json
{
  "id": 1,
  "name": "Rex",
  "species": "Cachorro",
  "breed": "Labrador",
  "age": 3,
  "ownerName": "Camila Souza"
}
```
 
> A resposta mostra apenas o nome do tutor (`ownerName`), não o objeto `Owner` completo — evitando payloads desnecessariamente grandes e mantendo a resposta focada no que interessa para esse endpoint.
 
## 🔥 Regra de negócio
 
| Regra | Comportamento |
|---|---|
| `ownerId` precisa existir para cadastrar um pet | `404 Not Found` |
 
## ⚠️ Tratamento de erros
 
A API utiliza exceções customizadas, capturadas globalmente através de `@RestControllerAdvice`:
 
- **`ResourceNotFoundException`** → devolve `404 Not Found` (recurso não encontrado pelo ID)
- **`BusinessRuleException`** → devolve `400 Bad Request` (reservada para futuras regras de negócio)
 
## ✅ Validações
 
Os DTOs de entrada possuem validação via Bean Validation nos campos obrigatórios (`name`, `email`, `species`, entre outros), retornando `400 Bad Request` quando violadas.
 
## ⚙️ Como rodar o projeto localmente
 
### Pré-requisitos
- Java 21 instalado
- MySQL rodando localmente
- Maven (ou usar o `mvnw` incluso no projeto)
 
### Passos
 
1. Clone o repositório
```bash
git clone <url-do-repositorio>
```
 
2. Configure o banco de dados no arquivo `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pet_clinic?createDatabaseIfNotExist=true
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.jpa.hibernate.ddl-auto=update
```
 
> ⚠️ Este arquivo contém credenciais sensíveis e não deveria ser versionado com dados reais em um projeto de produção.
 
3. Execute a aplicação:
```bash
./mvnw spring-boot:run
```
 
4. A API estará disponível em `http://localhost:8080`
 
### Testando
 
Recomenda-se o uso do [Postman](https://www.postman.com/) ou [Insomnia](https://insomnia.rest/) para testar os endpoints.
 
## 🗺️ Próximos passos (roadmap)
 
- [ ] Entidade `Appointment` (consultas), com relacionamento a `Pet`
- [ ] Endpoint para listar todos os pets de um tutor específico
- [ ] Autenticação e autorização com Spring Security + JWT
 
## 📝 O que aprendi construindo esse projeto
 
- Relacionamento bidirecional `@OneToMany` / `@ManyToOne`, e o papel do `mappedBy` para indicar qual lado "dono" da relação controla a chave estrangeira
- DTOs de resposta que expõem apenas um dado resumido de uma entidade relacionada (`ownerName`), em vez de aninhar o objeto inteiro
- Debug de um problema real de sincronização entre a Entity e o schema do banco: quando uma anotação de geração de ID é adicionada depois que a tabela já existe, é necessário recriar a tabela para a mudança ter efeito
- Reforço da separação entre a responsabilidade de cada Service — a regra "o tutor precisa existir" pertence ao `PetService`, não ao `OwnerService`
 
---
 
Desenvolvido por Petterson Oliveira como parte do meu portfólio de estudos em Java e Spring Boot.
