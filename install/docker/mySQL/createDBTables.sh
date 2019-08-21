#!/bin/sh
docker cp ../../../SQL/ddlScripts.sql $MYSQL_NAME:/
docker exec $MYSQL_NAME /bin/sh -c "mysql -u${MYSQL_CUSTOM_USER} -p${MYSQL_CUSTOM_USER_PASSWORD} </ddlScripts.sql"
#docker exec $MYSQL_NAME /bin/sh -c mysql -u$MYSQL_CUSTOM_USER -p$MYSQL_CUSTOM_USER_PASSWORD </ddlScripts.sql
