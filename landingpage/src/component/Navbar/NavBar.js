import React, { Component } from "react";
import {
  Navbar,
  Nav,
//  NavbarBrand,
  NavbarToggler,
  NavItem,
  NavLink,
  Container,
  Collapse,
} from "reactstrap";
import { Link } from "react-router-dom";

import logolight from "../../assets/images/logo-light.webp";

import ScrollspyNav from "./Scrollspy";

class NavbarPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isOpenMenu: false,
      isMobile: window.innerWidth <= 991,
      isLoaded: false
    };
    this.toggle = this.toggle.bind(this);
    this.handleMenuClick = this.handleMenuClick.bind(this);
    this.handleResize = this.handleResize.bind(this);
  }

  componentDidMount() {
    // Precargar el logo
    const logo = new Image();
    logo.src = logolight ;
    logo.onload = () => this.setState({ isLoaded: true });

    window.addEventListener('resize', this.handleResize);
    this.handleResize();
  }

  componentWillUnmount() {
    window.removeEventListener('resize', this.handleResize);
  }

  handleResize = () => {
    this.setState({
      isMobile: window.innerWidth <= 991
    });
  }

  toggle = () => {
    this.setState({ isOpenMenu: !this.state.isOpenMenu });
  };

  handleMenuClick = (e) => {
    e.preventDefault();
    this.setState({ isOpenMenu: false });
  
    const targetId = e.target.getAttribute('href').substring(1);
    const targetElement = document.getElementById(targetId);
  
    if (targetElement) {
      const navbarHeight = document.querySelector('.navbar-custom').offsetHeight || 0;
      const targetPosition = targetElement.getBoundingClientRect().top + window.pageYOffset - navbarHeight;
  
      window.scrollTo({
        top: targetPosition,
        behavior: 'smooth'
      });
    }
  };

  render() {
    const { isMobile, isLoaded } = this.state;
    let targetId = this.props.navItems.map((item) => {
      return item.idnm;
    });

    const mobileStyles = isMobile ? {
      position: 'absolute',
      top: '100%',
      left: 0,
      right: 0,
      backgroundColor: '#000',
      padding: '1rem',
      zIndex: 1000
    } : {};
/*
    const logoStyles = {
      opacity: isLoaded ? 1 : 0,
      transition: 'opacity 0.3s ease-in',
      display: 'block',
      width: '150px',
      height: '50px',
      objectFit: 'contain'
    };
*/
    return (
      <React.Fragment>
        <Navbar
          expand="lg"
          fixed={this.props.top === true ? "top" : ""}
          className={`${this.props.navClass} navbar-custom sticky sticky-dark px-4`}
          id="navbar"
        >
          <Container className="d-flex align-items-center position-relative">
{/* Logo de la navbar 
            <NavbarBrand href="/" className="d-flex align-items-center">

              <img
                src={this.props.imglight ? logolight : logodark}
                alt="Logo navbar Seguridad al Límite"
                style={logoStyles}
                width="150"
                height="50"
                loading="eager"
                importance="high"
              />
            </NavbarBrand>
*/}

            <NavbarToggler onClick={this.toggle} className="border-0">
              <i className="mdi mdi-menu text-white" style={{ fontSize: '24px' }}></i>
            </NavbarToggler>

            <Collapse 
              isOpen={this.state.isOpenMenu} 
              navbar 
              className="mobile-menu"
              style={mobileStyles}
            >
              <ScrollspyNav
                scrollTargetIds={targetId}
                scrollDuration="600"
                headerBackground="true"
                activeNavClass="active"
                className="navbar-collapse"
              >
                <Nav className={`${isMobile ? 'flex-column' : ''} mx-auto align-items-center`} navbar>
                  {this.props.navItems.map((item, key) => (
                    <NavItem
                      key={key}
                      className={`${item.navheading === "Home" ? "active" : ""} ${isMobile ? 'w-100 text-center mb-2' : ''}`}
                    >
                      <NavLink
                        className={`${item.navheading === "Home" ? "active" : ""} ${isMobile ? 'text-white' : ''}`}
                        href={"#" + item.idnm}
                        onClick={this.handleMenuClick}
                      >
                        {item.navheading}
                      </NavLink>
                    </NavItem>
                  ))}
                  <NavItem className="nav-item">
                    <Link
                      to="/servicios-seguridad-industrial"
                      className="nav-link"
                      onClick={() => this.setState({ isOpenMenu: false })}
                    >
                      Servicios
                    </Link>
                  </NavItem>
                </Nav>
              </ScrollspyNav>

              <div className={`d-${isMobile ? 'block' : 'none'} d-lg-flex align-items-center ms-auto`}>
                <a
                  href="https://cursos.seguridadallimite.com/#/student/inscription"
                  className={`btn btn-primary amarillo ${isMobile ? 'w-100 mb-2' : 'me-2'}`}
                >
                  Inscríbete
                </a>
                <a
                  href="https://cursos.seguridadallimite.com/#/student/certificate"
                  className={`btn btn-primary amarillo ${isMobile ? 'w-100' : ''}`}
                >
                  Certificados
                </a>
              </div>

            </Collapse>
          </Container>
        </Navbar>
      </React.Fragment>
    );
  }
}

export default NavbarPage;