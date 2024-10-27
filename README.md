# API de Conta Bancária

## Descrição

Esta API permite criar, gerenciar e manipular contas bancárias. As operações incluem criação de contas, depósitos, saques, transferências e consulta de saldo.

## Documentação da API

Quando a aplicação estiver rodando, os endpoints da aplicação podem ser acessados através do [Swagger](http://localhost:8080/swagger-ui/index.html#/).

## Endpoints Disponíveis

### Resetar Banco de Dados

**Método:** POST  
**Endpoint:** `/reset`  
**Descrição:** Limpa qualquer dado anterior no sistema.

### Obter Saldo de uma Conta Existente

**Método:** GET  
**Endpoint:** `/balance`  
**Descrição:** Verifica o saldo atual de uma conta existente.

### Event

**Método:** POST  
**Endpoint:** `/event`  
**Descrição:** Manipula eventos baseados no tipo (`type`). Dependendo do valor de `type`, o comportamento pode variar:
- `deposit`: Realiza um depósito em uma conta.
- `withdraw`: Realiza um saque de uma conta.
- `transfer`: Transfere fundos de uma conta para outra.

## Modelos de Dados

### BankAccount

```java
@Entity
public class BankAccount {
    @Id
    private Long id;
    private Double balance;
    // Construtores, getters e setters
}
```
## Serviços

### BankAccountService

- `resetDatabase()`: Reseta o banco de dados.
- `getBalance(Long accountId)`: Obtém o saldo de uma conta.
- `deposit(Long id, Double amount)`: Realiza um depósito.
- `withdraw(Long id, Double amount)`: Realiza um saque.
- `transfer(Long originId, Long destination, Double amount)`: Realiza uma transferência.

## Controladores

### EventController

- `@PostMapping("/reset")`: Reseta o banco de dados.
- `@GetMapping("/balance")`: Obtém o saldo de uma conta.
- `@PostMapping("/event")`: Manipula eventos (depósitos, saques, transferências).

### Dependências

````xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.6.0</version>
    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>

<build>
<plugins>
    <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
            <excludes>
                <exclude>
                    <groupId>org.projectlombok</groupId>
                    <artifactId>lombok</artifactId>
                </exclude>
            </excludes>
        </configuration>
    </plugin>
</plugins>
</build>
````