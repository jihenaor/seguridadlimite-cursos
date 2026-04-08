import React from "react";

const ServicesHeader = ({ title }) => {
  return (
    <div className="title-box text-center">
      <h3 className="services-header-title">{title}</h3>
      <div className="services-header-separator" aria-hidden="true"></div>
    </div>
  );
};

export default ServicesHeader; 