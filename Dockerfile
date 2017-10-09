FROM niaquinto/gradle:3.3

ENV ItunesResultLimit=5

ENV GoogleResultLimit=5

EXPOSE 8080

COPY ./ /home/gradle/project

WORKDIR /home/gradle/project


RUN gradle assemble

RUN printenv

#RUN gradle test

ENTRYPOINT java -jar ./build/libs//kramphub-v1.0.0-SNAPSHOT.jar