package logvinov.springcourse.Project2MyBoot.service;

import logvinov.springcourse.Project2MyBoot.models.Book;
import logvinov.springcourse.Project2MyBoot.models.Person;
import logvinov.springcourse.Project2MyBoot.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
//Все публичные методы в этом классе будут @Transactional(readOnly = true), поэтому
//если необходимо что-то изменить в бд, то переписываем аннотацию с пустыми скобками над самим методоиъм
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {

        List<Book> bookRepositoryAll = bookRepository.findAll();
        return bookRepositoryAll;
    }

    public List<Book> findAll(PageRequest pageRequest) {

        //List<Book> bookRepositoryAll = bookRepository.findAll(PageRequest.of(a, b)).getContent();
        List<Book> bookRepositoryAll = bookRepository.findAll(pageRequest).getContent();
        return bookRepositoryAll;
    }

    public List<Book> findAll(Sort publicationYear) {

        //List<Book> bookRepositoryAll = bookRepository.findAll(PageRequest.of(a, b)).getContent();
        List<Book> bookRepositoryAll = bookRepository.findAll(publicationYear);
        return bookRepositoryAll;
    }

    //Наш репозиторий знает, что у Персона есть айди и он Интеджер. Это мы указали, при объявлении
    //интерфейса репозитория
    public Book findOne(int id) {
        //Optional, потому что можем найти человека с этим айди в базе, а может и нет
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
    }

//    public Optional<Book> findByEmail(String email) {
//        //Optional, потому что можем найти человека с этим айди в базе, а может и нет
//        Optional<Book> bookbyEmail = bookRepository.findByEmail(email);
//        return bookbyEmail;
//    }

    @Transactional
    public void save(Book book) {
        //book.setDateOfRegistration(new Date());

        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {

        Book book = bookRepository.findById(id).get();
        updatedBook.setId(id);

        updatedBook.setOwner(book.getOwner());
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void releaseBook(int id) {

        //Book book = findOne(id);
        //Book book = bookRepository.findById(id).orElse(null);

        bookRepository.findById(id).ifPresent(
                book -> book.setOwner(null)
        );

        //Этот участок кода можно использовать, если по логике нужно использовать лист книг у конкретного овнера
        // и скорее всего в этом же методе.
        //Однако в этом случае он излишен.
//        Person owner = book.getOwner();
//        List<Book> bookList = owner.getBookList();
//        bookList.remove(book);

    }

    @Transactional
    public void assign(int idBook, Person person) {

        // В представленном коде используется метод findById из bookRepository для поиска книги по заданному idBook.
        // Если книга с указанным идентификатором не будет найдена в базе данных, то метод ifPresent не выполнится,
        // так как в этом случае Optional будет пустым.
        bookRepository.findById(idBook).ifPresent(
                book -> {
                    book.setDateWhenBookWasTaken(LocalDateTime.now());
                    book.setOwner(person);
                }
        );

        //Этот участок кода можно использовать, если по логике нужно использовать лист книг у конкретного овнера
        // и скорее всего в этом же методе.
        //Однако в этом случае он излишен.
//        List<Book> bookList = person.getBookList();
//
//        if (bookList == null) {
//            bookList = new ArrayList<>();
//            bookList.add(book);
//        } else {
//            bookList.add(book);
//        }

    }

    public List<Book> findByNameStartingWith(String nameStartingWith) {

        List<Book> bookRepositoryAll = bookRepository.findByNameStartingWith(nameStartingWith);
        return bookRepositoryAll;

    }


}







