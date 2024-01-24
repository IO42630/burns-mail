#!/bin/bash


mvn clean install &&

docker build -t io42630/burns-mail:0.1 . &&

docker compose up --force-recreate &




