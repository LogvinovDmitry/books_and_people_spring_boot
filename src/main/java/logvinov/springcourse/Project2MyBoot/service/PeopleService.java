package logvinov.springcourse.Project2MyBoot.service;

import logvinov.springcourse.Project2MyBoot.models.Book;
import logvinov.springcourse.Project2MyBoot.models.Person;

import logvinov.springcourse.Project2MyBoot.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
//Все публичные методы в этом классе будут @Transactional(readOnly = true), поэтому
//если необходимо что-то изменить в бд, то переписываем аннотацию с пустыми скобками над самим методоиъм
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        List<Person> peopleRepositoryAll = peopleRepository.findAll();
        return peopleRepositoryAll;
    }

    //Наш репозиторий знает, что у Персона есть айди и он Интеджер. Это мы указали, при объявлении
    //интерфейса репозитория
    public Person findOne(int id) {
        //Optional, потому что можем найти человека с этим айди в базе, а может и нет
        Optional<Person> person = peopleRepository.findById(id);
        return person.orElse(null);
    }

//    public Optional<Person> findOne(int id) {
//        //Optional, потому что можем найти человека с этим айди в базе, а может и нет
//        Optional<Person> optionalPerson = peopleRepository.findById(id);
//        return optionalPerson;
//    }

    public Optional<Person> findByEmail(String email) {
        //Optional, потому что можем найти человека с этим айди в базе, а может и нет
        Optional<Person> personbyEmail = peopleRepository.findByEmail(email);
        return personbyEmail;
    }

    @Transactional
    public void save(Person person) {
        person.setDateOfRegistration(new Date());

        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    //Мой вариант этого метода
//    public List<Book> getBooksByPersonId(int idPerson) {
//
//        Person person = peopleRepository.findById(idPerson).orElse(null);
//
//        if (person.getBookList() != null) {
//            return person.getBookList();
//        } else {
//            return Collections.emptyList();
//        }
//    }

    public List<Book> getBooksByPersonId(int idPerson) {

        Optional<Person> personOptional = peopleRepository.findById(idPerson);

        if (personOptional.isPresent()) {

            Hibernate.initialize(personOptional.get().getBookList());
            // Мы внизу итерируемся по книгам, поэтому они точно будут загружены, но на всякий случай
            // не мешает всегда вызывать Hibernate.initialize()
            // (на случай, например, если код в дальнейшем поменяется и итерация по книгам удалится)

            List<Book> bookList = personOptional.get().getBookList();

            bookList.forEach(e -> {

                //LocalDateTime nowDateTime = LocalDateTime.now();
                //LocalDateTime dateWhenBookWasTaken = LocalDateTime.of(2023, 1, 1, 12, 0); // Замените эту дату на нужную вам

                // Получим текущую дату и время
                //LocalDateTime nowDateTime = LocalDateTime.now();

                //Date nowDate = new Date();
                LocalDateTime nowDateTime = LocalDateTime.now();
                LocalDateTime dateWhenBookWasTaken = e.getDateWhenBookWasTaken();


                // Проверим разницу в днях
                long daysDifference = ChronoUnit.DAYS.between(dateWhenBookWasTaken, nowDateTime);

                // Проверим, превышает ли разница 10 дней


                e.setDifferenceGreaterThan10Days(daysDifference > 10);

            });

            return bookList;

        } else {
            return Collections.emptyList();
        }
    }

    public List<Person> met1() {
        return peopleRepository.met1();
    }

}







