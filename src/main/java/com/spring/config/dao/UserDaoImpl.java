package com.spring.config.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.spring.config.pojo.User;

@Component
public class UserDaoImpl implements UserDao {

	private JdbcTemplate jdbcTemplate;

	public UserDaoImpl(DataSource dataSoruce) {
		jdbcTemplate = new JdbcTemplate(dataSoruce);
	}

	public int create(User user) {

		String sql = "insert into user1(us_name,us_password,us_roles) values(?,?,?)";

		try {

			int counter = jdbcTemplate.update(sql,
					new Object[] { user.getUsername(), user.getPassword(), user.getRoles() });

			return counter;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public List<User> read() {
		List<User> userList = jdbcTemplate.query("SELECT * FROM user1", new RowMapper<User>() {

			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();

				user.setId(rs.getInt("us_id"));
				user.setUsername(rs.getString("us_name"));
				user.setPassword(rs.getString("us_password"));
				user.setRoles(rs.getString("us_roles"));

				return user;
			}

		});

		return userList;
	}

	public List<User> findUserById(int id) {

		List<User> userList = jdbcTemplate.query("SELECT * FROM user1 where us_id=?", new Object[] { id },
				new RowMapper<User>() {

					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						User user = new User();

						user.setId(rs.getInt("us_id"));
						user.setUsername(rs.getString("us_name"));
						user.setPassword(rs.getString("us_password"));
						user.setRoles(rs.getString("us_roles"));

						return user;
					}

				});

		return userList;
	}

	public int update(User user) {
		String sql = "update  user1 set us_name=?, us_password=?, us_roles=? where us_id=?";

		try {

			int counter = jdbcTemplate.update(sql,
					new Object[] { user.getUsername(), user.getPassword(), user.getRoles(), user.getId() });

			return counter;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int delete(int id) {

		String sql = "delete from user1 where stu_id=?";

		try {

			int counter = jdbcTemplate.update(sql, new Object[] { id });

			return counter;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

}
