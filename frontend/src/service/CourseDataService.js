import axios from 'axios';
// import {store} from '../store'

const INSTRUCTOR = "in28minutes";
const COURSE_API_URL = "http://localhost:8080";
const INSTRUCTOR_API_URL = `${COURSE_API_URL}/instructors/${INSTRUCTOR}`; // url + name of instructor

class CourseDataService {
    retrieveAllCourses() {
        return axios.get(`${INSTRUCTOR_API_URL}/courses`);
    }
    retrieveCourse(name, id) {
        return axios.get(`${INSTRUCTOR_API_URL}/courses/${id}`);
    }
    updateCourse(name, id, course) {
        return axios.put(`${INSTRUCTOR_API_URL}/courses/${id}`, course);
    }
    createCourse(name, course) {
        return axios.post(`${INSTRUCTOR_API_URL}/courses/`, course);
    }
    deleteCourse(name, id) {
        return axios.delete(`${INSTRUCTOR_API_URL}/courses/${id}`);
    }
}

export default new CourseDataService();