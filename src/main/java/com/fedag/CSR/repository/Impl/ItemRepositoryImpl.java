package com.fedag.CSR.repository.Impl;

import com.fedag.CSR.model.Item;
import com.fedag.CSR.repository.ItemRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ItemRepositoryImpl {
    private final String postgresqlUrl = "jdbc:postgresql://localhost:5432/CSR";

    private final String username = "postgres";

    private final String password = "123";

    public void addAllUserItems(List<Item> list) {

        try (Connection con = DriverManager.getConnection(postgresqlUrl, username, password)) {
            con.setAutoCommit(false);
            PreparedStatement pstmt =
                    con.prepareStatement(
                            "INSERT INTO item (type, title, rare, quality, price, balance_id, pack_id, steam_id)" +
                                    " VALUES (?,?,?,?,?,?,?,?)");

            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getPrice() != null && list.get(i).getQuality() != null) {
                    pstmt.setString(1, list.get(i).getType());
                    pstmt.setString(2, list.get(i).getTitle());
                    pstmt.setString(3, list.get(i).getRare());
                    pstmt.setString(4, list.get(i).getQuality());
                    pstmt.setDouble(5, list.get(i).getPrice());
                    pstmt.setInt(6, 4);
                    pstmt.setInt(7, 1);
                    pstmt.setString(8, list.get(i).getSteamId());
                    pstmt.addBatch();
                }
            }
            pstmt.executeBatch();
            con.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
