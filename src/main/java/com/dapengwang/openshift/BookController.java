package com.dapengwang.openshift;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController implements CommandLineRunner {

    @Autowired
    private BookRepository repository;

    @GetMapping("")
    public List<Book> retrieveAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public void run(String... args) throws Exception {
        repository.save(new Book(null, "Java", "Gosling"));
        repository.save(new Book(null, "Scala", "Odersky"));
        repository.save(new Book(null, "Python", "Van"));
    }
}
