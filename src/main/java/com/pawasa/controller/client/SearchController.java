package com.pawasa.controller.client;
import com.pawasa.model.Product;
import com.pawasa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
public class SearchController {
    @Autowired
    private ProductRepository productRepository;
    @GetMapping("/test")
    public String list(){
        return "pages/client/search";
    }

    @GetMapping("list")
    public String list(ModelMap model) {
        Set<Product> products = productRepository.searchAll();
//        int currentPage = 1;
//        int pageSize = 10;
//        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("productName"));
//        Page<Product> products = productRepository.searchByNameContain("1", pageable);
        model.addAttribute("products", products);
        return "pages/client/search";
    }
//
    @GetMapping("/search")
    public String search(ModelMap model,
                         @RequestParam("name") String name,
                         @SortDefault(sort = "productName", direction = Sort.Direction.ASC) Sort sort) {
        Set<Product> products1 = null;
//        List<Product> products2 = null;
//        List<Product> products3 = null;
//        List<Product> result = null;
        if (!name.trim().equals("")) {
            products1 = productRepository.searchByProductName(name, sort);
//            products2 = productRepository.searchByAuthor(name, sort);
//            products3 = productRepository.searchByPublisher(name, sort);
        } else {
            model.addAttribute("message", "Không tìm thấy loại sách theo yêu cầu");
        }
        model.addAttribute("products", products1);
        return "pages/client/search";
    }

//    @GetMapping("searchByPrice")
//    public String searchByPrice(ModelMap model,
//                                @RequestParam Optional<String> message,
//                                @RequestParam("priceRange") Optional<String> range,
//                                @PageableDefault(size = 5, sort = "productName", direction = Sort.Direction.ASC) Pageable pageable) {
////        Page<Product> pages = productRepository.searchByPrice(min.orElse(0d), max.orElse(Double.MAX_VALUE), pageable);
//////        if (message.isPresent()) {
//////            model.addAttribute("message", message.get());
//////        }
//////        List<Sort.Order> sortOrders = pages.getSort().stream().collect(Collectors.toList());
//////        if (sortOrders.size() > 0) {
//////            Sort.Order order = sortOrders.get(0);
//////
//////            model.addAttribute("sort", order.getProperty() + "," +
//////                    (order.getDirection() == Sort.Direction.ASC ? "asc" : "desc"));
//////        } else {
//////            model.addAttribute("sort", pages);
//////        }
//////        model.addAttribute("pages", pages);
//        List<Product> products1 = null;
//        if (range.isPresent()) {
//            switch (range.get()) {
//                case "1":
//                    products1 = productRepository.searchByPrice(0d, 1d);
//                    break;
//                case "2":
//                    products1 = productRepository.searchByPrice(1d, 2d);
//                    break;
//                case "3":
//                    products1 = productRepository.searchByPrice(2d, 3d);
//                    break;
//                case "4":
//                    products1 = productRepository.searchByPrice(3d, 5d);
//                    break;
//                case "5":
//                    products1 = productRepository.searchByPrice(5d, Double.MAX_VALUE);
//                    break;
//                default:
//                    products1 = productRepository.searchByPrice(0d, Double.MAX_VALUE);
//            }
//        }
//        model.addAttribute("products", products1);
//        model.addAttribute("list1Qty", products1.size());
////        model.addAttribute("ckbPrice", Integer.parseInt(range.get()));
//        return "categories/search_product/list";
//    }
//
//    @GetMapping("searchByLanguage")
//    public String searchByLanguage(ModelMap model,
//                                   @RequestParam("language") Optional<String> language,
//                                   @SortDefault(sort = "productName", direction = Sort.Direction.ASC) Sort sort) {
//        List<Product> products1 = null;
//        if (language.isPresent()) {
//            switch (language.get()) {
//                case "vn":
//                    products1 = productRepository.searchByLanguage("1", sort);
//                    break;
//                case "eng":
//                    products1 = productRepository.searchByLanguage("2", sort);
//                    break;
//                case "both":
//                    products1 = productRepository.searchByLanguage("3", sort);
//                    break;
//                case "other":
//                    products1 = productRepository.searchByLanguage("6", sort);
//                    break;
//            }
//        }
//        model.addAttribute("products", products1);
//        return "categories/search_product/list";
//    }
//
//    @GetMapping("paginated")
//    public String search2(ModelMap model,
//                          @RequestParam(value = "name", required = false) String name,
//                          @RequestParam("page") Optional<Integer> page,
//                          @RequestParam("size") Optional<Integer> size) {
//        int currentPage = page.orElse(1);
//        int pageSize = size.orElse(10);
//        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("productName"));
//
//        Page<Product> resultPage = null;
//
//        if (StringUtils.hasText(name)) {
//            resultPage = productRepository.searchByNameContain(name, pageable);
//            model.addAttribute("name", name);
//        } else {
//            resultPage = productRepository.findAll(pageable);
//        }
//        int totalPages = resultPage.getTotalPages();
//        if(totalPages > 0){
//            int start = Math.max(1, currentPage-2);
//            int end = Math.min(currentPage+2, totalPages);
//
//            if(totalPages > 5){
//                if(end == totalPages) start = end - 5;
//                else if(start == 1)end = start + 5;
//            }
//            List<Integer> pageNumbers = IntStream.rangeClosed(start,end)
//                    .boxed()
//                    .collect(Collectors.toList());
//
//            model.addAttribute("pageNumbers", pageNumbers);
//        }
//        model.addAttribute("products", resultPage);
//        return "categories/search_product/listPaginated";
//    }
}
