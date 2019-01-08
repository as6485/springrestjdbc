package org.ayan.rest.controller;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ayan.rest.dao.BookDAO;
import org.ayan.rest.dto.Book;
import org.ayan.rest.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringRestController {

	private static Logger logger = LogManager.getLogger(BookDAO.class);

	@Autowired
	BookService bookService;

	@GetMapping("/count")
	public int getCount() {

		return bookService.countBook();
	}

	@PostMapping("/book")
	public ResponseEntity<String> addBook(@RequestBody(required = true) Book book) {
		try {
			bookService.addBook(book);
		} catch (SQLException e) {
			logger.error(e);
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);

		}

		return new ResponseEntity<String>("Added book successfully", HttpStatus.CREATED);
	}

	@GetMapping("/books")
	public ResponseEntity<?> getAllBooks() {

		try {
			List<Book> books = bookService.getAllBooks();
			return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
		} catch (SQLException e) {
			logger.error(e);
			return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);

		}
	}

	@GetMapping("/book/{id}")
	public ResponseEntity<?> getAllBooks(@PathVariable Integer id) {

		try {
			List<Book> book = bookService.getBook(id);
			if (book.isEmpty()) {
				return new ResponseEntity<String>("Book not found", HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<List<Book>>(book, HttpStatus.OK);
			}
		} catch (SQLException e) {
			logger.error(e);
			return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);

		}

	}

	@PatchMapping("/book")
	public ResponseEntity<String> updateBook(@RequestBody(required = true) Book book) {
		try {
			int rc = bookService.updateBook(book);
			if (rc == 0) {
				return new ResponseEntity<String>("Book not found to update", HttpStatus.BAD_REQUEST);
			}
		} catch (SQLException e) {
			logger.error(e);
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);

		}

		return new ResponseEntity<String>("Updated book successfully", HttpStatus.OK);
	}

	@DeleteMapping("/book/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable long id) {

		try {
			int rc = bookService.deleteBook(id);
			if (rc == 0) {
				return new ResponseEntity<String>("Book not found to delete", HttpStatus.BAD_REQUEST);
			}

		} catch (SQLException e) {
			logger.error(e);
			return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<String>("Deleted book successfully", HttpStatus.OK);

	}

}
