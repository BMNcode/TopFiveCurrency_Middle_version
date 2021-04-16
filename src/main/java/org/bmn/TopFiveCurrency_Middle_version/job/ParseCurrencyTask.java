package org.bmn.TopFiveCurrency_Middle_version.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bmn.TopFiveCurrency_Middle_version.model.Currency;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ParseCurrencyTask {

    @Scheduled(fixedRate = 86400000)
    public List<Currency> topFiveChangeCurrency() {
        ObjectMapper mapper= new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readTree(new URL("https://www.cbr-xml-daily.ru/daily_json.js"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonNode nameNode = rootNode.path("Valute");
        return StreamSupport
                .stream(Spliterators
                        .spliteratorUnknownSize(nameNode.fields(), Spliterator.ORDERED), false)
                .map(e -> {
                    try {
                        return mapper.treeToValue(e.getValue(), Currency.class);
                    } catch (JsonProcessingException jsonProcessingException) {
                        throw new RuntimeException(jsonProcessingException);
                    }
                })
                .sorted(Currency::compare)
                .collect(Collectors.toList());
    }
}
