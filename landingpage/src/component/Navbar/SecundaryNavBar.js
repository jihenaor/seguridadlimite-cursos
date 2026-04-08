import React, { Component } from "react";
import {
  Navbar,
  Nav,
  NavbarToggler,
  NavItem,
  NavLink,
  Container,
  Collapse,
} from "reactstrap";
import { Link } from "react-router-dom";

import logolight from "../../assets/images/logo-circular-light-200x200.webp";


// Estilos constantes
const styles = {
  navbar: {
    transition: 'all 0.3s ease-in-out',
  },
  mobileMenu: {
    transition: 'all 0.3s ease-in-out',
    boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
  },
  navLink: {
    transition: 'color 0.2s ease-in-out',
    position: 'relative',
  },
  logo: {
    maxHeight: '48px',
    width: '48px',
    borderRadius: '50%',
    marginRight: '12px',
    background: '#fff',
    boxShadow: '0 2px 8px rgba(0,0,0,0.08)'
  },
  brand: {
    display: 'flex',
    alignItems: 'center',
    textDecoration: 'none',
    color: '#fff',
    fontWeight: 700,
    fontSize: '1.2rem',
    letterSpacing: '0.5px',
  },
  breadcrumbs: {
    fontSize: '0.98rem',
    color: '#333',
    marginTop: 0,
    marginLeft: 0,
    display: 'flex',
    alignItems: 'center',
    flexWrap: 'wrap',
    gap: '0.25rem',
  },
  breadcrumbLink: {
    color: '#D7D7D7',
    textDecoration: 'none',
    transition: 'color 0.2s',
  },
  breadcrumbActive: {
    color: '#D7D7D7',
    fontWeight: 600,
  }
};

class SecundaryNavbarPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isOpenMenu: false,
      isMobile: window.innerWidth <= 991,
      isLoaded: false,
      isScrolled: false
    };
    this.toggle = this.toggle.bind(this);
    this.handleMenuClick = this.handleMenuClick.bind(this);
    this.handleResize = this.handleResize.bind(this);
    this.handleScroll = this.handleScroll.bind(this);
  }

  componentDidMount() {
    // Precargar el logo
    const logo = new Image();
    logo.src = logolight;
    logo.onload = () => this.setState({ isLoaded: true });

    window.addEventListener('resize', this.handleResize);
    window.addEventListener('scroll', this.handleScroll);
    this.handleResize();
  }

  componentWillUnmount() {
    window.removeEventListener('resize', this.handleResize);
    window.removeEventListener('scroll', this.handleScroll);
  }

  handleScroll = () => {
    const isScrolled = window.scrollY > 50;
    if (isScrolled !== this.state.isScrolled) {
      this.setState({ isScrolled: isScrolled });
    }
  }

  handleResize = () => {
    this.setState({
      isMobile: window.innerWidth <= 991
    });
  }

  toggle = () => {
    this.setState(prevState => ({ isOpenMenu: !prevState.isOpenMenu }));
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

  renderBreadcrumbs() {
    const { breadcrumbs = [] } = this.props;
    if (!breadcrumbs.length) return null;
    return (
      <nav aria-label="breadcrumb" style={styles.breadcrumbs}>
        {breadcrumbs.map((crumb, idx) => (
          <span key={idx}>
            {crumb.href ? (
              <Link to={crumb.href} style={styles.breadcrumbLink}>{crumb.label}</Link>
            ) : (
              <span style={styles.breadcrumbActive}>{crumb.label}</span>
            )}
            {idx < breadcrumbs.length - 1 && <span style={{margin: '0 4px'}}>/</span>}
          </span>
        ))}
      </nav>
    );
  }

  render() {
    const { isMobile, isScrolled } = this.state;
    const { companyName = "Seguridad al Límite" } = this.props;

    const mobileStyles = isMobile ? {
      ...styles.mobileMenu,
      position: 'absolute',
      top: '100%',
      left: 0,
      right: 0,
      backgroundColor: '#000',
      padding: '1rem',
      zIndex: 1000,
      opacity: this.state.isOpenMenu ? 1 : 0,
      visibility: this.state.isOpenMenu ? 'visible' : 'hidden',
      transform: this.state.isOpenMenu ? 'translateY(0)' : 'translateY(-10px)',
    } : {};

    const navbarStyles = {
      ...styles.navbar,
      backgroundColor: isScrolled ? 'rgba(0, 0, 0, 0.95)' : '#111',
      padding: isScrolled ? '0.5rem 0' : '1rem 0',
      zIndex: 1050,
    };

    return (
      <React.Fragment>
        <style>{`
          .breadcrumb-responsive {
            font-size: 0.98rem;
          }
          @media (max-width: 576px) {
            .breadcrumb-responsive {
              font-size: 0.52rem;
            }
          }
        `}</style>
        <Navbar
          expand="lg"
          fixed={this.props.top === true ? "top" : ""}
          className={`${this.props.navClass} navbar-custom sticky sticky-dark px-4`}
          id="navbar"
          style={navbarStyles}
        >
          <Container className="position-relative">
            <div className="row align-items-center">
              <div className="col-12 d-flex align-items-center">
                {/* Logo y nombre de empresa */}
                <Link to="/" style={styles.brand} className="navbar-brand me-4 d-flex align-items-center">
                  <img
                    src={logolight}
                    alt="Logo Seguridad al Límite"
                    style={styles.logo}
                    width={48}
                    height={48}
                    loading="eager"
                    fetchpriority="high"
                  />
                  <span className="d-none d-md-inline" style={{marginLeft: 8}}>{companyName}</span>
                </Link>
                <NavbarToggler 
                  onClick={this.toggle} 
                  className="border-0 ms-auto"
                  aria-label="Toggle navigation"
                >
                  <i className="mdi mdi-menu text-white" style={{ fontSize: '24px' }}></i>
                </NavbarToggler>
                <Collapse 
                  isOpen={this.state.isOpenMenu} 
                  navbar 
                  className="mobile-menu"
                  style={mobileStyles}
                >
                  <Nav 
                    className={`${isMobile ? 'flex-column' : ''} mx-auto align-items-center`} 
                    navbar
                  >
                    {this.props.navItems.map((item, key) => (
                      <NavItem
                        key={key}
                        className={`${item.navheading === "Home" ? "active" : ""} ${isMobile ? 'w-100 text-center mb-2' : ''}`}
                      >
                        <NavLink
                          className={`${item.navheading === "Home" ? "active" : ""} ${isMobile ? 'text-white' : ''}`}
                          href={"#" + item.idnm}
                          onClick={this.handleMenuClick}
                          style={styles.navLink}
                          aria-current={item.navheading === "Home" ? "page" : undefined}
                        >
                          {item.navheading}
                        </NavLink>
                      </NavItem>
                    ))}
                  </Nav>
                </Collapse>
              </div>
            </div>
            <div className="row">
              <div className="col-12 d-flex flex-column flex-grow-1 breadcrumb-responsive">
                {this.renderBreadcrumbs()}
              </div>
            </div>
          </Container>
        </Navbar>
      </React.Fragment>
    );
  }
}

export default SecundaryNavbarPage;