#!/bin/sh
docker cp ../../../SQL/createUser.sql $MYSQL_NAME:/
#docker exec -it $MYSQL_NAME mysql -u$MYSQL_CUSTOM_USER -p$MYSQL_CUSTOM_USER_PASSWORD
docker exec $MYSQL_NAME /bin/sh -c "mysql -u root -p${MYSQL_PASSWORD} </createUser.sql"
