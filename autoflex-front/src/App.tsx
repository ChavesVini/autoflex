import './App.css'
import { Slide, ToastContainer } from "react-toastify";
import RawMaterials from './pages/rawMaterialsPage/RawMaterials'
import Products from './pages/productsPage/Products';

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
    <Products
    />
  </>
  )
}

export default App
