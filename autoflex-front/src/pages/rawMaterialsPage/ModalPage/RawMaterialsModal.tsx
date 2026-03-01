import { useState } from "react";
import ModalBase from "../../../components/modal/modalBase/ModalBase";
import { createRawMaterial } from "../../../services/rawMaterialsService";
import { toast } from "react-toastify";

function RawMaterialsModal({
  isOpen,
  onClose,
  onSuccess,
}: any) {
  const [name, setName] = useState("");
  const [quantity, setQuantity] = useState<number>(0);
  const [loading, setLoading] = useState(false);

  const handleCreate = async () => {
    if (!name.trim() || quantity <= 0) {
      toast.error("Please fill in all fields correctly!");
      return;
    }

    try {
      setLoading(true);
      await createRawMaterial({ name, quantity });

      toast.success("Raw Material created!");
      onSuccess();
      onClose();
      setName("");
      setQuantity(0);
      
    } catch {
      toast.error("Error when creating");
    } finally {
      setLoading(false);
    }
  };

  return (
    <ModalBase
      title="Criar Matéria-Prima"
      isOpen={isOpen}
      onClose={onClose}
      onConfirm={handleCreate}
      confirmText="Criar"
      loading={loading}
    >
      <div className="modal-field">
        <label>Nome</label>
        <input value={name} onChange={(e) => setName(e.target.value)} />
      </div>

      <div className="modal-field">
        <label>Quantidade</label>
        <input
          type="number"
          value={quantity}
          onChange={(e) => setQuantity(Number(e.target.value))}
        />
      </div>
    </ModalBase>
  );
}

export default RawMaterialsModal;