package logvinov.springcourse.Project2MyBoot.util;

import logvinov.springcourse.Project2MyBoot.models.Person;
import logvinov.springcourse.Project2MyBoot.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;
    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    //Этот метод необходим, что бы точно использовать валидатор на конкретном классе (Персон в данном случае)
    //Спринг сам подставит заместо "aClass" тот класс на котором мы хотим вызвать валидатор
    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Person person = (Person) o;

        //Этот мотод проверяет, есть ли уже в бд пользователь с таким Имейлом или нет.
        //Для его создания перегрузим метод show в ПерсонДао
        //isPresent() - проверяет, есть ли объект или нет (или он нулл)

        Person personDB = peopleService.findOne(person.getId());
        //изменение персона
        if (personDB != null) {
            String emailForm = person.getEmail();
            String emailDB = personDB.getEmail();
            if (!emailForm.equals(emailDB)) {
                if (peopleService.findByEmail(person.getEmail()).isPresent()) {
                    //метод rejectValue принимает три параметра: "Поле в котором ошибка", "Код ошибки", "Сообщение, пользователю"
                    errors.rejectValue("email", "", "This Email is already in use");
                }
            }
        //создание персона
        } else {
            if (peopleService.findByEmail(person.getEmail()).isPresent()) {
                //метод rejectValue принимает три параметра: "Поле в котором ошибка", "Код ошибки", "Сообщение, пользователю"
                errors.rejectValue("email", "", "This Email is already in use");
            }
        }

        if (person.getDataBirthDay() == null){
            errors.rejectValue("dataBirthDay", "", "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        }
        else if (person.getDataBirthDay().toString().equals("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/[0-9]{4}$")) {
            errors.rejectValue("dataBirthDay", "", "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        }

    }
}
