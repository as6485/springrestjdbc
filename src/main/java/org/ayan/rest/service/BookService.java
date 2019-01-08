package org.ayan.rest.service;

import java.sql.SQLException;
import java.util.List;

import org.ayan.rest.dao.BookDAO;
import org.ayan.rest.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class BookService {

	@Autowired
	BookDAO bookDAO;

	public int countBook() {

		return bookDAO.countBook();
	}


	public void addBook(Book book) throws SQLException {
		bookDAO.addBook(book);
	}


	public List<Book> getAllBooks() throws SQLException {
		return bookDAO.getAllBooks();
	}


	public List<Book> getBook(Integer id) throws SQLException {
		return bookDAO.getBook(id);
	}


	public int updateBook(Book book) throws SQLException {
		return bookDAO.updateBook(book);
		
	}


	public int deleteBook(long id) throws SQLException  {
		return bookDAO.deleteBook(id);
		
	}
	
}
