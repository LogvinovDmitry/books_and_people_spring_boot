package logvinov.springcourse.Project2MyBoot.repositories;

import logvinov.springcourse.Project2MyBoot.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Когда вы создаете интерфейс-репозиторий, расширяющий JpaRepository, Spring Data JPA автоматически
//создает реализацию этого интерфейса для вас. Это позволяет вам выполнять базовые операции с базой
//данных для сущности Person, не писать большинство стандартных SQL-запросов вручную.
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByNameStartingWith(String name);

}
