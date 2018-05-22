package com.antonina.health.repository;

import com.antonina.health.domain.Gender;
import com.antonina.health.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = new UserMapper();
    }

    public User findByEmailAndActiveTrue(String email) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from users where email=? and active is true", new Object[]{email}, userMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<User> findByNotifyHourAndActiveTrue(int hour) {
        return jdbcTemplate.query(
                "select * from users where notify_hour=? and active is true", new Object[]{hour}, userMapper);
    }

    public void insert(User user) {
        jdbcTemplate.update("INSERT INTO users(email, password, first_name, last_name, birth_date, gender, notify_hour, active)" +
                        "VALUES(?,?,?,?,?,?,?,?)", user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(),
                getBirthDate(user), getGender(user), user.getNotifyHour(), user.getActive());
    }

    public void update(User user) {
        jdbcTemplate.update("UPDATE users SET email=?, password=?, first_name=?, last_name=?, birth_date=?, gender=?, notify_hour=?, active=? WHERE id=?"
                , user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(),
                getBirthDate(user), getGender(user), user.getNotifyHour(), user.getActive(), user.getId());
    }

    private String getGender(User user) {
        Gender gender = user.getGender();
        String genderName = null;
        if (gender != null) {
            genderName = gender.name();
        }
        return genderName;
    }

    private String getBirthDate(User user) {
        LocalDate birthDate = user.getBirthDate();
        String birthDateFormat = null;
        if (birthDate != null) {
            birthDateFormat = birthDate.format(DateTimeFormatter.ISO_DATE);
        }
        return birthDateFormat;
    }

    public Optional<User> findById(long id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(
                    "select * from users where id=?", new Object[]{id}, userMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void delete(long id) {
        jdbcTemplate.update("update users set active = null where id=?", id);
    }

    @Component
    private static class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));

            Timestamp timestamp = rs.getTimestamp("birth_date");
            if (timestamp != null) {
                user.setBirthDate(timestamp.toLocalDateTime().toLocalDate());
            }
            String gender = rs.getString("gender");
            if (gender != null) {
                user.setGender(Gender.valueOf(gender));
            }
            user.setNotifyHour(rs.getInt("notify_hour"));
            user.setActive(rs.getBoolean("active"));
            return user;
        }
    }
}
