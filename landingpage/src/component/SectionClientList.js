import React, { useState, useEffect } from 'react';
import { Col, Row } from "reactstrap";
import ClientLogo from './ClientLogo';

import Client1 from '../assets/images/clients/logo-bomberos-150x150.webp';
import Client2 from '../assets/images/clients/logo-consumar-150x150.webp';
import Client3 from '../assets/images/clients/logo-decameron-panaca-150x150.webp';
import Client4 from '../assets/images/clients/logo-defensa-civil-150x150.webp';
import Client5 from '../assets/images/clients/logo-edeq-150x150.webp';
import Client6 from '../assets/images/clients/logo-meals-150x150.webp';
import Client7 from '../assets/images/clients/logo-panaca-150x150.webp';
import Client8 from '../assets/images/clients/logo-parque-nacional-150x150.webp';

const clientLogos = [
  { src: Client1, alt: "Bomberos" },
  { src: Client2, alt: "Consumar" },
  { src: Client3, alt: "Decameron Panaca" },
  { src: Client4, alt: "Defensa Civil" },
  { src: Client5, alt: "Edeq" },
  { src: Client6, alt: "Meals" },
  { src: Client7, alt: "Panaca" },
  { src: Client8, alt: "Parque Nacional" }
];

const chunkArray = (arr, size) => {
  return Array.from({ length: Math.ceil(arr.length / size) }, (v, i) =>
    arr.slice(i * size, i * size + size)
  );
};

const SectionClienteList = () => {
  const [isMobile, setIsMobile] = useState(window.innerWidth <= 768);

  useEffect(() => {
    const handleResize = () => {
      setIsMobile(window.innerWidth <= 768);
    };

    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  // Usar grupos diferentes según el dispositivo
  const logoGroups = isMobile ? 
    chunkArray(clientLogos, 2) : // 2 logos por fila en móvil
    chunkArray(clientLogos, 3);  // 3 logos por fila en PC

  return (
    <div className="clients-container" style={{ 
      padding: isMobile ? '10px 0' : '20px 0'
    }}>
      {logoGroups.map((group, groupIndex) => (
        <Row 
          className="justify-content-center align-items-center" 
          key={groupIndex}
          style={{
            marginLeft: isMobile ? '-8px' : '-15px',
            marginRight: isMobile ? '-8px' : '-15px',
            marginBottom: isMobile ? '8px' : '24px'
          }}
        >
          {group.map((client, index) => (
            <Col 
              xs={6} 
              md={4} 
              lg={4} 
              key={index} 
              className={isMobile ? 'mb-2' : 'mb-4'}
              style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                padding: isMobile ? '0 4px' : '0 15px'
              }}
            >
              <ClientLogo src={client.src} alt={client.alt} />
            </Col>
          ))}
        </Row>
      ))}
    </div>
  );
};

export default SectionClienteList;
