package compose.lets.calculator.curren.data

import compose.lets.calculator.R

class Constants {
    companion object {

        const val BASE_URL = "https://api.apilayer.com/currency_data/"

        val CURRENCY_CODES_LIST = listOf(
            CurrencyAndCountry("Australia", "AUD", icon = R.drawable.aud),
            CurrencyAndCountry("Brazil", "BRL", icon = R.drawable.brl),
            CurrencyAndCountry("Bulgaria", "BGN", icon = R.drawable.bgn),
            CurrencyAndCountry("Canada", "CAD", icon = R.drawable.cad),
            CurrencyAndCountry("China", "CNY", icon = R.drawable.cny),
            CurrencyAndCountry("Croatia", "HRK", icon = R.drawable.hrk),
            CurrencyAndCountry("Czech Republic", "CZK", icon = R.drawable.czk),
            CurrencyAndCountry("Denmark", "DKK", icon = R.drawable.dkk),
            CurrencyAndCountry("European Union", "EUR", icon = R.drawable.eur),
            CurrencyAndCountry("Great Britain", "GBP", icon = R.drawable.gbp),
            CurrencyAndCountry("Hong Kong", "HKD", icon = R.drawable.hkd),
            CurrencyAndCountry("Hungary", "HUF", icon = R.drawable.huf),
            CurrencyAndCountry("Iceland", "ISK", icon = R.drawable.isk),
            CurrencyAndCountry("India", "INR", icon = R.drawable.ruppee),
            CurrencyAndCountry("Indonesia", "IDR", icon = R.drawable.idr),
            CurrencyAndCountry("Israel", "ILS", icon = R.drawable.ils),
            CurrencyAndCountry("Japan", "JPY", icon = R.drawable.jpy),
            CurrencyAndCountry("Korea", "KRW", icon = R.drawable.krw),
            CurrencyAndCountry("Malaysia", "MYR", icon = R.drawable.myr),
            CurrencyAndCountry("Mexico", "MXN", icon = R.drawable.mxn),
            CurrencyAndCountry("New Zealand", "NZD", icon = R.drawable.nzd),
            CurrencyAndCountry("Norway", "NOK", icon = R.drawable.nok),
            CurrencyAndCountry("Philippines", "PHP", icon = R.drawable.php),
            CurrencyAndCountry("Poland", "PLN", icon = R.drawable.pln),
            CurrencyAndCountry("Romania", "RON", icon = R.drawable.ron),
            CurrencyAndCountry("Russia", "RUB", icon = R.drawable.rub),
            CurrencyAndCountry("Singapore", "SGD", icon = R.drawable.sgd),
            CurrencyAndCountry("South Africa", "ZAR", icon = R.drawable.zar),
            CurrencyAndCountry("Sweden", "SEK", icon = R.drawable.sek),
            CurrencyAndCountry("Switzerland", "CHF", icon = R.drawable.chf),
            CurrencyAndCountry("Thailand", "THB", icon = R.drawable.thb),
            CurrencyAndCountry("Turkey", "TRY", icon = R.drawable.try1),
            CurrencyAndCountry("United States", "USD", icon = R.drawable.usd),
        )
    }
}