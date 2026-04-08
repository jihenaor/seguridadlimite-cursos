import React from 'react';

// Definir dimensiones estándar para los logos
const LOGO_DIMENSIONS = {
  desktop: {
    width: 180,
    height: 120
  },
  mobile: {
    width: 100,
    height: 80
  }
};

const ClientLogo = ({ src, alt }) => {
  const [isMobile, setIsMobile] = React.useState(window.innerWidth <= 768);

  React.useEffect(() => {
    const handleResize = () => {
      setIsMobile(window.innerWidth <= 768);
    };

    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  const dimensions = isMobile ? LOGO_DIMENSIONS.mobile : LOGO_DIMENSIONS.desktop;

  const containerStyle = {
    borderRadius: '25%',
    border: isMobile ? '3px solid white' : '5px solid white',
    overflow: 'hidden',
    width: 'fit-content',
    margin: 'auto',
    backgroundColor: 'white',
    padding: isMobile ? '8px' : '15px',
    boxShadow: isMobile ? '0 2px 4px rgba(0,0,0,0.1)' : '0 4px 8px rgba(0,0,0,0.1)',
    transition: 'transform 0.3s ease-in-out'
  };

  return (
    <div className={`client-images ${isMobile ? 'mt-2' : 'mt-4'}`} style={containerStyle}>
      <img 
        src={src} 
        alt={alt || "Logo de cliente"} 
        className="mx-auto d-block" 
        width={dimensions.width}
        height={dimensions.height}
        style={{
          maxWidth: '100%',
          height: 'auto',
          objectFit: 'contain',
          margin: '0 auto',
          display: 'block',
          transition: 'transform 0.3s ease'
        }}
        onMouseOver={(e) => {
          if (!isMobile) {
            e.currentTarget.style.transform = 'scale(1.05)';
            e.currentTarget.parentElement.style.transform = 'scale(1.02)';
          }
        }}
        onMouseOut={(e) => {
          if (!isMobile) {
            e.currentTarget.style.transform = 'scale(1)';
            e.currentTarget.parentElement.style.transform = 'scale(1)';
          }
        }}
      />
    </div>
  );
};

export default ClientLogo;
