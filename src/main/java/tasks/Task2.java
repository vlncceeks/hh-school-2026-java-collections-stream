package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Collectors;

/*
Задача 2
На вход принимаются две коллекции объектов Person и величина limit
Необходимо объеденить обе коллекции
отсортировать персоны по дате создания и выдать первые limit штук.
 */
public class Task2 {

  public static List<Person> combineAndSortWithLimit(Collection<Person> persons1,
                                                     Collection<Person> persons2,
                                                     int limit) {
    // С помощью listOf сделала List<List<Person>>,
    // с помощью stream сделала Stream<Collection<Person>>,
    // потом с помощью flatMap сделала Stream<Person>

    Comparator<Person> comparator = (p1, p2) -> {
      return p1.createdAt().compareTo(p2.createdAt());
    };
    return List.of(persons1, persons2).stream()
            .flatMap(list -> list.stream())
            .sorted(comparator)
            .limit(limit)
            .collect(Collectors.toList());
  }
}
