import { useEffect, useState } from "react";
import { Button } from "../../components/ui/button/Button";
import "./DetailsProductPage.css"; 
import { toast } from 'react-toastify';
import { TrashIndicator } from "../../components/ui/trashIndicator/TrashIndicator";
import { getAllRawMaterials } from "../../services/rawMaterialsService";
import { getMaterialsByProduct, syncProductMaterials } from "../../services/productsRawMaterialsService";
import { FaCheck } from "react-icons/fa";
import { IconContext } from "react-icons";
import { ImCross } from "react-icons/im";

interface EditProductPageProps {
  product: any;
  closeModal: () => void;
  onSave: (updatedProduct: any) => void;
}

function EditProductPage({ product, closeModal, onSave }: EditProductPageProps) {
  const [name, setName] = useState(product.name);
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
      setAllAvailableMaterials(data.content || []);
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
      onSave({ ...product, name }); 
      closeModal();
    } catch (error) {
      toast.error("Failed to save materials");
    }
  };

  const removeMaterial = (rawMaterialId: number) => {
    setRawMaterials(rawMaterials.filter(rm => rm.rawMaterialId !== rawMaterialId));
  };

  return (
    <div className="overlay">
      <div className="modal edit-modal">
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

          <div className="raw-materials-section">
            <h3>Raw Materials</h3>
            <table className="inner-table">
              <thead>
                <tr>
                  <th>Raw Material</th>
                  <th>Quantity</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {rawMaterials.map((rm) => (
                  <tr key={rm.rawMaterialId}>
                    <td>{rm.rawMaterialName}</td>
                    <td>{rm.quantity}</td>
                    <td className="inner-actions">
                      <Button onClick={() => removeMaterial(rm.rawMaterialId)} backgroundColor={"#FEE2E2"} color={"white"}>
                        <TrashIndicator />
                      </Button>
                    </td>
                  </tr>
                ))}

                {isAdding && (
                  <tr className="adding-row">
                    <td>
                      <select 
                        className="modal-input"
                        value={newMaterial.rawMaterialId}
                        onChange={(e) => setNewMaterial({...newMaterial, rawMaterialId: e.target.value})}
                      >
                        <option value="">Select Material...</option>
                        {availableMaterials.map(m => (
                          <option key={m.id} value={m.id}>{m.name}</option>
                        ))}
                      </select>
                    </td>
                    <td>
                      <input 
                        type="number" 
                        className="modal-input" 
                        style={{ width: '80px' }}
                        value={newMaterial.quantity}
                        onChange={(e) => setNewMaterial({...newMaterial, quantity: Number(e.target.value)})}
                      />
                    </td>
                    <td className="inner-actions">

                      <Button
                        title="Save"
                        onClick={confirmAddMaterial}
                        backgroundColor="#e2fee8"
                        color="white"
                      >
                        <IconContext.Provider
                          value={{size: '20px' }}
                        >
                          <FaCheck className="check-indicator-icon"/>
                        </IconContext.Provider>     
                      </Button>

                      <Button
                        title="Cancel"
                        onClick={() => setIsAdding(false)}
                        backgroundColor="#FEE2E2"
                        color="white"
                      >
                        <IconContext.Provider
                          value={{size: '20px' }}
                        >
                          <ImCross className="cancel-indicator-icon"/>
                        </IconContext.Provider>   
                      </Button>
                    </td>
                  </tr>
                )}
              </tbody>
            </table>

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