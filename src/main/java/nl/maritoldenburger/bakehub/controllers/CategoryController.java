package nl.maritoldenburger.bakehub.controllers;

import nl.maritoldenburger.bakehub.services.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //todo - get all categories

    //todo - get category by id

    //todo - add recipe

    //todo - update recipe

    //todo - delete recipe

}
