package com.service

import com.dto.UserDTO
import com.exception.DatabaseException
import com.exception.CustomException
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DatabaseService {
    private val dataSource: HikariDataSource

    init {
        val config - HikariConfig().apply {
            jdbcUrl = System.getenv("DB_JAVA_URL") ?: throw IllegalStateException("DB_JAVA_URL is not specified")
            username = System.getenv("DB_USERNAME")?: throw IllegalStateException("DB_USERNAME is not specified")
            password = System.getenv("DB_PASSWORD")?: throw IllegalStateException("DB_PASSWORD is not specified")
            driveClassName = "com.mysql.cj.jdbc.Driver"
            maximumPoolSize = 3
        }
        dataSource = HikariDataSource(config)
    }

    fun login(username: String, password: String): UserDTO? {
        val query = "SELECT * FROM users WHERE username = ? AND password = ?"
        return try {
            dataSource.connection.use { connection ->
                connection.prepareStatement(query).use { statement ->
                    statement.setString(1, username)
                    statement.setString(2, password)
                    statement.executeQuery().use { resultSet ->
                        val userDTO: UserDTO? = if (resultSet.next()) UserDTO(resultSet.getString(1)) else null
                    }
                }
            }
        } catch (e: SQLException) {
            throw DatabaseException(e)
        }
    }
}
