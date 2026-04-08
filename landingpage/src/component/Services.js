import React, { Component } from "react";
import { Col, Container, Row } from "reactstrap";
import { Link } from "react-router-dom";
import OptimizedImage from '../components/OptimizedImage';
import { servicesList } from '../data/servicesList';

export default class Services extends Component {
  constructor(props) {
    super(props);
    this.state = {
      services: servicesList,
      isMobile: window.innerWidth <= 768,
      loadedImages: {}
    };
  }

  componentDidMount() {
    window.addEventListener('resize', this.handleResize);
    this.handleResize();
  }

  componentWillUnmount() {
    window.removeEventListener('resize', this.handleResize);
  }

  handleResize = () => {
    this.setState({
      isMobile: window.innerWidth <= 768
    });
  };

  handleImageLoad = (id) => {
    this.setState(prevState => ({
      loadedImages: {
        ...prevState.loadedImages,
        [id]: true
      }
    }));
  };

  render() {
    const { isMobile } = this.state;
    const service = this.state.services[0];

    return (
      <section className="section bg-services" id="services">
        <Container>
          <div className="title-box text-center">
            <h2 className="services-header-title">Nuestros Servicios</h2>
            <div className="services-header-separator" aria-hidden="true"></div>
          </div>

          <Row className="mt-1 pt-1">
            <Col lg={12}>
                
              <div className="services-box p-4 mt-4">
                <div style={{
                  display: 'flex',
                  alignItems: isMobile ? 'center' : 'flex-start',
                  minHeight: isMobile ? '140px' : '110px',
                  flexDirection: isMobile ? 'column' : 'row',
                  width: '100%'
                  }}>
                  <div className="services-icon bg-soft-primary" style={{
                    marginRight: isMobile ? '0' : '50px',
                    marginBottom: isMobile ? '20px' : '0',
                    position: 'relative',
                    width: isMobile ? '140px' : '120px',
                    height: isMobile ? '140px' : '120px',
                    borderRadius: "50%",
                    overflow: 'hidden',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    backgroundColor: '#f0f0f0',
                    alignSelf: isMobile ? 'center' : 'flex-start',
                    flexShrink: 0
                  }}>
                    {!this.state.loadedImages[service.id] && (
                      <div style={{
                        position: 'absolute',
                        top: 0,
                        left: 0,
                        right: 0,
                        bottom: 0,
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center'
                      }}>
                        <div className="spinner-border text-primary" role="status" style={{
                          width: isMobile ? '2rem' : '1.5rem',
                          height: isMobile ? '2rem' : '1.5rem'
                        }}>
                          <span className="visually-hidden">Cargando...</span>
                        </div>
                      </div>
                    )}
                    
                    <OptimizedImage
                      src={`assets/images/services/${service.id}.webp`}
                      alt="Servicios de seguridad industrial"
                      width={isMobile ? 140 : 120}
                      height={isMobile ? 140 : 120}
                      loading="lazy"
                      onLoad={() => this.handleImageLoad(service.id)}
                      style={{
                        width: '100%',
                        height: '100%',
                        objectFit: 'contain',
                        borderRadius: "50%",
                        opacity: this.state.loadedImages[service.id] ? 1 : 0,
                        transition: 'opacity 0.3s ease-in-out',
                        padding: isMobile ? '15px' : '12px'
                      }}
                      isMobile={isMobile}
                    />
                  </div>

                  <div className="course-title">
                    {service.title}
                  </div>
                </div>

                <div className="services-detail-box p-1 mt-4">
                  <h4 className="course-sub-title">
                    Programas de Servicios en Seguridad Industrial:
                  </h4>
                  <div className="mt-3">
                    <ul className="course-list">
                      {service.items.map((item, index) => (
                        <li key={index} className="course-list-item">
                          <Link 
                            to="/servicios-seguridad-industrial"
                            className="text-dark text-decoration-none"
                          >
                            {item}
                          </Link>
                        </li>
                      ))}
                    </ul>
                  </div>
                </div>

                <div className={`mt-4 ${isMobile ? 'text-center' : ''}`}>
                  <Link
                    to="/servicios-seguridad-industrial"
                    className={`btn btn-primary amarillo ${isMobile ? 'w-100' : ''}`}
                    style={{ maxWidth: isMobile ? '300px' : 'none' }}
                  >
                    Ver más detalles
                  </Link>
                </div>
              </div>
              
            </Col>
          </Row>
        </Container>
      </section>
    );
  }
}
