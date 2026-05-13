# 📋 Gestão de Tarefas – API REST

API REST desenvolvida em **Java com Spring Boot** para gerenciamento de tarefas.  
O projeto permite criar, listar, atualizar, ordenar e excluir tarefas, aplicando **regras de negócio**, **validações**, **status HTTP corretos** e **padrão de resposta JSON**.

---

## 🚀 Funcionalidades

- Criar tarefas
- Listar tarefas com filtros
- Ordenação por data de criação e prioridade
- Atualizar tarefa completa
- Atualizar apenas o status da tarefa
- Excluir tarefa com regra de negócio
- Validações com Bean Validation
- Tratamento de erros padronizado

---

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Bean Validation (Jakarta Validation)
- Swagger / OpenAPI
- Maven
- H2 Database (desenvolvimento)

---

## 📂 Estrutura do Projeto

```bash
src/main/java
└── gestao.com.br.gestao_tarefas
├── Controller
├── Service
├── Repository
├── Entity
├── Dto
├── Enum
├── Exception
└── Response
```

---

## ▶️ Como Rodar o Projeto

### Pré-requisitos
- Java 17 ou superior
- Maven

### Passos

```bash
git clone https://github.com/seu-usuario/gestao-tarefas-api.git
cd gestao-tarefas-api
mvn spring-boot:run
```

### A aplicação estará disponível em:
```bash
http://localhost:8080
Swagger: http://localhost:8080/swagger-ui.html
```
---

## 🔗 Endpoints da AP

### ➕ Criar tarefa
- POST /tasks/create

### 📄 Listar tarefas
- GET /tasks
##### Filtros Opcionais:
- status
- id
- priority
- dueDate
- sort=createdAt,asc|desc
- sort=priority,asc|desc

### ✏️ Atualizar tarefa
- PUT /tasks/update

### 🔄 Atualizar apenas status
- PATCH /tasks/update-status

### 🗑️ Deletar tarefa
- DELETE /tasks/delete/{id_task}

---
## 📦 Exemplos de Payload

### Criar tarefa
```bash
    {
      "title": "Estudar Spring Boot",
      "description": "Praticar CRUD com validações",
      "status": "PENDING",
      "priority": "HIGH",
      "due_date": "2026-05-30"
    }
```

### Atualizar tarefa
```bash
    {
      "title": "Estudar Spring Boot",
      "description": "Praticar CRUD com validações",
      "status": "PENDING",
      "priority": "HIGH",
      "due_date": "2026-05-30"
    }
```

### Atualizar Apenas Status
```bash
    {
      "id_task": 1,
      "status": "COMPLETED"
    }
```
---

## ⚠️ Regras de Negócio
- Título deve conter no mínimo 3 caracteres
- Data limite não pode ser anterior à data atual
- Não é permitido excluir tarefa com status COMPLETED
- Uso correto de HTTP Status:
  - 201 Created
  - 200 OK
  - 204 No Content
  - 400 Bad Request
  - 404 Not Found
---

## 📑 Exemplo de Resposta Padronizada
```bash
    {
      "success": true,
      "message": "Task successfully deleted!",
      "path": "/tasks/delete/1",
      "status": 200,
      "timestamp": "2026-05-13T14:30:00"
    }
```
---

## 📌 Diferenciais Técnicos
- Arquitetura organizada e desacoplada
- DTOs específicos para cada operação
- Validações centralizadas
- Tratamento global de exceções
- Código limpo e escalável
- Documentação via Swagger
---

## 👨‍💻 Autor
#### Bruna Prudencio
📧 Email: prudenciobruh@gmail.com
💼 LinkedIn: https://linkedin.com/in/bruna-prudencio
📌 Portfólio: https://bruna.arbru.com.br/
---