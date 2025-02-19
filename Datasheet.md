# Datasheet dell’Infrastruttura di Runtime

## Web Server
| **Componente**   | **CPU** | **RAM** | **Storage (BASE)** | **Note** |
|------------------|--------|--------|------------------|----------|
| Apache 1        | 1 vCPU | 2 GB   | 40 GB           | Bilanciato, certificato SSL |
| Apache 2        | 1 vCPU | 2 GB   | 40 GB           | Bilanciato, certificato SSL |
| **Sistema Operativo** | **CentOS 7** | - | - | - |

## Application Server (Runtime Java 17)
| **Componente** | **CPU** | **RAM** | **Storage (BASE)** | **Storage (STaaS)** | **Note** |
|---------------|--------|--------|------------------|----------------|---------|
| VV1          | 1 vCPU | 4 GB   | 40 GB           | 10 GB          | Runtime Java 17 |
| VV2          | 1 vCPU | 4 GB   | 40 GB           | 10 GB          | Runtime Java 17 |
| **Sistema Operativo** | **CentOS 7** | - | - | - | - |

## Database
| **Componente**   | **Tipo**     | **CPU** | **RAM** | **Storage (BASE)** | **Storage (Dati)** |
|------------------|-------------|--------|--------|------------------|----------------|
| PostgreSQL 15   | Standard    | 1 vCPU | 4 GB   | 40 GB           | 20 GB |

## Networking & Sicurezza
- **Load Balancer**: Bilanciamento tra Apache 1 e Apache 2  
- **Firewall**: Configurato per la sicurezza delle connessioni  
- **VPN**: Accesso sicuro all’infrastruttura  

## Gestione e Monitoraggio
- **Logging & Monitoring**: Delegata alla gestione cloud  

---
