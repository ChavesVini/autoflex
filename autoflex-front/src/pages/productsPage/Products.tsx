import Button from "../../components/ui/button/Button";
import SearchInput from "../../components/ui/searchInput/SearchInput";
import { Table } from "../../components/ui/table/Table";
import "./Products.css";

import { useEffect, useState } from "react";
import { getAllProducts, type Products } from "../../services/productsService";

function Products() {  
  const [products, setProducts] = useState<Products[]>([]);
  const [count, setCount] = useState<number>(0);

  async function loadData() {
    const data = await getAllProducts(0, 5);
    setProducts(data.content);
    setCount(data.totalElements);
  }

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
        <SearchInput></SearchInput>
        <Table   
          columns={[
            { header: "Product", accessor: "name" },
            { header: "Price", accessor: "price" },
            { header: "Actions", accessor: "actions" },
          ]}
          data={products}
        />
        <p>Showing {products.length} of {count} products.</p>

        <h1>Production Possibilities</h1>
        <SearchInput></SearchInput>
          <Table   
            columns={[
              { header: "Product", accessor: "name" },
              { header: "Can Produce", accessor: "canProduce" },
              { header: "Quantity Possible", accessor: "quantityPossible" },
            ]}
            data={products}
          />
        <p>Showing {products.length} of {count} products.</p>
      </div>
    </div>
  );
}

export default Products;