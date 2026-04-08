import React, { Component } from "react";
import { Container, Row, Col } from "reactstrap";
import OptimizedImage from '../components/OptimizedImage';

// Define el array fuera del componente
const SERVICES = [
  {
    id: 'service10',
    title: "Suministro de equipos para la seguridad industrial",
    description: 'Arnes, Eslingas, Cascos, Cuerdas, Líneas de vida, Eslingas posicionamiento,  Mosquetones, Sillas, Etc'
  },
  {
    id: 'service11',
    title: "Equipos para atención de emergencias",
    description: 'Botiquines, Guantes, Camillas, Kit inmovilizados.'
  },
  {
    id: 'service12',
    title: "Dotaciones y Elementos de Protección Personal",
    description: 'Guantes, Mascarillas, Gafas, Etc.'
  }
];

export default class Products extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loadedImages: {},
      isMobile: window.innerWidth <= 768
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
    const { isMobile, loadedImages } = this.state;

    return (
      <>
        <section className="section bg-services" id="products">
          <Container>
            <div className="title-box text-center">
              <h3 className="services-header-title">Consulta aquí nuestros productos VENTA DE EQUIPOS CERTIFICADOS PARA TRABAJO SEGURO EN ALTURAS</h3>
              <div className="services-header-separator" aria-hidden="true"></div>
            </div>

            <Row className="mt-1 pt-1">
              {SERVICES.map((item, key) => (
                <Col lg={4} key={key}>
                  <div className="services-box p-4 mt-4" style={{ minHeight: '300px'}}>
                    <div style={{ 
                      display: 'flex', 
                      alignItems: isMobile ? 'center' : 'flex-start',
                      minHeight: '80px',
                      flexDirection: isMobile ? 'column' : 'row',
                      width: '100%'
                    }}>
                      <div className="services-icon bg-soft-primary" style={{ 
                        marginRight: isMobile ? '0' : '30px',
                        marginBottom: isMobile ? '20px' : '0',
                        position: 'relative',
                        width: isMobile ? '120px' : '100px',
                        height: isMobile ? '120px' : '100px',
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
                          alt={item.title}
                          width={isMobile ? 120 : 100}
                          height={isMobile ? 120 : 100}
                          loading="lazy"
                          onLoad={() => this.handleImageLoad(item.id)}
                          style={{ 
                            borderRadius: "50%",
                            padding: isMobile ? '12px' : '10px',
                            objectFit: 'cover'
                          }}
                          isMobile={isMobile}
                        />
                      </div>

                      <div style={{
                        flex: 1,
                        display: 'flex',
                        alignItems: isMobile ? 'center' : 'flex-start',
                        justifyContent: isMobile ? 'center' : 'flex-start'
                      }}>
                        <h5 style={{
                          textAlign: isMobile ? 'center' : 'left',
                          margin: 0,
                          fontSize: isMobile ? '1.1rem' : '1.25rem',
                          lineHeight: '1.4'
                        }}>{item.title}</h5>
                      </div>
                    </div>

                    <div className="services-detail-box p-1 mt-4" style={{ minHeight: '150px'}}>
                      <p className="text-light mt-3">{item.description}</p>
                    </div>
                  </div>
                </Col>
              ))}
            </Row>
          </Container>
        </section>
      </>
    );
  }
}
