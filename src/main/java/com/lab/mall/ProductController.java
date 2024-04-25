package com.lab.mall;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ModelMapper modelMapper;

    String uploadPath="c:/workspace_spring/mall/src/main/resources/static/images";

    @GetMapping("write")
    public String write(){
        return "write";
    }

    @PostMapping("insert")
    public String insert(ProductDTO dto, @RequestParam MultipartFile file) {
        String filename = "-";
        if (file != null && !file.isEmpty()) {
            filename = file.getOriginalFilename();
            try {
                new File(uploadPath).mkdir();
                dto.getMultipartFile().transferTo(new File(uploadPath + filename));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dto.setFilename(filename);
        Product product = modelMapper.map(dto, Product.class);
        productRepository.save(product);
        return "redirect:/";
    }

    @RequestMapping(value="/", method={RequestMethod.GET, RequestMethod.POST})
    public ModelAndView list(@RequestParam(defaultValue="") String productName, ModelAndView mv) {
        mv.setViewName("list");
        if(productName.isEmpty()){
            mv.addObject("list", productRepository.findAll(Sort.by(Sort.Direction.ASC, "productName")));
        }else{
            mv.addObject("list", productRepository.findByProductNameContaining(productName, Sort.by(Sort.Direction.ASC, "productName")));
        }

        mv.addObject("productName", productName);
        return mv;
    }

    @PostMapping("update")
    public String update(ProductDTO dto, @RequestParam MultipartFile file) {
        String filename = "-";
        if (file != null && !file.isEmpty()) {
            filename = file.getOriginalFilename();
            try{
                new File(uploadPath).mkdir();
                file.transferTo(new File(uploadPath+filename));
                dto.setFilename(filename);
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            Optional<Product> optionalProduct = productRepository.findById(dto.getProductCode());
            Product dto2 = optionalProduct.get();
            dto.setFilename(dto2.getFilename());
        }
        Product product = modelMapper.map(dto, Product.class);
        productRepository.save(product);
        return "redirect:/";
    }

    @PostMapping("delete")
    public String delete(long productCode) {
        Optional<Product> optionalProduct = productRepository.findById(productCode);
        Product dto = optionalProduct.get();
        String filename = dto.getFilename();

        if(filename != null && !filename.equals("-")){
            File file = new File(uploadPath+filename);
            if(file.exists()){
                file.delete();
            }
        }
        productRepository.deleteById(productCode);
        return "redirect:/";
    }

    @GetMapping("detail/{productCode}")
    public ModelAndView detail(@PathVariable long productCode, ModelAndView mv){
        mv.setViewName("detail");
        Optional<Product> optionalProduct = productRepository.findById(productCode);
        Product dto = optionalProduct.get();
        mv.addObject("dto", dto);
        return mv;
    }

}
