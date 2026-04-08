import React from 'react';
// import HomeUrl from '../assets/images/home-border.png';

const SectionTitle = ({ title, subtitle, light }) => {

  const titleClass = light ? "title-heading mt-4" : "title-heading text-light mt-4";


  return (

    <div className="title-box text-center">
      <h3 className={titleClass}>{title}</h3>
      <p className="text-muted f-17 mt-3">{subtitle}</p>
      {/* 
            <img src={HomeUrl} height="15" className="mt-3" alt="" />
*/}
    </div>

  );
};

export default SectionTitle;