# java-omphaloskepsis

Java agent introspection via extension.

## backend

We run an otel collector and prometheus instance via `docker-compose`:

```bash
docker compose build
docker compose up
```

* Prometheus: http://localhost:9090/
* Grafana: http://localhost:3000/grafana/

## test app

petclinic tbd