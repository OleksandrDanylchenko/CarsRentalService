import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);

const router = new Router({
  mode: "history", // Use browser history
  routes: [
    {
      path: "/",
      name: "Landing",
      component: () => import("./components/site/Landing"),
    },
    {
      path: "/carSelect",
      name: "Car select",
      component: () => import("./components/site/CarSelectComponents"),
    },
    {
      path: "/maintenance",
      name: "Maintenance",
      component: () => import("./components/site/common/MaintenancePage"),
    },
    {
      path: "/models",
      name: "Models",
      component: () => import("./components/admin/model/ModelsList"),
    },
    {
      path: "/specifications",
      name: "Specifications",
      component: () =>
        import("./components/admin/specification/SpecificationsList"),
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
    },
  ],
  scrollBehavior () {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve({ x: 0, y: 0 })
      }, 150)
    })
  }
});

export default router;
