package ru.kibis.ticketing.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kibis.ticketing.model.Hall;
import ru.kibis.ticketing.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
                     "create table if not exists hall ( "
                             + "id serial primary key not null, "
                             + "row smallint, "
                             + "place smallint, "
                             + "availability boolean);"

                             + "create table if not exists movie_viewers ( "
                             + "id serial primary key not null, "
                             + "name varchar(200), "
                             + "phone smallint, "
                             + "place_id smallint);"
             )) {
            st.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public boolean booking(User user) {
        boolean result = false;
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement st = connection.prepareStatement(
                     "insert into movie_viewers(name, phone, place_id) values(?, ?, ?);"
                             + "update hall set availability = false where id = ?;"
             )) {
            st.setString(1, user.getName());
            st.setInt(2, user.getPhoneNumber());
            st.setInt(3, user.getPlace().getPlaceId());
            st.setInt(4, user.getPlace().getPlaceId());
            st.executeUpdate();
            result = true;
        } catch (SQLException e) {
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
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return places;
    }
}