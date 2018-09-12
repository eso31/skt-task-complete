package com.skytask.controller;

import com.skytask.common.Product;
import com.skytask.service.ProductServiceManagement;
import org.springframework.amqp.AmqpException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
class ProductController {

    private ProductServiceManagement productServiceManagement;

    public ProductController(ProductServiceManagement productServiceManagement) {
        this.productServiceManagement = productServiceManagement;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView list() throws IOException {
        List<Product> productList = null;
        List<String> errors = new ArrayList<>();
        try {
            productList = productServiceManagement.getProductListRabbit();
            if(productList==null)
                errors.add("Server failed to retrieve the product list");
        } catch (AmqpException amqpException) {
            errors.add("Failed to connect with rabbitmq");
        }

        if(errors.size()>0)
            return new ModelAndView("error", "errors", errors);

        return new ModelAndView("index", "products", productList);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView createform() {
        return new ModelAndView("create");
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("error", "errors",
                    bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toArray());
        }

        List<String> errors = new ArrayList<>();

        try {
            Long id = productServiceManagement.createProductRabbit(product);

            if(id==null)
                errors.add("Server failed to create a product");
        } catch (IllegalArgumentException e) {
            errors.add("Product can not be null");
        } catch (AmqpException amqpException) {
            errors.add("Failed to connect with rabbitmq");
        }

        if(errors.size()>0)
            return new ModelAndView("error", "errors", errors);

        return new ModelAndView("redirect:/");
    }
}
