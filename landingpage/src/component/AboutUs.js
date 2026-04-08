import React from "react";
import { Col, Container, Row } from "reactstrap";
import { Helmet } from 'react-helmet';
import bgImage from '../assets/images/bg-4.webp';

const AboutUs = () => {
  return (
    <>
      <Helmet>
        <link rel="preload" as="image" href={bgImage} />
        <style>
          {`
            @media (max-width: 768px) {
              .bg-mobile-fallback {
                background-color: #000;
                border-bottom-left-radius: 80px;
                background-image: none !important;
              }
              .bg-img {
                display: none !important;
              }
            }
          `}
        </style>
      </Helmet>
      <section
        className="section bg-light bg-cta bg-mobile-fallback"
        style={{
          position: 'relative',
          backgroundImage: `url(${bgImage})`,
          backgroundSize: 'cover',
          backgroundPosition: 'center',
        }}
      >
        <Container style={{ position: 'relative', zIndex: 1 }}>
          <Row className="justify-content-center">
            <Col lg={9}>
              <div className="text-center pt-5 px-3">
                <h2>
                  QUIENES SOMOS <span className="text-white">SEGURIDAD AL LIMITE</span>
                </h2>
                <p className="mt-4 text-white">
                  Somos una empresa fundada en el año 2015 con un grupo de profesionales especialistas
                  en seguridad y salud en el trabajo, que ofrece capacitación, asesoría y consultoría.
                </p>
                <p className="mt-4 text-white">
                  Técnicas en seguridad industrial, medicina preventiva, medicina laboral, gestión ambiental y 
                  gestión de calidad, gracias a nuestros aliados logramos de esta manera dar acompañamiento en todos los
                  procesos requeridos por las empresas.
                </p>
              </div>
            </Col>
          </Row>
        </Container>
      </section>
    </>
  );
};

export default AboutUs;
