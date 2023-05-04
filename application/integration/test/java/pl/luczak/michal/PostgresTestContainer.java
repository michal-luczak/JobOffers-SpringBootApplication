package pl.luczak.michal;

import org.testcontainers.containers.PostgreSQLContainer;

final class PostgresTestContainer extends PostgreSQLContainer<PostgresTestContainer> {

    private static final String IMAGE = "postgres:15.2";
    private static final String DATABASE_NAME = "job-offers";
    private static PostgreSQLContainer<PostgresTestContainer> container;

    PostgresTestContainer() {
        super(IMAGE);
    }

    static PostgreSQLContainer<PostgresTestContainer> getInstance() {
        if (container == null) {
            container = new PostgresTestContainer().withDatabaseName(DATABASE_NAME);
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.getProperty("POSTGRES_URL", container.getJdbcUrl());
        System.getProperty("POSTGRES_USERNAME", container.getUsername());
        System.getProperty("POSTGRES_PASSWORD", container.getPassword());
    }
}
