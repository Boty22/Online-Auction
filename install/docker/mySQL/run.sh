#!/bin/sh
sh envVariables.sh
sh createMySQLImg.sh
echo "Sleeping for 30 seconds to wait for DB to initialize..."
sleep 30
sh createUser.sh
sh createDBTables.sh
docker exec -it $MYSQL_NAME mysql -u$MYSQL_CUSTOM_USER -p$MYSQL_CUSTOM_USER_PASSWORD
