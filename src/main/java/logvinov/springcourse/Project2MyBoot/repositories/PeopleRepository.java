package logvinov.springcourse.Project2MyBoot.repositories;


import logvinov.springcourse.Project2MyBoot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//Когда вы создаете интерфейс-репозиторий, расширяющий JpaRepository, Spring Data JPA автоматически
//создает реализацию этого интерфейса для вас. Это позволяет вам выполнять базовые операции с базой
//данных для сущности Person, не писать большинство стандартных SQL-запросов вручную.
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByEmail(String email);

    List<Person> findByName(String name);
    //Список людей с одинаковым именем, отсортированных по возрасту
    List<Person> findByNameOrderByAge(String name);

    //Вернется список людей, у которых имя начинаеться на переданные символы
    List<Person> findByNameStartingWith(String name);
    //Вернется список людей у которых либо имя совпало, либо имеил
    List<Person> findByNameOrEmail(String name, String email);

@Query("from Person p WHERE p.id >90")
    List<Person> met1();

}
