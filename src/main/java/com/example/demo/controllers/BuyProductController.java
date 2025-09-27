package com.example.demo.controllers;

import com.example.demo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BuyProductController {
    private final ProductService productService;

    public BuyProductController(ProductService productService) {
        this.productService = productService;
    }

    //find item by id and return message
    @PostMapping("/products/{id}/buy")
    public String buy(@PathVariable("id") long id, RedirectAttributes redir) {

        boolean hasItem = productService.buyOne(id);
        if (hasItem) {
            redir.addFlashAttribute("message", "Purchase successful!");
            redir.addFlashAttribute("messageType", "success");
        } else {
            redir.addFlashAttribute("message", "Item out of stock — purchase failed.");
            redir.addFlashAttribute("messageType", "danger");
        }
        return "redirect:/mainscreen";
    }
}

