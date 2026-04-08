import React, { Component } from "react";
import { Row, Col } from 'reactstrap';

class Footer_link extends Component {
  render() {
    return (
      // Footer Link start
      <Row>
        <Col lg={12}>
          <div className="text-center">
            <a
              href="https://www.takesolut.com/"
              target="_blank"
              rel="noopener noreferrer"
              className="brillo"
              aria-label="Sitio web de TakeSolut"
            >
              <span className="text-white mb-0">
                {(new Date().getFullYear())} © Develop by TakeSolut
              </span>
            </a>
          </div>
        </Col>
      </Row>
    );
  }
}

export default Footer_link;
