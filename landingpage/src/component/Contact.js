import React, { Component } from "react";

import { Col, Container, Form, FormFeedback, FormGroup, Input, Label, Row } from "reactstrap";

import Feature from '../assets/images/features/formacion-andamios-380x638.webp';
// import ImgSeparator from "../component/ImgSeparator";
import emailjs from 'emailjs-com';

export default class Contact extends Component {
  constructor(props) {
    super(props);
    this.state = {
      requestType: 'empresa',
      emailSent: false,
      emailSentMessage: '',
      errors: {},
      isSubmitting: false,
    };
  }

  handleRequestTypeChange = (e) => {
    this.setState(prevState => ({
      ...prevState,
      requestType: e.target.value,
      errors: {}
    }));
  };

  validateForm = () => {
    const { requestType } = this.state;
    const form = document.getElementById('contact-form');
    const formData = new FormData(form);
    const errors = {};

    const nombrepersona = formData.get('nombrepersona');
    const apellido = formData.get('apellido');
    const telefonocontacto = formData.get('telefonocontacto');
    const email = formData.get('email');
    const message = formData.get('message');
    const nombreempresa = formData.get('nombreempresa');
    const nombrecontacto = formData.get('nombrecontacto');

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const phoneRegex = /^[0-9]{7,10}$/;

    if (requestType === 'personal') {
      if (!nombrepersona || nombrepersona.trim().length < 2) {
        errors.nombrepersona = 'El nombre debe tener al menos 2 caracteres';
      }
      if (!apellido || apellido.trim().length < 2) {
        errors.apellido = 'El apellido debe tener al menos 2 caracteres';
      }
    }

    if (requestType === 'empresa') {
      if (!nombreempresa || nombreempresa.trim().length < 2) {
        errors.nombreempresa = 'Ingrese el nombre de la empresa';
      }
      if (!nombrecontacto || nombrecontacto.trim().length < 2) {
        errors.nombrecontacto = 'Ingrese el nombre del contacto';
      }
    }

    if (!telefonocontacto || !phoneRegex.test(telefonocontacto)) {
      errors.telefonocontacto = 'Ingrese un teléfono válido (7-10 dígitos)';
    }

    if (!email || !emailRegex.test(email)) {
      errors.email = 'Ingrese un correo electrónico válido';
    }

    if (!message || message.trim().length < 10) {
      errors.message = 'El mensaje debe tener al menos 10 caracteres';
    }

    this.setState({ errors });
    return Object.keys(errors).length === 0;
  };

  sendEmail = (e) => {
    e.preventDefault();

    if (!this.validateForm()) {
      return;
    }

    this.setState({ isSubmitting: true });

    emailjs.sendForm('service_qfkyacx', 'template_0yz8q1e', e.target, '3yBrocKoF55x5oiCg')
      .then((result) => {
          this.setState({
            emailSent: true, 
            emailSentMessage: 'Correo enviado exitosamente!',
            isSubmitting: false,
            errors: {}
          });
          e.target.reset();
          setTimeout(() => this.setState({
            emailSent: false, 
            emailSentMessage: ''
          }), 5000);
      }, (error) => {
          this.setState({
            emailSent: true,
            emailSentMessage: 'Error al enviar el correo. Por favor intente de nuevo.',
            isSubmitting: false
          });
          setTimeout(() => this.setState({
            emailSent: false,
            emailSentMessage: ''
          }), 6000);
      });
  }

  render() {
    const { requestType, emailSent, emailSentMessage, errors, isSubmitting } = this.state;


    return (
      <>
        <section className="section" id="contact">
          <Container>
            <Row>
              <Col lg={12}>
                <div className="title-box text-center">
                  <h2 className="title-heading text-light mt-4">Solicite más información</h2>
                  <p className="text-white-50 mt-2" style={{ fontSize: '1rem' }}>
                    Un asesor se comunicará con usted a la brevedad.
                  </p>

                  <div className="services-header-separator" aria-hidden="true"></div>

                </div>
              </Col>
            </Row>
            <Row className="mt-1 pt-1">
              <Col lg={6}>
                <div className="mt-4 home-img text-center d-none d-lg-block">
                  <img 
                    src={Feature} 
                    className="img-contact img-fluid" 
                    alt="Imagen de trabajo en alturas"
                    style={{
                      maxWidth: '100%',
                      height: 'auto',
                      objectFit: 'cover'
                    }}
                  />
                </div>
              </Col>
              <Col lg={6}>
                <div className="custom-form mt-4">
                  <div id="form-status"></div>
                  <Form method="post" name="contact-form" id="contact-form" onSubmit={this.sendEmail} noValidate>
                    <Row>
                      <Col lg={12}>
                        <FormGroup className="mt-3">
                          <Label className="contact-lable" htmlFor="requestType">Tipo de solicitud</Label>
                          <Input
                            type="select"
                            name="requestType"
                            id="requestType"
                            className="form-control"
                            value={requestType}
                            onChange={this.handleRequestTypeChange}
                          >
                            <option value="empresa">Empresa</option>
                            <option value="personal">Personal</option>
                          </Input>
                        </FormGroup>
                      </Col>
                    </Row>
                    {requestType === 'empresa' && (
                      <>
                        <Row>
                          <Col lg={12}>
                            <FormGroup className="mt-3">
                              <Label className="contact-lable">Nombre de la empresa</Label>
                              <Input name="nombreempresa" 
                                id="nombreempresa" 
                                className={`form-control ${errors.nombreempresa ? 'is-invalid' : ''}`}
                                type="text" 
                                placeholder="Ingresa el nombre de la empresa"/>
                              {errors.nombreempresa && <FormFeedback>{errors.nombreempresa}</FormFeedback>}
                            </FormGroup>
                          </Col>
                        </Row>
                        <Row>
                          <Col lg={12}>
                            <FormGroup className="mt-3">
                              <Label className="contact-lable">Nombre completo de la persona contacto</Label>
                              <Input name="nombrecontacto" id="nombrecontacto" className={`form-control ${errors.nombrecontacto ? 'is-invalid' : ''}`} type="text"
                              placeholder="Ingrese nombre de la persona contacto" />
                              {errors.nombrecontacto && <FormFeedback>{errors.nombrecontacto}</FormFeedback>}
                            </FormGroup>
                          </Col>
                        </Row>
                      </>
                    )}

                    {requestType === 'personal' && (
                      <>
                        <Row>
                          <Col lg={6}>
                            <FormGroup className="mt-3">
                              <Label className="contact-lable">Nombre</Label>
                              <Input name="nombrepersona" 
                              id="nombrepersona" 
                                className={`form-control ${errors.nombrepersona ? 'is-invalid' : ''}`} 
                                type="text" 
                                placeholder="Nombre de la persona contacto"
                                maxLength={40}/>
                              {errors.nombrepersona && <FormFeedback>{errors.nombrepersona}</FormFeedback>}
                            </FormGroup>
                          </Col>
                          <Col lg={6}>
                            <FormGroup className="mt-3">
                              <Label className="contact-lable">Apellido</Label>
                              <Input name="apellido" id="apellido" className={`form-control ${errors.apellido ? 'is-invalid' : ''}`} type="text" maxLength={40}
                              placeholder="Ingrese el apellido"/>
                              {errors.apellido && <FormFeedback>{errors.apellido}</FormFeedback>}
                            </FormGroup>
                          </Col>
                        </Row>
                      </>
                    )}

                    <Row>
                      <Col lg={12}>
                        <FormGroup className="mt-3">
                          <Label className="contact-lable">Teléfono de contacto</Label>
                          <Input name="telefonocontacto" 
                          id="telefonocontacto" className={`form-control ${errors.telefonocontacto ? 'is-invalid' : ''}`} type="tel" maxLength={10} 
                          placeholder="Ingrese el numero del telefono de contacto"/>
                          {errors.telefonocontacto && <FormFeedback>{errors.telefonocontacto}</FormFeedback>}
                        </FormGroup>
                      </Col>
                    </Row>
                    <Row>
                      <Col lg={12}>
                        <FormGroup className="mt-3">
                          <Label className="contact-lable">Correo electrónico</Label>
                          <Input name="email" id="email" className={`form-control ${errors.email ? 'is-invalid' : ''}`} type="email" maxLength={80} required
                          placeholder="Ingrese el email"/>
                          {errors.email && <FormFeedback>{errors.email}</FormFeedback>}
                        </FormGroup>
                      </Col>
                    </Row>
                    <Row>
                      <Col lg={12}>
                        <FormGroup className="mt-3">
                          <Label className="contact-lable">Mensaje</Label>
                          <Input type="textarea" name="message" id="mensajecontacto" rows="5" className={`form-control ${errors.message ? 'is-invalid' : ''}`}
                          placeholder="ingrese el mensaje" />
                          {errors.message && <FormFeedback>{errors.message}</FormFeedback>}
                        </FormGroup>
                      </Col>
                    </Row>
                    <Row>
                      <Col lg={12} className="mt-3 text-right">
                        <button 
                          type="submit" 
                          className="submitBnt btn btn-primary btn-round" 
                          disabled={isSubmitting}
                          style={{ width: 'auto', color: '#fff' }}
                        >
                          {isSubmitting ? 'Enviando...' : 'Solicitar información'}
                        </button>
                        <div id="simple-msg">
                          {emailSent && <p className={emailSentMessage.includes('Error') ? 'text-danger' : 'text-success'}>{emailSentMessage}</p>}
                        </div>
                        
                      </Col>
                    </Row>
                  </Form>
                </div>
              </Col>
            </Row>
          </Container>
        </section>
      </>
    );
  }
}
