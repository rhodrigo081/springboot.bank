# 🏦 API de Operações Financeiras 

## 💡 Sobre o Projeto

Este projeto consiste em uma API RESTful completa, desenvolvida com **Spring Boot 3 (Java)**, que simula as funcionalidades centrais de um banco digital moderno. O foco principal é a **segurança, atomicidade** das transações financeiras e a implementação de um sistema de **transferências instantâneas** similar ao Pix.

Demonstra proficiência na modelagem de domínio complexo e na construção de um backend robusto, escalável e de alta disponibilidade.

## ✨ Funcionalidades

A API foi estruturada para suportar o ciclo de vida completo das transações bancárias:

* **🔐 Autenticação e Usuários:**
    * Registro e Login
    * Gerenciamento de dados do usuário (CRUD).
* **💳 Operações de Conta:**
    * Consulta de saldo e detalhes da conta.
* **💸 Transações Financeiras (Atomicidade e Integridade):**
    * Depósitos Seguros 
    * Saques Seguros 
* **⚡ Transferências Instantâneas (Pix-like):**
    * Transferência de fundos entre contas 
* **🔑 Gerenciamento de Chaves Pix:**
    * Criação, listagem e vinculação de chaves Pix (CPF, Email, Telefone, Aleatória).

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 17+
* **Framework:** Spring Boot 3
* **Persistência:** Spring Data JPA
* **Banco de Dados:** PostgreSQL
* **Infraestrutura:** Docker & Docker Compose
* **Segurança:** Spring Security e JWT (JSON Web Tokens)
* **Documentação:** Springdoc OpenAPI (Swagger UI)

## 🚀 Como Rodar o Projeto

### Pré-requisitos

* Docker e Docker Compose Instalados

### Configuração

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/rhodrigo081/springboot.bank
    cd springboot.bank
    ```

2.  **Gere o pacote do projeto:**
    No terminal, execute o comando do Maven Wrapper:
    ```bash
    ./mvnw clean package -DskipTests
    ```

3.  **Suba os containers:**
    ```bash
    docker-compose up --build
    ```
    Este comando irá baixar a imagem do PostgreSQL, construir a imagem da aplicação e conectar ambos em uma rede isolada.
    
### A API estará rodando em `http://localhost:8080` (ou na porta configurada).

## 📄 Documentação da API (Swagger UI)

Após iniciar o projeto, a documentação interativa da API estará disponível em:

**`http://localhost:8080/swagger-ui/index.html`**

Utilize esta interface para testar todos os *endpoints* (registros, login e transações), incluindo a funcionalidade de gerenciamento de chaves Pix.
