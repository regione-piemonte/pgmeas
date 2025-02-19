
# Prodotto
La piattaforma informatica PGMEAS è uno strumento a disposizione di tutte le aziende sanitarie piemontesi per gestire in modo digitale gli investimenti in edilizia e attrezzature sanitarie in tutte le loro fasi: la programmazione degli interventi, la gestione del finanziamento e il relativo monitoraggio.

Grazie a un iter autorizzativo completo, la soluzione garantisce agli operatori delle AASSRR una governance rafforzata e una condivisione efficiente delle informazioni.
Le voci Programmazione, Gestione, Monitoraggio governano le azioni che si possono fare sugli interventi nelle varie fasi, in particolare, inserimenti, modifiche e visualizzazioni dei dati saranno attive in base ad eventuali dati già compilati sull’intervento.

La fase 'Programmazione' permette di gestire i dati (tempi, costi e beneficiari) relativi alla programmazione, la sua eventuale proroga e una serie di funzionalità sugli interventi.

La fase 'Gestione' permette di gestire i dati relativi all’ammissione finanziamento, aggiudicazione, liquidazioni, varianti, opere supplementari, utilizzo economie e rendicontazione finale degli interventi.

La fase 'Monitoraggio' permette di gestire i dati riepilogativi degli interventi (stato di avanzamento).

La funzionalità trasversale 'Ricerca Interventi' consente di ricercare gli interventi, e di visualizzarne i dati in sola lettura indipendentemente dallo stato dell’intervento e dalla fase nella quale si trova l’intervento.

# Descrizione del prodotto 
Il prodotto è composto attualmente dalle seguenti componenti 
| Componente |Descrizione  |Versione |
|--|--|--|
| pgmeasapigateway  | Gateway API che gestisce il routing delle richieste tra i vari servizi di PGMEAS | 1.0.0 |
| pgmeasapp         | Applicazione principale di PGMEAS per la gestione degli investimenti | 1.0.0 |
| pgmeascommons     | Modulo condiviso con funzionalità e librerie comuni utilizzate dagli altri componenti PGMEAS | 1.0.0 |
| pgmeasgdpr        | Modulo per la gestione della tracciatura degli accessi ai servizi | 1.0.0|
| pgmeasnotifier    | Sistema di notifiche per l'invio di avvisi e aggiornamenti agli utenti | 1.0.0 |
| pgmeasproject     | Modulo di gestione dei progetti e del monitoraggio dell'avanzamento degli investimenti | 1.0.0 |
| pgmeasregistry    | Registro centralizzato delle liste di decodifica ad uso della applicazione web | 1.0.0 |
| pgmeasdb          | SScript di inizializzazione del DB delle componenti di PGMEAS | 1.0.0 |


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