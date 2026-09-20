package com.mateusdomelo.intentservice.intent;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class OrderNumberExtractor {
    public Optional<Integer> extract(String message) {
        Pattern pattern = Pattern.compile("^(?=.*\\d).*?(\\d+).*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(message);

        if (matcher.matches()) return Optional.of(Integer.parseInt(matcher.group(1)));
        else return Optional.empty();
    }
}
