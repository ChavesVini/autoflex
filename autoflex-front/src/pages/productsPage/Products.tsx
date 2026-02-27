import Button from "../../components/ui/button/Button";
import SearchInput from "../../components/ui/searchInput/SearchInput";
import { Table } from "../../components/ui/table/Table";
import "./Products.css";
import { toast } from 'react-toastify';

import { useEffect, useState } from "react";
import { deleteProduct, getAllProducts, updateProduct, type Products } from "../../services/productsService";
import { TrashIndicator } from "../../components/ui/trashIndicator/TrashIndicator";
import { PencilIndicator } from "../../components/ui/pencilIndicator/PencilIndicator";
import { getProduction } from "../../services/productsRawMaterialsService";
import YesIndicator from "../../components/ui/yesIndicator/YesIndicator";
import NoIndicator from "../../components/ui/noIndicator/NoIndicator";

function Products() {  
  const [products, setProducts] = useState<Products[]>([]);
  const [count, setCount] = useState<number>(0);
  const [editingId, setEditingId] = useState<number | null>(null);
  const [editValues, setEditValues] = useState<Omit<Products, "id">>({
    name: "",
    price: 0
  });
  const [production, setProduction] = useState<Products[]>([]);
  const [search, setSearch] = useState("");
  const [searchProduction, setSearchProduction] = useState("");
  const [countProduction, setCountProduction] = useState<number>(0);  

  async function loadData(searchText?: string, where?: "products" | "production") {
    if (!where) {
      const [productsData, productionData] = await Promise.all([
        getAllProducts(0, 5, searchText),
        getProduction(0, 5, searchText)
      ]);

      setProducts(productsData.content);
      setCount(productsData.totalElements);

      setProduction(productionData.content);
      setCountProduction(productionData.totalElements);

      return;
    }
    
    if (where === "products") {
      const data = await getAllProducts(0, 5, searchText);
      setProducts(data.content);
      setCount(data.totalElements);
    }
    
    if (where === "production") {
      const dataProduction = await getProduction(0, 5, searchText);
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

  function startEditing(material: Products) {
    setEditingId(material.id);
    setEditValues({
      name: material.name,
      price: material.price,
    });
  }
  
  async function handleSave(id: number) {
    const updated = await updateProduct(id, editValues);
  
    setProducts((prev) =>
      prev.map((item) =>
        item.id === id ? updated : item
      )
    );
  
    setEditingId(null);
  }
  
  function renderName(material: Products) {
    if (editingId !== material.id) return material.name;
  
    return (
      <input
        className="edit-input"
        value={editValues.name}
        onChange={(e) =>
          setEditValues({ ...editValues, name: e.target.value })
        }
      />
    );
  }
  
  function renderPrice(material: Products) {
    if (editingId !== material.id) return material.price;
  
    return (
      <input
        className="edit-input"
        type="number"
        value={editValues.price}
        onChange={(e) =>
          setEditValues({
            ...editValues,
            price: Number(e.target.value),
          })
        }
      />
    );
  }
  
  function renderActions(material: Products) {
    const isEditing = editingId === material.id;
  
    return (
      <>
      {isEditing ? (
        <Button
          title="Save"
          onClick={() => handleSave(material.id)}
          backgroundColor="#4CAF50"
          color="white"
        >
        ✔
        </Button>
        ) : (
        <Button
          title="Edit"
          onClick={() => startEditing(material)}
          backgroundColor="#DBEAFE"
          color="white"
        >
          <PencilIndicator />           
        </Button>
        )}  
        <Button
          title="Delete"
          onClick={() => handleDelete(material.id)}
          backgroundColor="#FEE2E2"
          color="white"
        >
          <TrashIndicator />
        </Button>
      </>
    );
  }
  
  const tableData = products.map((product) => ({
    ...product,
    name: renderName(product),
    price: renderPrice(product),
    actions: renderActions(product),
  }));

  useEffect(() => {
    loadData();
  }, []);

  return ( 
    <div className="container-product-area">
      <div className="side-bar">
        <Button title="Products" backgroundColor="#1e293b" color="white" disabled={true} />
        <Button title="Raw Materials" onClick={() => console.log("Raw Materials clicado!")} backgroundColor="#1e293b" color="white" />
      </div>
      <div className="product-area">
        <h1>Produtos</h1>
        <SearchInput
          value={search}
          onChange={setSearch}
          onSearch={() => loadData(search, "products")}
        />
        <Table   
          columns={[
            { header: "Product", accessor: "name" },
            { header: "Price", accessor: "price" },
            { header: "Actions", accessor: "actions" },
          ]}
          data={tableData}
        />

        <p>Showing {tableData.length} of {count} products.</p>

        <h1>Production Possibilities</h1>
          <SearchInput
            value={searchProduction}
            onChange={setSearchProduction}
            onSearch={() => loadData(searchProduction, "production")}
          />
          <Table   
            columns={[
              { header: "Product", accessor: "name" },
              { header: "Can Produce", accessor: "canProduce", cell: (value: boolean) => value ? <YesIndicator /> : <NoIndicator /> },
              { header: "Quantity Possible", accessor: "quantityPossible" },
            ]}
            data={production}
          />
        <p>Showing {production.length} of {countProduction} products.</p>
      </div>
    </div>
  );
}

export default Products;