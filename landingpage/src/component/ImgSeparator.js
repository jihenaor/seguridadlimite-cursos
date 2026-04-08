import React from "react";
import HomeUrl from "../assets/images/home-border.png";

const ImgSeparator = () => {
  return (
    <img 
      src={HomeUrl} 
      height="15" 
      width="200" 
      alt="Separador decorativo"
      loading="lazy" 
      style={{
        display: 'block',
        margin: '0 auto',
        maxWidth: '100%'
      }}
    />
  );
};

export default ImgSeparator;
