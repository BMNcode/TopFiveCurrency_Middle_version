package org.bmn.TopFiveCurrency_Middle_version.controller;


import org.bmn.TopFiveCurrency_Middle_version.job.ParseCurrencyTask;
import org.bmn.TopFiveCurrency_Middle_version.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("currency")
public class CurrencyController {

    @Autowired
    private ParseCurrencyTask parseCurrencyTask;

    @GetMapping
    public List<Currency> currentList() {
        return parseCurrencyTask.topFiveChangeCurrency();
    }
}
