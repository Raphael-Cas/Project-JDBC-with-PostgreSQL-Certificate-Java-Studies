# Project-JDBC-with-PostgreSQL-certificate-Java-studies

# JDBC Library

A small console application that manages a library catalog using **plain JDBC** and **PostgreSQL**. No Maven, no Spring Boot, no Hibernate/JPA: just the JDK and the PostgreSQL JDBC driver.

## Features

Menu-driven CRUD for books (`livro`):

- Register a book
- List all books
- Find a book by ID
- Update a book (press Enter to keep the current value of a field)
- Delete a book
- Exit

## Project structure

```
biblioteca
│
├── Main.java                         # console menu (Scanner); never touches the database
├── biblioteca.sql                    # tables and sample data
├── connection
│   └── ConnectionFactory.java        # opens connections: ConnectionFactory.getConnection()
├── entity
│   ├── Livro.java
│   ├── Usuario.java
│   └── Emprestimo.java
└── repository
    └── LivroRepository.java          # all SQL for the "livro" table (PreparedStatement)
```

How the layers talk to each other:

```
Main  ->  LivroRepository  ->  ConnectionFactory  ->  PostgreSQL
(menu)    (SQL / CRUD)         (opens connections)
```

## Data model

| Table        | Columns                                                                                                       |
|--------------|---------------------------------------------------------------------------------------------------------------|
| `livro`      | `id` (PK), `titulo`, `autor`, `ano_publicacao`, `disponivel`                                                  |
| `usuario`    | `id` (PK), `nome`, `email` (unique)                                                                           |
| `emprestimo` | `id` (PK), `livro_id` (FK), `usuario_id` (FK), `data_emprestimo`, `data_prevista_devolucao`, `data_devolucao` |

A book that is referenced by a loan cannot be deleted (foreign key). The application shows a friendly message in that case.

## Prerequisites

- [JDK](https://adoptium.net/) 11 or higher
- [PostgreSQL](https://www.postgresql.org/download/) running on `localhost:5432`
- [pgAdmin 4](https://www.pgadmin.org/) (optional)
- [PostgreSQL JDBC driver](https://jdbc.postgresql.org/download/) (`.jar`), placed in the project root folder

## Setup

### 1. Create the database and tables

In pgAdmin: create the `biblioteca` database, open the **Query Tool** on it and run `biblioteca.sql`.

Or from the terminal:

```bash
psql -U postgres -c "CREATE DATABASE biblioteca;"
psql -U postgres -d biblioteca -f biblioteca.sql
```

### 2. Configure the connection

`ConnectionFactory` reads three environment variables and falls back to defaults:

| Variable      | Default                                       |
|---------------|-----------------------------------------------|
| `DB_URL`      | `jdbc:postgresql://localhost:5432/biblioteca` |
| `DB_USER`     | `postgres`                                    |
| `DB_PASSWORD` | `your_password`                               |

Set your password in the terminal before running (it lasts until you close that terminal):

```bash
# Windows PowerShell
$env:DB_PASSWORD="your_real_password"

# Windows cmd
set DB_PASSWORD=your_real_password

# Linux / macOS
export DB_PASSWORD=your_real_password
```

This keeps your real password out of the source code and out of Git. Never commit it.

### 3. Compile and run

Windows:

```bash
javac -d out -cp "*" -sourcepath . Main.java
java -cp "out;*" Main
```

Linux/macOS:

```bash
javac -d out -cp "*" -sourcepath . Main.java
java -cp "out:*" Main
```

The `*` in the classpath includes every `.jar` in the folder (the driver), and `-d out` keeps the compiled `.class` files in a separate folder.

If accented characters look wrong in the Windows terminal, run `chcp 65001` before starting the application.

## Example

```
===== LIBRARY =====
1 - Register book
2 - List books
3 - Find book by ID
4 - Update book
5 - Delete book
0 - Exit
Choose an option: 2

--- Books ---
#1 | Dom Casmurro | Machado de Assis | 1899 | available
#2 | Memórias Póstumas de Brás Cubas | Machado de Assis | 1881 | on loan
...
```

## Good practices used

- **Separation of concerns:** menu, data access and connection logic live in different classes.
- **`PreparedStatement` everywhere:** all queries use parameters, which prevents SQL injection.
- **`try-with-resources`:** connections, statements and result sets are always closed.
- **No secrets in code:** credentials come from environment variables.
- **`Optional` for lookups:** `findById` makes the "not found" case explicit.

## Next steps

- `UsuarioRepository` and `EmprestimoRepository`
- Menu options to register and return loans (updating `livro.disponivel`)
- `JOIN` queries to list loans with book title and user name
- Transactions for operations that touch more than one table
