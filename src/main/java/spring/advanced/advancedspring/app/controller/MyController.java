package spring.advanced.advancedspring.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.advanced.advancedspring.app.service.MyService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MyController {

    private final MyService myService;

    @GetMapping("/save")
    public String save() {
        String name = UUID.randomUUID().toString();
        myService.save(name);
        return name;
    }

    @GetMapping("/find")
    public String find() {
        return myService.find();
    }
}
