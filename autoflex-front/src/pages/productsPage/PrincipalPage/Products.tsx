import Button from "../../../components/ui/button/Button";
import SearchInput from "../../../components/ui/searchInput/SearchInput";
import { Table } from "../../../components/ui/table/Table";
import "./Products.css";
import { toast } from 'react-toastify';

import { useEffect, useState } from "react";
import { deleteProduct, getAllProducts, updateProduct, type Product} from "../../../services/productsService";
import { TrashIndicator } from "../../../components/ui/trashIndicator/TrashIndicator";
import { PencilIndicator } from "../../../components/ui/pencilIndicator/PencilIndicator";
import { getProduction } from "../../../services/productsRawMaterialsService";
import YesIndicator from "../../../components/ui/yesIndicator/YesIndicator";
import NoIndicator from "../../../components/ui/noIndicator/NoIndicator";
import { useNavigate } from "react-router";
import PlusIndicator from "../../../components/ui/plusIndicator/plusIndication";

import ProductModal from "../ModalPage/ProductModal";
import EditProductPage from "../../editProductPage/EditProductPage";

function Products() {  
  const [products, setProducts] = useState<Product[]>([]);
  const [count, setCount] = useState<number>(0);
  const [editingId, setEditingId] = useState<number | null>(null);
  const [production, setProduction] = useState<Product[]>([]);
  const [searchProduction, setSearchProduction] = useState("");
  const [countProduction, setCountProduction] = useState<number>(0);
  const [pageProducts, setPageProducts] = useState<number>(0);
  const [pageProduction, setPageProduction] = useState<number>(0);
  const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [appliedSearchProducts, setAppliedSearchProducts] = useState("");
  const [appliedSearchProduction, setAppliedSearchProduction] = useState("");
  const size = 5;

  const navigate = useNavigate();
  
  const totalPagesProducts = Math.ceil(count / size);
  const totalPagesProduction = Math.ceil(countProduction / size);
  
  async function loadData(searchText?: string, where?: "products" | "production") {
    if (!where) {
      const [data, dataProduction] = await Promise.all([
        getAllProducts(pageProducts, size, searchText),
        getProduction(pageProduction, size, searchText)
      ]);

      setProducts(data.content);
      setCount(data.totalElements);

      setProduction(dataProduction.content);
      setCountProduction(dataProduction.totalElements);

      return;
    }
    
    if (where === "products") {
      const data = await getAllProducts(pageProducts, size, searchText);
      setProducts(data.content);
      setCount(data.totalElements);
    }
    
    if (where === "production") {
      const dataProduction = await getProduction(pageProduction, size, searchText);
      setProduction(dataProduction.content);
      setCountProduction(dataProduction.totalElements);
    }
  }

  async function handleDelete(id: number) {
    try {
      await deleteProduct(id);
      
      setEditingId(null);

      await loadData();

    } catch (error: any) {

      console.log(error.response.status);

      if (error.response?.status === 409) {
        toast.error("This raw material is associated with products and cannot be deleted.");
        return;
      }

      if (error.response?.status === 404) {
        toast.error("Raw material not found.");
        return;
      }

      toast.error("Unexpected error while deleting raw material.");
    }
  }
  
  async function handleSave(id: number, updatedValues: Omit<Product, "id">) {
    try {
      const updated = await updateProduct(id, updatedValues);
    
      setProducts((prev) =>
        prev.map((item) => (item.id === id ? updated : item))
      );
    
      setSelectedProduct(null);
      await loadData();
    } catch (error) {
      toast.error("Error updating product");
    }
  }

  function renderActions(product: Product) {
    return (
      <div>
        <Button
          title="Edit"
          onClick={() => setSelectedProduct(product)}
          backgroundColor="#DBEAFE"
          color="white"
        >
          <PencilIndicator />
        </Button>

        <Button
          title="Delete"
          onClick={() => handleDelete(product.id)}
          backgroundColor="#FEE2E2"
          color="white"
        >
          <TrashIndicator />
        </Button>
      </div>
    );
  }

  const tableData = products.map((product) => ({
    ...product,
    name: product.name,
    price: product.price,
    actions: renderActions(product),
  }));


  useEffect(() => {
    loadData(appliedSearchProducts, "products");
  }, [pageProducts]);

  useEffect(() => {
    loadData(appliedSearchProduction, "production");
  }, [pageProduction]);

  return ( 
    <div className="container-product-area">
      <div className="side-bar">
        <Button title="Products" backgroundColor="#1e293b" color="white" disabled={true} />
        <Button title="Raw Materials" onClick={() => navigate("/raw-materials")} backgroundColor="#1e293b" color="white" />
      </div>
      <div className="product-area">
        <h1>Products</h1>
        <div className="actions-header">
          <SearchInput
            value={searchTerm}
            onChange={setSearchTerm}
            onSearch={() => {
              setAppliedSearchProducts(searchTerm);
              setPageProducts(0);
              loadData(searchTerm, "products");
            }}
          />
          <div className="button-create">
            <Button
              onClick={() => setIsModalOpen(true)} backgroundColor="#1e293b" color="white">
              <PlusIndicator /> { "Create New Product" }
            </Button>
          </div>
        </div>
        <Table   
          columns={[
            { header: "Product", accessor: "name" },
            { header: "Price", accessor: "price" },
            { header: "Actions", accessor: "actions" },
          ]}
          data={tableData}
        />

        <p className="count-text"> Showing {Math.min(pageProducts * size + tableData.length, count)} of {count} products. </p>

        <div className="pagination">
          <Button
            disabled={pageProducts === 0}
            onClick={() => setPageProducts(prev => prev - 1)} backgroundColor="#1e293b" color="white">
            {"<"}
          </Button>

          {Array.from({ length: totalPagesProducts }, (_, index) => (
            <Button
              key={index}
              onClick={() => setPageProducts(index)}
              backgroundColor="#1e293b"
              color="white"
              disabled={pageProducts === index}
            >
              {index + 1}
            </Button>
          ))}

          <Button
            disabled={pageProducts === totalPagesProducts - 1}
            onClick={() => setPageProducts(prev => prev + 1)} backgroundColor="#1e293b" color="white">
            {">"}
          </Button>
        </div>

        <h1>Production Possibilities</h1>
          <div className="actions-header">
            <SearchInput
              value={searchProduction}
              onChange={setSearchProduction}
              onSearch={() => {
                setAppliedSearchProduction(searchProduction);
                setPageProduction(0);
                loadData(searchProduction, "production");
              }}
            />
          </div>
          <Table   
            columns={[
              { header: "Product", accessor: "name" },
              { header: "Can Produce", accessor: "canProduce", cell: (value: boolean) => value ? <YesIndicator /> : <NoIndicator /> },
              { header: "Quantity Possible", accessor: "quantityPossible" },
            ]}
            data={production}
          />
        <p className="count-text"> Showing {Math.min(pageProduction * size + production.length, count)} of {count} production possibilities.</p>

        <div className="pagination">
          <Button
            disabled={pageProduction === 0}
            onClick={() => setPageProduction(prev => prev - 1)} backgroundColor="#1e293b" color="white">
            {"<"}
          </Button>

          {Array.from({ length: totalPagesProduction }, (_, index) => (
            <Button
              key={index}
              onClick={() => setPageProduction(index)}
              backgroundColor="#1e293b"
              color="white"
              disabled={pageProduction === index}
            >
              {index + 1}
            </Button>
          ))}

          <Button
            disabled={pageProduction === totalPagesProduction - 1}
            onClick={() => setPageProduction(prev => prev + 1)} backgroundColor="#1e293b" color="white">
            {">"}
          </Button>

          <ProductModal
            isOpen={isModalOpen}
            onClose={() => setIsModalOpen(false)}
            onSuccess={() => loadData()}
          />

          {selectedProduct && (
            <EditProductPage
              product={selectedProduct}
              closeModal={() => setSelectedProduct(null)}
              onSave={(updatedValues) => {
                handleSave(selectedProduct.id, updatedValues);
              }}
            />
          )}
        </div>
      </div>
    </div>
  );
}

export default Products;