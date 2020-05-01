import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

const router = new Router({
    mode: 'history', // Use browser history
    routes: [
        {
            path: "/",
            name: "Home",
            component: () => import("./components/Landing"),
        },
        {
            path: "/models",
            name: "Models",
            component: () => import("./components/model/ModelsList"),
        },
        {
            path: "/specifications",
            name: "Specifications",
            component: () => import("./components/specification/SpecificationList"),
        },
        {
            path: "/courses",
            name: "Courses",
            component: () => import("./components/ListCoursesComponent"),
        },
        {
            path: "/courses/:id",
            name: "Course Details",
            component: () => import("./components/CourseComponent"),
        },
    ]
});

export default router;