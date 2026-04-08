import React, { Component } from "react";
import { Container, Row, Col } from "reactstrap";
import FooterLink from "../Footer/Footer_link";
import './Footer.scss';  // Importar los estilos

class Footer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      links: [
        {
          id: 1,
          title: "Horario De Atención administrativo",
          child: [
            { title: "Lunes a viernes 08:00 am a 12:00 pm y de 2:00 pm a 6:00 pm" },
            { title: "Sabados de 8:00 am a 12:00 pm" },
          ],
        },
        {
          id: 2,
          title: "Nuestra Ubicación",
          child: [
            { title: "Km. 2 Vía La Tebaida" },
            { title: "Armenia Quindío" },
            { title: "Colombia" }
          ],
        },
      ],
    };
  }

  render() {
    return (
      <>
        <footer className="section bg-light bg-footer pb-5 footer-responsive">
          <Container>
            <Row>
              <Col lg={4}>
                <div className="footer-info footer-section mt-4">
                  <p className="text-white mt-4 mb-2">
                    <a href="tel:+573225936279" className="text-white text-decoration-none">+57 322 593 6279</a>
                  </p>
                  <p className="text-white mt-4 mb-2">
                    <a href="tel:+573116263300" className="text-white text-decoration-none">+57 311 626 3300</a>
                  </p>
                  <p className="text-white mt-4 mb-2">
                    <a href="mailto:seguridadlimite@hotmail.com" className="text-white text-decoration-none">seguridadlimite@hotmail.com</a>
                  </p>
                </div>
              </Col>
              <Col lg={4}>
                <Row className="pl-0 md-lg-5">
                  {this.state.links.map((item, key) => (
                    <Col lg={6} key={key} className="footer-section">
                      <div className="mt-4">
                        <h5 className="f-20 footer-title">{item.title}</h5>
                        <ul className="list-unstyled footer-link mt-3">
                          {item.child.map((linkItem, key) => (
                            <li key={key} className="text-white mt-4 mb-2">{linkItem.title}</li>
                          ))}
                        </ul>
                      </div>
                    </Col>
                  ))}
                </Row>
              </Col>
              <Col lg={4}>
                <div className="mt-4 footer-section">
                  <h5 className="f-20 footer-title">Redes Sociales</h5>
                  <div className="subscribe mt-4 pt-1">
                    <div className="team-social mt-4 pt-2">
                      <ul className="list-inline mb-0">
                        <li className="list-inline-item">
                          <a
                            href="https://www.facebook.com/seguridadallimite"
                            className="text-reset"
                            target="_blank"
                            rel="noopener noreferrer"
                            aria-label="Facebook Seguridad al Límite"
                          >
                            <i className="mdi mdi-facebook mdi-24px"></i>
                          </a>
                        </li>
                        <li className="list-inline-item">
                          <a
                            href="https://www.instagram.com/seguridadallimite"
                            className="text-reset"
                            target="_blank"
                            rel="noopener noreferrer"
                            aria-label="Instagram Seguridad al Límite"
                          >
                            <i className="mdi mdi-instagram mdi-24px"></i>
                          </a>
                        </li>
                        <li className="list-inline-item">
                          <a
                            href="https://wa.me/573225936279"
                            className="text-reset"
                            target="_blank"
                            rel="noopener noreferrer"
                            aria-label="WhatsApp Seguridad al Límite"
                          >
                            <i className="mdi mdi-whatsapp mdi-24px"></i>
                          </a>
                        </li>
                      </ul>
                    </div>
                  </div>
                </div>
              </Col>
            </Row>
            <hr className="my-5" />
            <FooterLink />
          </Container>
        </footer>
      </>
    );
  }
}

export default Footer;
