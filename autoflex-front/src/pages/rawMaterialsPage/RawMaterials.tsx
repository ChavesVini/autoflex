import Button from "../../components/ui/button/Button";
import SearchInput from "../../components/ui/searchInput/SearchInput";
import { Table } from "../../components/ui/table/Table";
import "./RawMaterials.css";
import { toast } from 'react-toastify';

import { useEffect, useState } from "react";
import { deleteRawMaterial, getAllRawMaterials, updateRawMaterial, type RawMaterial } from "../../services/rawMaterialsService";
import TrashIndicator from "../../components/ui/trashIndicator/TrashIndicator";
import PencilIndicator from "../../components/ui/pencilIndicator/PencilIndicator";

function RawMaterials() {  
  const [materials, setMaterials] = useState<RawMaterial[]>([]);
  const [count, setCount] = useState<number>(0);
  const [editingId, setEditingId] = useState<number | null>(null);
  const [editValues, setEditValues] = useState<Omit<RawMaterial, "id">>({
    name: "",
    quantity: 0,
  });

  async function handleDelete(id: number) {
    try {
      await deleteRawMaterial(id);
      
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

  function startEditing(material: RawMaterial) {
    setEditingId(material.id);
    setEditValues({
      name: material.name,
      quantity: material.quantity,
    });
  }

  async function handleSave(id: number) {
    const updated = await updateRawMaterial(id, editValues);

    setMaterials((prev) =>
      prev.map((item) =>
        item.id === id ? updated : item
      )
    );

    setEditingId(null);
  }


  function renderName(material: RawMaterial) {
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

  function renderQuantity(material: RawMaterial) {
    if (editingId !== material.id) return material.quantity;

    return (
      <input
        className="edit-input"
        type="number"
        value={editValues.quantity}
        onChange={(e) =>
          setEditValues({
            ...editValues,
            quantity: Number(e.target.value),
          })
        }
      />
    );
  }

  function renderActions(material: RawMaterial) {
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

  const tableData = materials.map((material) => ({
    ...material,
    name: renderName(material),
    quantity: renderQuantity(material),
    actions: renderActions(material),
  }));
  
  async function loadData() {
    const data = await getAllRawMaterials(0, 5);
    console.log(data);
    setMaterials(data.content);
    setCount(data.totalElements);
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
          data={tableData}
        />
        <p>Showing {materials.length} of {count} products.</p>
        <Button title="Products" backgroundColor="#DBEAFE" color="white"> <PencilIndicator/> </Button>
        <Button title="Raw Materials" backgroundColor="#FEE2E2" color="white"> <TrashIndicator/> </Button>
      </div>
    </div>
  );
}

export default RawMaterials;