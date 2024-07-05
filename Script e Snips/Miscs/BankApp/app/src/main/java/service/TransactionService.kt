package com.service

import com.dto.TransactionDTO
import com.exception.CSVGenerationException
import com.exception.CustomException
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.sql.SQLException

class TransactionService {

    private val databaseService = DatabaseService()

    fun getTransactions(): List<TransactionDTO> {
        val query = "SELECT * FROM transactions"
        return try {
            databaseService.dataSource.connection.use { connection ->
                connection.prepareStatement(query).use { statement ->
                    statement.executeQuery().use { rs ->
                        generateSequence {
                            if (rs.next()) {
                                TransactionDTO(
                                    rs.getString("date"),
                                    rs.getString("description"),
                                    rs.getDouble("amount")
                                )
                            } else {
                                null
                            }
                        }.toList()
                    }
                }
            }
        } catch (e: SQLException) {
            throw CustomException("Database error during transaction retrieval", e)
        }
    }

    fun generateCSV(transaction: List<TransactionDTO>) {
        val file = File("transactions.csv")
        try {
            file.createNewFile()
            val writer = FileWriter(file)
            writer.append("date,description,amount\n")
            transaction.forEach {
                writer.append("${it.date},${it.description},${it.amount}\n")
            }
            writer.flush()
            writer.close()
        } catch (e: IOException) {
            throw CustomException("Error during CSV generation", e)
        }
    }
}