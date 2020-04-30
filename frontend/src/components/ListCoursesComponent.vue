<template>
    <div class="container">
        <h3>All Courses</h3>
        <div v-show="message" class="alert alert-success">
            {{message}}
        </div>
        <div class="container">
            <table class="table">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Description</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="course in courses" :key="course.id">
                    <td>{{course.id}}</td>
                    <td>{{course.description}}</td>
                    <td>
                        <button class="btn btn-success" @click="updateCourseClicked(course.id)">Update</button>
                    </td>
                    <td>
                        <button class="btn btn-warning" @click="deleteCourseClicked(course.id)">Delete</button>
                    </td>

                </tr>
                </tbody>
            </table>
            <div class="row">
                <button class="btn btn-success" v-on:click="addCourseClicked()">Add</button>
            </div>
        </div>
    </div>
</template>

<script>
    import CourseDataService from "../service/CourseDataService";

    export default {
        name: "CoursesList",
        data() {
            return {
                courses: [],
                message: null,
                INSTRUCTOR: "in28minutes"
            };
        },
        methods: {
            refreshCourses() {
                CourseDataService.retrieveAllCourses(this.INSTRUCTOR) //HARDCODED
                    .then(response => {
                        this.courses = response.data;
                    });
            },
            addCourseClicked() {
                this.$router.push(`/courses/-1`);
            },
            updateCourseClicked(id) {
                this.$router.push(`/courses/${id}`);
            },
            deleteCourseClicked(id) {
                CourseDataService.deleteCourse(this.INSTRUCTOR, id).then(() => {
                    this.message = `Delete of course ${id} Successful`;
                    this.refreshCourses();
                });
            }
        },
        created() {
            this.refreshCourses();
        }
    };
</script>