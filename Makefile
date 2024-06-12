prof_server:
	gradlew :run --args='--name=team4 --url=https://husksheets.fly.dev --password=IgUraDn4(kS>_-7>'

run_local:
	gradlew :run --args='--name=admin --url=http://localhost:8090 --password=admin123'

build:
	gradlew :build

test:
