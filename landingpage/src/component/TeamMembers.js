import React, { Component } from "react";
import { Col, Container, Row } from "reactstrap";
import Carousel from "react-bootstrap/Carousel";
import Img1 from '../assets/images/users/24-225x300.webp';

class TeamMembers extends Component {
  constructor(props) {
    super(props);
    this.state = {
      teams: [
        {
          id: 1,
          img: Img1,
          name: 'Cesar Pineda',
          nickname: 'Entrenador',
          description: [
            'Profesional en seguridad y salud laboral.',
            'Técnico urgencias médicas.',
            'Rescatista y entrenador trabajo en alturas'
          ]
        },
        // Puedes agregar más miembros aquí
      ],
    };
  }

  render() {
    return (
      <section className="section pt-0" id="team">
        <Container>
          <Row>
            <Col lg="12">
              <div className="espace title-box text-center">
                <h3 className="title-heading text-light mt-4 my-4">Conoce nuestro personal </h3>
              </div>
            </Col>
          </Row>
          <Row className="mt-1 pt-1">
            <Col lg="12">
              <Carousel
                indicators={this.state.teams.length > 1}
                controls={this.state.teams.length > 1}
                interval={4000}
                pause="hover"
              >
                {this.state.teams.map((team, teamindex) => (
                  <Carousel.Item key={teamindex}>
                    <Row className="d-flex flex-column-reverse flex-lg-row align-items-center">
                      <Col xs="12" lg="6">
                        <div className="mt-4">
                          <h5 className="mt-2 text-dark">{team.name}</h5>
                          <p className="f-20 text-info">{team.nickname}</p>
                          <ul className="text-muted mt-3">
                            {team.description.map((desc, index) => (
                              <li key={index}>{desc}</li>
                            ))}
                          </ul>
                        </div>
                      </Col>
                      <Col xs="12" lg="6">
                        <div className="mt-4 text-center">
                          <img src={team.img} alt="Imagen instructor" width={225} height={300} className="img-fluid rounded" />
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
    );
  }
}

export default TeamMembers;