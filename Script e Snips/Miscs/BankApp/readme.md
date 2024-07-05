# Sistema de Login com Dashboard

Este projeto é um exemplo de aplicação Android desenvolvida em Kotlin que demonstra a implementação de um sistema de login com um dashboard. A aplicação utiliza princípios de programação orientada a objetos (POO), design patterns e boas práticas de desenvolvimento de software.

## Resumo

A aplicação é composta por um sistema de autenticação de usuários, onde os usuários podem fazer login e acessar um dashboard com informações geradas a partir de dados de um arquivo CSV. A arquitetura do sistema é modular, com foco em robustez, manutenção e escalabilidade.

### Funcionalidades

- **Tela de Login:** Permite que os usuários entrem com suas credenciais.
- **Dashboard:** Exibe informações extraídas e processadas a partir de um arquivo CSV.
- **Geração de CSV:** Cria um arquivo CSV com dados simulados.
- **Log de Erros:** Implementação de logging para monitoramento e depuração.

## Conceitos Aplicados

- **DTO (Data Transfer Object):** Utilizado para transferir dados entre camadas da aplicação.
- **Exceções Personalizadas:** Definição e uso de exceções específicas para a aplicação, como `CustomException`, `DatabaseException` e `CSVGenerationException`.
- **Injeção de Dependência:** Usada para fornecer instâncias de classes e promover um design desacoplado.
- **Logging:** Implementação de `log4j2` para registrar erros e eventos do sistema.
- **Programação Orientada a Objetos:** Aplicação dos princípios de POO, como encapsulamento, herança e polimorfismo.
- **Design Patterns:** Uso de padrões de projeto como Singleton e DAO (Data Access Object).
- **Programação Assíncrona:** Implementação de chamadas de rede e operações de I/O assíncronas para melhorar a performance da aplicação.

## Estrutura de Diretórios

A estrutura de diretórios do projeto é organizada da seguinte forma:

```mermaid
classDiagram
    %% Pacote de Autenticação
    class LoginActivity {
        +editTextUsername: EditText
        +editTextPassword: EditText
        +buttonLogin: Button
        +loginViewModel: LoginViewModel
        +onCreate(savedInstanceState: Bundle): Unit
        +onLoginButtonClick(): Unit
    }

    class LoginViewModel {
        +login(username: String, password: String): Unit
        -userRepository: UserRepository
    }

    class AuthenticationService {
        +authenticate(username: String, password: String): UserDTO
    }

    class UserRepository {
        +findUserByUsername(username: String): UserDTO
    }

    LoginActivity --> LoginViewModel : uses
    LoginViewModel --> AuthenticationService : uses
    AuthenticationService --> UserRepository : uses

    %% Pacote de Dashboard
    class DashboardActivity {
        +textViewUserInfo: TextView
        +buttonGenerateCSV: Button
        +buttonLogout: Button
        +dashboardViewModel: DashboardViewModel
        +onCreate(savedInstanceState: Bundle): Unit
        +onGenerateCSVButtonClick(): Unit
        +onLogoutButtonClick(): Unit
    }

    class DashboardViewModel {
        +generateCSV(): Unit
        -dashboardService: DashboardService
    }

    class DashboardService {
        +fetchTransactionData(): List<CSVDataDTO>
        +generateCSV(data: List<CSVDataDTO>, path: String): Unit
    }

    class CSVGenerator {
        +writeCSV(data: List<CSVDataDTO>, path: String): Unit
    }

    class CSVRepository {
        +saveCSVFile(path: String, data: List<CSVDataDTO>): Unit
    }

    DashboardActivity --> DashboardViewModel : uses
    DashboardViewModel --> DashboardService : uses
    DashboardService --> CSVGenerator : uses
    DashboardService --> CSVRepository : uses

    %% Pacote de Exceções
    class CustomException {
        +message: String
        +CustomException(message: String): Unit
    }

    class DatabaseException {
        +message: String
        +DatabaseException(message: String): Unit
    }

    class CSVGenerationException {
        +message: String
        +CSVGenerationException(message: String): Unit
    }

    CustomException --|> Exception
    DatabaseException --|> CustomException
    CSVGenerationException --|> CustomException

    %% Pacote de Utilitários
    class CSVUtils {
        +parseCSV(filePath: String): List<CSVDataDTO>
        +convertToDTO(resultSet: ResultSet): List<CSVDataDTO>
    }

    class EnvironmentConfig {
        +getDatabaseUrl(): String
        +getDatabaseUser(): String
        +getDatabasePassword(): String
        +getCSVFilePath(): String
    }

    CSVUtils : +parseCSV(filePath: String): List<CSVDataDTO>
    CSVUtils : +convertToDTO(resultSet: ResultSet): List<CSVDataDTO>
    EnvironmentConfig : +getDatabaseUrl(): String
    EnvironmentConfig : +getDatabaseUser(): String
    EnvironmentConfig : +getDatabasePassword(): String
    EnvironmentConfig : +getCSVFilePath(): String

    %% Pacote de Modelos
    class UserDTO {
        +username: String
        +password: String
    }

    class CSVDataDTO {
        +date: String
        +description: String
        +amount: Double
    }

    UserDTO : +username: String
    UserDTO : +password: String
    CSVDataDTO : +date: String
    CSVDataDTO : +description: String
    CSVDataDTO : +amount: Double

    %% Pacote de Configuração
    class AppConfig {
        +loadConfig(): Unit
    }

    AppConfig : +loadConfig(): Unit

    %% Associações
    CSVUtils -- CSVDataDTO : parses
    UserDTO --|> CSVDataDTO : shares data
    CSVGenerator --|> CSVDataDTO : writes
    CSVRepository --|> CSVGenerator : stores
    EnvironmentConfig --|> AppConfig : retrieves settings
    AppConfig --|> EnvironmentConfig : uses

```

