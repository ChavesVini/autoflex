import './App.css'
import { Slide, ToastContainer } from "react-toastify";
import RawMaterials from './pages/rawMaterialsPage/PrincipalPage/RawMaterials'
import Products from './pages/productsPage/PrincipalPage/Products';
import { StrictMode } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';

function App() {
  return (
    <>
      <ToastContainer 
        position="top-right"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable={false}
        pauseOnHover
        theme="dark"
        transition={Slide}
      />
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Products />} />
          <Route path="/raw-materials" element={<RawMaterials />} />
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
