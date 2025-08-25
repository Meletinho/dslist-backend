# 📋 DSList - Gerenciamento de Listas de Jogos

<img width="307" height="260" alt="image" src="https://github.com/user-attachments/assets/eb003aed-928a-46eb-a001-d5b70186c143" />



![DSList Architecture](https://via.placeholder.com/800x400.png?text=DSList+Architecture+Diagram)
*Diagrama da arquitetura do projeto*

# 📖 Sobre o Projeto

O **DSList** é uma API RESTful completa para gerenciamento de listas personalizadas de jogos, permitindo operações de CRUD, reordenação inteligente de itens e consultas otimizadas. Desenvolvido com arquitetura em camadas e melhores práticas do Spring ecosystem.

# 🏗️ Arquitetura e Padrões

# Estrutura em Camadas
```
src/
├── 📁 controllers      # Camada de apresentação (Endpoints REST)
├── 📁 services         # Lógica de negócio e regras
├── 📁 repositories     # Camada de persistência (Spring Data JPA)
├── 📁 dtos             # Objetos de Transferência de Dados
├── 📁 entities         # Entidades JPA (Mapeamento ORM)
└── 📁 resources        # Configurações e data seeding
```

# Padrões Implementados
- **Arquitetura RESTful** - Endpoints sem estado e recursos bem definidos
- **Data Transfer Object (DTO)** - Isolamento da camada de apresentação
- **Transactional Operations** - Consistência nas operações de banco
- **Layer Separation** - Separação clara de responsabilidades
- **N-N Relationships** - Com classe de associação e embedded ID

# 🛠️ Tecnologias

| Tecnologia | Descrição | Logo |
|------------|-----------|------|
| **Spring Boot 3.2.0** | Framework principal da aplicação | ![Spring](https://via.placeholder.com/40x40/6DB33F/FFFFFF?text=S) |
| **Spring Data JPA** | Persistência e consultas de dados | ![Spring Data](https://via.placeholder.com/40x40/6DB33F/FFFFFF?text=SD) |
| **Spring Web MVC** | Construção de APIs REST | ![Spring Web](https://via.placeholder.com/40x40/6DB33F/FFFFFF?text=SW) |
| **H2 Database** | Banco em memória para desenvolvimento | ![H2](https://via.placeholder.com/40x40/1E90FF/FFFFFF?text=H2) |
| **PostgreSQL** | Banco de dados principal via Docker | ![PostgreSQL](https://via.placeholder.com/40x40/336791/FFFFFF?text=PG) |
| **Docker** | Containerização dos serviços | ![Docker](https://via.placeholder.com/40x40/2496ED/FFFFFF?text=D) |

# 📦 Modelo de Dados

![Modelo de Dados](https://via.placeholder.com/600x300.png?text=Game+-+GameList+-+Belonging+ER+Diagram)

```java
// Estrutura principal de entidades
Game ───────┐
            │ N
            ├── Belonging (EmbeddedId)
            │ 1
GameList ───┘
```

# Entidades Principais
- **Game**: Entidade com dados completos do jogo
- **GameList**: Representação de uma lista personalizada
- **Belonging**: Classe de associação com posicionamento (N-N com atributos)

# 🚀 Como Executar

# Pré-requisitos
- Java 17+
- Docker e Docker Compose
- Maven 3.6+

# 1. Clone e prepare o projeto
```bash
git clone <repositorio>
cd dslist
```

# 2. Execute com Docker
```bash
 Inicie os containers do banco
docker-compose up -d

 Execute a aplicação Spring Boot
mvn spring-boot:run
```

# 3. Acesse os serviços
- **API REST**: http://localhost:8080
- **PgAdmin**: http://localhost:5050 (credenciais: admin@dslist.com/admin)
- **PostgreSQL**: localhost:5433

![PgAdmin Interface](https://via.placeholder.com/600x300.png?text=PgAdmin+Interface)
*Interface do PgAdmin para gerenciamento do banco*

# 📡 Endpoints da API

# Listas de Jogos
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/lists` | Lista todas as coleções |
| `GET` | `/lists/{id}/games` | Jogos de uma lista específica |
| `POST` | `/lists` | Cria uma nova lista |
| `POST` | `/{listId}/replacement` | Move jogo entre posições |

# Jogos
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/games` | Lista todos os jogos |
| `GET` | `/games/{id}` | Detalhes de um jogo específico |

# 🔄 Funcionalidade de Reordenação

# Exemplo de Uso
```http
POST /lists/5/replacement
Content-Type: application/json

{
  "sourceIndex": 2,
  "destinationIndex": 5
}
```

# Fluxo da Operação
1. **Validação** dos índices de origem e destino
2. **Busca** da lista atual de jogos
3. **Remoção** do elemento na posição de origem
4. **Inserção** na posição de destino
5. **Atualização otimizada** apenas das posições afetadas
6. **Transação** para garantir consistência

![Reordering Process](https://via.placeholder.com/600x200.png?text=Reordering+Process+Flow)
*Fluxo do processo de reordenação*

```java
// Exemplo de implementação
@Transactional
public void move(long listId, int sourceIndex, int destinationIndex) {
    List<GameMinProjection> list = gameRepository.searchByList(listId);
    GameMinProjection obj = list.remove(sourceIndex);
    list.add(destinationIndex, obj);
    
    // Atualização eficiente apenas do range modificado
    int min = Math.min(sourceIndex, destinationIndex);
    int max = Math.max(sourceIndex, destinationIndex);
    
    for (int i = min; i <= max; i++) {
        gameListRepository.updateBelongingPosition(listId, list.get(i).getId(), i);
    }
}
```

# ⚡ Otimizações

# Projections para Consultas Otimizadas
```java
public interface GameMinProjection {
    Long getId();
    String getTitle();
    Integer getYear();
    String getImgUrl();
    String getShortDescription();
}
```

# Consultas Nativas para Performance
```java
@Modifying
@Query(nativeQuery = true, 
       value = "UPDATE tb_belonging SET position = :newPosition WHERE list_id = :listId AND game_id = :gameId")
void updateBelongingPosition(long listId, Long gameId, Integer newPosition);
```

# 🗃️ Configuração do Banco

# Perfil de Desenvolvimento (H2)
- Banco em memória
- Console em: `/h2-console`
- Data seeding automático

# Perfil de Produção (PostgreSQL)
- Container Docker na porta 5433
- Configuração via variáveis de ambiente
- Persistência de dados

![Database Configuration](https://via.placeholder.com/600x200.png?text=H2+vs+PostgreSQL+Configuration)
*Configuração de ambientes de banco de dados*

# 🌱 Database Seeding

O projeto inclui população automática de dados iniciais através do arquivo `import.sql`, contendo:

- Jogos populares com informações completas
- Listas temáticas pré-definidas
- Associações entre jogos e listas

# 📊 Exemplos de Consultas

# Busca por Projeção
```java
@Query(nativeQuery = true, value = """
    SELECT g.id, g.title, g.game_year AS year, g.img_url AS imgUrl, 
           g.short_description AS shortDescription, belonging.position
    FROM tb_game g
    INNER JOIN tb_belonging belonging ON g.id = belonging.game_id
    WHERE belonging.list_id = :listId
    ORDER BY belonging.position
""")
List<GameMinProjection> searchByList(Long listId);
```

# 🔧 Configurações Avançadas

# Variáveis de Ambiente
```properties
 Banco Principal
spring.datasource.url=jdbc:postgresql://localhost:5433/dslist
spring.datasource.username=postgres
spring.datasource.password=postgres

 H2 (Desenvolvimento)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

# Docker Compose
```yaml
version: '3.8'
services:
  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: dslist
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5433:5432"
  
  pgadmin:
    image: dpage/pgadmin4
    environment:
      PGADMIN_DEFAULT_EMAIL: admin@dslist.com
      PGADMIN_DEFAULT_PASSWORD: admin
    ports:
      - "5050:80"
```

# 📋 Melhores Práticas Aplicadas

1. **Separação de responsabilidades** entre camadas
2. **Transações** para operações atômicas
3. **Consultas otimizadas** com projections e native queries
4. **Validação** de entrada via DTOs
5. **Tratamento adequado** de códigos HTTP
6. **Configuração diferenciada** por ambiente
7. **Containerização** de dependências

# 🎯 Próximas Melhorias

- [ ] Autenticação e autorização com Spring Security
- [ ] Documentação automatizada com Swagger/OpenAPI
- [ ] Testes unitários e de integração
- [ ] Paginação e filtros avançados
- [ ] Cache de consultas frequentes
- [ ] Deployment configuration para cloud

---

**Desenvolvido** com ❤️e muito ÓDIO! 

![Spring Boot Logo](https://via.placeholder.com/100x40/6DB33F/FFFFFF?text=Spring+Boot)
