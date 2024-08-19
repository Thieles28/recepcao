## Descrição da Aplicação

Esta aplicação é um serviço RESTful desenvolvido em Java utilizando o Spring Boot, destinado ao gerenciamento de clientes. A aplicação se conecta a um banco de dados MySQL e utiliza JPA/Hibernate para realizar operações de persistência de dados. Além disso, a migração do banco de dados é gerenciada automaticamente pelo Flyway, garantindo que a estrutura do banco esteja sempre em sincronia com o código da aplicação.

### Configurações Principais

- **Nome da Aplicação**: `clientes`
- **Banco de Dados**: MySQL
  - **URL de Conexão**: `jdbc:mysql://localhost:3306/clientes?useSSL=false&serverTimezone=UTC`
  - **Usuário**: `root`
  - **Senha**: `1234`
  - **Driver JDBC**: `com.mysql.cj.jdbc.Driver`
- **JPA/Hibernate**:
  - **DDL-Auto**: `update` (Atualiza o esquema do banco de dados automaticamente com base nas entidades)
  - **Mostrar SQL**: `true` (Exibe as consultas SQL no console para depuração)
  - **Dialect do Hibernate**: `org.hibernate.dialect.MySQL8Dialect`
- **Flyway**:
  - **Migrações de Banco de Dados**: Ativado (`spring.flyway.enabled=true`)
- **Outras Configurações**:
  - **Contexto Base da API**: `/api/v1`
  - **Documentação Swagger**: A documentação da API gerada pelo SpringDoc Swagger UI está disponível em `/swagger-ui.html`.

### Recursos

- **Gerenciamento de Clientes**: APIs para criar, atualizar, listar e deletar clientes.
- **Persistência**: Uso do JPA/Hibernate para interação com o banco de dados MySQL.
- **Migração de Banco de Dados**: Flyway para controle de versões do banco de dados.
- **Documentação da API**: Acesso à documentação interativa da API via Swagger UI.

### Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/Thieles28/recepcao.git
   cd clientes
