# Simple Contenerized REST JPA Java Application

Build (wrapper Maven) :

```bash
./mvnw -P prod package
```

Résultat :
- `target/<artifactId>-<version>.jar` — JAR principal
- `target/libs/` — tous les JAR runtime nécessaires

Exécution locale (sans Docker - nécessite une base de données accessible) :

```bash
java -jar target/<artifactId>-<version>.jar
# ou explicitement
java -cp "target/libs/*:target/<artifactId>-<version>.jar" fr.univtln.bruno.demos.docker.Main
```

## Image de l'application

- L'image Docker est construite depuis le `Dockerfile` qui copie le JAR et le dossier `libs`.
- Le `Dockerfile` utilise la route `/restjpa/health` comme check de santé du conteneur.

```bash
# construire l'image
docker build -t restjpa:latest .
# ou via docker-compose
docker compose up --build
```

Important runtime / env vars

- `DB_URL` — JDBC URL (used by `persistence.xml` if provided)
- `DB_USER` — database user
- `DB_PASSWORD` — database password
- `DB_NAME` — used by compose example for the Postgres container
- `DB_SCHEMA_ACTION` — controls Hibernate DDL mode (defaults to `create` in `persistence.xml`); you can set to `create`/`none` as needed

## Orchestration avec Docker Compose

- Le fichier `docker-compose.yml` définit deux services : `db` (PostgreSQL) et `app` (notre application Java).
- Les variables d'environnement pour la connexion à la base de données sont configurées dans le service `app`.
    - `.env` fichier utilisé pour définir les valeurs par défaut des variables d'environnement.
- Le service `app` dépend du service `db`, assurant que la base de données est prête avant le démarrage de l'application.
- Le port `8088` du service `app` est mappé au port `8088` de l'hôte.

Pour démarrer les services :

```bash
docker compose up --build
```
Pour tester l'application une fois démarrée :

```bash
curl http://localhost:8088/restjpa/health

curl http://localhost:8088/restjpa/persons

curl -X POST http://localhost:8088/restjpa/persons -H "Content-Type: application/json" -d '{"name":"John Doe","age":30}'
```

