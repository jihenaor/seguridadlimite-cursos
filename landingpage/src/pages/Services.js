import React, { Component, Suspense, lazy } from "react";
import SecundaryNavbar from "../component/Navbar/SecundaryNavBar";
// Importaciones lazy de componentes
const ServicesDetails = lazy(() => import("../component/ServicesDetails"));
const Footer = lazy(() => import("../component/Footer/Footer"));

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

class ServicesPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      navItems: [
        // { id: 1, idnm: "home", navheading: "Inicio" },

      ],
      pos: document.documentElement.scrollTop,
      imglight: true,
      navClass: "navbar-light",
      fixTop: true
    };
  }

  componentDidMount() {
    window.addEventListener("scroll", this.scrollNavigation, true);
    // Scroll al inicio de la página
    window.scrollTo(0, 0);
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
        <SecundaryNavbar
          navItems={this.state.navItems}
          navClass={this.state.navClass}
          imglight={this.state.imglight}
          top={this.state.fixTop}
          breadcrumbs={[
            { label: "Inicio", href: "/" },
            { label: "Seguridad Industrial" }
          ]}
        />

        <div className="main-content">
          <Suspense fallback={<LoadingSpinner />}>
            <ServicesDetails />
            <Footer />
          </Suspense>
        </div>
      </>
    );
  }
}

export default ServicesPage; 