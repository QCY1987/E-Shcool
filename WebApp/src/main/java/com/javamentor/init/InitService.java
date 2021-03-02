package com.javamentor.init;


import com.javamentor.model.direction.Direction;
import com.javamentor.model.direction.Lesson;
import com.javamentor.model.student_class.ClassLevel;
import com.javamentor.model.student_class.Deduction;
import com.javamentor.model.student_class.Recovery;
import com.javamentor.model.student_class.StudentClass;
import com.javamentor.model.student_class.SymbolClass;
import com.javamentor.model.student_class.Transfer;
import com.javamentor.model.templates.MailTemplate;
import com.javamentor.model.user.Admin;
import com.javamentor.model.user.Role;
import com.javamentor.model.user.Student;
import com.javamentor.model.user.Teacher;
import com.javamentor.repository.DeductionRepository;
import com.javamentor.repository.StudentClass.ClassLevelRepository;
import com.javamentor.repository.StudentClass.SymbolClassRepository;
import com.javamentor.repository.StudentClass.TransferRepository;
import com.javamentor.repository.direction.DirectionRepository;
import com.javamentor.repository.lesson.LessonRepository;
import com.javamentor.repository.mailTemplate.MailTemplateRepository;
import com.javamentor.repository.user.RoleRepository;
import com.javamentor.repository.user.StudentRepository;
import com.javamentor.repository.user.TeacherRepository;
import com.javamentor.repository.user.UserRepository;
import com.javamentor.service.studentClassService.StudentClassService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class InitService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final UserRepository adminRepository;
    private final ClassLevelRepository classLevelRepository;
    private final StudentClassService studentClassService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final DirectionRepository directionRepository;
    private final LessonRepository lessonRepository;
    private final MailTemplateRepository mailTemplateRepository;
    private final TransferRepository transferRepository;
    private final SymbolClassRepository symbolClassRepository;
    private final DeductionRepository deductionRepository;


    public InitService(
            StudentRepository studentRepository,
            TeacherRepository teacherRepository,
            UserRepository adminRepository,
            ClassLevelRepository classLevelRepository,
            StudentClassService studentClassService,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            DirectionRepository directionRepository,
            LessonRepository lessonRepository,
            MailTemplateRepository mailTemplateRepository,
            TransferRepository transferRepository,
            SymbolClassRepository symbolClassRepository,
            DeductionRepository deductionRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.adminRepository = adminRepository;
        this.classLevelRepository = classLevelRepository;
        this.studentClassService = studentClassService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.directionRepository = directionRepository;
        this.lessonRepository = lessonRepository;
        this.mailTemplateRepository = mailTemplateRepository;
        this.transferRepository = transferRepository;
        this.symbolClassRepository = symbolClassRepository;
        this.deductionRepository = deductionRepository;
    }

    @PostConstruct
    private void init() {
        initSymbolClass();
        initClassLevel();
        initStudentClass();
        initRoles();
        initAdmins();
        initTeachers();
        initStudents();
        initAddDirectionsAndLessons();
        initAddStudentsToClass();
        initMailTemplates();
        initTransfer();
        initDeduction();
        initRecovery();
    }

    private void initMailTemplates() {
        MailTemplate mailTemplate = new MailTemplate("<!DOCTYPE html>\n" +
                "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h3>Hello!</h3>\n" +
                "<span th:text=\"${message}\"></span>\n" +
                "<h3>Bye!</h3>\n" +
                "\n" +
                "</body>\n" +
                "</html>",
                "simple_mail");
        mailTemplateRepository.save(mailTemplate);
    }

    private void initSymbolClass() {
        symbolClassRepository.save(new SymbolClass('А'));
        symbolClassRepository.save(new SymbolClass('Б'));
        symbolClassRepository.save(new SymbolClass('В'));
        symbolClassRepository.save(new SymbolClass('Г'));
        symbolClassRepository.save(new SymbolClass('Д'));
        symbolClassRepository.save(new SymbolClass('Е'));
        symbolClassRepository.save(new SymbolClass('Ж'));
        symbolClassRepository.save(new SymbolClass('З'));
        symbolClassRepository.save(new SymbolClass('И'));
        symbolClassRepository.save(new SymbolClass('К'));
        symbolClassRepository.save(new SymbolClass('Л'));
    }

    private void initClassLevel() {
        classLevelRepository.save(new ClassLevel(1));
        classLevelRepository.save(new ClassLevel(2));
        classLevelRepository.save(new ClassLevel(3));
        classLevelRepository.save(new ClassLevel(4));
        classLevelRepository.save(new ClassLevel(5));
        classLevelRepository.save(new ClassLevel(6));
        classLevelRepository.save(new ClassLevel(7));
        classLevelRepository.save(new ClassLevel(8));
        classLevelRepository.save(new ClassLevel(9));
        classLevelRepository.save(new ClassLevel(10));
        classLevelRepository.save(new ClassLevel(11));
    }

    private void initStudentClass() {
        StudentClass studentClass1A = new StudentClass();
        ClassLevel classLevel1 = classLevelRepository.getClassLevelByLevel(1);
        studentClass1A.setClassLevel(classLevel1);
        studentClass1A.setSymbolClass(symbolClassRepository.getSymbolClassBySymbol('А'));
        studentClassService.save(studentClass1A);

        StudentClass studentClass1B = new StudentClass();
        studentClass1B.setClassLevel(classLevel1);
        studentClass1B.setSymbolClass(symbolClassRepository.getSymbolClassBySymbol('Б'));
        studentClassService.save(studentClass1B);

        StudentClass studentClass1V = new StudentClass();
        studentClass1V.setClassLevel(classLevel1);
        studentClass1V.setSymbolClass(symbolClassRepository.getSymbolClassBySymbol('В'));
        studentClassService.save(studentClass1V);

        StudentClass studentClass1G = new StudentClass();
        studentClass1G.setClassLevel(classLevel1);
        studentClass1G.setSymbolClass(symbolClassRepository.getSymbolClassBySymbol('Г'));
        studentClassService.save(studentClass1G);

        StudentClass studentClass1D = new StudentClass();
        studentClass1D.setClassLevel(classLevel1);
        studentClass1D.setSymbolClass(symbolClassRepository.getSymbolClassBySymbol('Д'));
        studentClassService.save(studentClass1D);

        StudentClass studentClass2A = new StudentClass();
        ClassLevel classLevel2 = classLevelRepository.getClassLevelByLevel(2);
        studentClass2A.setClassLevel(classLevel2);
        studentClass2A.setSymbolClass(symbolClassRepository.getSymbolClassBySymbol('А'));
        studentClassService.save(studentClass2A);

        StudentClass studentClass2B = new StudentClass();
        studentClass2B.setClassLevel(classLevel2);
        studentClass2B.setSymbolClass(symbolClassRepository.getSymbolClassBySymbol('Б'));
        studentClassService.save(studentClass2B);

        StudentClass studentClass2V = new StudentClass();
        studentClass2V.setClassLevel(classLevel2);
        studentClass2V.setSymbolClass(symbolClassRepository.getSymbolClassBySymbol('В'));
        studentClassService.save(studentClass2V);

        StudentClass studentClass2G = new StudentClass();
        studentClass2G.setClassLevel(classLevel2);
        studentClass2G.setSymbolClass(symbolClassRepository.getSymbolClassBySymbol('Г'));
        studentClassService.save(studentClass2G);

        StudentClass studentClass2D = new StudentClass();
        studentClass2D.setClassLevel(classLevel2);
        studentClass2D.setSymbolClass(symbolClassRepository.getSymbolClassBySymbol('Д'));
        studentClassService.save(studentClass2D);

        StudentClass studentClass3A = new StudentClass();
        ClassLevel classLevel3 = classLevelRepository.getClassLevelByLevel(3);
        studentClass3A.setClassLevel(classLevel3);
        studentClass3A.setSymbolClass(symbolClassRepository.getSymbolClassBySymbol('А'));
        studentClassService.save(studentClass3A);

        StudentClass studentClass4A = new StudentClass();
        ClassLevel classLevel4 = classLevelRepository.getClassLevelByLevel(4);
        studentClass4A.setClassLevel(classLevel4);
        studentClass4A.setSymbolClass(symbolClassRepository.getSymbolClassBySymbol('А'));
        studentClassService.save(studentClass4A);

        StudentClass studentClass5A = new StudentClass();
        ClassLevel classLevel5 = classLevelRepository.getClassLevelByLevel(5);
        studentClass5A.setClassLevel(classLevel5);
        studentClass5A.setSymbolClass(symbolClassRepository.getSymbolClassBySymbol('А'));
        studentClassService.save(studentClass5A);

        StudentClass studentClass6A = new StudentClass();
        ClassLevel classLevel6 = classLevelRepository.getClassLevelByLevel(5);
        studentClass6A.setClassLevel(classLevel6);
        studentClass6A.setSymbolClass(symbolClassRepository.getSymbolClassBySymbol('А'));
        studentClassService.save(studentClass6A);

    }

    private void initRoles() {
        roleRepository.save(new Role("ADMIN"));
        roleRepository.save(new Role("TEACHER"));
        roleRepository.save(new Role("STUDENT"));
    }

    private void initAdmins() {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findById(1L).get());
        Admin firstAdmin = new Admin();
        firstAdmin.setEmail("admin@mail.ru");
        firstAdmin.setFirstName("Александр");
        firstAdmin.setLastName("Курма");
        firstAdmin.setMiddleName("Владимирович");
        firstAdmin.setPassword(passwordEncoder.encode("password"));
        firstAdmin.setRoles(roles);
        firstAdmin.setEnabled(true);
        firstAdmin.setRegistrationDate(LocalDate.now());
        adminRepository.save(firstAdmin);
        for (int numberAdmin = 1; numberAdmin < 2; numberAdmin++) {
            Admin admin = new Admin();
            admin.setEmail(numberAdmin + "_admin@gmail.com");
            admin.setFirstName(numberAdmin + "_adminFirstName");
            admin.setLastName(numberAdmin + "_adminLastName");
            admin.setMiddleName(numberAdmin + "_adminMiddleName");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRoles(roles);
            admin.setEnabled(true);
            admin.setRegistrationDate(LocalDate.now());
            adminRepository.save(admin);
        }
    }

    private void initTeachers() {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findById(2L).get());
        List<Teacher> curatorList1A = new ArrayList<>();
        Teacher curator1 = new Teacher("algebra@ya.ru", "Мария", "Алгебра", "Александровна", passwordEncoder.encode("password"), roles, true, LocalDate.now());
        curatorList1A.add(curator1);
        Teacher curator2 = new Teacher("history@ya.ru", "Анна", "История", "Петровна", passwordEncoder.encode("password"), roles, true, LocalDate.now());
        curatorList1A.add(curator2);

        List<Teacher> curatorList1B = new ArrayList<>();

        Teacher curator3 = new Teacher("geometry@ya.ru", "Валерия", "Рисование", "Евгеньевна", passwordEncoder.encode("password"), roles, true, LocalDate.now());
        curatorList1B.add(curator3);
        Teacher curator4 = new Teacher("geometry@ya.ru", "Екатерина", "Геометрия", "Васильевна", passwordEncoder.encode("password"), roles, true, LocalDate.now());
        curatorList1B.add(curator4);
        curatorList1B.add(curator2);

        List<Teacher> curatorList2A = new ArrayList<>();

        Teacher curator5 = new Teacher("leader@ya.ru", "Юлия", "Руководитель", "Тимуровна", passwordEncoder.encode("password"), roles, true, LocalDate.now());
        curatorList2A.add(curator5);
        Teacher curator6 = new Teacher("allcurators@ya.ru", "Юлия", "Завуч", "Ивановна", passwordEncoder.encode("password"), roles, true, LocalDate.now());
        curatorList2A.add(curator6);
        curatorList2A.add(curator4);
        curatorList1B.add(curator5);
        curatorList1A.add(curator5);

        curatorList2A.add(curator4);
        curatorList1B.add(curator5);
        curatorList1A.add(curator5);

        teacherRepository.saveAll(curatorList1A);
        List<Long> curatorListIds1A = new ArrayList<>();
        curatorList1A.forEach(c -> curatorListIds1A.add(c.getId()));
        teacherRepository.saveAll(curatorList1B);
        List<Long> curatorListIds1B = new ArrayList<>();
        curatorList1B.forEach(c -> curatorListIds1B.add(c.getId()));
        teacherRepository.saveAll(curatorList2A);
        List<Long> curatorListIds2A = new ArrayList<>();
        curatorList2A.forEach(c -> curatorListIds2A.add(c.getId()));

        studentClassService.addCuratorListToStudentClass(1L, curatorListIds1A);
        studentClassService.addCuratorListToStudentClass(2L, curatorListIds1B);
        studentClassService.addCuratorListToStudentClass(6L, curatorListIds2A);
    }

    private void initStudents() {

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findById(3L).get());
        List<Student> studentList1A = new ArrayList<>();

        Student student1 = new Student("voron@ya.ru", "Антон", "Воронов", "Александрович", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2013, 8, 13), LocalDate.of(2020, 9, 1));
        studentList1A.add(student1);
        Student student2 = new Student("voronf@ya.ru", "Федор", "Воронов", "Семенович", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2013, 5, 2), LocalDate.of(2020, 9, 1));
        studentList1A.add(student2);
        Student student3 = new Student("vorona@ya.ru", "Анна", "Воронова", "Владимировна", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2013, 3, 23), LocalDate.of(2020, 9, 1));
        studentList1A.add(student3);
        Student student4 = new Student("ivoronova@ya.ru", "Инна", "Воронова", "Радионовна", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2013, 2, 14), LocalDate.of(2020, 9, 1));
        studentList1A.add(student4);
        studentRepository.saveAll(studentList1A);
        List<Long> studentListIds1A = new ArrayList<>();
        studentList1A.forEach(student -> studentListIds1A.add(student.getId()));

        studentClassService.addStudentListToStudentClass(1L, studentListIds1A);

        List<Student> studentList1B = new ArrayList<>();
        Student student5 = new Student("lvoronova@ya.ru", "Любовь", "Воронова", "Антоновна", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2013, 3, 5), LocalDate.of(2020, 9, 1));
        studentList1B.add(student5);
        Student student6 = new Student("ldrozdova@ya.ru", "Любовь", "Дроздова", "Константиновна", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2013, 2, 11), LocalDate.of(2020, 9, 1));
        studentList1B.add(student6);
        Student student7 = new Student("idrozdova@ya.ru", "Инга", "Дроздова", "Макаровна", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2013, 7, 17), LocalDate.of(2020, 9, 1));
        studentList1B.add(student7);
        Student student8 = new Student("innadrozdova@ya.ru", "Инна", "Дроздова", "Прохоровна", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2013, 1, 7), LocalDate.of(2020, 9, 1));
        studentList1B.add(student8);
        studentRepository.saveAll(studentList1B);
        List<Long> studentListIds1B = new ArrayList<>();
        studentList1B.forEach(c -> studentListIds1B.add(c.getId()));
        studentClassService.addStudentListToStudentClass(2L, studentListIds1B);

        List<Student> studentList2A = new ArrayList<>();
        Student student9 = new Student("adrozdov@ya.ru", "Антон", "Дроздов", "Васильевич", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2012, 1, 7), LocalDate.of(2019, 9, 1));
        studentList2A.add(student9);
        Student student10 = new Student("drozdovf@ya.ru", "Федор", "Дроздов", "Алексеевич", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2012, 4, 30), LocalDate.of(2019, 9, 1));
        studentList2A.add(student10);
        Student student11 = new Student("orlov@ya.ru", "Виталий", "Орлов", "Борисович", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2012, 1, 26), LocalDate.of(2019, 9, 1));
        studentList2A.add(student11);
        Student student12 = new Student("orlovd@ya.ru", "Дмитрий", "Орлов", "Дмитриевич", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2012, 5, 8), LocalDate.of(2019, 9, 1));
        studentList2A.add(student12);
        Student student13 = new Student("vsh@ya.ru", "Василий", "Шукшин", "Лукич", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2012, 4, 17), LocalDate.of(2019, 9, 1));
        studentList2A.add(student13);

        studentRepository.saveAll(studentList2A);
        List<Long> studentListIds2A = new ArrayList<>();
        studentList2A.forEach(c -> studentListIds2A.add(c.getId()));
        studentClassService.addStudentListToStudentClass(6L, studentListIds2A);

        List<Student> studentList2B = new ArrayList<>();
        Student student14 = new Student("ash@ya.ru", "Антон", "Шукшин", "Захарович", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2012, 2, 17), LocalDate.of(2019, 9, 1));
        studentList2B.add(student14);
        Student student15 = new Student("antonov@ya.ru", "Антон", "Антонов", "Антонович", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2012, 4, 29), LocalDate.of(2019, 9, 1));
        studentList2B.add(student15);
        Student student16 = new Student("sherbant@ya.ru", "Антон", "Щербаков", "Александрович", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2012, 11, 17), LocalDate.of(2019, 9, 1));
        studentList2B.add(student16);
        Student student17 = new Student("sherbant@ya.ru", "Антон", "Щербаков", "Андреевич", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2012, 10, 6), LocalDate.of(2019, 9, 1));
        studentList2B.add(student17);
        Student student18 = new Student("esherbakov@ya.ru", "Егор", "Щербаков", "Егорович", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2012, 4, 18), LocalDate.of(2019, 9, 1));
        studentList2B.add(student18);
        Student student19 = new Student("egant@ya.ru", "Егор", "Антонов", "Сергеевич", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2012, 6, 16), LocalDate.of(2019, 9, 1));
        studentList2B.add(student19);
        Student student20 = new Student("tchud@ya.ru", "Тимур", "Чудаков", "Антонович", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2012, 8, 1), LocalDate.of(2019, 9, 1));
        studentList2B.add(student20);
        Student student21 = new Student("achud@ya.ru", "Андрей", "Чудаков", "Михайлович", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2012, 4, 4), LocalDate.of(2019, 9, 1));
        studentList2B.add(student21);
        Student student22 = new Student("ptyol@ya.ru", "Ольга", "Птицына", "Михайловна", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2012, 1, 13), LocalDate.of(2019, 9, 1));
        studentList2B.add(student22);

        studentRepository.saveAll(studentList2B);
        List<Long> studentListIds2B = new ArrayList<>();
        studentList2B.forEach(c -> studentListIds2B.add(c.getId()));
        studentClassService.addStudentListToStudentClass(7L, studentListIds2B);
        List<Student> studentList3A = new ArrayList<>();
        Student student23 = new Student("efmih@ya.ru", "Михаил", "Ефимов", "Евгеньевич", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2012, 1, 13), LocalDate.of(2019, 9, 1));
        studentList3A.add(student23);


        studentRepository.saveAll(studentList3A);
        List<Long> studentListIds3A = new ArrayList<>();
        studentList3A.forEach(c -> studentListIds3A.add(c.getId()));
        studentClassService.addStudentListToStudentClass(11L, studentListIds3A);

        List<Student> studentList4A = new ArrayList<>();
        Student student30 = new Student("albelous@ya.ru", "Алиса", "Белоусова", "Аркадиевна", passwordEncoder.encode("password"), roles, true, LocalDate.now(), LocalDate.of(2012, 1, 13), LocalDate.of(2019, 9, 1));
        studentList4A.add(student30);

        studentRepository.saveAll(studentList4A);
        List<Long> studentListIds4A = new ArrayList<>();
        studentList4A.forEach(c -> studentListIds4A.add(c.getId()));
        studentClassService.addStudentListToStudentClass(12L, studentListIds4A);

    }

    private void initTransfer() {
        Transfer transfer1 =
                new Transfer(
                        "Причина",
                        studentRepository.getStudentById(8L),
                        studentClassService.getStudentClassById(1L),
                        studentClassService.getStudentClassById(2L),
                        LocalDate.now());
        transferRepository.save(transfer1);
    }

    private void initDeduction() {
        Deduction deduction1 = new Deduction("Отчислен без причины", studentRepository.getStudentById(9L), studentClassService.getStudentClassById(1L), LocalDate.now());
        studentClassService.deductStudent(deduction1);
    }

    private void initRecovery() {
        Recovery recovery = new Recovery("Приказ 404", studentRepository.getStudentById(9L), studentClassService.getStudentClassById(2L), LocalDate.now());
        studentClassService.recoverStudent(recovery);
    }


    void initAddDirectionsAndLessons() {
        List<Direction> directions = new ArrayList<>();
        Direction russianLanguage = new Direction("русский язык");
        directions.add(russianLanguage);
        Direction foreignLanguage = new Direction("иностранный язык");
        directions.add(foreignLanguage);
        Direction mathematics = new Direction("математика");
        directions.add(mathematics);
        Direction painting = new Direction("рисование");
        directions.add(painting);
        Direction music = new Direction("музыка");
        directions.add(music);
        Direction nature = new Direction("природоведение");
        directions.add(nature);
        Direction geography = new Direction("география");
        directions.add(geography);
        Direction botanics = new Direction("ботаника");
        directions.add(botanics);
        Direction counting = new Direction("устный счет");
        directions.add(counting);
        Direction drawing = new Direction("черчение");
        directions.add(drawing);
        Direction training = new Direction("физкультура");
        directions.add(training);
        Direction geometry = new Direction("геометрия");
        directions.add(geometry);
        Direction history = new Direction("история");
        directions.add(history);

        directionRepository.saveAll(directions);


        List<Lesson> lessons = new ArrayList<>();
        Lesson lesson1 = new Lesson();
        lesson1.setDirection(music);
        lesson1.setTeacher(teacherRepository.getTeacherById(7L));
        lesson1.setStartLesson(LocalDateTime.of(2020, 11, 16, 9, 0));
        lesson1.setEndLesson(LocalDateTime.of(2020, 11, 16, 9, 45));
        StudentClass class1a = studentClassService.getStudentClassById(1L);
        StudentClass class1b = studentClassService.getStudentClassById(2L);
        Set<StudentClass> lesson1Classes = new HashSet<>();
        lesson1Classes.add(class1a);
        lesson1Classes.add(class1b);
        lesson1.setStudentClass(lesson1Classes);

        Lesson lesson2 = new Lesson();
        lesson2.setDirection(history);
        lesson2.setStartLesson(LocalDateTime.of(2020, 11, 16, 10, 0));
        lesson2.setEndLesson(LocalDateTime.of(2020, 11, 16, 10, 45));
        lesson2.setTeacher(teacherRepository.getTeacherById(3L));
        StudentClass class2a = studentClassService.getStudentClassById(6L);
        Set<StudentClass> lesson2Classes = new HashSet<>();
        lesson2Classes.add(class2a);
        lesson2.setStudentClass(lesson2Classes);


        Lesson lesson3 = new Lesson();
        lesson3.setDirection(geometry);
        lesson3.setStartLesson(LocalDateTime.of(2020, 11, 16, 11, 0));
        lesson3.setEndLesson(LocalDateTime.of(2020, 11, 16, 11, 45));
        lesson3.setTeacher(teacherRepository.getTeacherById(6L));
        StudentClass class2b = studentClassService.getStudentClassById(7L);
        Set<StudentClass> lesson3Classes = new HashSet<>();
        lesson3Classes.add(class2b);
        lesson3.setStudentClass(lesson3Classes);

        Lesson lesson4 = new Lesson();
        lesson4.setDirection(music);
        lesson4.setStartLesson(LocalDateTime.of(2020, 11, 16, 12, 0));
        lesson4.setEndLesson(LocalDateTime.of(2020, 11, 16, 12, 45));
        lesson4.setTeacher(teacherRepository.getTeacherById(5L));
        StudentClass class1v = studentClassService.getStudentClassById(3L);
        Set<StudentClass> lesson4Classes = new HashSet<>();
        lesson4Classes.add(class1v);
        lesson4.setStudentClass(lesson4Classes);

        Lesson lesson5 = new Lesson();
        lesson5.setDirection(history);
        lesson5.setStartLesson(LocalDateTime.of(2020, 11, 16, 13, 0));
        lesson5.setEndLesson(LocalDateTime.of(2020, 11, 16, 13, 45));
        lesson5.setTeacher(teacherRepository.getTeacherById(3L));
        StudentClass class2g = studentClassService.getStudentClassById(9L);
        Set<StudentClass> lesson5Classes = new HashSet<>();
        lesson5Classes.add(class2g);
        lesson5.setStudentClass(lesson5Classes);

        Lesson lesson6 = new Lesson();
        lesson6.setDirection(history);
        lesson6.setStartLesson(LocalDateTime.of(2020, 11, 16, 14, 0));
        lesson6.setEndLesson(LocalDateTime.of(2020, 11, 16, 14, 45));
        lesson6.setTeacher(teacherRepository.getTeacherById(3L));
        StudentClass class2d = studentClassService.getStudentClassById(10L);
        Set<StudentClass> lesson6Classes = new HashSet<>();
        lesson6Classes.add(class2d);
        lesson6.setStudentClass(lesson6Classes);


        lessons.add(lesson1);
        lessons.add(lesson2);
        lessons.add(lesson3);
        lessons.add(lesson4);
        lessons.add(lesson5);

        lessonRepository.saveAll(lessons);


    }

    void initAddStudentsToClass() {
        List<Student> studentList = studentRepository.findAll();
        for (Student student : studentList) {
            student.setClassLevel(classLevelRepository
                    .getClassLevelByLevel(LocalDate.now().getYear() - student.getBirthday().getYear() - 7));
        }
        studentRepository.saveAll(studentList);
    }

}
