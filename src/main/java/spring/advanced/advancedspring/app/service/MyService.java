package spring.advanced.advancedspring.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.advanced.advancedspring.app.repository.MyRepository;

@Service
@RequiredArgsConstructor
public class MyService {
    private final MyRepository myRepository;

    public void save(String name) {
        myRepository.save(name);
    }

    public String find() {
        return myRepository.find();
    }
}
