FROM openjdk:8

ADD target/*.jar /the-courtroom/
ADD src/main/resources/*.properties /the-courtroom/

ADD https://raw.githubusercontent.com/mrrusof/the-bench/master/ruby/script/ruby-judge.sh /the-courtroom/
RUN chmod +x /the-courtroom/ruby-judge.sh
ENV PATH=$PATH:/the-courtroom/

RUN mkdir -p /tmp/download && \
    curl -L https://get.docker.com/builds/Linux/x86_64/docker-17.03.1-ce.tgz | tar -xz -C /tmp/download && \
    rm -rf /tmp/download/docker/dockerd && \
    mv /tmp/download/docker/docker* /usr/local/bin/ && \
    rm -rf /tmp/download

EXPOSE 8080

ENTRYPOINT java -jar /the-courtroom/*.jar
