import React, { Component } from "react";
import { Col, Container, Row } from "reactstrap";

import SectionTitle from "./SectionTitle";
import SectionClienteList from "./SectionClientList";


export default class Clientes extends Component {

  render() {
    return (
      <>
        <section className="section bg-light bg-clients" id="clients">
          <Container>
            <Row>
              <Col lg={12}>
                <SectionTitle 
                    title="Nuestros clientes" 
                    light={true}
                  />
              </Col>
            </Row>
            <SectionClienteList />

          </Container>
        </section>
      </>
    );
  }
}
