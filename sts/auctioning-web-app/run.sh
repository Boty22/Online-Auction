docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
docker network rm utd-builders-network
docker network create utd-builders-network
mvn clean install -Dmaven.test.skip=true
docker run --network=utd-builders-network --name mysql-standalone -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=auctionDB -e MYSQL_USER=sa -e MYSQL_PASSWORD=password -d mysql:5.6
docker run -p 5672:5672 -p 15672:15672 --network=utd-builders-network --name rabbitmq -d rabbitmq:management
echo "Sleeping for 30 seconds"
sleep 30
docker build . -t utd-web-app
docker run -p 8086:8086 --name utd-web-app --network=utd-builders-network -d utd-web-app
#docker run -v /target/utd-web-app.war:/usr/local/tomcat/webapps/utd-web-app.war --network=utd-builders-network -it -p 8080:8080 -d tomcat
