package com.antonina.health.repository;

import com.antonina.health.domain.Result;
import com.antonina.health.repository.paging.Page;
import com.antonina.health.repository.paging.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
public class ResultRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ResultMapper resultMapper;

    public ResultRepository(JdbcTemplate jdbcTemplate, ResultMapper resultMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.resultMapper = resultMapper;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM results WHERE id=?", id);
    }

    public Optional<Result> findById(long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM results WHERE id = ?", new Object[]{id}, resultMapper));
    }

    public Page<Result> findByUserId(Long userId, PageRequest pageRequest) {
        String query = String.format("SELECT * FROM results WHERE user_id = ? ORDER BY %s %s LIMIT %d OFFSET %d",
                pageRequest.getSort().getProperty(),
                pageRequest.getSort().getDirection(),
                pageRequest.getSize(),
                pageRequest.getOffset());

        List<Result> content = jdbcTemplate.query(query,
                new Object[]{userId},
                resultMapper);

        int totalElements = jdbcTemplate.queryForObject("SELECT count(*) FROM results WHERE user_id=?",
                new Object[]{userId}, Integer.class);

        return new Page<>(content, totalElements, pageRequest);
    }

    public List<Result> findByUserIdOrderByDateTime(Long userId) {
        return jdbcTemplate.query("SELECT * FROM results WHERE user_id=? ORDER BY date_time",
                new Object[]{userId}, resultMapper);
    }

    public void insert(Result result) {
        jdbcTemplate.update("INSERT INTO results(user_id, date_time, pressure_sys, pressure_dia, temperature, mood) " +
                        "VALUES(?,?,?,?,?,?)", result.getUser().getId(), result.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME),
                result.getPressureSys(), result.getPressureDia(), result.getTemperature(), result.getMood());
    }

    public void update(Result result) {
        jdbcTemplate.update("UPDATE results SET user_id=?, date_time=?, pressure_sys=?, pressure_dia=?," +
                        " temperature=?, mood=? WHERE id=?",
                result.getUser().getId(), result.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME),
                result.getPressureSys(), result.getPressureDia(), result.getTemperature(), result.getMood(), result.getId());
    }

    @Component
    private static class ResultMapper implements RowMapper<Result> {
        private final UserRepository userRepository;

        public ResultMapper(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Override
        public Result mapRow(ResultSet rs, int rowNum) throws SQLException {
            Result result = new Result();
            result.setId(rs.getLong("id"));
            result.setUser(userRepository.findById(rs.getLong("user_id")).get());
            result.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
            result.setPressureSys(rs.getInt("pressure_sys"));
            result.setPressureDia(rs.getInt("pressure_dia"));
            result.setTemperature(rs.getBigDecimal("temperature"));
            result.setMood(rs.getInt("mood"));
            return result;
        }
    }
}