import { useEffect, useState } from "react";
import { Button } from "../../components/ui/button/Button";
import "./EditProductPage.css"; 
import { toast } from 'react-toastify';
import { TrashIndicator } from "../../components/ui/trashIndicator/TrashIndicator";
import { getAllRawMaterials } from "../../services/rawMaterialsService";
import { getMaterialsByProduct, syncProductMaterials } from "../../services/productsRawMaterialsService";
import { FaCheck } from "react-icons/fa";
import { IconContext } from "react-icons";
import { ImCross } from "react-icons/im";
import Table from "../../components/ui/table/Table";

interface EditProductPageProps {
  product: any;
  closeModal: () => void;
  onSave: (updatedProduct: any) => void;
}

function EditProductPage({ product, closeModal, onSave }: EditProductPageProps) {
  const [name, setName] = useState(product.name);
  const [price, setPrice] = useState(product.price);
  const [rawMaterials, setRawMaterials] = useState<any[]>([]);
  
  const [isAdding, setIsAdding] = useState(false);
  const [newMaterial, setNewMaterial] = useState({ rawMaterialId: "", quantity: 1 });
  const [allAvailableMaterials, setAllAvailableMaterials] = useState<any[]>([]);

  useEffect(() => {
    const loadCurrentMaterials = async () => {
      try {
        const data = await getMaterialsByProduct(product.id);
        setRawMaterials(data);
      } catch (error) {
        toast.error("Error loading product materials");
      }
    };
    loadCurrentMaterials();
  }, [product.id]);

  useEffect(() => {
    const fetchAll = async () => {
      const data = await getAllRawMaterials(0, 500); 
      setAllAvailableMaterials(data.content);
    };
    fetchAll();
  }, []);

  const availableMaterials = allAvailableMaterials.filter(
    m => !rawMaterials.some(rel => rel.rawMaterialId === m.id)
  );

  const confirmAddMaterial = () => {
    if (!newMaterial.rawMaterialId) {
      toast.error("Select a material");
      return;
    }

    const selected = allAvailableMaterials.find(m => m.id.toString() === newMaterial.rawMaterialId);

      if (selected) {
        const newItem = {
          rawMaterialId: selected.id,
          rawMaterialName: selected.name,
          quantity: newMaterial.quantity
        };

        setRawMaterials([...rawMaterials, newItem]); 
        
        setIsAdding(false);
        setNewMaterial({ rawMaterialId: "", quantity: 1 });
      }
    };

  const handleSave = async () => {
    if (!name) {
      toast.error("Name is required");
      return;
    }

    try {
      await syncProductMaterials(product.id, rawMaterials);
      
      toast.success("Product updated successfully!");
      onSave({ ...product, name, price }); 
      closeModal();
    } catch (error) {
      toast.error("Failed to save materials");
    }
  };

  const removeMaterial = (rawMaterialId: number) => {
    setRawMaterials(rawMaterials.filter(rm => rm.rawMaterialId !== rawMaterialId));
  };

  const rawMaterialsTableData = [
    ...rawMaterials.map((rm) => ({
      rawMaterial: rm.rawMaterialName,
      quantity: rm.quantity,
      actions: (
        <Button
          onClick={() => removeMaterial(rm.rawMaterialId)}
          backgroundColor="#FEE2E2"
          color="white"
        >
          <TrashIndicator />
        </Button>
      ),
    })),

    ...(isAdding
      ? [
          {
            rawMaterial: (
              <select
                className="modal-input"
                value={newMaterial.rawMaterialId}
                style={{ width: "100%" }}
                onChange={(e) =>
                  setNewMaterial({
                    ...newMaterial,
                    rawMaterialId: e.target.value,
                  })
                }
              >
                <option value="">Select</option>
                {availableMaterials.map((m) => (
                  <option key={m.id} value={m.id}>
                    {m.name}
                  </option>
                ))}
              </select>
            ),
            quantity: (
              <input
                type="number"
                className="modal-input"
                style={{ width: "80px" }}
                value={newMaterial.quantity}
                onChange={(e) =>
                  setNewMaterial({
                    ...newMaterial,
                    quantity: Number(e.target.value),
                  })
                }
              />
            ),
            actions: (
              <div className="buttons-edit-raw-materials">
                <Button
                  title="Save"
                  onClick={confirmAddMaterial}
                  backgroundColor="#e2fee8"
                  color="white"
                >
                  <IconContext.Provider value={{ size: "20px" }}>
                    <FaCheck className="check-indicator-icon" />
                  </IconContext.Provider>
                </Button>

                <Button
                  title="Cancel"
                  onClick={() => setIsAdding(false)}
                  backgroundColor="#FEE2E2"
                  color="white"
                >
                  <IconContext.Provider value={{ size: "20px" }}>
                    <ImCross className="cancel-indicator-icon" />
                  </IconContext.Provider>
                </Button>
              </div>
            )
          },
        ]
      : []),
  ];

  return (
    <div className="overlay">
      <div className="edit-modal">
        <div className="modal-header">
          <h2>Edit Product: {product.name}</h2>
          <button className="close-x" onClick={closeModal}>✕</button>
        </div>

        <div className="modal-body">
          <div className="form-group">
            <label>Product Name</label>
            <input 
              type="text" 
              value={name} 
              onChange={(e) => setName(e.target.value)} 
              className="modal-input"
            />
          </div>

          <div className="form-group">
            <label>Price</label>
            <input 
              type="number" 
              value={price} 
              onChange={(e) => setPrice(e.target.value)} 
              className="modal-input"
            />
          </div>

          <div className="raw-materials-section">
            <h3>Raw Materials</h3>

            <Table
              columns={[
                { header: "Raw Material", accessor: "rawMaterial" },
                { header: "Quantity", accessor: "quantity" },
                { header: "Actions", accessor: "actions" }
              ]}
            data={rawMaterialsTableData}
            />

            {!isAdding && (
              <button className="add-material-btn" onClick={() => setIsAdding(true)}>
                + Add Raw Material
              </button>
            )}
          </div>
        </div>

        <div className="modal-footer">
          <div className="actions-buttons">
            <Button onClick={closeModal} backgroundColor="#f1f5f9" color="#475569"> Cancel </Button>
            <Button onClick={handleSave} backgroundColor="#3b82f6" color="white"> Save Changes </Button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default EditProductPage;