package com.nhlstenden.swiftionapi.database.mysql;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQL {
    private final Dotenv env;
    private Connection conn;

    public MySQL() {
        this.env = Dotenv.load();
        this.conn = null;
    }

    /**
     * get a MySQL database connection
     *
     * @return open MySQL db connection
     */
    public Connection getConnection() {
        try {
            this.conn = DriverManager.getConnection(
                    String.format(
                            "jdbc:mariadb://%s:%s/%s?allowPublicKeyRetrieval=true&allowMultiQueries=true",
                            this.env.get("mysql_host"), this.env.get("mysql_port"),
                            this.env.get("mysql_database")
                    ),
                    this.env.get("mysql_username"),
                    this.env.get("mysql_password")
            );
        } catch (Exception e) {
            System.err.println(e);
        }
        return this.conn;
    }
}