package com.said;

public class Main {







    static abstract class KeywordAnalyzer implements TextAnalyzer {

        protected abstract String[] getKeywords();

        protected abstract Label getLabel();

        //обработка текста
        @Override
        public Label processText(String text) {
            String[] strKeywords = getKeywords();

            //String text = "OTUS is a good company";
            //String[] words = text.split(" ");
            //for(String word : words){
            //    System.out.println(word);
            //}

            /*Label label;
            for (String str : strKeywords) {
                if (str.equals(text)) {
                    label = getLabel();
                    return label;
                }
            }*/


            Label label;
            for (String str : strKeywords) {
                if (text.indexOf(str) != -1) {
                    label = getLabel();
                    return label;
                }
            }
            return Label.OK;
        }
    }


    //наследник анализирует спам
    static class SpamAnalyzer extends KeywordAnalyzer {
        private String[] keywords;

        //SpamAnalyzer должен конструироваться от массива строк с ключевыми словами.
        public  SpamAnalyzer(String[] keywords){
            this.keywords = keywords;
        }

        @Override
        public String[] getKeywords() {
            return keywords;
        }

        @Override
        public Label getLabel() {
            return Label.SPAM;
        }

    }
    //наследник анализ негативные коммен
    static class NegativeTextAnalyzer extends KeywordAnalyzer {
        private String[] keywords={":(", "=(", ":|"};

        @Override
        protected String[] getKeywords(){
            return keywords;
        }

        @Override
        protected Label getLabel(){
            return Label.NEGATIVE_TEXT;
        }

    }

//анализ длинных комментариев
    static class TooLongTextAnalyzer implements TextAnalyzer{
        private int maxLength;
        public TooLongTextAnalyzer(int maxLength){
            this.maxLength=maxLength;
        }

        protected int getMaxLength(){
            return maxLength;
        }

        protected Label getLabel(){
            return Label.TOO_LONG;
        }

        //обработка текста на длинну
        @Override
        public Label processText(String text){
            Label label = getLabel();

            if (text.length() >= maxLength) {
                //label = Label.TOO_LONG;
                return label;
            }
            return Label.OK;
        }

    }

    public static void main(String[] args) { // main в ответ не печатаем, только для теста

        String[] spamKeywords = {"bad","spam"}; // Слова, относящиеся к спаму
        int commentMaxLength = 10; // Максимальная длина строки
        String s = "Это весело"; // Тестируемая строка

        TextAnalyzer[] textAnalyzers = { // Массив, подаваемый на вход checkLabels()
                new SpamAnalyzer(spamKeywords),
                new NegativeTextAnalyzer(),
                new TooLongTextAnalyzer(commentMaxLength)
        };

        System.out.println(checkLabels(textAnalyzers, s)); // На выходе SPAM, так как String s содержит ключевое слово "spam" из spamKeywords
    }

    public static Label checkLabels(TextAnalyzer[] analyzers, String text) { // Для удобства тестирования checkLabels() делаем временно static
        int i=0;

        for (TextAnalyzer a : analyzers){


            if (a.processText(text) != Label.OK)
                {

                    return a.processText(text);
                }

        }
        // Здесь перебираем массив analyzers и подставляем processText(text) //
        return Label.OK;
    }

}