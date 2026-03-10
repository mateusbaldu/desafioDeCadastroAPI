# Desafio de Cadastro API

API RESTful de cadastro de pets desenvolvida com **Spring Boot 4** e **Java 21**, como resposta ao desafio proposto pelo [@devmagro](https://www.linkedin.com/in/karilho/).

---

## Sobre o Projeto

Uma API completa para gerenciamento de cadastro de pets (cães e gatos), incluindo informações detalhadas como nome, raça, idade, peso, sexo e endereço. A aplicação oferece operações completas de CRUD, busca com filtros dinâmicos e validações customizadas.

---

## Tecnologias Utilizadas

| Tecnologia | Versão | Descrição |
|---|---|---|
| **Java** | 21 | Linguagem principal |
| **Spring Boot** | 4.0.3 | Framework backend |
| **PostgreSQL** | Latest | Banco de dados relacional |
| **Flyway** | - | Migrações de banco de dados |
| **MapStruct** | 1.6.3 | Mapeamento de DTOs |
| **Lombok** | - | Redução de boilerplate |
| **Docker** | - | Containerização |
| **Maven** | - | Gerenciamento de dependências |
| **pgAdmin** | Latest | Interface de administração do banco |

---

## Endpoints da API

> **Base Path:** `/desafio-cadastro`

### Pets — `/pets`

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/pets?petType={CAT\|DOG}` | Buscar pets com filtros |
| `POST` | `/pets` | Cadastrar novo pet |
| `PUT` | `/pets` | Atualizar pet existente |
| `DELETE` | `/pets/{id}` | Remover pet por ID |

#### Parâmetros de Busca (`GET /pets`)

| Parâmetro | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| `petType` | `CAT` / `DOG` | Sim | Tipo do pet |
| `name` | String | Não | Nome do pet |
| `sex` | `MALE` / `FEMALE` | Não | Sexo do pet |
| `age` | Float | Não | Idade do pet |
| `weight` | Float | Não | Peso do pet |
| `race` | String | Não | Raça do pet |
| `city` | String | Não | Cidade do endereço |
| `street` | String | Não | Rua do endereço |

#### Exemplo de Request — `POST /pets`

```json
{
  "name": "Rex",
  "petType": "DOG",
  "petSex": "MALE",
  "age": "3",
  "weight": "12.5",
  "race": "Labrador",
  "address": {
    "city": "São Paulo",
    "street": "Rua das Flores",
    "number": "123",
    "complement": "Apto 4B",
    "district": "Centro"
  }
}
```

#### Exemplo de Response

```json
{
  "id": 1,
  "name": "Rex",
  "petType": "DOG",
  "petSex": "MALE",
  "age": 3.0,
  "weight": 12.5,
  "race": "Labrador",
  "address": {
    "id": 1,
    "petId": 1,
    "city": "São Paulo",
    "street": "Rua das Flores",
    "number": "123",
    "complement": "Apto 4B",
    "district": "Centro"
  }
}
```

---

### Endereços — `/addresses`

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/addresses` | Cadastrar novo endereço |
| `GET` | `/addresses/{id}` | Buscar endereço por ID |
| `PUT` | `/addresses` | Atualizar endereço existente |
| `DELETE` | `/addresses/{id}` | Remover endereço por ID |

---

## Como Executar

### Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- [Docker](https://www.docker.com/) e [Docker Compose](https://docs.docker.com/compose/)
- (Opcional) [Java 21](https://adoptium.net/) e [Maven](https://maven.apache.org/) para execução local sem Docker

### Passo a passo

**1.** Clone o repositório:

```bash
git clone https://github.com/mateusbaldu/desafioDeCadastroAPI.git
cd desafioDeCadastroAPI
```

**2.** Crie um arquivo `.env` na raiz do projeto com as variáveis de ambiente:

```env
POSTGRES_USER=seu_usuario
POSTGRES_PW=sua_senha
POSTGRES_DB=cadastro_db
PGADMIN_MAIL=admin@admin.com
PGADMIN_PW=admin
```

**3.** Suba os containers com Docker Compose:

```bash
docker compose up --build
```

**4.** Aguarde todos os serviços iniciarem. Você verá os logs de inicialização do Spring Boot no terminal.

**5.** Acesse os serviços:

- **API:** `http://localhost:8080/desafio-cadastro`
- **pgAdmin:** `http://localhost:5050`
- **PostgreSQL:** `localhost:5432`
- **Debug (JVM):** `localhost:5005`

**6.** Para parar a aplicação, pressione `Ctrl+C` no terminal ou execute:

```bash
docker compose down
```

---

## Testes

O projeto inclui testes unitários para os serviços. Para executar:

```bash
cd cadastro
./mvnw test
```

---

## Banco de Dados

As migrações do banco são gerenciadas pelo **Flyway** e executadas automaticamente ao iniciar a aplicação:

| Migração | Descrição |
|---|---|
| `V1__init.sql` | Criação das tabelas iniciais |
| `V2__update_pet_address_columns.sql` | Atualização das colunas de pet e endereço |
| `V3__add_unaccent_extension.sql` | Adição da extensão `unaccent` para buscas |


---

## Desafio Criado Por

### Lucas Carrilho - [@devmagro](https://www.linkedin.com/in/karilho/)

[![Twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://x.com/devmagro)
[![Instagram](https://img.shields.io/badge/Instagram-E4405F?style=for-the-badge&logo=instagram&logoColor=white)](https://instagram.com/devmagro)
[![YouTube](https://img.shields.io/badge/YouTube-FF0000?style=for-the-badge&logo=youtube&logoColor=white)](https://www.youtube.com/@devmagro)

---

## Licença

Este projeto foi desenvolvido como resposta a um desafio de programação. Sinta-se à vontade para usar como referência de estudo.
