#!/bin/bash

# Activer le journal pour Bind9
touch /var/log/named.log
chmod 666 /var/log/named.log

# Vérifier si les fichiers de configuration sont valides
named-checkconf /etc/bind/named.conf
if [ $? -ne 0 ]; then
    echo "La configuration DNS est invalide. Veuillez corriger les erreurs."
    exit 1
fi

# Démarrer le serveur DNS
echo "Démarrage du serveur DNS (Bind9)..."
named -g
