# If the first argument is "run"...
ifeq (run,$(firstword $(MAKECMDGOALS)))
  # use the rest as arguments for "run"
  RUN_ARGS := $(wordlist 2,$(words $(MAKECMDGOALS)),$(MAKECMDGOALS))
  # ...and turn them into do-nothing targets
  $(eval $(RUN_ARGS):;@:)
endif

run:
	gradlew :run --args=$(RUN_ARGS)

prof_server:
	gradlew :run --args='--name=team4 --url=https://husksheets.fly.dev/ --password=IgUraDn4(kS>_-7>'

build:
	gradlew :build