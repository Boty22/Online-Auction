mvn clean install -Dmaven.test.skip=true
docker stop utd-web-app
docker rm utd-web-app
docker build . -t utd-web-app
docker run -p 8086:8086 --name utd-web-app --network=utd-builders-network -d utd-web-app
