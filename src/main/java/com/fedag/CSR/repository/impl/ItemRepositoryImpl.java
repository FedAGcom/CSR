package com.fedag.CSR.repository.impl;

import com.fedag.CSR.model.Item;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class ItemRepositoryImpl {

    private final String postgresqlUrl = "jdbc:postgresql://localhost:5432/postgres";

    private final String username = "postgres";

    private final String password = "admin";

    public void addAllUserItems(List<Item> list) {

        try (Connection con = DriverManager.getConnection(postgresqlUrl, username, password)) {
            con.setAutoCommit(false);
            PreparedStatement pstmt =
                    con.prepareStatement(
                            "INSERT INTO item (type, title, rare, quality, price, balance_id, pack_id, steam_id)" +
                                    " VALUES (?,?,?,?,?,?,?,?)");
            for (int i = 0; i < list.size(); i++) {
                pstmt.setString(1, list.get(i).getType());
                pstmt.setString(2, list.get(i).getTitle());
                pstmt.setString(3, list.get(i).getRare());
                pstmt.setString(4, list.get(i).getQuality());
                pstmt.setDouble(5, 0);
                pstmt.setInt(6, 1);
                pstmt.setInt(7, 1);
                pstmt.setString(8, list.get(i).getSteam_id());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            con.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
