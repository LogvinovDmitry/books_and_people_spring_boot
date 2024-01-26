package logvinov.springcourse.Project2MyBoot.controller;

import logvinov.springcourse.Project2MyBoot.models.Book;
import logvinov.springcourse.Project2MyBoot.models.Person;
import logvinov.springcourse.Project2MyBoot.service.BookService;
import logvinov.springcourse.Project2MyBoot.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/books", produces = "text/plain;charset=UTF-8")
//@RequestMapping("/books")
public class BookController {

    //private final BookValidator personValidator;
    // Автоматически создает Бин personDAO и подставляет его в этот конструктор.
    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(required = false) Integer page,
                        @RequestParam(required = false) Integer books_per_page,
                        @RequestParam(required = false) Boolean sort_by_year) {

        List<Book> books;
        if (page == null) {

            if (sort_by_year == null || !sort_by_year) {
                books = bookService.findAll();
            } else {
                books = bookService.findAll(Sort.by("publicationYear"));
            }

        } else {
            if (sort_by_year == null || !sort_by_year) {
                books = bookService.findAll(PageRequest.of(page, books_per_page));
            } else {
                books = bookService.findAll(PageRequest.of(page, books_per_page, Sort.by("publicationYear")));
            }
        }
        model.addAttribute("books", books);

        return "books/index";
    }

    // Во время выполнения приложения, в строке адреса мы можем задать id. С помощью аннотации @PathVariable
    // этот айдишник будет доступен внутри метода show. (И будет помещен в переменную int id).
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person") Person person) {
        // Получим одного человека по id из DAO и передадим на отображение в представление.
        Book book = bookService.findOne(id);

        if (book.getOwner() != null) {
            Person owner = book.getOwner();
            model.addAttribute("owner", owner);
        } else {
            List<Person> people = peopleService.findAll();
            model.addAttribute("people", people);
        }

        model.addAttribute("book", book);

        return "books/show";
    }

    // Метод добавления нового человека.
    // В аргументы передаем Модель, в которой помещен объект new Book(),
    // для того, что бы этот объект был доступен в форме Таймлив.
    //В этой форме Таймлив, спомощью его синтаксиса, будем сразу назначать значение полей объекта new Book().
//    @GetMapping("/new")
//    public String newBook(Model model) {
//        model.addAttribute("person", new Book());
//
//        return "books/formNew";
//    }

    // Эквивалент метода выше.
    // @ModelAttribute создаст объект класса Book с пустыми полями и положит его в модель.
    // А к обекту, который уже положен в модель есть доступ в представлении, где мы сразу и назначаем поля
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {

        return "books/formNew";
    }

    // Метод для обработки формы с добавлением нового человека в бд
    // @ModelAttribute("person") Book person: создается новый объект и кладет в него данные из формы.

    // @Valid используется на самом классе Book, она будет проверять условия валидности, которые заданы
    // в моделе на самми полях.
    // Если появляются ошибки, то они буут записаны в специальный объект BindingResult bindingResult
    // (его надо ставить СРАЗУ после той модели, которая валидируется)
    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        //Используем наш собственный валидатор для более сложных проверок,
        //таких как проверка на одинаковый имейл в бд

        //Date dataBirthDay = person.getDataBirthDay();
//        personValidator.validate(person, bindingResult);
//        if (bindingResult.hasErrors())
//            return "books/formNew";

        bookService.save(book);

        return "redirect:/books";
    }

    // Метод для редактирования человека
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {

        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        //Используем наш собственный валидатор для более сложных проверок,
        //таких как проверка на одинаковый имейл в бд
//        personValidator.validate(person, bindingResult);
//        if (bindingResult.hasErrors())
//            return "books/edit";

        bookService.update(id, book);

        return "redirect:/books";
    }

    @PatchMapping("/{id}/releaseBook")
    public String releaseBook(@PathVariable("id") int id) {
        bookService.releaseBook(id);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookService.assign(id, person);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(name = "textInput", required = false) String userInput) {

        List<Book> bookListByNameStartingWith;

        if (StringUtils.isEmpty(userInput)) {
            bookListByNameStartingWith = new ArrayList<>();
        } else {
            //Установка нужной кодировки из поля формы, которую заполняет пользователь..
            //Удалось побороть кракозябры только таким способом
            try {
                userInput = new String(userInput.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // Обработка исключения, если не удается установить кодировку
                e.printStackTrace();
            }
            bookListByNameStartingWith = bookService.findByNameStartingWith(userInput);
        }

        model.addAttribute("bookListByNameStartingWith", bookListByNameStartingWith);
        return "books/search";
    }
}
