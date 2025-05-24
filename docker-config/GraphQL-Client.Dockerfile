FROM eclipse-temurin:24
RUN apt-get update -qq && apt-get install -y -qq curl jq && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY --chmod=755 docker-config/tests/*.sh .
COPY docker-config/tests/queries queries/
CMD ["bash"]