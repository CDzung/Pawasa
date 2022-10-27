package com.pawasa.controller.client;

import com.pawasa.model.Category;
import com.pawasa.model.Product;
import com.pawasa.repository.CategoryRepository;
import com.pawasa.repository.ProductRepository;
import com.pawasa.service.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class SearchController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    Map<Integer, Boolean> pricesMap = new HashMap<>();

    SearchController() {
        for (int i = 0; i < 5; i++) {
            pricesMap.put(i, false);
        }
    }

    @GetMapping("list")
    public String list(ModelMap model,
                       @RequestParam(value = "page") Optional<Integer> page,
                       @RequestParam(value = "size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(12);

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("productName"));

        Page<Product> resultPage = null;

        resultPage = productRepository.findAll(pageable);
        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);

            if (totalPages > 5) {
                if (end == totalPages) start = end - 5;
                else if (start == 1) end = start + 5;
            }
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("products", resultPage);
        return "/pages/client/test";
    }

    @GetMapping("search")
    public String searchByName(ModelMap model,
                               @RequestParam(value = "name", required = false) String name,
                               @RequestParam("lang") Optional<Integer[]> languageIDs,
                               @RequestParam("price") Optional<Integer[]> priceIDs,
                               @RequestParam("book-layout") Optional<Integer[]> bookLayoutIDs,
                               @RequestParam(value = "page") Optional<Integer> page,
                               @RequestParam(value = "size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(12);

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("productName"));

        Page<Product> resultPage = null;

        if (StringUtils.hasText(name)) {
            List<Product> tmp = productRepository.searchByProductName(name, Sort.by("productName"));
            resultPage = new PageImpl<>(tmp, pageable, tmp.size());
            //model.addAttribute("quantity", productRepository.searchByProductName(name, Sort.by("productName")).size());
        } else {
            resultPage = productRepository.findAll(pageable);
        }
        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);

            if (totalPages > 5) {
                if (end == totalPages) start = end - 5;
                else if (start == 1) end = start + 5;
            }
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("products", resultPage);
        return "/pages/client/searchByWord";
    }

    @GetMapping("category")
    public String searchByCategory(ModelMap model,
                                   @RequestParam(value = "id", required = false) Long categoryId,
                                   @RequestParam("lang") Optional<Integer[]> languageIDs,
                                   @RequestParam("price") Optional<Integer[]> priceIDs,
                                   @RequestParam("book-layout") Optional<Integer[]> bookLayoutIDs,
                                   @RequestParam("exists") Optional<Integer> quantity,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(12);
        int qty = quantity.orElse(1);
        boolean[] pricesArr = new boolean[6];
        boolean[] langsArr = new boolean[6];
        boolean[] bookLayoutsArr = new boolean[6];
        Arrays.fill(pricesArr, false);
        Arrays.fill(langsArr, false);
        Arrays.fill(bookLayoutsArr, false);

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("productName"));
        Page<Product> resultPage = null;
        Category category = productRepository.findByCategoryID(categoryId);

        if (category != null) {
            List<Long> idLst = new ArrayList<>();
            List<Product> lst = productRepository.findAll();
            for (int i = 0; i < lst.size(); i++) {
                if (lst.get(i).getCategory().getId() == categoryId ||
                        lst.get(i).getCategory().getParentCategory().getId() == categoryId ||
                        lst.get(i).getCategory().getParentCategory().getParentCategory().getId() == categoryId) {
                    idLst.add(lst.get(i).getId());
                }
            }
            Specification<Product> spe = Specification.where(ProductSpecification.hasProductId(idLst));

            spe = spe.and(ProductSpecification.qtyGreaterThan0(qty));
            if (priceIDs.isPresent()) {
                Specification<Product> priceSpe = Specification.where(ProductSpecification.priceInRange(priceIDs.get()[0]));
                if (priceIDs.get().length > 1) {
                    for (int i = 1; i < priceIDs.get().length; i++) {
                        priceSpe = priceSpe.or(ProductSpecification.priceInRange(priceIDs.get()[i]));
                    }
                }
                spe = spe.and(priceSpe);
                for (int i = 0; i < priceIDs.get().length; i++) {
                    pricesArr[priceIDs.get()[i]] = true;
                }
            }
            if (languageIDs.isPresent()) {
                Specification<Product> langSpe = Specification.where(ProductSpecification.hasLanguage(languageIDs.get()[0]));
                if (languageIDs.get().length > 1) {
                    for (int i = 1; i < languageIDs.get().length; i++) {
                        langSpe = langSpe.or(ProductSpecification.hasLanguage(languageIDs.get()[i]));
                    }
                }
                spe = spe.and(langSpe);
                for (int i = 0; i < languageIDs.get().length; i++) {
                    langsArr[languageIDs.get()[i]] = true;
                }
            }
            if (bookLayoutIDs.isPresent()) {
                Specification<Product> bookLayoutSpe = Specification.where(ProductSpecification.hasBookLayout(bookLayoutIDs.get()[0]));
                if (bookLayoutIDs.get().length > 1) {
                    for (int i = 1; i < bookLayoutIDs.get().length; i++) {
                        bookLayoutSpe = bookLayoutSpe.or(ProductSpecification.hasBookLayout(bookLayoutIDs.get()[i]));
                    }
                }
                spe = spe.and(bookLayoutSpe);
                for (int i = 0; i < bookLayoutIDs.get().length; i++) {
                    bookLayoutsArr[bookLayoutIDs.get()[i]] = true;
                }
            }
            resultPage = productRepository.findAll(spe, pageable);
            model.addAttribute("category", category);
        } else {
            resultPage = productRepository.findAll(pageable);
        }
        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);
            if(totalPages >5){
                if (end == totalPages) start = end - 4;
                else if (start == 1) end = start + 4;
            }
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("products", resultPage);
        model.addAttribute("pricesArr", pricesArr);
        model.addAttribute("bookLayoutsArr", bookLayoutsArr);
        model.addAttribute("langsArr", langsArr);
        model.addAttribute("size", pageSize);
        model.addAttribute("exists", qty);

        return "/pages/client/searchByCategory";
    }
}
