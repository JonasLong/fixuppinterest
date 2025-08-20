# Pinfix


## About

A simple webservice that allows url-shortened Pinterest links (like `https://pin.it/<some ID>`) to have their content automatically embedded on other social media sites. Comparable to fixup- and vx- sites for other social media.

This project was built from a SpringBoot REST template into a fully functioning dockerized application, complete with CI publishing in ~4 hours.


## Deploying

The service runs on port 8080, and responds to all http traffic regardless of hostname. *Please* only use this service behind a reverse proxy if exposing it to the public internet.

### Docker

- Run locally with `docker run ghcr.io/jonaslong/fixuppinterest:latest`
- Use a compose file (see the sample [docker-compose.yml](docker-compose.yml) file)
- Clone the repository, then build it for docker using `docker build -t pinfix/fixuppinterest .` (Requires BuildKit)


### Running locally

Requires Java 17
- Clone the repository to a local folder
- For testing changes locally, run `FixupPinterestApplication.java` with Java 17
- Compile into a jar using `./mvnw clean package`
