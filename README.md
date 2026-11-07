# Project-JDBC-with-PostgreSQL-certificate-Java-studies

# JDBC Library

A study project for **plain JDBC** in Java, connecting to a **PostgreSQL** library database (books, users and loans). No Maven, no frameworks and no ORM: just `DriverManager`, `Statement` and `ResultSet`.

## What this project demonstrates

- Opening a connection with `DriverManager.getConnection(...)`
- Running a simple `SELECT` on the `livro` (book) table
- Reading a `ResultSet` row by row
- Manually creating `Livro` objects, filling them field by field
- Using `try-with-resources` to close the connection automatically

## Project structure

A simple project with all files in the root folder (no `src` folder):

```
.
├── Main.java        # connection, SELECT and printing the books
├── Livro.java       # model class (id, titulo, autor, anoPublicacao, disponivel)
├── biblioteca.sql   # table creation and sample data
└── README.md
```

## Data model

| Table        | Columns                                                                                                       |
|--------------|---------------------------------------------------------------------------------------------------------------|
| `livro`      | `id` (PK), `titulo`, `autor`, `ano_publicacao`, `disponivel`                                                  |
| `usuario`    | `id` (PK), `nome`, `email` (unique)                                                                           |
| `emprestimo` | `id` (PK), `livro_id` (FK), `usuario_id` (FK), `data_emprestimo`, `data_prevista_devolucao`, `data_devolucao` |

`data_devolucao` is `NULL` while the book has not been returned.

## Prerequisites

- [JDK](https://adoptium.net/) 11 or higher
- [PostgreSQL](https://www.postgresql.org/download/) running on `localhost:5432`
- [pgAdmin 4](https://www.pgadmin.org/) (optional, to run the script visually)
- [PostgreSQL JDBC driver](https://jdbc.postgresql.org/download/) (`.jar` file), placed in the same folder as the `.java` files

## How to run

### 1. Create the database and tables

In pgAdmin: create the `biblioteca` database, open the **Query Tool** on it and run `biblioteca.sql`.

Or from the terminal:

```bash
psql -U postgres -c "CREATE DATABASE biblioteca;"
psql -U postgres -d biblioteca -f biblioteca.sql
```

### 2. Configure the connection

In `Main.java`, adjust the constants to match your environment:

```java
static final String URL = "jdbc:postgresql://localhost:5432/biblioteca";
static final String USER = "postgres";
static final String PASSWORD = "your_password";
```

> **Note:** never commit your real password. The repository keeps the `your_password` placeholder.

### 3. Compile and run

The `*` in the classpath includes every `.jar` in the folder (including the driver).

Windows:

```bash
javac -cp ".;*" *.java
java -cp ".;*" Main
```

Linux/macOS:

```bash
javac -cp ".:*" *.java
java -cp ".:*" Main
```

## Expected output

```
Connection opened successfully!

Books found: 6
Livro{id=1, titulo='Dom Casmurro', autor='Machado de Assis', anoPublicacao=1899, disponivel=true}
...
```

## Next steps

- List users and loans
- Use `PreparedStatement` with parameters
- Implement `INSERT`, `UPDATE` and `DELETE`
- Write `JOIN` queries across `emprestimo`, `livro` and `usuario`
- Move data access into DAO classes
