package logvinov.springcourse.Project2MyBoot.dao;//package logvinov.springcourse.dao;
//
//import logvinov.springcourse.models.Person;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//
//@Component
//public class PersonDAO {
//
//    //Запросы для бд МайСКЛ (с кавычками)
////    private static final String GET_ALL_PEOPLE = "SELECT id, name, age, email FROM `springmvc_crud`.person";
////    private static final String GET_PERSON_BY_ID = "SELECT id, name, age, email FROM `springmvc_crud`.person WHERE id = ?";
////    private static final String INSERT_NEW_PERSON = "INSERT INTO `springmvc_crud`.`person` VALUES (NULL, ?, ?, ?)";
////    private static final String INSERT_NEW_PERSON_FOR_TEST = "INSERT INTO `springmvc_crud`.`person1` VALUES (?, ?, ?, ?)";
////    private static final String UPDATE_PERSON = "UPDATE `springmvc_crud`.`person` SET `name` = ?, `age` = ?, `email` = ? WHERE (`id` = ?)";
////    private static final String DELETE_PERSON = "DELETE FROM `springmvc_crud`.`person` WHERE (`id` = ?)";
//
//
//    private static final String GET_ALL_PEOPLE = "SELECT id, name, age, email, address FROM person";
//
//
//    private static final String GET_PERSON_BY_ID = "SELECT id, name, age, email, address FROM person WHERE id = ?";
//    private static final String GET_PERSON_BY_EMAIL = "SELECT id, name, age, email, address FROM person WHERE email = ?";
//    private static final String INSERT_NEW_PERSON = "INSERT INTO person (name, age, email, address) VALUES (?, ?, ?, ?);";
//    //private static final String INSERT_NEW_PERSON_FOR_TEST = "INSERT INTO `springmvc_crud_postgres`.`person1` VALUES (?, ?, ?, ?)";
//    private static final String UPDATE_PERSON = "UPDATE person SET name = ?, age = ?, email = ?, address = ? WHERE (id = ?)";
//    private static final String DELETE_PERSON = "DELETE FROM person WHERE (id = ?)";
//
//
////    private final JdbcTemplate jdbcTemplate;
////
////    @Autowired
////    public PersonDAO(JdbcTemplate jdbcTemplate) {
////        this.jdbcTemplate = jdbcTemplate;
////    }
//
//    private final SessionFactory sessionFactory;
//
//    @Autowired
//    public PersonDAO(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    //Эта аннотация открывает и закрывает транзакцию. т.е. когда в этом методе отработают все строки
//    //то будет автоматически вызван комит транзакции
//    @Transactional(readOnly = true)
//    public List<Person> index() {
//        Session session = sessionFactory.getCurrentSession();
//
//        List<Person> people = session.createQuery("select p from Person p", Person.class)
//                .getResultList();
//
//        return people;
//
//
//        // List<Person> query = jdbcTemplate.query(GET_ALL_PEOPLE, new PersonMapper());
//
//        //return jdbcTemplate.query(GET_ALL_PEOPLE, new PersonMapper()); //с использованием своего напечатанного Роумапера
//        //return jdbcTemplate.query(GET_ALL_PEOPLE, new BeanPropertyRowMapper<>(Person.class)); //с использованием встроенного Роумапера в спринг,
//        //который переводит значения из БД в поля нужного объекта.
//    }
//
//
//    //new Object[]{id} - вторй арргумент ожидает массив, значения которго будут подставлены заместо
//    // знаков вопросов в запросе к БД.
//
//    //Запрос Темплейта query возвращает нам список, а метод show должен вернуть один элемент
//
//    // .stream().findAny().orElse(null) - если в списке есть элемент с нужным айди, он будет возвращен;
//    // если его нет то будет возврещен null.
//    @Transactional(readOnly = true)
//    public Person show(int id) {
//        if (id == 0) {
//            return null;
//        }
//        Session session = sessionFactory.getCurrentSession();
//
//        Person person = session.get(Person.class, id);
//        return person;
////        if (id == 0) {
////            return null;
////        }
////        return jdbcTemplate.query(GET_PERSON_BY_ID, new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
//
//    }
//
//    ///Возвращается объкт типа Обшионал.. т.е. который может существовать а может и нет
//    //Альтернатива приверки на нулл.
//    //Проверяем в самом классе валидатора
//    @Transactional(readOnly = true)
//    public Optional<Person> show(String email) {
//        Session session = sessionFactory.getCurrentSession();
//
//        List<Person> people = session.createQuery("select p from Person p where p.email=:email", Person.class)
//                .setParameter("email", email).getResultList();
//        Optional<Person> person = people.stream().findAny();
//
//        return person;
////        return jdbcTemplate.query(GET_PERSON_BY_EMAIL, new Object[]{email}, new BeanPropertyRowMapper<>(Person.class))
////                .stream().findAny();
//
//    }
//
//    @Transactional
//    public void save(Person person) {
//        Session session = sessionFactory.getCurrentSession();
//
//        session.save(person);
//
//
//        //jdbcTemplate.update(INSERT_NEW_PERSON, person.getName(), person.getAge(), person.getEmail(), person.getAddress());
//
//    }
//
//    @Transactional
//    public void update(int id, Person updatePerson) {
//
//        Session session = sessionFactory.getCurrentSession();
//
//        Person personOld = session.get(Person.class, id);
//        personOld.setName(updatePerson.getName());
//        personOld.setAge(updatePerson.getAge());
//        personOld.setEmail(updatePerson.getEmail());
//        personOld.setAddress(updatePerson.getAddress());
//
//        //jdbcTemplate.update(UPDATE_PERSON, updatePerson.getName(), updatePerson.getAge(), updatePerson.getEmail(), updatePerson.getAddress(), id);
//
//    }
//
//    @Transactional
//    public void delete(int id) {
//        Session session = sessionFactory.getCurrentSession();
//
//        Person person = session.get(Person.class, id);
//        session.remove(person);
//        // jdbcTemplate.update(DELETE_PERSON, id);
//
//    }
//
//
//    ////////////////////////////////////////
//    ///////////////////////Тестирую производительность пакетной вставки
//    ///////////////////////Если нужно запутить код ниже, то вначале необходимо поправить бд и запросы
//    ////////////////////////////////////////
//
////
////    public void testMultipleUpdate() {
////        List<Person> people = create100people();
////
////        long before = System.currentTimeMillis();
////
////        for (Person person : people) {
////            jdbcTemplate.update(INSERT_NEW_PERSON_FOR_TEST, person.getId(), person.getName(), person.getAge(), person.getEmail());
////        }
////
////        long after = System.currentTimeMillis();
////
////        System.out.println("Time: " + (after - before));
////
////    }
////
////    public void testBatchUpdate() {
////        List<Person> people = create100people();
////
////        long before = System.currentTimeMillis();
////
////
////        jdbcTemplate.batchUpdate(INSERT_NEW_PERSON_FOR_TEST, new BatchPreparedStatementSetter() {
////            @Override
////            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
////                preparedStatement.setInt(1, people.get(i).getId());
////                preparedStatement.setString(2, people.get(i).getName());
////                preparedStatement.setInt(3, people.get(i).getAge());
////                preparedStatement.setString(4, people.get(i).getEmail());
////            }
////
////            @Override
////            public int getBatchSize() {
////                return people.size();
////            }
////        });
////
////
////        long after = System.currentTimeMillis();
////
////        System.out.println("Time: " + (after - before));
////
////    }
////
////
////    public List<Person> create100people() {
////        List<Person> people = new ArrayList<>();
////
////        for (int i = 1; i < 1000; i++) {
////            people.add(new Person(i, "Name" + i, 30, "test" + i + "mail.ru"));
////        }
////        return people;
////    }
//
//
//}
