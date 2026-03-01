import Button from "../../ui/button/Button";
import "./ModalBase.css";

interface ModalBaseProps {
  title: string;
  isOpen: boolean;
  onClose: () => void;
  onConfirm: () => void;
  confirmText?: string;
  loading?: boolean;
  children: React.ReactNode;
}

export default function ModalBase({
  title,
  isOpen,
  onClose,
  onConfirm,
  confirmText = "Salvar",
  loading = false,
  children,
}: ModalBaseProps) {
  if (!isOpen) return null;

  return (
    <div className="modal-overlay">
      <div className="modal-container">
        <h2>{title}</h2>

        <div className="modal-content">{children}</div>

        <div className="modal-actions">
          <Button
            onClick={onClose}
            disabled={loading}
            backgroundColor={"#9ca3af"}
            color={"white"}
          >
            Cancelar
          </Button>

          <Button
            onClick={onConfirm}
            disabled={loading}
            backgroundColor={"4caf50"}
            color={"white"}
          >
            {loading ? "Salvando..." : confirmText}
          </Button>
        </div>
      </div>
    </div>
  );
}