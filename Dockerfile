FROM java:8

ADD target/falkor-api.jar .

EXPOSE 5000

CMD java -jar falkor-api.jar -m falkor.main
