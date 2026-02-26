import Button from "../../components/ui/button/Button";
import SearchInput from "../../components/ui/searchInput/SearchInput";
import { Table } from "../../components/ui/table/Table";
import "./RawMaterials.css";

import { useEffect, useState } from "react";
import { getAllRawMaterials, type RawMaterial } from "../../services/rawMaterialsService";
import TrashIndicator from "../../components/ui/trashIndicator/sadasdas";
import PencilIndicator from "../../components/ui/pencilIndicator/PencilIndicator";

function RawMaterials() {  
  const [materials, setMaterials] = useState<RawMaterial[]>([]);
  const [count, setCount] = useState<number>(0);

  async function loadData() {
    const data = await getAllRawMaterials(0, 5);
    console.log(data);
    setMaterials(data.content);
    setCount(data.totalElements);
  }

  async function handleUpdate(id: number) {
    console.log("Update", id);
  }

  useEffect(() => {
    loadData();
  }, []);

  return ( 
    <div className="container-product-area">
      <div className="side-bar">
        <Button title="Products" onClick={() => console.log("Products clicado!")} backgroundColor="#1e293b" color="white" />
        <Button title="Raw Materials" backgroundColor="#1e293b" color="white" disabled={true} />
      </div>
      <div className="product-area">
        <h1>Raw Materials</h1>
        <SearchInput></SearchInput>
        <Table
          columns={[
            { header: "Product", accessor: "name" },
            { header: "Quantity", accessor: "quantity" },
            { header: "Actions", accessor: "actions" },
          ]}
          data={materials}
        />
        <p>Showing {materials.length} of {count} products.</p>
        <Button title="Products" onClick={() => handleUpdate} backgroundColor="#DBEAFE" color="white"> <PencilIndicator/> </Button>
        <Button title="Raw Materials" backgroundColor="#FEE2E2" color="white"> <TrashIndicator/> </Button>
      </div>
    </div>
  );
}

export default RawMaterials;