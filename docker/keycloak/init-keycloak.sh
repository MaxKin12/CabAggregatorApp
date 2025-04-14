#!/bin/bash

# Ждём, пока Keycloak станет доступен
until /opt/keycloak/bin/kcadm.sh config credentials \
  --server http://keycloak:9080 \
  --realm master \
  --user admin \
  --password $KEYCLOAK_PASSWORD &> /dev/null; do
  sleep 5
done

# Создание realm через API
/opt/keycloak/bin/kcadm.sh create realms -s realm=cab-aggregator -s enabled=true \
  --no-config --server http://keycloak:9080 --realm master \
  --user admin --password ${KEYCLOAK_PASSWORD}

# Настройка клиента
/opt/keycloak/bin/kcadm.sh create clients -r cab-aggregator \
  -s clientId=auth-service -s enabled=true \
  -s publicClient=false -s secret=${KEYCLOAK_CLIENT_SECRET}
