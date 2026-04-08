import React, { Component } from "react";
import { Col, Container, Row } from "reactstrap";
import Carousel from 'react-bootstrap/Carousel';

import Img1 from '../assets/images/testi-img/img-1.webp';
import Img2 from '../assets/images/testi-img/img-2.webp';

import SectionTitle from "./SectionTitle";

const slideitems = [
  {
    id: 100,
    img: Img1,
    description: 'Proporcionamos entrenamiento especializado en técnicas de trabajo y rescate en alturas, equipada con infraestructura y equipos de última generación para simular condiciones reales. Los programas se adaptan a diferentes industrias y están enfocados en la seguridad, habilidad y confianza.'
  },
  {
    id: 101,
    img: Img2,
    description: 'Contamos con un entorno controlado y seguro con andamios y equipos de última generación. Nuestros cursos están diseñados para equipar a los participantes con habilidades prácticas esenciales y conocimientos técnicos, garantizando que puedan realizar su trabajo con la mayor seguridad posible.  Utilizando nuestra infraestructura de andamios podemos simular un ambiente de trabajo realista y desafiante, donde nuestros clientes pueden practicar y perfeccionar sus habilidades bajo la supervisión de instructores certificados y con una gran experiencia en la industria.'
  },
];

export default class Practices extends Component {
  render() {
    return (
      <>
        <section className="section">
          <Container>
            <Row>
              <Col lg={12}>
                <SectionTitle 
                  title="Prácticas e instalaciones" 
                  light={false}
                />
              </Col>
            </Row>
            <Row className="mt-1 pt-1">
              <Col lg="11">
                <Carousel>
                  {slideitems.map((slideitem) => (
                    <Carousel.Item key={slideitem.id}>
                      <Row className="align-items-center">
                        <Col lg={6}>
                          <div className="text-center">
                            <img
                              src={slideitem.img}
                              className="img-intallations img-fluid"
                              alt="Imagen de una practica"
                              width={500}
                              height={225}
                            />
                          </div>
                        </Col>
                        <Col lg={6}>
                          <div className="client-box">
                            <p className="line-height_1_6 text-light">{slideitem.description}</p>
                          </div>
                        </Col>
                      </Row>
                    </Carousel.Item>
                  ))}
                </Carousel>
              </Col>
            </Row>
          </Container>
        </section>
      </>
    );
  }
}
