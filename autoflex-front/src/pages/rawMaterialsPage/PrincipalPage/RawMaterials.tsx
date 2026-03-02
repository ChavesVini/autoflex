import Button from "../../../components/ui/button/Button";
import SearchInput from "../../../components/ui/searchInput/SearchInput";
import { Table } from "../../../components/ui/table/Table";
import "./RawMaterials.css";
import { toast } from 'react-toastify';

import { useEffect, useState } from "react";
import { deleteRawMaterial, getAllRawMaterials, updateRawMaterial, type RawMaterial } from "../../../services/rawMaterialsService";
import TrashIndicator from "../../../components/ui/trashIndicator/TrashIndicator";
import PencilIndicator from "../../../components/ui/pencilIndicator/PencilIndicator";
import { useNavigate } from "react-router-dom";
import { IconContext } from "react-icons";
import { FaCheck } from "react-icons/fa";
import { ImCross } from "react-icons/im";
import PlusIndicator from "../../../components/ui/plusIndicator/plusIndication";
import RawMaterialsModal from "../ModalPage/RawMaterialsModal";

function RawMaterials() {  
  const [materials, setMaterials] = useState<RawMaterial[]>([]);
  const [count, setCount] = useState<number>(0);
  const [editingId, setEditingId] = useState<number | null>(null);
  const [editValues, setEditValues] = useState<Omit<RawMaterial, "id">>({
    name: "",
    quantity: 0,
  });
  const [search, setSearch] = useState("");
  const [page, setPage] = useState<number>(0);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [appliedSearch, setAppliedSearch] = useState("");

  const size = 5;

  const navigate = useNavigate();

  const totalPages = Math.ceil(count / size);

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
    toast.success("Raw material saved successfully!");
  }

  function handleCancel() {
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
    const hasChanges = (editValues.name !== material.name) || (editValues.quantity !== material.quantity);
  
    return (
      <>
        {isEditing ? (
          <>
            <Button
              title="Save"
              onClick={() => handleSave(material.id)}
              backgroundColor="#C8E6C9"
              color="white"
              disabled={!hasChanges}
            >
              <IconContext.Provider
                value={{size: '20px' }}
              >
              <FaCheck className="check-icon"/>
              </IconContext.Provider>
            </Button>

            <Button
              title="Cancel"
              onClick={handleCancel}
              backgroundColor="#FEE2E2"
              color="white"
            >
              <IconContext.Provider
                value={{size: '20px' }}
              >
              <ImCross className="cancel-icon"/>
              </IconContext.Provider>
            </Button>
          </>
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

        {!isEditing && (
          <Button
            title="Delete"
            onClick={() => handleDelete(material.id)}
            backgroundColor="#FEE2E2"
            color="white"
          >
            <TrashIndicator />
          </Button>
        )}
      </>
    );
  }

  const tableData = materials.map((material) => ({
    ...material,
    name: renderName(material),
    quantity: renderQuantity(material),
    actions: renderActions(material),
  }));
  
  async function loadData(searchTerm?: string) {
    const data = await getAllRawMaterials(page, size, searchTerm);
    setMaterials(data.content);
    setCount(data.totalElements);
  }

  useEffect(() => {
    loadData(appliedSearch);
  }, [page]);

  return ( 
    <div className="container-product-area">
      <div className="side-bar">
        <Button title="Products" onClick={() => navigate("/")} backgroundColor="#1e293b" color="white" />
        <Button title="Raw Materials" backgroundColor="#1e293b" color="white" disabled={true} />
      </div>
      <div className="product-area">
        <h1>Raw Materials</h1>
        <div className="actions-header">
        <SearchInput
          value={search}
          onChange={setSearch}
          onSearch={() => {
            setAppliedSearch(search);
            setPage(0);
            loadData(search);
          }}
        />

          <div className="button-create">
            <Button
              onClick={() => setIsModalOpen(true)} backgroundColor="#1e293b" color="white">
              <PlusIndicator />{ "Create New Raw Material"}
            </Button>
          </div>

        </div>
        <Table
          columns={[
            { header: "Product", accessor: "name" },
            { header: "Quantity", accessor: "quantity" },
            { header: "Actions", accessor: "actions" },
          ]}
          data={tableData}
        />
        <p className="count-text"> Showing {Math.min(page * size + materials.length, count)} of {count} raw materials. </p>
        <div className="pagination">
          <Button
            disabled={page === 0}
            onClick={() => setPage(prev => prev - 1)} backgroundColor="#1e293b" color="white">
            {"<"}
          </Button>

          {Array.from({ length: totalPages }, (_, index) => (
            <Button
              key={index}
              onClick={() => setPage(index)}
              backgroundColor="#1e293b"
              color="white"
              disabled={page === index}
            >
              {index + 1}
            </Button>
          ))}

          <Button
            disabled={page === totalPages - 1}
            onClick={() => setPage(prev => prev + 1)} backgroundColor="#1e293b" color="white">
            {">"}
          </Button>
          
          <RawMaterialsModal
            materials={materials}
            isOpen={isModalOpen}
            onClose={() => setIsModalOpen(false)}
            onSuccess={() => loadData()}
          />
        </div>
      </div>
    </div>
  );
}

export default RawMaterials;