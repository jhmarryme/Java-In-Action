package com.jhmarryme.ddd.bank.external;

import com.jhmarryme.ddd.bank.types.Currency;
import com.jhmarryme.ddd.bank.types.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 *
 * @author JiaHao Wang
 * @date 2021/7/19 17:50
 */
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Autowired
    private YahooForexService yahooForexService;

    @Override
    public ExchangeRate getExchangeRate(Currency source, Currency target) {
        if (source.equals(target)) {
            return new ExchangeRate(BigDecimal.ONE, source, target);
        }
        BigDecimal forex = yahooForexService.getExchangeRate(source.getValue(), target.getValue());
        return new ExchangeRate(forex, source, target);
    }

    private class YahooForexService {
        public BigDecimal getExchangeRate(Object value, Object value1) {
            return null;
        }
    }

}
