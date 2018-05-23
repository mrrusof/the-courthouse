FROM openjdk:8

RUN mkdir -p /tmp/download && \
    curl -L https://get.docker.com/builds/Linux/x86_64/docker-17.03.1-ce.tgz | tar -xz -C /tmp/download && \
    rm -rf /tmp/download/docker/dockerd && \
    mv /tmp/download/docker/docker* /usr/local/bin/ && \
    rm -rf /tmp/download

ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /the-courtroom/
ADD https://github.com/mrrusof/the-witness-stand/releases/download/0.3.0-2018.05.22/the-witness-stand-0.3.0-2018.05.22.tar.gz /
RUN tar -xzf /the-witness-stand-0.3.0-2018.05.22.tar.gz
RUN chmod +x /the-courtroom/*.sh
ENV PATH=$PATH:/the-courtroom/:/tws/bin/

EXPOSE 8080

ADD src/main/resources/*.properties /the-courtroom/
ADD target/*.jar /the-courtroom/

ENTRYPOINT wait-for-it.sh 127.0.0.1:5432 -- java -jar /the-courtroom/*.jar
