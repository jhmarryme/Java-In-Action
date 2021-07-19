package com.jhmarryme.ddd.bank.external;

import com.jhmarryme.ddd.bank.types.Currency;
import com.jhmarryme.ddd.bank.types.ExchangeRate;

/**
 *
 * @author JiaHao Wang
 * @date 2021/7/19 12:57
 */
public interface ExchangeRateService {
    ExchangeRate getExchangeRate(Currency source, Currency target);
}
