import React from "react";
import { Route, Routes } from "react-router-dom";

// Importar páginas
import Home from "./pages/Home";
import Services from "./pages/Services";

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/servicios-seguridad-industrial" element={<Services />} />
    </Routes>
  );
};

export default AppRoutes;
