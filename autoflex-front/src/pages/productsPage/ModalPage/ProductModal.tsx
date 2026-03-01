import { useState } from "react";
import ModalBase from "../../../components/modal/modalBase/ModalBase";
import { createProduct } from "../../../services/productsService";
import { toast } from "react-toastify";

interface Props {
  isOpen: boolean;
  onClose: () => void;
  onSuccess: () => void;
}

function ProductModal({
  isOpen,
  onClose,
  onSuccess,
}: Props) {
  const [name, setName] = useState("");
  const [price, setPrice] = useState<number>(0);
  const [loading, setLoading] = useState(false);

  const handleCreate = async () => {
    if (!name.trim() || price <= 0) {
      toast.error("Preencha corretamente");
      return;
    }

    try {
      setLoading(true);
      await createProduct({ name, price });

      toast.success("Produto criado!");
      onSuccess();
      onClose();
      setName("");
      setPrice(0);
    } catch {
      toast.error("Erro ao criar");
    } finally {
      setLoading(false);
    }
  };

  return (
    <ModalBase
      title="Criar Produto"
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
        <label>Preço</label>
        <input
          type="number"
          value={price}
          onChange={(e) => setPrice(Number(e.target.value))}
        />
      </div>
    </ModalBase>
  );
}

export default ProductModal;