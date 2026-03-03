package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  private long count;

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй
  public List<String> getNames(List<Person> persons) {
    // Удаление из списка нельзя использовать, потому что если менять исходные данные,
    // то при повторном обращении к методу будут удаляться уже не "фальшивые" персоны

    // Если удаление убирать, то и проверка persons.size() == 0 не нужна,
    // stream api в плохом случае вернет пустой список

    return persons.stream().skip(1).map(Person::firstName).collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    // Было две итерации по коллекции: в getNames() и getNames().stream, сейчас одна, поэтому производительность выше
    return new HashSet<>(getNames(persons));
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    // Меньше кода
    return Stream.of(person.secondName(), person.firstName(), person.middleName())
            .filter(Objects::nonNull).collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    // Меньше кода
    // Сделала filter(person -> idsCheck.add(person.id())) для замены проверки !map.containsKey(person.id()),
    // чтобы одинаковых id не было в мапе

    return persons.stream()
            .collect(Collectors.toMap(person -> person.id(), person -> convertPersonToString(person), (a, b) -> a));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    // Через disjoint проверяю есть ли общие элементы. Если нет общих элементов, то возвращается true, поэтому использую !
    // Меньше кода
    return !Collections.disjoint(persons1, persons2);
  }

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    // Нет смысла создавать переменную и увеличивать ее
    return numbers.filter(num -> num % 2 == 0).count();
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  void listVsSet() {
    // Лист integers создается отсортированным, поэтому и snapshot отсортированный
    // HashSet реализован через HashMap
    // в HashMap количество корзин определяется степенью двойки, ближайшее число покрывающее 10000 элементов - это 2^14 = 16384. Поэтому в данном примере будет 16384 корзины
    // Индекс корзины будет определяться как HashCode() % capacity, у Integer HashCode() равен самому числу, а capacity равно количеству корзин, то есть 16384
    // Получается, что элементы от 1 до 10000 будут в корзинах с индексами от 1 до 10000 соответственно, коллизий не будет
    // toString() у HashSet проходит по корзинам в порядке их индексов (от 1 до 16384), значит числа будут отображаться в порядке от 1 до 10000
    // ArrayList всегда хранит порядок и toString() выводит именно в этом порядке, а значит числа также будут отображаться в порядке от 1 до 10000
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
