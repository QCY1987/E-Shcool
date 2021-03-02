package com.javamentor.service.teacherService;

import com.javamentor.config.SimplePasswordGenerator;
import com.javamentor.dto.model.teacher.TeacherDto;
import com.javamentor.dto.model.UserDto;
import com.javamentor.model.user.Role;
import com.javamentor.model.user.Student;
import com.javamentor.model.user.Teacher;
import com.javamentor.repository.StudentClass.StudentClassRepository;
import com.javamentor.repository.user.RoleRepository;
import com.javamentor.repository.user.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final RoleRepository roleRepository;

    private static final Logger log = Logger.getLogger(Student.class.getName());

    private final SimplePasswordGenerator passwordGenerator;

    private final BCryptPasswordEncoder passwordEncoder;

    private final TeacherRepository teacherRepository;

    private final StudentClassRepository studentClassRepository;

    @Autowired
    public TeacherServiceImpl(
            RoleRepository roleRepository, SimplePasswordGenerator passwordGenerator,
            BCryptPasswordEncoder passwordEncoder,
            TeacherRepository teacherRepository, StudentClassRepository studentClassRepository) {
        this.roleRepository = roleRepository;
        this.passwordGenerator = passwordGenerator;
        this.passwordEncoder = passwordEncoder;
        this.teacherRepository = teacherRepository;
        this.studentClassRepository = studentClassRepository;
    }

    @Override
    public void save(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public List<TeacherDto> getAllTeacherDto() {
        List<TeacherDto> teacherDtoList = teacherRepository.findAllTeachersDto();
        teacherDtoList.forEach(teacherDto -> {
            teacherDto.setStudentClassesName(studentClassRepository.getStudentClassesNameByTeacherId(teacherDto.getId()));
        });
        return teacherDtoList;
    }

    @Override
    public List<TeacherDto> getAllTeacherDtoBySearch(String search) {
        List<TeacherDto> teacherDtoList = teacherRepository.findAllTeachersDtoBySearch(search);
        teacherDtoList.forEach(teacherDto -> {
            teacherDto.setStudentClassesName(studentClassRepository.getStudentClassesNameByTeacherId(teacherDto.getId()));
        });
        return teacherDtoList;
    }

    @Override
    public List<UserDto> getAllTeacherDtoIsCurator() {
        return teacherRepository.findAllTeachersIsCurator();
    }

    @Override
    public List<UserDto> getAllTeacherDtoIsNotCurator() {
        return teacherRepository.findAllTeacherIsNotCurator();
    }

    @Override
    public boolean existsTeacherByEmail(String email) {
        return teacherRepository.existsByEmail(email);
    }

    @Override
    public UserDto getTeacherDTOById(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        assert teacher != null;
        return new UserDto(teacher);
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existTeacherById(Long id) {
        return teacherRepository.existsById(id);
    }

    @Override
    public boolean saveTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
        return true;
    }

    /**
     * Метод сохраняет нового учителя, если его нет в базе, и редактирует
     * существующего, если в базе такой учитель найден.
     * В качестве пароля для нового учителя выступает метод генерации случайных символов.
     *
     * @param teacherDto - принимает с фронта данные об учителе
     * @return String - возвращает строку с информацией о выполненных действиях
     */
    @Override
    public String saveTeacherByDTO(TeacherDto teacherDto) {
        try {
            if (teacherDto.getId() == null) {
                Set<Role> roles = Collections.singleton(roleRepository.findRoleByName("TEACHER"));
                String randomPass = passwordGenerator.randomPassword(10);
                Teacher teacher = new Teacher(teacherDto, passwordEncoder.encode(randomPass), roles);
                teacherRepository.save(teacher);
                log.info("Учитель " + teacher.getLastName() + " "
                        + teacher.getFirstName()
                        + " был добавлен.");

                return "Учитель " + teacher.getFirstName() + " "
                        + teacher.getLastName() + " успешно добавлен.";

            } else {
                Teacher existingTeacher = teacherRepository.getTeacherById(teacherDto.getId());
                existingTeacher.setFirstName(teacherDto.getFirstName());
                existingTeacher.setLastName(teacherDto.getLastName());
                existingTeacher.setMiddleName(teacherDto.getMiddleName());
                existingTeacher.setRegistrationDate(teacherDto.getRegistrationDate());
                existingTeacher.setEnabled(teacherDto.getEnabled());
                existingTeacher.setEmail(teacherDto.getEmail());
                teacherRepository.save(existingTeacher);
                log.info("Данные учителя с id "
                        + existingTeacher.getId() + " "
                        + existingTeacher.getLastName() + " "
                        + existingTeacher.getFirstName()
                        + " были отредактированы.");

                return "Учитель " + existingTeacher.getFirstName() + " "
                        + existingTeacher.getLastName() + " успешно обновлен.";
            }
        } catch (DataAccessException dae) {
            return "Ошибка сохранения учителя " + dae.getMessage();
        }
    }
}
