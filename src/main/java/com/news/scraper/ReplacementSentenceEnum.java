package com.news.scraper;

public enum ReplacementSentenceEnum {
    SENT1("به گزارش سایت خبری ساعدنیوز"),
    SENT2("به گزارش سایت خبری ساعد نیوز");
    private final String text;

    ReplacementSentenceEnum(final String text) {
        this.text = text;
    }


    @Override
    public String toString() {
        return text;
    }
}
