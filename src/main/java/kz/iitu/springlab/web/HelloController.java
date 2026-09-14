package kz.iitu.springlab.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
// Individual assignment endpoint will be added below
@RestController
@RequestMapping("/api")
public class HelloController {

    @Value("${app.owner:unknown}")
    private String owner;

    @GetMapping("/hello")
    public Greeting hello(@RequestParam(defaultValue = "world") String name) {
        return new Greeting("Hello, " + name + "!", owner, LocalDateTime.now());
    }

    @GetMapping("/info")
    public Info info() {
        return new Info(owner,
                System.getProperty("java.version"),
                Runtime.getRuntime().availableProcessors());
    }

    public record Greeting(String message, String owner, LocalDateTime timestamp) { }

    public record Info(String owner, String javaVersion, int cpuCores) { }
    @GetMapping("/wordcount")
    public WordCountResult wordCount(@RequestParam(defaultValue = "") String text) {
        if (text == null || text.isBlank()) {
            return new WordCountResult(0, 0, "");
        }

        String trimmed = text.trim();
        String[] words = trimmed.split("\\s+");

        int wordCount = words.length;
        int charCount = text.length();

        String longestWord = "";
        for (String word : words) {
            if (word.length() > longestWord.length()) {
                longestWord = word;
            }
        }

        return new WordCountResult(wordCount, charCount, longestWord);
    }

    public record WordCountResult(int wordCount, int charCount, String longestWord) { }
}