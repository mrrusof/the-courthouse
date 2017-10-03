FROM openjdk:8

ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /the-courtroom/
ADD https://raw.githubusercontent.com/mrrusof/the-bench/master/judge/script/run-judge.sh /the-courtroom/
RUN chmod +x /the-courtroom/*.sh
ENV PATH=$PATH:/the-courtroom/

RUN mkdir -p /tmp/download && \
    curl -L https://get.docker.com/builds/Linux/x86_64/docker-17.03.1-ce.tgz | tar -xz -C /tmp/download && \
    rm -rf /tmp/download/docker/dockerd && \
    mv /tmp/download/docker/docker* /usr/local/bin/ && \
    rm -rf /tmp/download

EXPOSE 8080

ADD src/main/resources/*.properties /the-courtroom/
ADD target/*.jar /the-courtroom/

ENTRYPOINT wait-for-it.sh the-law:5432 -- java -jar /the-courtroom/*.jar
