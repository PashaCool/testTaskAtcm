#!/usr/bin/env bash
docker stop db_ataccama_postgres
docker rm db_ataccama_postgres
docker-compose up --build -d