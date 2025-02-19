# Installing

## Compilazione

### Libreria pgmeascommons
La componente **pgmeascommons** può essere compilata con il tool **Maven** version: 3.8.1 e **JDK Adoptium Temurin 17**.  

Prima di procedere con l'installazione nel repository della libreria, è necessario impostare la versione con il seguente comando:  

```sh
mvn versions:set -DnewVersion=${p.version}  -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn -B -DskipTests=true
```

Dove `${p.version}` può assumere i seguenti valori:
- `1.0.0alfa`
- `1.0.0`

Successivamente, si può procedere con la compilazione utilizzando il comando:

```sh
mvn clean install -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn -B -DskipTests=true
```

### Componenti  di back-end
Le seguenti componenti possono essere compilate con **Maven** version: 3.8.1 e **JDK Adoptium Temurin 17**:  

- `pgmeasapigateway`  
- `pgmeasgdpr`  
- `pgmeasnotifier`  
- `pgmeasproject`  
- `pgmeasregistry`  

Le opzioni per l'esecuzione di Maven sono:

```sh
mvn dependency:purge-local-repository clean install -P ${environment} -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn -B -DskipTests=true -Dassembly.skipAssembly=false
```

Dove `${environment}` attiva uno dei seguenti profili:
- `test`: utilizza **pgmeascommons 1.0.0alfa**
- `prod`: utilizza **pgmeascommons 1.0.0**

### Componente di front-end
La componente **pgmeasapp** si compila con **npm**, con la versione di **Node.js 14.21.1**.  

Il comando per la build è:

```sh
npm run build -- -c prod-rp-01 --configuration=prod-rp-01 --output-path=./dist_arch
```

---

## Installazione

### Distribuzione delle componenti

#### Su server con Webserver Apache  
- Installazione di **pgmeasapp**  
- Configurazione del **ProxyPass** verso **pgmeasapigateway**  
- La componente **pgmeasapp** deve essere configurata come **context root** del VirtualHost di Apache.  

#### Server o cluster  per l'esecuzione del back-end
Le seguenti componenti devono essere installate su un **server** o un **cluster**:  

- `pgmeasapigateway`  
- `pgmeasgdpr`  
- `pgmeasproject`  
- `pgmeasregistry`  

La componente **pgmeasnotifier** deve invece essere installata su un **singolo nodo**.  

#### Database  
Gli script del **pgmeasDB** devono essere eseguiti su un'utenza **PostgreSQL**.

---

## Configurazione dei file application.properties


### `pgmeasapigateway`
| Proprietà | Valore di Default | Descrizione |
|-----------|------------------|-------------|
| `server.servlet.context-path` | `/pgmeasapigateway` | Path del contesto dell'applicazione |
| `application.title` | `pgmeasapigateway` | Nome dell'applicazione |
| `application.version` | `1.0.0` | Versione dell'applicazione |
| `server.port` | `9090` | Porta del server |
| `log.ProxyUrl.debugOn` | `true` | Attivazione debug per il proxy URL |
| `logging.file.path` | `/var/log/springboot/tst` | Percorso file di log |
| `management.endpoints.web.base-path` | `/actuator` | Path base per gli endpoint di gestione |
| `management.server.port` | `9000` | Porta per il server di gestione |
| `management.endpoint.health.probes.enabled` | `true` | Abilita le probe di health check |
| `IrideIdAdapterFilter.devmode` | `false` | Modalità di sviluppo per il filtro IrideIdAdapter |
| `configuratore.XCodiceServizio` | `PGMEAS` | Codice del servizio per il configuratore |
| `configuratore.auth.base64` | `xxx` | Credenziali base64 per l'autenticazione |
| `configuratore.base.url` | `xxx` | URL del servizio di configurazione |
| `configuratore.conn.timeout` | `10000` | Timeout di connessione (ms) |
| `download.base.path` | `/appserv/springboot/pgmeas/repository/` | Path base per il download dei file |
| `log.audit.uri` | `http://localhost:9050/pgmeasgdpr/api/audit/add` | URI per il log audit |
| `logging.level.it.csi.pgmeas.service.gateway.proxy` | `DEBUG` | Livello di logging per il proxy gateway |
| `management.endpoints.web.exposure.include` | `health,info,prometheus` | Endpoint esposti per la gestione |
| `routes.mock.regExp` | `api/(mock.*)` | Regex per il routing delle richieste mock |
| `routes.mock.targetHost` | `http://localhost:8080` | Host di destinazione per le richieste mock |
| `routes.mock.targetUri` | `/` | URI di destinazione per le richieste mock |
| `server.tomcat.max-swallow-size` | `100MB` | Dimensione massima per il payload delle richieste |
| `session.datasource.driverClassName` | `org.postgresql.Driver` | Driver del database |
| `session.datasource.embedded` | `false` | Indica se il database è embedded |
| `session.datasource.jdbc-url` | `xxx` | URL del database |
| `session.datasource.maximum-pool-size` | `10` | Numero massimo di connessioni nel pool |
| `session.datasource.password` | `xxx` | Password del database |
| `session.datasource.username` | `xxx` | Nome utente del database |
| `spring.cloud.config.enabled` | `false` | Abilita/disabilita Spring Cloud Config |
| `spring.servlet.multipart.max-file-size` | `10MB` | Dimensione massima dei file caricabili |
| `spring.servlet.multipart.max-request-size` | `10MB` | Dimensione massima della richiesta |



### `pgmeasproject`
| Proprietà | Valore di Default | Descrizione |
|-----------|------------------|-------------|
| `server.servlet.context-path` | `/pgmeasproject` | Path del contesto dell'applicazione |
| `application.title` | `pgmeasproject` | Nome dell'applicazione |
| `application.version` | `1.0.0` | Versione dell'applicazione |
| `server.port` | `9080` | Porta del server |
| `logging.file.path` | `/var/log/springboot/tst` | Percorso file di log |
| `management.endpoints.web.base-path` | `/actuator` | Path base per gli endpoint di gestione |
| `management.server.port` | `9980` | Porta per il server di gestione |
| `management.endpoint.health.probes.enabled` | `true` | Abilita le probe di health check |
| `IrideIdAdapterFilter.devmode` | `false` | Modalità di sviluppo per il filtro IrideIdAdapter |
| `download.base.path` | `/appserv/springboot/pgmeas/repository/` | Path base per il download dei file |
| `logging.level.org.hibernate.SQL` | `DEBUG` | Livello di logging per SQL Hibernate |
| `management.endpoints.web.exposure.include` | `health,info,prometheus` | Endpoint esposti per la gestione |
| `spring.cloud.config.enabled` | `false` | Abilita/disabilita Spring Cloud Config |
| `spring.datasource.driverClassName` | `org.postgresql.Driver` | Driver del database |
| `spring.datasource.hikari.connection-test-query` | `select 1` | Query di test per il pool di connessioni |
| `spring.datasource.hikari.maximum-pool-size` | `25` | Numero massimo di connessioni nel pool |
| `spring.datasource.hikari.minimum-idle` | `3` | Numero minimo di connessioni inattive |
| `spring.datasource.hikari.pool-name` | `pgmeasprojectLogPool` | Nome del pool di connessioni |
| `spring.datasource.password` | `xxx` | Password del database |
| `spring.datasource.url` | `xxx` | URL del database |
| `spring.datasource.username` | `xxx` | Nome utente del database |
| `spring.jpa.properties.hibernate.format_sql` | `true` | Formattazione SQL per Hibernate |
| `spring.jpa.show-sql` | `true` | Mostra SQL nelle log |
| `springdoc.swagger-ui.path` | `/swagger-ui.html` | Path della documentazione Swagger |



### `pgmeasregistry`
| Proprietà | Valore di Default | Descrizione |
|-----------|------------------|-------------|
| `server.servlet.context-path` | `/pgmeasregistry` | Path del contesto dell'applicazione |
| `application.title` | `pgmeasregistry` | Nome dell'applicazione |
| `application.version` | `1.0.0` | Versione dell'applicazione |
| `server.port` | `9070` | Porta del server |
| `logging.file.path` | `/var/log/springboot/tst` | Percorso file di log |
| `management.endpoints.web.base-path` | `/actuator` | Path base per gli endpoint di gestione |
| `management.server.port` | `9970` | Porta per il server di gestione |
| `management.endpoint.health.probes.enabled` | `true` | Abilita le probe di health check |
| `IrideIdAdapterFilter.devmode` | `false` | Modalità di sviluppo per il filtro IrideIdAdapter |
| `AbstractHttpConfigurer.disable` | `true` | Disabilita l'HttpConfigurer per Spring Security |
| `management.endpoints.web.exposure.include` | `health,info,prometheus` | Endpoint esposti per la gestione |
| `spring.cloud.config.enabled` | `false` | Abilita/disabilita Spring Cloud Config |
| `spring.datasource.driverClassName` | `org.postgresql.Driver` | Driver del database |
| `spring.datasource.hikari.connection-test-query` | `select 1` | Query di test per il pool di connessioni |
| `spring.datasource.hikari.maximum-pool-size` | `25` | Numero massimo di connessioni nel pool |
| `spring.datasource.hikari.minimum-idle` | `3` | Numero minimo di connessioni inattive |
| `spring.datasource.hikari.pool-name` | `pgmeasprojectLogPool` | Nome del pool di connessioni |
| `spring.datasource.password` | `xxx` | Password del database |
| `spring.datasource.url` | `xxx` | URL del database |
| `spring.datasource.username` | `xxx` | Nome utente del database |
| `spring.jpa.properties.hibernate.hbm2ddl.auto` | `validate` | Modalità di gestione dello schema Hibernate |

---


### Struttura di installazione delle componenti Java  
Le componenti Java possono essere installate secondo la seguente struttura:

```
[nomecomponente]/applicazione.jar
[nomecomponente]/config/application.properties
```

Il file `application.properties` deve essere configurato impostando i parametri in base all'ambiente di installazione.

---

## Esecuzione dell'applicazione  
Per avviare l'applicazione, eseguire il seguente comando:

```sh
java -jar /appserv/springboot/pgmeas/deployments/pgmeasproject/pgmeasproject-1.0.0.jar
```

Impostando come **directory di lavoro** `[nomecomponente]`.

