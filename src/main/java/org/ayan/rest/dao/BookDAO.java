package org.ayan.rest.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ayan.rest.dto.Book;
import org.ayan.rest.dto.BookRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookDAO {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired
	private JdbcTemplate jdbcTemplate2;

	private static Logger LOG = LogManager.getLogger(BookDAO.class);

	public int countBook() {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("vol", 1);
		String sql = "SELECT COUNT(*) FROM BOOK";
		int result = jdbcTemplate.queryForObject(sql, params, Integer.class);
		return result;
	}

	public void addBook(Book book) throws SQLException {
		LOG.info("Inside addBook");
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = "INSERT INTO BOOK(bookid, title, price, volume, publishDate) VALUES (:bookid, :title, :price, :volume, :publishDate)";
		params.addValue("bookid", book.getBookid());
		params.addValue("title", book.getTitle());
		params.addValue("price", book.getPrice());
		params.addValue("volume", book.getVolume());
		params.addValue("publishDate", book.getPublishDate());
		jdbcTemplate.update(sql, params);

	}

	public List<Book> selectBook(String title) {
		String finaltitle = "%" + title.toLowerCase().trim() + "%";
		LOG.info("Search string :: " + title);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("ttl", finaltitle);
		String sql = "SELECT * FROM BOOK WHERE LOWER(TITLE) like :ttl";
		LOG.info("Search query :: " + sql);
		return jdbcTemplate.query(sql, params, new BookRowMapper());

	}

	public int deleteBook(long bookid) {
		LOG.info("Inside deleteBook");
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = "DELETE FROM BOOK WHERE BOOKID = :bookid";
		params.addValue("bookid", bookid);
		return jdbcTemplate.update(sql, params);
	}

	public void sortByTitle() {
		List<Book> books;
		String sql = "SELECT * FROM BOOK";

		books = jdbcTemplate.query(sql, new BookRowMapper());

		LOG.info("Before sorting...");

		for (Book book : books) {
			LOG.info("Found book :: " + book.getTitle() + " Price :: " + book.getPrice() + " Volume :: "
					+ book.getVolume() + " Publish Date :: " + book.getPublishDate());
		}

		List<Book> sortedByTitle = books.stream().sorted((o1, o2) -> o1.getTitle().compareTo(o2.getTitle()))
				.collect(Collectors.toList());

		LOG.info("After sorting...");

		for (Book book : sortedByTitle) {
			LOG.info("Found book :: " + book.getTitle() + " Price :: " + book.getPrice() + " Volume :: "
					+ book.getVolume() + " Publish Date :: " + book.getPublishDate());
		}

	}

	public void sortByDate() {
		List<Book> books;
		String sql = "SELECT * FROM BOOK";

		books = jdbcTemplate.query(sql, new BookRowMapper());

		LOG.info("Before sorting...");

		for (Book book : books) {
			LOG.info("Found book :: " + book.getTitle() + " Price :: " + book.getPrice() + " Volume :: "
					+ book.getVolume() + " Publish Date :: " + book.getPublishDate());
		}

		List<Book> sortedByDate = books.stream().sorted((o1, o2) -> o1.getPublishDate().compareTo(o2.getPublishDate()))
				.collect(Collectors.toList());

		LOG.info("After sorting...");

		for (Book book : sortedByDate) {
			LOG.info("Found book :: " + book.getTitle() + " Price :: " + book.getPrice() + " Volume :: "
					+ book.getVolume() + " Publish Date :: " + book.getPublishDate());
		}

	}

	public List<Book> getAllBooks() throws SQLException {

		final String sql = "SELECT * FROM BOOK";
		final List<Book> books = new ArrayList<Book>();
		final List<Map<String, Object>> rows = jdbcTemplate2.queryForList(sql);

		for (Map<String, Object> row : rows) {
			Book book = new Book();
			Integer bookid = ((Integer) row.get("bookid"));
			book.setBookid(bookid.longValue());
			book.setTitle((String) row.get("title"));
			book.setPrice((Double) row.get("price"));
			Integer volume = (Integer) row.get("volume");
			book.setVolume(String.valueOf(volume));

			book.setPublishDate(String.valueOf((Date) row.get("publishDate")));
			books.add(book);
		}
		return books;

	}

	public List<Book> getBook(Integer bookid) throws SQLException {
		String sql = "SELECT * FROM BOOK WHERE BOOKID = :book_id";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("book_id", bookid);
		LOG.info("Search query :: " + sql);
		return jdbcTemplate.query(sql, params, new BookRowMapper());
	}

	public int updateBook(Book book) throws SQLException {
		LOG.info("Inside addBook");
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = "UPDATE BOOK SET TITLE = :title , PRICE = :price, VOLUME = :volume, PUBLISHDATE = :publishDate WHERE BOOKID = :bookid";
		params.addValue("bookid", book.getBookid());
		params.addValue("title", book.getTitle());
		params.addValue("price", book.getPrice());
		params.addValue("volume", book.getVolume());
		params.addValue("publishDate", book.getPublishDate());
		return jdbcTemplate.update(sql, params);

	}

}
