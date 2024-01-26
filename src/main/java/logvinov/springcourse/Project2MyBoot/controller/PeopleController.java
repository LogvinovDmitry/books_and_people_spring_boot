package logvinov.springcourse.Project2MyBoot.controller;

import logvinov.springcourse.Project2MyBoot.models.Book;
import logvinov.springcourse.Project2MyBoot.models.Person;
import logvinov.springcourse.Project2MyBoot.service.PeopleService;
import logvinov.springcourse.Project2MyBoot.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    // Автоматически создает Бин personDAO и подставляет его в этот конструктор.
    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    //Адрес этого метода будет /people
    @GetMapping()
    public String index(Model model) {
        // Получим всех людей из DAO и передадим на отображение в представление

        List<Person> people = peopleService.findAll();
        //List<Person> people = peopleService.met1();
//
//        itemService.findByName("333333");
//        itemService.findByOwner(peopleService.findAll().get(0));
//        peopleService.test();

        //personDAOTestNplus1.testNplus1();

        model.addAttribute("people", people);
        return "people/index";
    }

    // Во время выполнения приложения, в строке адреса мы можем задать id. С помощью аннотации @PathVariable
    // этот айдишник будет доступен внутри метода show. (И будет помещен в переменную int id).
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        // Получим оного человека по id из DAO и передадим на отображение в представление.

        Person person = peopleService.findOne(id);
//        Optional<Person> one = peopleService.findOne(id);
//        one.ifPresent(e -> model.addAttribute("person", e));

        //List<Book> bookList = person.getBookList();

        List<Book> booksByPersonId = peopleService.getBooksByPersonId(id);

        model.addAttribute("person", person);
        model.addAttribute("booksByPersonId", booksByPersonId);
        return "people/show";
    }

    // Метод добавления нового человека.
    // В аргументы передаем Модель, в которой помещен объект new Person(),
    // для того, что бы этот объект был доступен в форме Таймлив.
    //В этой форме Таймлив, спомощью его синтаксиса, будем сразу назначать значение полей объекта new Person().
//    @GetMapping("/new")
//    public String newPerson(Model model) {
//        model.addAttribute("person", new Person());
//
//        return "people/formNew";
//    }

    // Эквивалент метода выше.
    // @ModelAttribute создаст объект класса Person с пустыми полями и положит его в модель.
    // А к обекту, который уже положен в модель есть доступ в представлении, где мы сразу и назначаем поля
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {

        return "people/formNew";
    }

    // Метод для обработки формы с добавлением нового человека в бд
    // @ModelAttribute("person") Person person: создается новый объект и кладет в него данные из формы.

    // @Valid используется на самом классе Person, она будет проверять условия валидности, которые заданы
    // в моделе на самми полях.
    // Если появляются ошибки, то они буут записаны в специальный объект BindingResult bindingResult
    // (его надо ставить СРАЗУ после той модели, которая валидируется)
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        //Используем наш собственный валидатор для более сложных проверок,
        //таких как проверка на одинаковый имейл в бд

        Date dataBirthDay = person.getDataBirthDay();
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) return "people/formNew";

        peopleService.save(person);

        return "redirect:/people";
    }

    // Метод для редактирования человека
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {

        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id) {
        //Используем наш собственный валидатор для более сложных проверок,
        //таких как проверка на одинаковый имейл в бд
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) return "people/edit";

        peopleService.update(id, person);

        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }

}
