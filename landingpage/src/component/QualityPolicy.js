import React, { Component } from "react"; 
import { Col, Container, Row } from "reactstrap";
import Img1 from '../assets/images/features/imagen-certificado-400x230.webp';

export default class QualityPolicy extends Component {
  render() {
    return (
      <>
        <style>
          {`
            @media (max-width: 768px) {
              .quality-policy-section {
                background-color: #000;
                border-bottom-right-radius: 50px;
                padding: 20px;
              }
              .quality-policy-text {
                color: #fff !important;
                text-align: justify;
              }
              .img-certificate {
                display: none; /* Ocultar la imagen en mobile */
              }
            }
          `}
        </style>
        <section className="section bg-light bg-features quality-policy-section">
          <Container>
            <Row className="align-items-center">
              <Col lg={5} className="d-none d-lg-block">
                <div className="mt-4 home-img text-center">
                  <img
                    src={Img1}
                    alt="Certificado de calidad Seguridad al Límite"
                    className="img-fluid"
                    style={{ borderRadius: '12px', maxWidth: '100%' }}
                    loading="lazy"
                  />
                </div>
              </Col>
              <Col lg={6} className="offset-lg-1">
                <div className="mt-4">
                  <h2 style={{ color: '#FFC107' }}>Política de calidad</h2> {/* Color amarillo */}
                  <p className="mt-4 text-white">
                    SEGURIDAD AL LIMITE SAS, presta servicios de consultoría y asesoramiento en
                    seguridad industrial y formación de trabajo seguro en alturas, dando cumplimiento
                    a la norma NTC 6072, en proceso de certificación, seguridad y salud en el trabajo,
                    Medio ambiente, riesgos químicos y planes de gestión SST cumpliendo los requisitos
                    legales aplicables. SEGURIDADLIMITE SAS tiene un compromiso con la satisfacción
                    de nuestros clientes y mejora continua, estando a la vanguardia con la tecnología,
                    generando rentabilidad en beneficio de la sociedad y la empresa mejorando la
                    eficacia de nuestros procesos.
                  </p>
                </div>
              </Col>
            </Row>
          </Container>
        </section>
      </>
    );
  }
}
