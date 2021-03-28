package com.dapengwang.openshift;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookRepository repository;

    @GetMapping("")
    public List<Book> retrieveAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
    
    @PostMapping("")
    public ResponseEntity<Book> createBooks(@RequestBody Book book) {
        Book result = repository.save(book);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(result.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location.toString()).body(result);
    }

    @GetMapping("/{id}")
    public Book retrieveBook(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PatchMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        return repository.findById(id).map(current -> {
            current.setName(book.getName());
            repository.save(current);
            return current;
        }).orElse(null);
    }
}

