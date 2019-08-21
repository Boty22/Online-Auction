#/bin/sh
#Create image and run mySQL
docker stop $MYSQL_NAME
docker rm $MYSQL_NAME
#docker run --name $MYSQL_NAME -e MYSQL_ROOT_PASSWORD=$MYSQL_PASSWORD -d -p 3306:3306 mysql:latest
docker run --name $MYSQL_NAME -e MYSQL_ROOT_PASSWORD=$MYSQL_PASSWORD -d -p 3307:3306 --network="utdbuilders" mysql:5.5.61
#latest version is not compliant with spring/node

#=====================
#EXTRA
#=====================
#To SSH in:
#docker exec -it $MYSQL_NAME mysql -uroot -p$MYSQL_PASSWORD
#docker exec -it $MYSQL_NAME mysql -u$MYSQL_CUSTOM_USER -p$MYSQL_CUSTOM_USER_PASSWORD
#=====================
#=====================
#To run scripts
#docker cp ../../SQL/createUser.sql $MYSQL_NAME:/
#docker exec $MYSQL_NAME /bin/sh -c "mysql -u root -p$MYSQL_PASSWORD </createDatabase.sql"
#docker run --name $MYSQL_NAME -e MYSQL_ROOT_PASSWORD=$MYSQL_PASSWORD -d -p 3306:3306 mysql:latest
#docker run --name $MYSQL_NAME -e MYSQL_ROOT_PASSWORD=$MYSQL_PASSWORD -d mysql:5.6.41
