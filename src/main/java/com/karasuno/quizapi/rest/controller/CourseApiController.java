package com.karasuno.quizapi.rest.controller;

import com.karasuno.quizapi.dto.*;
import com.karasuno.quizapi.entity.*;
import com.karasuno.quizapi.rest.DataResponse;
import com.karasuno.quizapi.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/courses")
public class CourseApiController {

    private CourseService courseService;

    private ModelMapper modelMapper;

    @Autowired
    public CourseApiController(CourseService courseService, ModelMapper modelMapper) {
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }

    // GET COURSE BY CATEGORY ID
    @GetMapping(value = {"/category", "/category/{id}"})
    public ResponseEntity<DataResponse> findByCategoryId(@PathVariable("id") Optional<Integer> id) throws Exception {
        DataResponse response = new DataResponse();

        try {
            HttpStatus status;

            if (id.isPresent()) {
                List<Course> coursesByCategory = courseService.findAllCoursesByCategoryId(id.get());
                status = coursesByCategory != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;

                List<CourseDTO> dataListByCategory = coursesByCategory.stream().map(this::convertToCourseDTO).collect(Collectors.toList());
                response.setContent(dataListByCategory);
            } else {
                List<Course> courses = courseService.findAllCourses();
                status = HttpStatus.OK;

                List<CourseDTO> dataList = courses.stream().map(this::convertToCourseDTO).collect(Collectors.toList());
                response.setContent(dataList);
            }

            response.setStatusCode(status.value());

            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    // GET COURSE BY ID
    @GetMapping(value = {"", "/{id}"})
    public ResponseEntity<DataResponse> findById(@PathVariable("id") Optional<Integer> id) throws Exception {
        DataResponse response = new DataResponse();

        try {
            HttpStatus status;

            if (id.isPresent()) {
                Course course = courseService.findCourseById(id.get());
                status = course != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;

                CourseDTO data = convertToCourseDTO(course);
                response.setContent(data);
            } else {
                List<Course> courses = courseService.findAllCourses();
                status = HttpStatus.OK;

                List<CourseDTO> dataList = courses.stream().map(this::convertToCourseDTO).collect(Collectors.toList());
                response.setContent(dataList);
            }

            response.setStatusCode(status.value());

            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    // CREATE/UPDATE COURSE
    @RequestMapping(value = {"", "/{id}"}, method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<DataResponse> saveCourse(@PathVariable(required = false, value = "id") Integer id, @RequestBody Course course) throws Exception {
        DataResponse response = new DataResponse();

        try {
            HttpStatus status;
            if (id == null) {
                course.setId(0);
                status = HttpStatus.CREATED;
            } else {
                status = HttpStatus.OK;
            }
            courseService.saveCourse(course);
            response.setStatusCode(status.value());

            CourseDTO data = convertToCourseDTO(course);
            response.setContent(data);
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    // SEARCH COURSE BY KEYWORD
    @GetMapping(value = "/search/byKeyword")
    public ResponseEntity<DataResponse> findByKeyword(@RequestParam(required = false, value = "keyword") String keyword) throws Exception {
        DataResponse response = new DataResponse();

        try {
            HttpStatus status;

            List<Course> courses = courseService.findByKeyword("%" + keyword + "%");
            status = courses != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;

            List<CourseDTO> dataList = courses.stream().map(this::convertToCourseDTO).collect(Collectors.toList());
            response.setContent(dataList);

            response.setStatusCode(status.value());

            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    // GET QUIZ INFO
    @GetMapping(value = "/{id}/quiz")
    public ResponseEntity<DataResponse> getQuiz(@PathVariable("id") int id,
                                                @RequestParam(value = "tag", required = false) String tag,
                                                @RequestParam(value = "level", required = false) String level) throws Exception {
        DataResponse response = new DataResponse();

        try {
            HttpStatus status;
            List<Question> questions = null;

            if (tag != null && level != null) {
                questions = courseService.getQuizByCourseIdAndTagNameAndDifficultyLevel(id, tag, level);
            } else if (tag != null && level == null) {
                questions = courseService.getQuizByCourseIdAndTagName(id, tag);
            } else if (tag == null && level != null) {
                questions = courseService.getQuizByCourseIdAndDifficultyLevel(id, level);
            } else {
                questions = courseService.getQuizByCourseId(id);
            }

            status = questions != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            response.setStatusCode(status.value());

            List<QuestionDTO> dataList = questions.stream().map(this::convertToQuestionDTO).collect(Collectors.toList());
            response.setContent(dataList);

            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    // GET CATEGORIES
    @GetMapping(value = "/categories")
    public ResponseEntity<DataResponse> findAllCategories() throws Exception {
        DataResponse response = new DataResponse();

        try {
            HttpStatus status;

            List<Category> categories = courseService.findAllCategories();
            status = categories != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            response.setStatusCode(status.value());

            List<CategoryDTO> dataList = categories.stream().map(this::convertToCategoryDTO).collect(Collectors.toList());
            response.setContent(dataList);

            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    private CourseDTO convertToCourseDTO(Course course) {
        return modelMapper.map(course, CourseDTO.class);
    }

    private QuestionDTO convertToQuestionDTO(Question question) {
        return modelMapper.map(question, QuestionDTO.class);
    }

    private CategoryDTO convertToCategoryDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

}
