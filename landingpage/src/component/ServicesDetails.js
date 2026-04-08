import React, { Component } from "react";
import { Col, Container, Row } from "reactstrap";
import OptimizedImage from '../components/OptimizedImage';
import { services } from '../data/services';

export default class ServicesDetails extends Component {
  constructor(props) {
    super(props);
    this.state = {
      services,
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
    const { isMobile } = this.state;

    return (
      <section className="section bg-services" id="services">
        <Container>

          <div className="title-box text-center">
            <h1 className="services-header-title">Nuestros Servicios en seguridad industrial</h1>
            <div className="services-header-separator" aria-hidden="true"></div>
          </div>

          <Row className="mt-1 pt-1">
            {this.state.services.map((item, key) => (
              <Col lg={12} key={key}>
                <div className="services-box p-3 mt-4" style={{ minHeight: "300px" }}>
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
                      {!this.state.loadedImages[item.id] && (
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
                          padding: isMobile ? '8px' : '6px',
                          objectFit: 'cover',
                          maxWidth: '100%'
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

                  <div className="services-detail-box p-1 mt-4">
                    <div className="mt-2">

                      
                      {item.description.length > 0 && (
                        <p className="text-muted mt-3">{item.description}</p>
                      )}
                      
                      <ul>
                        {item.text?.map((text, index) => (
                          <li key={index * 10} className="text-light mt-3">{text}</li>
                        ))}
                      </ul>
                    </div>
                  </div>
                </div>
              </Col>
            ))}
          </Row>
        </Container>
      </section>
    );
  }
}
