import React, { Component, Suspense, lazy } from "react";

import Navbar from "../component/Navbar/NavBar";

import Section from "../component/Section";

// Importaciones lazy de componentes
const Courses = lazy(() => import("../component/Courses"));
const Services = lazy(() => import("../component/Services"));
const Products = lazy(() => import("../component/Products"));
const QualityPolicy = lazy(() => import("../component/QualityPolicy"));
const AboutUs = lazy(() => import("../component/AboutUs"));
const TeamMembers = lazy(() => import("../component/TeamMembers"));
const Practices = lazy(() => import("../component/Practices"));
const Contact = lazy(() => import("../component/Contact"));
const Footer = lazy(() => import("../component/Footer/Footer"));
const Clients = lazy(() => import("../component/Clients"));

// Componente de carga
const LoadingSpinner = () => (
  <div style={{ 
    minHeight: '200px', 
    display: 'flex', 
    alignItems: 'center', 
    justifyContent: 'center',
    backgroundColor: 'rgba(255, 255, 255, 0.8)'
  }}>
    <div className="spinner-border text-primary" role="status" style={{ width: '3rem', height: '3rem' }}>
      <span className="visually-hidden">Cargando...</span>
    </div>
  </div>
);

class HomePage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      navItems: [
        { id: 1, idnm: "home", navheading: "Inicio" },
        { id: 2, idnm: "courses", navheading: "Cursos" },
        { id: 3, idnm: "services", navheading: "Servicios" },
        { id: 4, idnm: "products", navheading: "Productos" },
        { id: 5, idnm: "team", navheading: "Personal" },
        { id: 6, idnm: "clients", navheading: "Clientes" },
        { id: 7, idnm: "contact", navheading: "Contacto" },
      ],
      pos: document.documentElement.scrollTop,
      imglight: true,
      navClass: "navbar-light",
      fixTop : true
    };
  }

  componentDidMount() {
    window.addEventListener("scroll", this.scrollNavigation, true);
  }

  componentWillUnmount() {
    window.removeEventListener("scroll", this.scrollNavigation, true);
  }

  scrollNavigation = () => {
    var scrollup = document.documentElement.scrollTop;
    if (scrollup > this.state.pos) {
      this.setState({ navClass: "nav-sticky", imglight: false });
    } else {
      this.setState({ navClass: "navbar-light", imglight: true });
    }
  };

  render() {
    return (
      <>
          <Navbar
            navItems={this.state.navItems}
            navClass={this.state.navClass}
            imglight={this.state.imglight}
            top={this.state.fixTop}
          />

          <Section />

          <Suspense fallback={<LoadingSpinner />}>
            <Courses />
            <Services />
            <Products />
            <AboutUs />
            <TeamMembers />
            <QualityPolicy />
            <Practices />
            <Clients />
            <Contact />
            <Footer />
          </Suspense>
      </>
    );
  }
}
export default HomePage;
  