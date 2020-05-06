import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

const router = new Router({
    mode: 'history', // Use browser history
    routes: [
        {
            path: "/",
            name: "Home",
            component: () => import("./components/site/Landing"),
        },
        {
            path: "/models",
            name: "Models",
            component: () => import("./components/admin/model/ModelsList"),
        },
        {
            path: "/specifications",
            name: "Specifications",
            component: () => import("./components/admin/specification/SpecificationsList"),
        },
        {
            path: "/classes",
            name: "Classes",
            component: () => import("./components/admin/class/ClassList"),
        },
        {
            path: "/rent_centers",
            name: "RentCenters",
            component: () => import("./components/admin/rentCenter/RentCentersList"),
        },
        {
            path: "/clients",
            name: "Clients",
            component: () => import("./components/admin/client/ClientList"),
        },
        {
            path: "/discounts",
            name: "Discounts",
            component: () => import("./components/admin/discount/DiscountsList"),
        },
        {
            path: "/cars",
            name: "Cars",
            component: () => import("./components/admin/car/CarsList"),
        },
        {
            path: "/rents",
            name: "Rents",
            component: () => import("./components/admin/rent/RentsList"),
        },
        {
            path: "/complaints",
            name: "Complaints",
            component: () => import("./components/admin/complaint/ComplaintsList"),
        }
    ]
});

export default router;