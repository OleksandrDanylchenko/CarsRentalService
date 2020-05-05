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
            component: () => import("./components/specification/SpecificationsList"),
        },
        {
            path: "/classes",
            name: "Classes",
            component: () => import("./components/class/ClassList"),
        },
        {
            path: "/rent_centers",
            name: "RentCenters",
            component: () => import("./components/rentCenter/RentCentersList"),
        },
        {
            path: "/clients",
            name: "Clients",
            component: () => import("./components/client/ClientList"),
        },
        {
            path: "/discounts",
            name: "Discounts",
            component: () => import("./components/discount/DiscountsList"),
        },
        {
            path: "/cars",
            name: "Cars",
            component: () => import("./components/car/CarsList"),
        },
        {
            path: "/rents",
            name: "Rents",
            component: () => import("./components/rent/RentsList"),
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