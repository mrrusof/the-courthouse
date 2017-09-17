VER=0.0.1
SRC=$(shell find src -type f -a \! -name '*~' -a \! -name '.*')
IMAGE=the-courtroom
REPO=mrrusof/$(IMAGE)
TAG=latest

all: build

build: .build

target/the-courtroom-$(VER).jar: $(SRC)
	./mvnw package

.build: Dockerfile target/the-courtroom-$(VER).jar
	docker build -t $(REPO) .
	touch .build

push: build
	docker push $(REPO):$(TAG)

clean:
	docker rm -v --force `docker ps -a | grep $(IMAGE) | awk '{print $$1}'` || true
	docker image rm --force $(REPO) || true
	rm -rf .build

exec: build
	docker run --rm -ti --net=host -p 8080:8080 -v /var/run/docker.sock:/var/run/docker.sock $(REPO)

.PHONY: all build push clean exec
