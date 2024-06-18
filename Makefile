prof_server:
	./gradlew :run --args='--name=team4 --url=https://husksheets.fly.dev --password=IgUraDn4(kS>_-7>'

prof_server_alice:
	./gradlew :run --args='--name=alice --url=https://husksheets.fly.dev --password=ert*hdu4GGwkw89'

run_local: run_server run_local_client

run_local_client:
	./gradlew :run --args='--name=admin --url=http://localhost:8090 --password=admin123'

run_local_alice:
	./gradlew :run --args='--name=alice --url=http://localhost:8090 --password=ert*hdu4GGwkw89'

run_server:
	./gradlew :runServer

build:
	./gradlew :build

test:
	./gradlew :test