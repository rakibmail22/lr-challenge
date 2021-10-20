FROM openjdk:17-alpine
RUN addgroup -S lrapp && adduser -S lrapp -G lrapp
USER lrapp:lrapp
ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.lforward.challenge.ChallengeApplication"]