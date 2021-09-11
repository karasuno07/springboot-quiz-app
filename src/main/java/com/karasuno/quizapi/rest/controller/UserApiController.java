package com.karasuno.quizapi.rest.controller;

import com.karasuno.quizapi.dto.*;
import com.karasuno.quizapi.entity.*;
import com.karasuno.quizapi.rest.DataResponse;
import com.karasuno.quizapi.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserApiController {

    private UserService userService;

    private CourseService courseService;

    private ModelMapper modelMapper;

    @Autowired
    public UserApiController(UserService userService, CourseService courseService, ModelMapper modelMapper) {
        this.userService = userService;
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = {"", "/{id}"})
    public ResponseEntity<DataResponse> findById(@PathVariable Optional<Integer> id) throws Exception {

        DataResponse response = new DataResponse();

        try {
            HttpStatus status;

            if (id.isPresent()) {
                User user = userService.findById(id.get());
                status = user != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;

                UserDTO data = convertToUserDTO(user);
                response.setContent(data);
            } else {
                List<User> users = userService.findAll();
                status = HttpStatus.OK;

                List<UserDTO> dataList = users.stream().map(this::convertToUserDTO).collect(Collectors.toList());
                response.setContent(dataList);
            }

            response.setStatusCode(status.value());

            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @GetMapping(value = "/courses/byUserId/{id}")
    public ResponseEntity<DataResponse> getUserCourses(
            @PathVariable int id,
            @RequestParam(required = false, value = "sortBy") String sortBy,
            @RequestParam(required = false, value = "order") String order) {
        DataResponse response = new DataResponse();

        HttpStatus status;

        List<Course> courses = courseService.findAllCoursesByUserId(id);

        List<UserCourseDTO> dataList = courses.stream().map(this::convertToUserCourseDTO).collect(Collectors.toList());

        for (int i = 0; i < dataList.size(); i++) {
            Date purchasedDate = courses.get(i).getUsersCourses().get(0).getPurchasedDate();
            Date recentActiveDate = courses.get(i).getUsersCourses().get(0).getRecentActiveDate();

            dataList.get(i).setPurchasedDate(purchasedDate);
            dataList.get(i).setRecentActiveDate(recentActiveDate);
        }

        try {
            if (sortBy.equals("active-date")) {
                Collections.sort(dataList, (d1, d2) -> d1.getRecentActiveDate().compareTo(d2.getRecentActiveDate()));
            }

            if (order.equals("desc")) {
                Collections.reverse(dataList);
            }
        } catch (Exception e) {
        } finally {
            status = dataList != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            response.setStatusCode(status.value());
            response.setContent(dataList);
        }

        return new ResponseEntity<>(response, status);
    }

    @PostMapping(value = "/add-course")
    public ResponseEntity<Boolean> addCourseToUserWatchList(@RequestBody UserCourseIdDTO id) {

        userService.addCourseToWatchList(id.getUserId(), id.getCourseId());

        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{userId}/course/{courseId}/update-active-date")
    public ResponseEntity<Boolean> updateActiveDate(
            @PathVariable int userId,
            @PathVariable int courseId,
            @RequestBody Date date) {

        UserCourse userCourse = userService.findByUserIdAndCourseId(userId, courseId);
        userCourse.setRecentActiveDate(date);

        userService.saveUserCourse(userCourse);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @DeleteMapping (value = "/remove-course")
    public ResponseEntity<Boolean> removeCourseFromUserWatchList(@RequestBody UserCourseIdDTO id) {

        userService.removeCourseFromWatchList(id.getUserId(), id.getCourseId());

        return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/search/isUnique")
    public ResponseEntity<DataResponse> isUnique(@RequestParam("username") String username) {

        DataResponse response = new DataResponse();

        try {
            User user = userService.findByUsername(username);
            response.setStatusCode(HttpStatus.OK.value());
            response.setContent(user == null);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.OK.value());
            response.setContent(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @RequestMapping(value = {"", "/{id}"}, method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<DataResponse> save(@PathVariable(required = false) Integer id, @RequestBody User user) throws Exception {
        DataResponse response = new DataResponse();

        try {
            HttpStatus status;
            if (id == null) {
                user.setId(0);
                status = HttpStatus.CREATED;
            } else {
                status = HttpStatus.OK;
            }
            userService.save(user);
            response.setStatusCode(status.value());

            UserDTO data = convertToUserDTO(user);
            response.setContent(data);
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<DataResponse> delete(@PathVariable int id) throws Exception {
        DataResponse response = new DataResponse();

        try {
            userService.delete(id);
            response.setStatusCode(HttpStatus.NO_CONTENT.value());
            response.setContent("Deleted entity with id: " + id);

            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new Exception();
        }
    }


    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private UserCourseDTO convertToUserCourseDTO(Course course) {
        return modelMapper.map(course, UserCourseDTO.class);
    }
}
