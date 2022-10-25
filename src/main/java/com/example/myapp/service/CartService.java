package com.example.myapp.service;

import com.example.myapp.dao.AuthorDao;
import com.example.myapp.dao.CategoryDao;
import com.example.myapp.ds.Book;
import com.example.myapp.ds.BookDto;
import com.example.myapp.ds.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CartService {
    @Autowired
    private Card card;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private AuthorDao authorDao;


    public BookDto toDto(Book book){
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getPrice(),
                book.getYearPublished(),
                book.getDescription(),
                book.getImgUrl(),
                book.getAuthor().getId(),
                book.getCategory().getId());
    }

    public Book toEntity(BookDto  bookDto){
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setPrice(bookDto.getPrice());
        book.setAuthor(authorDao.findById(bookDto.getAuthorId()).get());
        book.setCategory(categoryDao.findById(bookDto.getCategoryId()).get());
        book.setYearPublished(bookDto.getYearPublished());
        book.setDescription(bookDto.getDescription());
        book.setTitle(bookDto.getTitle());
        book.setImgUrl(bookDto.getImgUrl());
        return book;
    }

    public void addToCart(Book book){
        card.addToCart(toDto(book));
    }

    public Set<BookDto> listCart(){
        return card.getBookDtos();
    }

    public int cartSize(){
        return card.cartSize();
    }

    public void remove(BookDto bookDto){
        card.removeBookFromCart(bookDto);
    }

    public void clearCart(){
        card.clearCart();
    }
}
