package tasks;

import common.Area;
import common.Person;

import java.util.*;
import java.util.stream.Collectors;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 {

  public static Set<String> getPersonDescriptions(Collection<Person> persons,
                                                  Map<Integer, Set<Integer>> personAreaIds,
                                                  Collection<Area> areas) {

    Map<Integer, String> areasMap = areas.stream().collect(Collectors.toMap(
            Area::getId,
            Area::getName
    ));

    Map<Integer, String> personsMap = persons.stream().collect(Collectors.toMap(
              Person::id,
              Person::firstName
    ));

    return personAreaIds.entrySet().stream()
            .flatMap(
                integerSetEntry -> integerSetEntry.getValue().stream().map(
                        area -> personsMap.get(integerSetEntry.getKey()) + " - " + areasMap.get(area)
                )
            ).collect(Collectors.toSet());

  }
}
