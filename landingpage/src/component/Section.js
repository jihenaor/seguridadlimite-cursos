import React, { Component } from "react";
import { Container, Row, Col } from "reactstrap";
import { Helmet } from 'react-helmet';
import Typewriter from 'typewriter-effect';

// Importar imágenes optimizadas
import logodark from "../assets/images/logo-circular-dark.webp";
import logolight from "../assets/images/logo-circular-light.webp";
import bgImage from "../assets/images/bg-3.jpg";

class Section extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isMobile: window.innerWidth <= 768,
      isLoaded: false
    };
  }

  componentDidMount() {
    // Precargar imágenes críticas
    const images = [
      this.props.imglight ? logolight : logodark,
      bgImage
    ];

    Promise.all(
      images.map(src => {
        return new Promise((resolve, reject) => {
          const img = new Image();
          img.src = src;
          img.onload = resolve;
          img.onerror = reject;
        });
      })
    ).then(() => {
      this.setState({ isLoaded: true });
    });

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
  }

  render() {
    const { isMobile, isLoaded } = this.state;

    const sectionStyle = {
      position: 'relative',
      width: '100%',
      display: 'flex',
      alignItems: 'center',
      overflow: 'hidden',
      backgroundColor: '#000',
      minHeight: '100vh',
      padding: isMobile ? '5rem 0 2rem' : 0,
      backgroundImage: `url(${bgImage})`,
      backgroundSize: 'cover',
      backgroundPosition: 'center center',
      backgroundRepeat: 'no-repeat',
      opacity: isLoaded ? 1 : 0,
      transition: 'opacity 0.3s ease-in'
    };

    return (
      <>
        <Helmet>
          <title>Centro de Entrenamiento para Trabajo Seguro en Alturas en Armenia, Quindío | Seguridad al Límite</title>
          <meta name="description" content="Centro autorizado para certificación y entrenamiento en trabajo seguro en alturas en Armenia. Cursos certificados por el SENA, instructores expertos y modernas instalaciones." />
          <meta name="keywords" content="trabajo en alturas armenia, certificación trabajo en alturas quindío, curso trabajo en alturas, entrenamiento alturas, SENA" />
          <link rel="canonical" href="https://seguridadallimite.com/" />
          
          {/* Precargar recursos críticos */}
          <link rel="preload" as="image" href={this.props.imglight ? logolight : logodark} />
          <link rel="preload" as="image" href={bgImage} />
        </Helmet>

        <section 
          className="bg-home home-bg-2" 
          id="home" 
          style={sectionStyle}
        >
          <div 
            className="bg-overlay" 
            style={{ 
              position: 'absolute',
              top: 0,
              left: 0,
              right: 0,
              bottom: 0,
              zIndex: 1,
              backgroundColor: 'rgba(0, 0, 0, 0.6)',
              pointerEvents: 'none'
            }}
          />
          <div 
            className="home-center"
            style={{
              position: 'relative',
              zIndex: 2,
              width: '100%',
              padding: 0
            }}
          >
            <div className="home-desc-center">
              <Container>
                <Row className="justify-content-center">
                  <Col lg={9}>
                    <div className="home-content text-center" style={{
                      marginTop: isMobile ? '30px' : '0'
                    }}>                        
                      <img 
                        src={this.props.imglight ? logolight : logodark}
                        alt="Logo Seguridad al Límite" 
                        width={isMobile ? "180" : "200"}
                        height={isMobile ? "180" : "200"}
                        style={{
                          borderRadius: "50%",
                          maxWidth: isMobile ? "180px" : "200px",
                          height: "auto",
                          display: "block",
                          marginTop: isMobile ? "30px" : "5rem",
                          marginBottom: isMobile ? "1rem" : "2rem",
                          marginLeft: "auto",
                          marginRight: "auto",
                          opacity: isLoaded ? 1 : 0,
                          transition: 'opacity 0.3s ease-in'
                        }} 
                        loading="eager"
                        fetchPriority="high"
                      />

                      <h1 className="home-title text-white" style={{
                        fontSize: isMobile ? '1.5rem' : '2.5rem',
                        lineHeight: isMobile ? '1.3' : '1.5',
                        margin: isMobile ? '1rem 0' : '2rem 0',
                        fontWeight: 600
                      }}>
                        Centro de Entrenamiento para Trabajo Seguro en Alturas
                      </h1>

                      <div className="element text-primary mt-2">
                        <Typewriter
                          options={{
                            strings: ['SEGURIDAD AL LÍMITE'],
                            autoStart: true,
                            loop: true,
                            delay: 50,
                            deleteSpeed: 25
                          }}
                        />
                      </div>

                      <p className="text-white-50 mt-2" style={{ 
                        fontSize: isMobile ? '14px' : '20px',
                        lineHeight: isMobile ? '1.4' : '1.6',
                        margin: isMobile ? '0.5rem 0' : '1rem 0'
                      }}>
                        Centro autorizado por el SENA para certificación en trabajo seguro en alturas.
                        Brindamos formación especializada en seguridad y salud en el trabajo.
                      </p>

                      <div className={`${isMobile ? 'mt-3' : 'mt-4 pt-2'}`}>
                        <a 
                          href="https://cursos.seguridadallimite.com/#/student/inscription" 
                          className="btn btn-primary amarillo d-block d-sm-inline-block"
                          style={{ 
                            marginBottom: isMobile ? '0.5rem' : 0,
                            marginRight: isMobile ? 0 : '20px',
                            padding: isMobile ? '0.5rem 1rem' : '0.75rem 1.5rem',
                            fontSize: isMobile ? '14px' : '16px'
                          }}
                        >
                          Inscríbete Ahora
                        </a>

                        <a 
                          href="https://cursos.seguridadallimite.com/#/student/certificate" 
                          className="btn btn-primary amarillo d-block d-sm-inline-block"
                          style={{ 
                            marginLeft: isMobile ? 0 : '20px',
                            padding: isMobile ? '0.5rem 1rem' : '0.75rem 1.5rem',
                            fontSize: isMobile ? '14px' : '16px'
                          }}
                        >
                          Verificar Certificado
                        </a>
                      </div>

                      <div className="mt-4">
                        <p className="text-white-50" style={{ fontSize: isMobile ? '12px' : '14px' }}>
                          Resolución 4272 de 2021 | Certificación SENA | Instalaciones Especializadas
                        </p>
                      </div>
                    </div>
                  </Col>
                </Row>
              </Container>
            </div>
          </div>
        </section>
      </>
    );
  }
}

export default Section;
