package com.javamentor.service.studentClassService;

import com.javamentor.dto.model.student.StudentDto;
import com.javamentor.dto.model.student_class.StudentClassDto;
import com.javamentor.model.student_class.ClassLevel;
import com.javamentor.model.student_class.Deduction;
import com.javamentor.model.student_class.Recovery;
import com.javamentor.model.student_class.StudentClass;
import com.javamentor.model.student_class.SymbolClass;
import com.javamentor.model.user.Student;
import com.javamentor.model.user.Teacher;
import com.javamentor.repository.DeductionRepository;
import com.javamentor.repository.RecoveryRepository;
import com.javamentor.repository.StudentClass.StudentClassRepository;
import com.javamentor.repository.StudentClass.TransferRepository;
import com.javamentor.repository.user.StudentRepository;
import com.javamentor.repository.user.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


@Service
public class StudentClassServiceImpl implements StudentClassService {

    private final StudentClassRepository studentClassRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final TransferRepository transferRepository;
    private final DeductionRepository deductionRepository;
    private final RecoveryRepository recoveryRepository;
    private static final Logger log = Logger.getLogger(StudentClassService.class.getName());


    @Autowired
    public StudentClassServiceImpl(StudentClassRepository studentClassRepository,
                                   StudentRepository studentRepository,
                                   TeacherRepository teacherRepository,
                                   TransferRepository transferRepository,
                                   DeductionRepository deductionRepository,
                                   RecoveryRepository recoveryRepository) {
        this.studentClassRepository = studentClassRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.transferRepository = transferRepository;
        this.deductionRepository = deductionRepository;
        this.recoveryRepository = recoveryRepository;
    }

    @Override
    public void save(StudentClass studentClass) {
        studentClassRepository.save(studentClass);
    }

    @Override
    public void deleteById(Long studentClassId) {
        studentClassRepository.deleteById(studentClassId);
    }

    @Override
    public List<StudentClass> getAllStudentClasses() {
        return studentClassRepository.findAll();
    }

    @Override
    public StudentClass getStudentClassById(Long id) {
        return studentClassRepository.findById(id).orElse(null);
    }

    /**
     * метод возвращает заполненный List<StudentClassDto>
     *
     * @param teacherId      id учителя
     */
    @Override
    public List<StudentClassDto> getListStudentsClassByCurator(Long teacherId) {
        List<StudentClassDto> listStudentClassDto = studentClassRepository.getListStudentsClassByCurator(teacherId);
        listStudentClassDto.forEach(studentClassDto -> studentClassDto.setStudentList(
                studentRepository.getListStudentsDtoByStudentsClassId(studentClassDto.getId())));
        return listStudentClassDto;
    }

    @Override
    public boolean existStudentClassById(Long id) {
        return studentClassRepository.existsById(id);
    }

    /**
     * метод добавления студента в класс
     *
     * @param studentClassId id класса
     * @param studentId      id студента
     */
    @Override
    public void addStudentToStudentClass(Long studentClassId, Long studentId) {
        StudentClass studentClass = getStudentClassById(studentClassId);
        Student student = studentRepository.getStudentById(studentId);

        if (student.getStudentClass() == null) {
            student.setStudentClass(studentClass);
            studentRepository.save(student);
            log.info("студент с id "
                    + student.getId() + " "
                    + student.getLastName() + " "
                    + student.getFirstName()
                    + " зачислен в класс "
                    + studentClass.toString());
        }
    }

    /**
     * метод добавления студентов в класс
     *
     * @param studentClassId id класса
     * @param studentsIds    список id студентов
     */
    @Override
    public void addStudentListToStudentClass(Long studentClassId, List<Long> studentsIds) {
        StudentClass studentClass = getStudentClassById(studentClassId);
        studentsIds
                .forEach(student -> addStudentToStudentClass(studentClassId, student));
        log.info("студенты из списка зачислены в класс " + studentClass.toString());
    }

    /**
     * метод удаления студента из класса
     *
     * @param studentClassId id класса
     * @param studentId      id студента
     */
    @Override
    public void deleteStudentFromStudentClass(Long studentClassId, Long studentId) {
        StudentClass studentClass = getStudentClassById(studentClassId);
        Student student = studentRepository.getStudentById(studentId);
        if ((!(student.getStudentClass() == null))
                && student.getStudentClass().equals(studentClass)) {
            student.setStudentClass(null);
            studentRepository.save(student);
            log.info("студент с id "
                    + student.getId() + " "
                    + student.getLastName() + " "
                    + student.getFirstName()
                    + " удален из класса "
                    + studentClass.toString());
        }
    }

    /**
     * метод получения списка id кураторов класса
     *
     * @param studentClassId id класса
     * @return список id кураторов
     */
    @Override
    public List<Long> getCuratorListIdsByStudentClassId(Long studentClassId) {
        return studentClassRepository.getCuratorListIdsByStudentClassId(studentClassId);
    }

    /**
     * метод получения списка id всех кураторов
     *
     * @return список id кураторов
     */
    @Override
    public List<Long> getCuratorListIds() {
        return studentClassRepository.getCuratorListIds();
    }

    /**
     * метод удаления нескольких студентов из класса
     *
     * @param studentClassId id класса
     * @param studentsIds    список id студентов
     */
    @Override
    public void deleteStudentListFromStudentClass(Long studentClassId, List<Long> studentsIds) {
        StudentClass studentClass = getStudentClassById(studentClassId);
        studentsIds
                .forEach(id -> deleteStudentFromStudentClass(studentClassId, id));
        log.info("удаление студентов из класса "
                + studentClass.toString()
                + " завершено");
    }

    /**
     * метод проверки на наличие в классе данного куратора
     *
     * @param studentClassId id класса
     * @param curatorId      id куратора
     * @return true / false
     */
    @Override
    public boolean isCuratorInStudentClass(Long studentClassId, Long curatorId) {
        List<Long> curatorsIds = studentClassRepository.getCuratorListIdsByStudentClassId(studentClassId);
        return curatorsIds.contains(curatorId);
    }


    /**
     * метод добавления куратора в класс
     *
     * @param studentClassId id класса
     * @param curatorId      id куратора
     */
    @Override
    public void addCuratorToStudentClass(Long studentClassId, Long curatorId) {
        StudentClass studentClass = this.getStudentClassById(studentClassId);
        Teacher curator = teacherRepository.getTeacherById(curatorId);
        Set<Teacher> curators = studentClass.getCurators();
        if (!isCuratorInStudentClass(studentClassId, curatorId)) {
            curator.setCurator(true);
            curators.add(curator);
            studentClass.setCurators(curators);
            save(studentClass);
            log.info("преподаватель с id "
                    + curator.getId() + " "
                    + curator.getLastName() + " "
                    + curator.getFirstName()
                    + " назначен куратором в класс "
                    + studentClass.toString());
        }
    }

    /**
     * метод проверки на наличие указанных кураторов в классе
     *
     * @param studentClassId id класса
     * @param curatorsIds    список id кураторов
     * @return true/false
     */

    @Override
    public boolean isCuratorsInStudentClass(Long studentClassId, List<Long> curatorsIds) {
        List<Long> curatorsInStudentClassIds = studentClassRepository.getCuratorListIdsByStudentClassId(studentClassId);
        return curatorsInStudentClassIds.containsAll(curatorsIds);
    }

    /**
     * метод добавления нескольких кураторов в класс
     *
     * @param studentClassId id класса
     * @param curatorsIds    список id кураторов
     */
    @Override
    public void addCuratorListToStudentClass(Long studentClassId, List<Long> curatorsIds) {
        StudentClass studentClass = getStudentClassById(studentClassId);
        Set<Teacher> beforeUpdateCuratorsList = studentClass.getCurators();
        Set<Teacher> afterUpdateCuratorsList = new HashSet<>();
        curatorsIds
                .forEach(curator -> {
                    Teacher curatorToAdd = teacherRepository.getTeacherById(curator);
                    if (!beforeUpdateCuratorsList.contains(curatorToAdd)) {
                        curatorToAdd.setCurator(true);
                        afterUpdateCuratorsList.add(curatorToAdd);
                        log.info("преподаватель с id "
                                + curatorToAdd.getId() + " "
                                + curatorToAdd.getLastName() + " "
                                + curatorToAdd.getFirstName()
                                + " назначен куратором в класс "
                                + studentClass.toString());
                    }
                });
        afterUpdateCuratorsList.addAll(beforeUpdateCuratorsList);
        studentClass.setCurators(afterUpdateCuratorsList);
        save(studentClass);
        log.info("новые кураторы из списка добавлены в класс "
                + studentClass.toString());
    }

    /**
     * метод удаления куратора из класса
     *
     * @param studentClassId id класса
     * @param curatorId      id куратора
     */
    @Override
    public void deleteCuratorFromStudentClass(Long studentClassId, Long curatorId) {
        StudentClass studentClass = getStudentClassById(studentClassId);
        Teacher curator = teacherRepository.getTeacherById(curatorId);
        Set<Teacher> curators = studentClass.getCurators();
        if (curators.contains(curator)) {
            curators.remove(curator);
            List<Long> curatorListIds = getCuratorListIds();
            if (!curatorListIds.contains(curator.getId())) {
                curator.setCurator(false);
                teacherRepository.save(curator);
            }
            studentClass.setCurators(curators);
            save(studentClass);
            log.info("преподаватель с id "
                    + curator.getId() + " "
                    + curator.getLastName() + " "
                    + curator.getFirstName()
                    + " больше не является куратором класса "
                    + studentClass.toString());
        }
    }

    /**
     * метод удаления нескольких кураторов из класса
     *
     * @param studentClassId id класса
     * @param curatorsIds    список id кураторов
     */
    @Override
    public void deleteCuratorListFromStudentClass(Long studentClassId, List<Long> curatorsIds) {
        StudentClass studentClass = getStudentClassById(studentClassId);
        Set<Teacher> beforeUpdateCuratorsList = studentClass.getCurators();
        Set<Teacher> afterUpdateCuratorsList = new HashSet<>();
        beforeUpdateCuratorsList
                .forEach(curator -> {
                    if (!curatorsIds.contains(curator.getId())) {
                        afterUpdateCuratorsList.add(curator);
                    } else {
                        List<Long> curatorListIds = getCuratorListIds();
                        if (!curatorListIds.contains(curator.getId())) {
                            curator.setCurator(false);
                            teacherRepository.save(curator);
                        }
                        log.info("преподаватель с id "
                                + curator.getId() + " "
                                + curator.getLastName() + " "
                                + curator.getFirstName()
                                + " из кураторов класса "
                                + studentClass.toString()
                                + " удален. ");
                    }
                });
        studentClass.setCurators(afterUpdateCuratorsList);
        save(studentClass);
        log.info("из класса " + studentClass.toString() + " удалены кураторы");
    }

    /**
     * метод проверки класса на наличие в нем студентов.
     * возвращает true если в классе нет студентов
     *
     * @param studentClassId id класса
     * @return true/false
     */
    @Override
    public boolean isStudentClassEmpty(Long studentClassId) {
        List<Student> students = studentRepository.getStudentsByStudentClassId(studentClassId);
        return CollectionUtils.isEmpty(students);
    }

    /**
     * метод удаления класса.
     * перед удалением происходит проверка на наличие студентов в классе отдельным методом
     * также перед удалением из класса удаляются кураторы
     *
     * @param studentClassId id класса
     * @return true/false
     */
    @Override
    public boolean deleteStudentClass(Long studentClassId) {
        StudentClass studentClass = getStudentClassById(studentClassId);
        if (isStudentClassEmpty(studentClassId)) {
            List<Long> curatorsIds = getCuratorListIdsByStudentClassId(studentClassId);
            deleteCuratorListFromStudentClass(studentClassId, curatorsIds);
            deleteById(studentClassId);
            log.info(studentClass.toString() + " класс удален");
            return true;
        }
        log.warning(studentClass.toString() + " класс не может быть удален, т.к. в нем числятся студенты");
        return false;
    }

    /**
     * метод проверки существования класса на цифру и букву.
     * если класс существует - вернет true, иначе - false.
     *
     * @param classLevel       - проверяемая цифра
     * @param checkSymbolClass - проверяемая буква
     * @return true/false
     */

    @Override
    public boolean existStudentClassByNumberAndSymbol(ClassLevel classLevel, SymbolClass checkSymbolClass) {
        return studentClassRepository.existStudentClassByLevelAndSymbol(classLevel, checkSymbolClass);
    }

    /**
     * метод отчисления студента из класса
     *
     * @param deduction -  сущность с информацией об отчислении
     */
    @Transactional
    @Override
    public void deductStudent(Deduction deduction) {
        deduction.setDeductionDate(LocalDate.now());
        studentRepository.deductStudentFromClass(deduction.getStudent().getId(), deduction.getFromStudentClass().getId());
        deductionRepository.save(deduction);
        log.info(deduction.toString());
    }

    /**
     * метод восстановления студента из класса
     *
     * @param recovery -  сущность с информацией о восстановлении
     */
    @Transactional
    @Override
    public void recoverStudent(Recovery recovery) {
        recovery.setRecoveryDate(LocalDate.now());
        studentRepository.recoverStudentToClass(recovery.getStudent().getId(), recovery.getToStudentClass().getId());
        recoveryRepository.save(recovery);
        log.info("Студент " +
                recovery.getStudent().getLastName() + " " +
                recovery.getStudent().getFirstName() +
                " восстановлен в " + recovery.getToStudentClass().getClassLevel().getLevel() +
                " " + recovery.getToStudentClass().getSymbolClass().getSymbol() + " класс");
    }
}



