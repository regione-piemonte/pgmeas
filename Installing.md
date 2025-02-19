
# Prodotto


PGMEAS è la piattaforma digitale della Regione Piemonte per la programmazione, gestione e monitoraggio degli investimenti in edilizia e attrezzature sanitarie. È rivolta principalmente ai funzionari delle Aziende Sanitarie Regionali, che vi accedono tramite un sistema di autenticazione sicuro. La piattaforma consente di seguire l'intero iter autorizzativo, dalla programmazione alla rendicontazione tecnico-economica finale, garantendo una governance efficiente e un monitoraggio in tempo reale dello stato di avanzamento degli interventi.

[regione.piemonte.it](https://www.regione.piemonte.it/web/temi/sanita/strumenti-operativi/pgmeas-piattaforma-programmazione-gestione-monitoraggio-investimenti-edilizia-attrezzature-sanitarie)



# Descrizione del prodotto 
Il prodotto è composto attualmente dalle seguenti componenti 
| Componente |Descrizione  |Versione |
|--|--|--|
| **pgmeasapigateway**  | Gateway API che gestisce il routing delle richieste tra i vari servizi di PGMEAS | 1.0.0 |
| **pgmeasapp**         | Applicazione principale di PGMEAS per la gestione degli investimenti | 1.0.0 |
| **pgmeascommons**     | Modulo condiviso con funzionalità e librerie comuni utilizzate dagli altri componenti PGMEAS | 1.0.0 |
| **pgmeasgdpr**        | Modulo per la gestione della tracciatura degli accessi ai servizi | 1.0.0|
| **pgmeasnotifier**    | Sistema di notifiche per l'invio di avvisi e aggiornamenti agli utenti | 1.0.0 |
| **pgmeasproject**     | Modulo di gestione dei progetti e del monitoraggio dell'avanzamento degli investimenti | 1.0.0 |
| **pgmeasregistry**    | Registro centralizzato delle liste di decodifica ad uso della applicazione web | 1.0.0 |
| **pgmeasdb**          | SScript di inizializzazione del DB delle componenti di PGMEAS | 1.0.0 |


# Prerequisiti di sistema 

## Software
- [Apache 2.4](https://www.apache.org/)
- [OpenJDK 17 distribution from Adoptium](https://adoptium.net/temurin/releases/?version=17) 
- [PostgreSQL 15](https://www.postgresql.org/download/)
- [CentOS 7.6](https://www.centos.org/)

## Dipendenze da sistemi esterni


### Sistema di autenticazione
Il sistema di autenticazione su cui si basa il PGMEAS è esterno al presente prodotto ed è basato sul framework SHIBBOLETH composto da Service Provider e Identity Provider. 
Gli operatori che accedono ai servizi online della Sanità regionale piemontese si basano su 
- credenziali della PA Piemontese
- certificati digitali

### Sistema di profilazione 
Il sistema di profilazione  su cui si basa il PGMEAS  è esterno al presente prodotto ed è basato sul prodotto [LCCE](https://github.com/regione-piemonte/lcce)



# Installing

Vedi file [Installing.md](Installing.md)



# Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

# Authors
La lista delle persone che hanno partecipato alla realizzazione del software sono:
- ALCIATO Nives
- BOURLOT Gaia
- CALO' Nicolo' Saverio
- CHIAPPO Maurizio
- DALOISO Pasquale
- GAUDENZI Nicola
- IMBESI Maria Letizia
- IUNCO Giuliano
- LOFARO Antonino
- MANTOVANI Paola
- MASSA Simona
- MASTRORILLI Michele
- MERCURI Nicola
- TODARO Benedetta Alessia



# Copyrights
© Copyright Regione Piemonte – 2025


# License
EUPL-1.2 Compatibile
Vedere il file LICENSE.txt per i dettagli.