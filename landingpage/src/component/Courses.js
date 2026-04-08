import React, { useState, useEffect } from "react";
import { Col, Container, Row } from "reactstrap";
import OptimizedImage from "../components/OptimizedImage";

// Define el array fuera del componente
const SERVICES = [
  {
    id: 'service7',
    title: "Cursos de Trabajo Seguro en Alturas",
    description: 'Somos un centro autorizado en Armenia, Quindío, especializado en formación y certificación en trabajo seguro en alturas. Nuestros programas están diseñados para garantizar la seguridad de los trabajadores y cumplen con la normatividad vigente, incluyendo la Resolución 4272 de 2021.',
    text: [
      {
        highlight: 'TRABAJADOR AUTORIZADO',
        regular: ': Dirigido a personal que realice tránsito vertical – horizontal. 32 horas.'
      },
      {
        highlight: 'NIVEL REENTRENAMIENTO',
        regular: ': Dirigido a personal que realice tránsito vertical – horizontal. Mínimo 8 horas. Actualización de certificado.'
      },
      {
        highlight: 'NIVELES JEFE DE ÁREA (ADMINISTRATIVO)',
        regular: ': Dirigido a personal que realice tránsito vertical, sobre piso o plataformas. 8 horas.'
      },
      {
        highlight: 'NIVEL COORDINADOR DE TRABAJO SEGURO EN ALTURAS',
        regular: ': Dirigido a personal que realice tránsito vertical – horizontal y firme permisos para trabajo en alturas. 80 horas.'
      },

    ]
  }
];

const Courses = () => {
  const [isMobile, setIsMobile] = useState(window.innerWidth <= 768);
  const [loadedImages, setLoadedImages] = useState({});

  useEffect(() => {
    const handleResize = () => setIsMobile(window.innerWidth <= 768);
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  const handleImageLoad = (id) => {
    setLoadedImages(prev => ({
      ...prev,
      [id]: true
    }));
  };

  return (
    <section className="section bg-services" id="courses">
      <Container>
        <div className="title-box text-center">
          <h3 className="services-header-title">Somos un centro autorizado en Armenia, Quindío, especializado en formación y certificación en trabajo seguro en alturas. Nuestros programas están diseñados para garantizar la seguridad de los trabajadores y cumplen con la normatividad vigente, incluyendo la Resolución 4272 de 2021.</h3>
          <div className="services-header-separator" aria-hidden="true"></div>
        </div>
        <Row className="mt-1 pt-1">
          {SERVICES.map((item, key) => (
            <Col lg={12} key={key}>
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
                    {!loadedImages[item.id] && (
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
                      src={`assets/images/services/${item.id}.webp`}
                      alt="Entrenamiento en trabajo en alturas Armenia Quindío"
                      width={isMobile ? 140 : 120}
                      height={isMobile ? 140 : 120}
                      loading="lazy"
                      onLoad={() => handleImageLoad(item.id)}
                      style={{
                        width: '100%',
                        height: '100%',
                        objectFit: 'contain',
                        borderRadius: "50%",
                        opacity: loadedImages[item.id] ? 1 : 0,
                        transition: 'opacity 0.3s ease-in-out',
                        padding: isMobile ? '15px' : '12px'
                      }}
                      isMobile={isMobile}
                    />
                  </div>

                  <div className="course-title">
                    {item.title}
                  </div>
                </div>

                <div className="services-detail-box p-1 mt-4">
                  <h4 className="course-sub-title">
                    Programas de Certificación en Trabajo en Alturas:
                  </h4>
                  <div className="mt-3">
                    <ul className="course-list">
                      {item.text?.map((text, index) => (
                        <li key={index * 10} className="course-list-item">
                          <strong style={{ color: '#000' }}>{text.highlight}</strong>
                          <span style={{ color: '#666' }}>{text.regular}</span>
                        </li>
                      ))}
                    </ul>
                  </div>
                </div>

                <div className={`mt-4 ${isMobile ? 'text-center' : ''}`}>
                  <a
                    href="https://cursos.seguridadallimite.com/#/student/inscription"
                    className={`btn btn-primary amarillo ${isMobile ? 'w-100' : ''}`}
                    style={{ maxWidth: isMobile ? '300px' : 'none' }}
                    title="Inscríbete en nuestros cursos de trabajo en alturas"
                  >
                    Inscríbete Ahora
                  </a>
                </div>
              </div>
            </Col>
          ))}
        </Row>
      </Container>
    </section>
  );
};

export default Courses;
