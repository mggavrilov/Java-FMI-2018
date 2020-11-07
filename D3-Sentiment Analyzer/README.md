# Домашно #3

## Movie Review Sentiment Analyzer

`Краен срок: 16.12.2017, 23:45`

*Sentiment Analysis* е процесът по алгоритмично идентифициране и категоризиране на мнения, изразени в свободен текст, особено за да се определи дали отношението на автора към конкретна тема, продукт и т.н. е позитивно, негативно или неутрално.

*Machine Learning* е особено нашумяло в наши дни направление в компютърните науки, което изучава класове от алгортими, които "се учат" от данни. Такъв тип алгоритми работят като изграждат модел от примерни входни данни и използват този модел за да правят предсказания или взимат решения.

Задачата е да се имплементира sentiment analyzer за филмови отзиви, който автоматично ще определя степента на позитивност на даден отзив в свободен текст.

Например, нашият алгоритъм би определил отзивът

*"Dire disappointment: dull and unamusing freakshow"*

като твърдо негативен, докато отзивът 

*"Immersive ecstasy: energizing artwork!"*

ще се класифицира като еднозначно позитивен.

Данните, от които ще "учи" нашият алгоритъм е множество от 8,529 филмови отзива (ревюта), за които отношението на автора е било оценено от човек по скала от 0 до 4 със следната семантика:

| рейтинг | семантика         |
| ------- | ----------------- |
| 0       | negative          |
| 1       | somewhat negative |
| 2       | neutral           |
| 3       | somewhat positive |
| 4       | positive          |

Ще използваме data set от сайта [Rotten Tomatoes](https://www.rottentomatoes.com/), използван наскоро за престижния [Кaggle machine learning competition](https://www.kaggle.com/c/sentiment-analysis-on-movie-reviews).

Данните са налични в текстовия файл [movieReviews.txt](https://github.com/fmi/java-course/blob/master/homeworks/03-movie-review-sentiment-analyzer/resources/movieReviews.txt), като всеки ред от файла започва с рейтинг, следван от интервал и текста на отзива, например:

```
4 The performances are an absolute joy .
```

Имайте предвид, че е напълно очаквано в подобен real-life data set да има typos, жаргонни или направо несъществуващи думи. 

Има обаче едно множество от често срещани в свободен текст думи, които носят твърде малко семантика: определителни членове, местоимения, предлози, съюзи и т.н. Такива думи се наричат *stopwords* и много алгоритми, свързани с обработка на естествен език (NLP, natural language proessing) ги игнорират - т.е. премахват ги от съответните входни текстове, защото внасят "шум", т.е. намаляват качеството на резултата. Няма еднозначна дефиниция (или речник) коя дума е stopword в даден език. В нашия алгоритъм ще ползваме списъка от 174 stopwords в английския език, записани по една на ред в текстовия файл [stopwords.txt](https://github.com/fmi/java-course/blob/master/homeworks/03-movie-review-sentiment-analyzer/resources/stopwords.txt), който сме заимствали от сайта [ranks.nl](https://www.ranks.nl/stopwords).

Алгоритъмът, който трябва да имплементирате, е следният:

Обучение:

1. Изчитат се отзивите в [movieReviews.txt](https://github.com/fmi/java-course/blob/master/homeworks/03-movie-review-sentiment-analyzer/resources/movieReviews.txt)
2. Изчислява се sentiment score на всяка дума като средно аритметично (без закръгляване) на всички рейтинги, в които участва дадената дума. Дума е последователност от малки и главни латински букви и цифри с дължина поне един символ. Думите са case-insensitive, т.е. "Movie", "movie" и "movIE" се третират като една и съща дума. Един отзив се състои от думи, разделени с разделители: интервали, табулации и препинателни знаци - въобще всеки символ, който не е буква или цифра. Stopwords се игнорират, т.е. не се взимат под внимание.

Разпознаване:

1. По даден текст на отзив се изчислява неговият sentiment score като средно аритметично (без закръгляване) на sentiment scores на всяка дума в отзива. Дефиницията на дума е същата като по-горе, и stopwords отново се игнорират. Игнорират се също всички (непознати) думи, за които алгоритъмът не е обучен, т.е. не се срещат нито веднъж в [movieReviews.txt](https://github.com/fmi/java-course/blob/master/homeworks/03-movie-review-sentiment-analyzer/resources/movieReviews.txt). Sentiment score на отзив, в който не се съдържа нито една дума с известен sentiment score (състои се само от непознати думи и/или stopwords) се приема за -1.0 (unknown).

В пакет `bg.uni.sofia.fmi.mjt.sentiment` създайте клас `MovieReviewSentimentAnalyzer`, който имплементира интерфейса `SentimentAnalyzer` по-долу и има конструктор

`public MovieReviewSentimentAnalyzer(String reviewsFileName, String stopwordsFileName)`

като двата параметъра са имената съответно на файла с филмовите отзиви и файла със stopwords.

```java
package bg.uni.sofia.fmi.mjt.sentiment;

import java.util.Collection;

public interface SentimentAnalyzer {

    /**
     * @param review
     *            the text of the review
     * @return the review sentiment as a floating-point number in the interval [0.0,
     *         4.0] if known, and -1.0 if unknown
     */
    double getReviewSentiment(String review);

    /**
     * @param review
     *            the text of the review
     * @return the review sentiment as a name: "negative", "somewhat negative",
     *         "neutral", "somewhat positive", "positive" or "unknown". Standard
     *         rounding rules apply, e.g. 1.49 is "somewhat negative" while 1.50 is
     *         "neutral"
     */
    String getReviewSentimentAsName(String review);

    /**
     * @param word
     * @return the review sentiment of the word as a floating-point number in the
     *         interval [0.0, 4.0] if known, and -1.0 if unknown
     */
    double getWordSentiment(String word);

    /**
     * Returns a collection of the n most frequent words found in the reviews
     */
    Collection<String> getMostFrequentWords(int n);

    /**
     * Returns a collection of the n most positive words in the reviews
     */
    Collection<String> getMostPositiveWords(int n);

    /**
     * Returns a collection of the n most negative words in the reviews
     */
    Collection<String> getMostNegativeWords(int n);

    /**
     * Returns the total number of words with known sentiment score
     */
    int getSentimentDictionarySize();

    /**
     * Returns whether a word is a stopword
     */
    boolean isStopWord(String word);

}
```

### Оценяване

Решението може да ви донесе до 100 точки, като ще бъде оценявано:

* за функционална пълнота и коректност, и за автоматични тестове с добро покритие (70% от оценката)
* за добър дизайн и чист код (30% от оценката)
