FROM oracle/graalvm-ce:19.2.1 as graalvm
RUN gu install native-image
WORKDIR /work
COPY ./build/libs/*-all.jar .
RUN native-image --no-server -cp *.jar -H:Name=app

FROM frolvlad/alpine-glibc
COPY --from=graalvm /work/app /app
ENTRYPOINT ["/app"]
