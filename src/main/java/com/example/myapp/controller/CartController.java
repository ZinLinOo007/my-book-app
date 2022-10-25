package com.example.myapp.controller;

import com.example.myapp.ds.Book;
import com.example.myapp.ds.BookDto;
import com.example.myapp.service.BookService;
import com.example.myapp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private BookService bookService;

    @GetMapping("/cart/add")
    public String addTOCart(@RequestParam("id") int id, Model model){
        cartService.addToCart(bookService.findBookById(id));

        return "redirect:/shop/books/details?id=" + id;
    }

    @GetMapping("/cart/view")
    public String viewCard(Model model){
        model.addAttribute("cartItems",cartService.listCart());
        model.addAttribute("cartSize",cartService.cartSize());
        model.addAttribute("bookDto",new BookDto());

        return "cart-view";
    }

    @GetMapping("/cart/delete/{id}")
    public String removeFromCart(@PathVariable("id") int id,Model model){
        model.addAttribute("cartSize",cartService.cartSize());
        Book book = bookService.findBookById(id);
        cartService.remove(cartService.toDto(book));

         return "redirect:/cart/view";
    }

    @GetMapping("/card/clear")
    public String clearCart(){
        cartService.clearCart();
        return "redirect:/cart/view";
    }



}
