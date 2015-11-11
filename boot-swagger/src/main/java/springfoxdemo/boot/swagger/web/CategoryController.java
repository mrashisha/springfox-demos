package springfoxdemo.boot.swagger.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
    @RequestMapping(value = "/category/Resource", method = RequestMethod.GET)
    public ResponseEntity<String> search(@RequestParam(value = "someEnum") Category someEnum) {
        return ResponseEntity.ok(someEnum.name());
    }
    
    @RequestMapping(value="/category/Resource1/{id:\\d}", method=RequestMethod.GET)
    public String getById_singleDigit(@PathVariable("id") int id) {
        return lookupCategoryById(id);
    }
    
    @RequestMapping(value="/category/Resource2/{id:\\d\\d}", method=RequestMethod.GET)
    public String getById_twoDigits(@PathVariable("id") int id) {
        return lookupCategoryById(id);
    }
    
    @RequestMapping(value="/category/Resource3/{id:\\d+}", method=RequestMethod.GET)
    public String getById_quantifier(@PathVariable("id") int id) {
        return lookupCategoryById(id);
    }
    
    /******************** Houston, we have a problem! *********************/
    @RequestMapping(value="/category/Resource4/{id:\\d{2}}", method=RequestMethod.GET)
    public String getById_anotherQuantifier(@PathVariable("id") int id) {
        return lookupCategoryById(id);
    }
    
    @RequestMapping(value="/category/Resource5/{id:[0-9]}", method=RequestMethod.GET)
    public String getById_range(@PathVariable("id") int id) {
        return lookupCategoryById(id);
    }

    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public String handleIllegalArgumentException(IllegalArgumentException iae) {
        return iae.getMessage();
    }
    
    private String lookupCategoryById(int id) {
        switch(id % 3) {
        case 1:
            return Category.ONE.toString();
        case 2:
            return Category.TWO.toString();
        case 3:
            return Category.THREE.toString();
            
        default:
            throw new IllegalArgumentException("Category with id=" + id + " not found");
        }
    }
}
