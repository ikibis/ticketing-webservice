package ru.kibis.ticketing.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kibis.ticketing.model.Hall;

import java.sql.*;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

public class DbStore {
    private static final Logger LOGGER = LogManager.getLogger(DbStore.class.getName());
    private static final BasicDataSource SOURCE = new BasicDataSource();
    private static final DbStore INSTANCE = new DbStore();

    private DbStore() {
        SOURCE.setUrl("jdbc:postgresql://127.0.0.1:5432/tracker");
        SOURCE.setUsername("postgres");
        SOURCE.setPassword("fastin");
        SOURCE.setDriverClassName("org.postgresql.Driver");
        SOURCE.setMinIdle(5);
        SOURCE.setMaxIdle(10);
        SOURCE.setMaxOpenPreparedStatements(100);
    }

    public static DbStore getInstance() {
        INSTANCE.createNewDB();
        return INSTANCE;
    }

    private void createNewDB() {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement st = connection.prepareStatement(
                     "drop table hall;"
                             + "create table if not exists hall ( "
                             + "id serial primary key not null, "
                             + "row smallint, "
                             + "place smallint, "
                             + "availability boolean);"

                             + "create table if not exists movie_viewers ( "
                             + "id serial primary key not null, "
                             + "name varchar(200), "
                             + "phone varchar, "
                             + "place_id smallint);"

                             + "insert into hall(row, place, availability) values (1, 1, 'true');"
                             + "insert into hall(row, place, availability) values (1, 2, 'true');"
                             + "insert into hall(row, place, availability) values (1, 3, 'true');"
                             + "insert into hall(row, place, availability) values (2, 1, 'true');"
                             + "insert into hall(row, place, availability) values (2, 2, 'true');"
                             + "insert into hall(row, place, availability) values (2, 3, 'true');"
                             + "insert into hall(row, place, availability) values (3, 1, 'true');"
                             + "insert into hall(row, place, availability) values (3, 2, 'true');"
                             + "insert into hall(row, place, availability) values (3, 3, 'true');"
             )) {
            st.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public boolean booking(int placeId, String name, String phone) {
        boolean result;
        Savepoint savePoint = null;
        try (Connection connection = SOURCE.getConnection()) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
            String query = "select availability from hall where id = " + placeId + "for update";
            try (Statement checkStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
                 PreparedStatement updateStatement = connection.prepareStatement(
                         "update hall set availability = false where id = ?;"
                 );
                 PreparedStatement insertStatement = connection.prepareStatement(
                         "insert into movie_viewers(name, phone, place_id) values(?, ?, ?);"
                 )
            ) {
                savePoint = connection.setSavepoint();
                ResultSet rs = checkStatement.executeQuery(query);
                connection.commit();
                rs.next();
                if (rs.getBoolean("availability")) {
                    updateStatement.setInt(1, placeId);
                    updateStatement.executeUpdate();

                    insertStatement.setString(1, name);
                    insertStatement.setString(2, phone);
                    insertStatement.setInt(3, placeId);
                    insertStatement.executeUpdate();

                    connection.commit();
                    result = true;
                } else {
                    connection.rollback(savePoint);
                    result = false;
                }
                connection.setTransactionIsolation(2);
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                result = false;
                connection.rollback(savePoint);
                connection.setTransactionIsolation(2);
                LOGGER.error(e.getMessage(), e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            result = false;
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

    public List<Hall> findPlaces() {
        List<Hall> places = new CopyOnWriteArrayList<>();
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement st = connection.prepareStatement(
                     "select * from hall;"
             )) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                places.add(new Hall(
                        rs.getInt("id"),
                        rs.getInt("row"),
                        rs.getInt("place"),
                        rs.getBoolean("availability")
                ));
            }
            Comparator<Hall> comp = (Hall a, Hall b) -> {
                return a.getPlaceId() - b.getPlaceId();
            };
            places.sort(comp);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return places;
    }

    public Hall getPlaceById(int id) {
        Hall result = null;
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement st = connection.prepareStatement(
                     "select * from hall where id = ?;"
             )) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                result = new Hall(
                        rs.getInt("id"),
                        rs.getInt("row"),
                        rs.getInt("place"),
                        rs.getBoolean("availability")
                );
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }
}