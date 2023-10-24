#!/bin/bash

echo "Gerando build dos microsserviços."

cd ./ms-feedbacks
mvn clean
mvn package

cd ..
cd ./ms-orders
mvn clean
mvn package

cd ..
cd ./ms-products
mvn clean
mvn package

cd ..
docker-compose up
