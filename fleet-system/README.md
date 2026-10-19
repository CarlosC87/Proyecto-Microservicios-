# Sistema de Trazabilidad y Perfil de Flota (API Composite)

Tres microservicios Spring Boot (Java 21):

| Servicio | Puerto | Stack | Base de datos |
|---|---|---|---|
| `vehicle-service` | 8081 | Spring MVC + JPA | PostgreSQL |
| `driver-service` | 8082 | Spring MVC + Spring Data MongoDB | MongoDB |
| `fleet-composite-service` | 8080 | Spring WebFlux + WebClient | — (stateless) |

> Cada carpeta es un proyecto Maven independiente, pensado para vivir en **su propio repositorio**.

## Arranque rápido (Docker)

```bash
docker compose up --build
```

Los servicios insertan datos de ejemplo (`veh-001` y `drv-552`) al iniciar.

```bash
# Endpoint consolidado (el que consume la web)
curl http://localhost:8080/api/v1/fleet-insights/vehicles/veh-001

# Vehículo sin conductor asignado -> 200 con "driver": null
curl -X POST http://localhost:8081/vehicles -H "Content-Type: application/json" \
  -d '{"id":"veh-002","plate":"XYZ-9876","model":"EcoVan","batteryStatusPercentage":60}'
curl http://localhost:8080/api/v1/fleet-insights/vehicles/veh-002

# Vehículo inexistente -> 404
curl -i http://localhost:8080/api/v1/fleet-insights/vehicles/nope

# Servicio caído -> 503
docker compose stop driver-service
curl -i http://localhost:8080/api/v1/fleet-insights/vehicles/veh-001
```

## Arranque local (sin Docker para las apps)

```bash
docker compose up postgres mongo          # solo las bases de datos
cd vehicle-service && mvn spring-boot:run
cd driver-service && mvn spring-boot:run
cd fleet-composite-service && mvn spring-boot:run
```

## Pruebas

```bash
cd fleet-composite-service && mvn test
```

## Comportamiento del composite

| Situación | Respuesta |
|---|---|
| Vehículo y conductor encontrados | 200 con JSON consolidado |
| Vehículo sin conductor asignado | 200 con `"driver": null` |
| Vehículo inexistente | 404 |
| Servicio caído / timeout / 5xx | 503 (tras 2 reintentos con backoff) |

## Diagramas

Cada servicio tiene los suyos en `docs/architecture.md` (Mermaid).
