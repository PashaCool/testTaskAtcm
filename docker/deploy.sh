#!/usr/bin/env bash
docker stop db_ataccama_postgres external_product_db
docker rm db_ataccama_postgres external_product_db
docker-compose up --build -d